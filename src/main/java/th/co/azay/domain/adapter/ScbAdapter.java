package th.co.azay.domain.adapter;

import th.co.azay.model.AccessTokenApiResponse;
import th.co.azay.model.CashlessPaymentRequest;
import th.co.azay.model.DeeplinkApiResponse;

public interface ScbAdapter {
    public AccessTokenApiResponse generateAccessToken();
    public DeeplinkApiResponse deeplinkForPayment(AccessTokenApiResponse accessToken, CashlessPaymentRequest cashlessPayment);
}
