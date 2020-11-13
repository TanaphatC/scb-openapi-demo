package th.co.azay.domain.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.logging.log4j.core.tools.Generate;
import th.co.azay.model.*;

public interface ScbAdapter {
    AccessTokenData generateAccessToken();
    DeeplinkApiResponse deeplinkForPayment(AccessTokenData accessToken, CashlessPaymentRequest cashlessPayment);
    ScbTransactions getTransactions(String transactionId, AccessTokenData accessToken) throws JsonProcessingException;
    GenerateQRCodeData generateQRCode(AccessTokenData accessToken, GenerateQRCodeRequest generateQRCodeRequest);
}
