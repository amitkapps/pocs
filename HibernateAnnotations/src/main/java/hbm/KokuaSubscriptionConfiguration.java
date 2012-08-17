package hbm;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: Jun 17, 2010
 * Time: 10:20:45 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "MT_KOKUA_SUBSCRIPTION_CONFIG")
public class KokuaSubscriptionConfiguration{

    private Long id;
    private String truckerCode;
    private String organizationName;
    private OceanSubscription subscription;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MT_KOKUA_SUBSCRIPTION_CFG_SEQ")
    @SequenceGenerator(name = "MT_KOKUA_SUBSCRIPTION_CFG_SEQ", sequenceName = "MT_KOKUA_SUBSCRIPTION_CFG_SEQ")
    @Column(name = "CONFIGURATION_ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "TRUCKER_CD")
    public String getTruckerCode() {
        return truckerCode;
    }

    public void setTruckerCode(String truckerCode) {
        this.truckerCode = truckerCode;
    }

    @Column(name = "ORGANIZATION_NAME")
    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    @OneToOne
    @JoinColumn(name = "SUBSCRIPTION_ID", referencedColumnName = "SUBSCRIPTION_ID")
    public OceanSubscription getSubscription() {
        return subscription;
    }

    public void setSubscription(OceanSubscription subscription) {
        this.subscription = subscription;
    }
}
