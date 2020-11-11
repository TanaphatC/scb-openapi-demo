package th.co.azay.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScbTransactionsApiResponse {
    private ScbApiReturnStatus status;
    private ScbTransactions data;
}
