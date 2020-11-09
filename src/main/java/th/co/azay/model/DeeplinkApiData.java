package th.co.azay.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeeplinkApiData {
    private String transactionId;
    private String deeplinkUrl;
    private String userRefId;
}
