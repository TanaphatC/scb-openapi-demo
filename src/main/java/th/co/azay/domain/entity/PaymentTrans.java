package th.co.azay.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="payment_trans")
@Data
@NoArgsConstructor
public class PaymentTrans implements Serializable {

    @Id
    @Column(name = "trans_id")
    private String transId;
    @Column(name = "touchpoint")
    private String touchpoint;
    @Column(name = "trans_type")
    private String transType;
    @Column(name = "trans_subtype")
    private String transSubType;
    @Column(name = "session_validity_period")
    private Integer sessionValidityPeriod;
    @Column(name = "session_valid_until")
    private Integer sessionValidUntil;
    @Column(name = "create_date")
    private Date createDate;

}
