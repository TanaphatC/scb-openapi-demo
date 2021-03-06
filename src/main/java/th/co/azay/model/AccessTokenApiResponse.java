package th.co.azay.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessTokenApiResponse {
    private ScbApiReturnStatus status;
    private AccessTokenData data;
}
