package th.co.azay.domain.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import th.co.azay.model.AccessTokenApiResponse;
import th.co.azay.model.CashlessPaymentRequest;
import th.co.azay.model.DeeplinkApiResponse;
import th.co.azay.model.ScbTransactions;

public interface ScbAdapter {
    AccessTokenApiResponse generateAccessToken();
    DeeplinkApiResponse deeplinkForPayment(AccessTokenApiResponse accessToken, CashlessPaymentRequest cashlessPayment);
    ScbTransactions getTransactions(String transactionId, AccessTokenApiResponse accessToken) throws JsonProcessingException;
}
