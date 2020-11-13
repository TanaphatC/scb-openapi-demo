package th.co.azay.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenerateQRCodeData {
    private String qrRawData;
    private String qrImage;
    private String csExtExpiryTime;
    private String responseCode;
    private String qrCodeType;
    private Integer qrCodeId;
    private Integer poi;
    private BigDecimal amount;
    private String currencyCode;
    private String currencyName;
    private String csNote;
    private String invoice;
    private String merchantId;
    private String merchantName;
    private String csUserDefined;
    private String terminalId;
    private String terminalName;
    private List<GenerateQRCodeChannels> channels;

}
