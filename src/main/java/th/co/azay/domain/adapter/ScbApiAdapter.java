package th.co.azay.domain.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import th.co.azay.application.constants.ScbTransactionStatus;
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
    public AccessTokenData generateAccessToken() {
        AccessTokenApiResponse accessToken;
        try {
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("applicationKey", "l79b60f8086f9145df9f8c53c74dd98808");
            map.add("applicationSecret", "1f7610a4f1fc48649f4c9598f51d3a3b");
            map.add("authCode", "");
            map.add("state", "");
            map.add("codeChallenge", "");

            String requestUId = getRequestUId();
            HttpHeaders headers = getDefaultHttpHeader(requestUId);
            logger.info("requestUId = {}", requestUId);

            HttpEntity<MultiValueMap<String, String>> payload = new HttpEntity<>(map, headers);

            String GENERATE_TOKEN_API = "/v1/oauth/token";
            String endpoint = this.getFullEndpoint(GENERATE_TOKEN_API);
            logger.info("get access token from scb");
            ResponseEntity<AccessTokenApiResponse> response = scbRestTemplate.exchange(endpoint, HttpMethod.POST, payload, AccessTokenApiResponse.class);
            logger.info("response status code = {}", response.getStatusCode());
            if (!response.hasBody()) {
                logger.warn("Response has no body");
                return null;
            }
            accessToken = response.getBody();
            if (accessToken == null) {
                throw new RestClientException("access token is null");
            }

            logger.debug("get access token success");
        } catch (RestClientException rcex) {
            logger.error("Error has occurred while get access token (SCB API): {}", rcex.getMessage());
            accessToken = null;
        }
        return accessToken.getData();
    }

    @Override
    public DeeplinkApiResponse deeplinkForPayment(AccessTokenData accessToken, CashlessPaymentRequest cashlessPayment) {
        DeeplinkApiResponse result;
        List<Deeplink> deeplinkList = new ArrayList<>();
        try {
            String requestUId = getRequestUId();
            HttpHeaders headers = getDefaultHttpHeader(requestUId);
            headers.add("channel", "scbeasy");
            String authorization = accessToken.getTokenType() + " " + accessToken.getAccessToken();
            headers.add("authorization", authorization);
            logger.info("authorization = {}", authorization);
            logger.info("requestUId = {}", requestUId);

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

            List<String> transSubType = cashlessPayment.getTransactionSubType();
            logger.info("trans sub type = {}", transSubType);
            body.setTransactionSubType(transSubType);
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
            paymentTrans.setTransId(requestUId);
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
            if (!response.hasBody()) {
                logger.warn("Response has no body");
                return null;
            }
            result = response.getBody();

            if (result == null) {
                throw new RestClientException("access token is null");
            } else {
                logger.info("deeplink = {}", result);
                Deeplink scbDeeplink = new Deeplink();
                scbDeeplink.setTransId(requestUId);
                scbDeeplink.setBank("scb");
                scbDeeplink.setDeeplink(result.getData().getDeeplinkUrl());
                scbDeeplink.setBankTransId(result.getData().getTransactionId());
                scbDeeplink.setCreateDate(new Date());

                deeplinkList.add(scbDeeplink);
            }

            // insert deep link detail
            if (!deeplinkList.isEmpty()) {
                paymentTransactionsService.insertDeeplink(deeplinkList);
            }

            logger.debug("get deeplink for payment success");
        } catch (RestClientException rcex) {
            logger.error("Error has occurred while get deeplink for payment (SCB API): {}", rcex.getMessage());
            result = null;
        }
        return result;
    }

    @Override
    public ScbTransactions getTransactions(String transactionId, AccessTokenData accessToken) throws JsonProcessingException {
        ScbTransactions scbTransactions;
        try {

            String requestUId = getRequestUId();
            HttpHeaders headers = getDefaultHttpHeader(requestUId);
            headers.add("channel", "scbeasy");
            String authorization = accessToken.getTokenType() + " " + accessToken.getAccessToken();
            headers.add("authorization", authorization);
            logger.info("authorization = {}", authorization);
            logger.info("transactionId = {}", transactionId);

            HttpEntity<String> request = new HttpEntity<>(null, headers);

            String transactionApiUrl = "/v2/transactions/" + transactionId;
            String endpoint = getFullEndpoint(transactionApiUrl);

            logger.info("get SCB transaction");
            // call scb api
            ResponseEntity<ScbTransactionsApiResponse> response = scbRestTemplate.exchange(endpoint, HttpMethod.GET, request, ScbTransactionsApiResponse.class);

            logger.info("response status code = {}", response.getStatusCode());
            if (!response.hasBody()) {
                logger.warn("Response has no body");
                return null;
            }
            scbTransactions = response.getBody().getData();

            if (scbTransactions == null) {
                throw new RestClientException("SCB transaction not found");
            } else {
                if (scbTransactions.getStatusCode() != null) {
                    ScbTransactionStatus scbTransactionStatus = ScbTransactionStatus.getScbTransactionStatus(scbTransactions.getStatusCode());
                    scbTransactions.setStatusCodeDesc(scbTransactionStatus.getValue());
                    logger.info("SCB transaction status = {}", scbTransactionStatus.getValue());
                }

                ObjectMapper mapper = new ObjectMapper();
                String prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response);
                logger.info("SCB transaction detail = \r\n {}", prettyJson);
            }
        } catch (RestClientException rcex) {
            logger.error("Error has occurred while get transactions (SCB API): {}", rcex.getMessage());
            scbTransactions = null;
        }
        return scbTransactions;
    }

    @Override
    public GenerateQRCodeData generateQRCode(AccessTokenData accessToken, GenerateQRCodeRequest generateQRCodeRequest) {
        GenerateQRCodeData generateQRCodeData = null;
        try {

            String requestUId = getRequestUId();
            HttpHeaders headers = getDefaultHttpHeader(requestUId);
            String authorization = accessToken.getTokenType() + " " + accessToken.getAccessToken();
            headers.add("authorization", authorization);
            logger.info("authorization = {}", authorization);
            logger.info("requestUId = {}", requestUId);

            generateQRCodeRequest.setCsExtExpiryTime("60");
            generateQRCodeRequest.setInvoice("INVOICE");
            generateQRCodeRequest.setMerchantId("567885242752383");
            generateQRCodeRequest.setTerminalId("701167406717881");

            generateQRCodeRequest.setPpType("BILLERID");
            generateQRCodeRequest.setPpId("187874780050850"); // merchant biller id
            generateQRCodeRequest.setRef1("REFERENCE1");
            generateQRCodeRequest.setRef2("REFERENCE2");
            generateQRCodeRequest.setRef3("SCB");

            Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            String bodyString = gson.toJson(generateQRCodeRequest);

            logger.info("body is {}", bodyString);
//            HttpEntity<String> request = new HttpEntity<>(bodyString, headers);

            HttpEntity<GenerateQRCodeRequest> request = new HttpEntity<>(generateQRCodeRequest, headers);

            String generateQRCodeApiUrl = "/v1/payment/qrcode/create";
            String endpoint = getFullEndpoint(generateQRCodeApiUrl);

            logger.info("Generating QR Code...");
            // call scb api
            ResponseEntity<GenerateQRCodeResponse> response = scbRestTemplate.exchange(endpoint, HttpMethod.POST, request, GenerateQRCodeResponse.class);

            logger.info("response status code = {}", response.getStatusCode());
            if (!response.hasBody()) {
                logger.warn("Response has no body");
                return null;
            }
            generateQRCodeData = response.getBody().getData();

            if (generateQRCodeData == null) {
                throw new RestClientException("SCB transaction not found");
            } else {
                ObjectMapper mapper = new ObjectMapper();
                String prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(generateQRCodeData);
                logger.info("SCB QR code detail = \r\n {}", prettyJson);
            }
        } catch (RestClientException | JsonProcessingException rcex) {
            logger.error("Error has occurred while generate QR code (SCB API): {}", rcex.getMessage());
            generateQRCodeData = null;
        }
        return generateQRCodeData;
    }

    private String getRequestUId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString() + "-" + System.currentTimeMillis();
    }

    private String getFullEndpoint(String endpoint) {
        StringBuilder fullEndpoint = new StringBuilder();
        String SCB_URL = "https://api-sandbox.partners.scb/partners/sandbox";
        fullEndpoint.append(SCB_URL);
        fullEndpoint.append(endpoint);
        logger.info("end point = {}", fullEndpoint);
        return fullEndpoint.toString();
    }

    private HttpHeaders getDefaultHttpHeader(String transactionId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("accept-language", "EN");
        headers.add("requestUId", transactionId);
        headers.add("resourceOwnerId", "Cashless");
        return headers;
    }
}
