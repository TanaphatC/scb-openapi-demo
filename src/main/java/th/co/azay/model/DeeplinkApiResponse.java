package th.co.azay.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeeplinkApiResponse {
    private ScbApiReturnStatus status;
    private DeeplinkApiData data;
}
