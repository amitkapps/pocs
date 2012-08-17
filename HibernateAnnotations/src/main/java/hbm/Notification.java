package hbm;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: Jun 17, 2010
 * Time: 1:04:29 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "MT_NOTIFICATION")
public class Notification implements Serializable {

    private Long id;
    private String name;
    private List<Subscription> subscriptions;

    @Id
    @Column(name = "NOTIFICATION_ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "NOTIFICATION_NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "notification")
    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", notificationName='" + name + '\'' +
                '}';
    }
}
