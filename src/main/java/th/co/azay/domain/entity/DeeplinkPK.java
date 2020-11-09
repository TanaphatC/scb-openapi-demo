package th.co.azay.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class DeeplinkPK implements Serializable {

    private String transId;
    private String bank;

}
