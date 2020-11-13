package th.co.azay.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GenerateQRCodeRequest {
    private String qrType;
    private String amount;

    // QR30
    private String ppType;
    private String ppId;
    private String ref1;
    private String ref2;
    private String ref3;

    // QRCS
    private String csExtExpiryTime;
    private String csNote;
    private String csUserDefined;
    private String invoice;
    private String merchantId;
    private String terminalId;
}