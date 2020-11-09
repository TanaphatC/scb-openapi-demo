package th.co.azay.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CashlessPaymentRequest {
    private List<String> banks;
    private List<String> transactionSubType;
    private BillPayment billPayment;
    private CreditCardFullAmount creditCardFullAmount;
    private InstallmentPaymentPlan installmentPaymentPlan;
}
