package th.co.azay.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import th.co.azay.domain.adapter.ScbAdapter;
import th.co.azay.model.*;

@RestController
@RequestMapping("/api")
public class ApiController {

    private static final Logger logger = LogManager.getLogger(ApiController.class);

    @Autowired
    ScbAdapter scbAdapter;

    @PostMapping("/deeplink")
    public ResponseEntity deeplink(@RequestBody CashlessPaymentRequest request) {
        logger.info("json request: {}", request);

        AccessTokenApiResponse accessToken = scbAdapter.generateAccessToken();
        logger.info("scb token response = {}", accessToken);

        DeeplinkApiResponse deeplink = scbAdapter.deeplinkForPayment(accessToken, request);

        String redirect = "/cashless?scb="+deeplink.getData().getDeeplinkUrl()+"&transactionId="+deeplink.getData().getTransactionId();
        return ResponseEntity.ok(new DeeplinkResponse(redirect));
    }

    @GetMapping("/transactions/{transactionId}")
    public ResponseEntity transactions(@PathVariable("transactionId") String transactionId) throws JsonProcessingException {
        logger.info("transactionId: {}", transactionId);

        AccessTokenApiResponse accessToken = scbAdapter.generateAccessToken();

        ScbTransactions scbTransactions = scbAdapter.getTransactions(transactionId, accessToken);

        return ResponseEntity.ok(scbTransactions);
    }

    @GetMapping("/version")
    public String redirect(){
        return "1.0.3";
    }

}
