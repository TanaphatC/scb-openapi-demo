package th.co.azay.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import th.co.azay.application.constants.ScbTransactionStatus;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScbTransactions {
    private String transactionType;
    private List<String> transactionSubType;
    private String transactionMethod;
    private String accountFrom;
    private String paidAmount;
    private Integer sessionValidUntil;
    private BillPayment billPayment;
    private CreditCardFullAmount creditCardFullAmount;
    private InstallmentPaymentPlan installmentPaymentPlan;
    private MerchantMetaData merchantMetaData;
    private String partnerId;
    private String partnerName;
    private Integer sessionValidityPeriod;
    private Date createdTimestamp;
    private Date updatedTimestamp;
    private Integer statusCode;
    private String statusCodeDesc;

}
