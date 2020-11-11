package th.co.azay.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

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
    @Column(name = "bank_trans_id")
    private String bankTransId;
    @Column(name = "status")
    private String status;
    @Column(name = "create_date")
    private Date createDate;
    @Column(name = "update_date")
    private Date updateDate;

}
