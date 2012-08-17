package hbm;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: Jun 17, 2010
 * Time: 10:09:18 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "MT_OCEAN_SUBSCRIPTION_CONFIG")
public class OceanSubscriptionConfiguration extends Auditable implements Serializable{

    private Long id;
//    private Long customerArolAuthorizationId;
    private CustomerArolAuthorization customerArolAuthorization;
    private OceanSubscription subscription;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MT_OCEAN_SUBSCRIPTION_CFG_SEQ")
    @SequenceGenerator(name = "MT_OCEAN_SUBSCRIPTION_CFG_SEQ", sequenceName = "MT_OCEAN_SUBSCRIPTION_CFG_SEQ", allocationSize = 2)
    @Column(name = "OCEAN_SUBSCRIPTION_CONFIG_ID")
    public Long getId() {
        return id;
    }

    public OceanSubscriptionConfiguration setId(Long id) {
        this.id = id;
        return this;
    }

/*
    @Column(name = "CUSTOMER_AROL_AUTH_ID")
    public Long getCustomerArolAuthorizationId() {
        return customerArolAuthorizationId;
    }


    public OceanSubscriptionConfiguration setCustomerArolAuthorizationId(Long customerArolAuthorizationId) {
        this.customerArolAuthorizationId = customerArolAuthorizationId;
        return this;
    }
*/

    @Id
    @OneToOne
    @JoinColumn(name = "CUSTOMER_AROL_AUTH_ID", referencedColumnName = "WEBA_AUTHORIZATION_ID")
    public CustomerArolAuthorization getCustomerArolAuthorization() {
        return customerArolAuthorization;
    }

    public OceanSubscriptionConfiguration setCustomerArolAuthorization(CustomerArolAuthorization customerArolAuthorization) {
        this.customerArolAuthorization = customerArolAuthorization;
        return this;
    }

    @ManyToOne
    @JoinColumn(name = "SUBSCRIPTION_ID", referencedColumnName = "SUBSCRIPTION_ID")
    public OceanSubscription getSubscription() {
        return subscription;
    }

    public OceanSubscriptionConfiguration setSubscription(OceanSubscription subscription) {
        this.subscription = subscription;
        return this;
    }
}
