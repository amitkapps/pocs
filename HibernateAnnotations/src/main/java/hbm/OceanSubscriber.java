package hbm;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: Jun 17, 2010
 * Time: 10:36:40 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@DiscriminatorValue(value = "OCEAN")
public class OceanSubscriber extends Subscriber{

    private Customer customer;

    @OneToOne
    @JoinTable(name = "ML_SUBSCRIBER_CP_CUSTOMER"
                , joinColumns = {@JoinColumn(name = "SUBSCRIBER_ID")}
                , inverseJoinColumns = {@JoinColumn(name = "WEB_ID")}
            )
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "OceanSubscriber{" +
                "id = " + getId() +
                ", class=" + getClass() +
                "customer=" + customer +
                '}';
    }
}
