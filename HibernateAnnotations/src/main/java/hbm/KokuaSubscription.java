package hbm;

import javax.persistence.*;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: Jun 17, 2010
 * Time: 9:59:34 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@DiscriminatorValue("KOKUA")
public class KokuaSubscription extends Subscription {

    private KokuaSubscriptionConfiguration kokuaSubscriptionConfiguration;

    @OneToOne(mappedBy = "subscription", cascade = CascadeType.ALL)
    public KokuaSubscriptionConfiguration getKokuaSubscriptionConfiguration() {
        return kokuaSubscriptionConfiguration;
    }

    public void setKokuaSubscriptionConfiguration(KokuaSubscriptionConfiguration kokuaSubscriptionConfiguration) {
        this.kokuaSubscriptionConfiguration = kokuaSubscriptionConfiguration;
    }
}
