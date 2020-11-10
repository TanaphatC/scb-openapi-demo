package th.co.azay.domain.adapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import th.co.azay.domain.entity.Deeplink;
import th.co.azay.domain.entity.PaymentTrans;
import th.co.azay.model.*;
import th.co.azay.service.PaymentTransactionsService;

import java.util.*;

public class ScbApiAdapter implements ScbAdapter {

    private static final Logger logger = LogManager.getLogger(ScbApiAdapter.class);

    @Autowired
    @Qualifier("scbRestTemplate")
    RestTemplate scbRestTemplate;

    @Autowired
    PaymentTransactionsService paymentTransactionsService;

    @Override
    public AccessTokenApiResponse generateAccessToken() {
        AccessTokenApiResponse accessToken;
        try{
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("applicationKey","l79b60f8086f9145df9f8c53c74dd98808");
            map.add("applicationSecret","1f7610a4f1fc48649f4c9598f51d3a3b");
            map.add("authCode","");
            map.add("state","");
            map.add("codeChallenge","");

            String transactionId = getTransactionId();
            HttpHeaders headers = getDefaultHttpHeader(transactionId);
            logger.info("transactionId = {}", transactionId);

            HttpEntity<MultiValueMap<String,String>> payload = new HttpEntity<>(map, headers);

            String GENERATE_TOKEN_API = "/v1/oauth/token";
            String endpoint = this.getFullEndpoint(GENERATE_TOKEN_API);
            logger.info("get access token from scb");
            ResponseEntity<AccessTokenApiResponse> response = scbRestTemplate.exchange(endpoint, HttpMethod.POST, payload, AccessTokenApiResponse.class);
            logger.info("response status code = {}", response.getStatusCode());
            if(!response.hasBody()){
                logger.warn("Response has no body");
                return null;
            }
            accessToken = response.getBody();
            if(accessToken == null){
                throw new RestClientException("access token is null");
            }

            logger.debug("get access token success");
        }catch(RestClientException rcex){
            logger.error("Error has occurred while get access token (SCB API): {}",  rcex.getMessage());
            accessToken = null;
        }
        return accessToken;
    }

    @Override
    public DeeplinkApiResponse deeplinkForPayment(AccessTokenApiResponse accessToken, CashlessPaymentRequest cashlessPayment) {
        DeeplinkApiResponse result;
        List<Deeplink> deeplinkList = new ArrayList<>();
        try{
//            UUID uuid = UUID.randomUUID();
            String transactionId = getTransactionId();
            HttpHeaders headers = getDefaultHttpHeader(transactionId);
            headers.add("channel", "scbeasy");
            String authorization = accessToken.getData().getTokenType() + " " + accessToken.getData().getAccessToken();
            headers.add("authorization", authorization);
            logger.info("authorization = {}", authorization);
            logger.info("transactionId = {}", transactionId);

            DeeplinkForPaymentRequest body = new DeeplinkForPaymentRequest();

            BillPayment billPayment = cashlessPayment.getBillPayment();
            billPayment.setAccountTo("187874780050850");
            cashlessPayment.getBillPayment().setRef1("ABCDEFGHIJ1234567890");
            cashlessPayment.getBillPayment().setRef2("ABCDEFGHIJ1234567890");
            cashlessPayment.getBillPayment().setRef3("ABCDEFGHIJ1234567890");
            body.setBillPayment(billPayment);

            CreditCardFullAmount creditCardFullAmount = cashlessPayment.getCreditCardFullAmount();
            creditCardFullAmount.setMerchantId("567885242752383");
            creditCardFullAmount.setTerminalId("701167406717881");
            creditCardFullAmount.setOrderReference("AA100001");
            body.setCreditCardFullAmount(creditCardFullAmount);

            InstallmentPaymentPlan installmentPaymentPlan = cashlessPayment.getInstallmentPaymentPlan();
            installmentPaymentPlan.setMerchantId("567885242752383");
            installmentPaymentPlan.setTerminalId("701167406717881");
            installmentPaymentPlan.setOrderReference("AA100001");
            installmentPaymentPlan.setIppType("3");
            installmentPaymentPlan.setProdCode("1001");
            body.setInstallmentPaymentPlan(installmentPaymentPlan);

            body.setTransactionSubType(cashlessPayment.getTransactionSubType());
            body.setTransactionType("PURCHASE");

            MerchantMetaData merchantMetaData = new MerchantMetaData();
            merchantMetaData.setCallbackUrl("http://cashless-app.southeastasia.azurecontainer.io:8080/redirect");
            MerchantInfo merchantInfo = new MerchantInfo();
            merchantInfo.setName("SANDBOX MERCHANT NAME");
            body.setMerchantMetaData(merchantMetaData);

            Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            String bodyString = gson.toJson(body);

            logger.info("body is {}", bodyString);
            HttpEntity<String> request = new HttpEntity<>(bodyString, headers);
            logger.info("request is {}", request);
            String GET_DEEPLINK = "/v3/deeplink/transactions";
            String endpoint = this.getFullEndpoint(GET_DEEPLINK);
            logger.info("get deeplink for payment from scb");

            // insert payment detail to DB
            PaymentTrans paymentTrans = new PaymentTrans();
            paymentTrans.setTransId(transactionId);
            paymentTrans.setTouchpoint("MYALLIANZ");
            paymentTrans.setTransSubType(body.getTransactionSubType().toString());
            paymentTrans.setTransType(body.getTransactionType());

            TimeZone timeZone = TimeZone.getTimeZone("GMT+7");
            Calendar c = Calendar.getInstance(timeZone);
            paymentTrans.setCreateDate(c.getTime());

            logger.info("paymentTrans = {}", paymentTrans);

            paymentTransactionsService.insertPaymentDetail(paymentTrans);

            // call scb api
            ResponseEntity<DeeplinkApiResponse> response = scbRestTemplate.postForEntity(endpoint, request, DeeplinkApiResponse.class);

            logger.info("response status code = {}", response.getStatusCode());
            if(!response.hasBody()){
                logger.warn("Response has no body");
                return null;
            }
            result = response.getBody();

            if(result == null){
                throw new RestClientException("access token is null");
            }else{
                Deeplink scbDeeplink = new Deeplink();
                scbDeeplink.setTransId(transactionId);
                scbDeeplink.setBank("scb");
                scbDeeplink.setDeeplink(result.getData().getDeeplinkUrl());

                deeplinkList.add(scbDeeplink);
            }

            // insert deep link detail
            if(!deeplinkList.isEmpty()){
                paymentTransactionsService.insertDeeplink(deeplinkList);
            }

            logger.debug("get deeplink for payment success");
        }catch(RestClientException rcex){
            logger.error("Error has occurred while get deeplink for payment (SCB API): {}",  rcex.getMessage());
            result = null;
        }
        return result;
    }

    private String getTransactionId(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString() + "-" + System.currentTimeMillis();
    }

    private String getFullEndpoint(String endpoint){
        StringBuilder fullEndpoint = new StringBuilder();
        String SCB_URL = "https://api-sandbox.partners.scb/partners/sandbox";
        fullEndpoint.append(SCB_URL);
        fullEndpoint.append(endpoint);
        logger.info("end point = {}", fullEndpoint);
        return fullEndpoint.toString();
    }

    private HttpHeaders getDefaultHttpHeader(String transactionId){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("accept-language","EN");
        headers.add("requestUId", transactionId);
        headers.add("resourceOwnerId","Cashless");
        return headers;
    }
}
