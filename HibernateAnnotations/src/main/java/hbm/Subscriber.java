package hbm;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: Jun 17, 2010
 * Time: 10:32:12 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "MT_SUBSCRIBER")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "SUBSCRIBER_TYPE")
public abstract class Subscriber extends Auditable implements Serializable {

    private Long id;
    private boolean active;
    private List<Subscription> subscriptions;

    @Id
    @Column(name = "SUBSCRIBER_ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "IS_ACTIVE")
    @Type(type = "org.hibernate.type.YesNoType")
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @OneToMany(mappedBy = "subscriber")
    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

}
