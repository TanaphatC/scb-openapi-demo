package th.co.azay.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstallmentPaymentPlan {
    private BigDecimal paymentAmount;
    private String tenor;
    private String merchantId;
    private String terminalId;
    private String orderReference;
    private String ippType;
    private String prodCode;
}
