package th.co.azay.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenerateQRCodeResponse {
    private ScbApiReturnStatus status;
    private GenerateQRCodeData data;
}
