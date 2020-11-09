package th.co.azay.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessTokenData {
    private String accessToken;
    private String tokenType;
    private Integer expiresIn;
    private Timestamp expiresAt;
    private String refreshToken;
    private Integer refreshExpiresIn;
    private Timestamp refreshExpiresAt;
}
