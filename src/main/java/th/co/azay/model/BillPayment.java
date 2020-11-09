package th.co.azay.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillPayment {
    private BigDecimal paymentAmount;
    private String accountTo;
    private String accountFrom;
    private String ref1;
    private String ref2;
    private String ref3;
}
