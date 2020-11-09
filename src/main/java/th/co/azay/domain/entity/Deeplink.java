package th.co.azay.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="deeplink")
@IdClass(DeeplinkPK.class)
@Data
@NoArgsConstructor
public class Deeplink implements Serializable {

    @Id
    @Column(name = "trans_id")
    private String transId;
    @Id
    @Column(name = "bank")
    private String bank;
    @Column(name = "deeplink")
    private String deeplink;

}
