package hbm;

import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: Jun 17, 2010
 * Time: 9:11:59 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "MT_SUBSCRIPTION")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "SUBSCRIPTION_TYPE")
public abstract class Subscription extends Auditable implements Serializable {

    private Long id;
    private boolean emailDelivery = false;
    private boolean faxDelivery = false;
//    private Long notificationId;
    private Notification notification;
//    private Long subscriberId;
    private Subscriber subscriber;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MT_SUBSCRIPTION_SEQ")
    @SequenceGenerator(name = "MT_SUBSCRIPTION_SEQ", sequenceName = "MT_SUBSCRIPTION_SEQ", allocationSize = 2)
    @Column(name = "SUBSCRIPTION_ID")
    public Long getId(){
        return id;
    }

    public Subscription setId(Long id) {
        this.id = id;
        return this;
    }

/*
    @Column(name = "SUBSCRIBER_ID")
    public Long getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(Long subscriberId) {
        this.subscriberId = subscriberId;
    }
*/
    @NaturalId
    @ManyToOne
    @JoinColumn( name = "SUBSCRIBER_ID", referencedColumnName = "SUBSCRIBER_ID")
    public Subscriber getSubscriber() {
        return subscriber;
    }

    public Subscription setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
        return this;
    }

    @NaturalId
    @ManyToOne
    @JoinColumn(name = "NOTIFICATION_ID", referencedColumnName = "NOTIFICATION_ID")
    public Notification getNotification() {
        return notification;
    }

    public Subscription setNotification(Notification notification) {
        this.notification = notification;
        return this;
    }


    /*
    @Column(name = "NOTIFICATION_ID")
    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }
*/

    @Column(name = "IS_EMAIL_DELIVERY")
    @Type(type = "org.hibernate.type.YesNoType")
    public boolean isEmailDelivery() {
        return emailDelivery;
    }

    public Subscription setEmailDelivery(boolean emailDelivery) {
        this.emailDelivery = emailDelivery;
        return this;
    }

    @Column(name = "IS_FAX_DELIVERY")
    @Type(type = "org.hibernate.type.YesNoType")
    public boolean isFaxDelivery() {
        return faxDelivery;
    }

    public Subscription setFaxDelivery(boolean faxDelivery) {
        this.faxDelivery = faxDelivery;
        return this;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "id=" + id +
                ", emailDelivery=" + emailDelivery +
                ", faxDelivery=" + faxDelivery +
                ", notification=" + notification.getId() +
                ", subscriber=" + subscriber.getId() +
                '}';
    }
}
