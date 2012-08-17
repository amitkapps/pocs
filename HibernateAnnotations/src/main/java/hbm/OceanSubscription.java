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
@DiscriminatorValue("OCEAN")
public class OceanSubscription extends Subscription{

    private List<OceanSubscriptionConfiguration> oceanSubscriptionConfigurations;

    @OneToMany(mappedBy = "subscription", cascade = CascadeType.ALL)
    public List<OceanSubscriptionConfiguration> getOceanSubscriptionConfigurations() {
        return oceanSubscriptionConfigurations;
    }

    public OceanSubscription setOceanSubscriptionConfigurations(List<OceanSubscriptionConfiguration> oceanSubscriptionConfigurations) {
        this.oceanSubscriptionConfigurations = oceanSubscriptionConfigurations;
        return this;
    }
}
