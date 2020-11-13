package th.co.azay.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GenerateQRCodeChannels {
    private Integer seqNo;
    private String channelName;
    private String channelCode;
}
