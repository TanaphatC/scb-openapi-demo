package th.co.azay.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeeplinkForPaymentRequest {
    private String transactionType;
    private List<String> transactionSubType;
    private Integer sessionValidityPeriod;
    private Integer sessionValidUntil;
    private BillPayment billPayment;
    private CreditCardFullAmount creditCardFullAmount;
    private InstallmentPaymentPlan installmentPaymentPlan;
    private MerchantMetaData merchantMetaData;
}
