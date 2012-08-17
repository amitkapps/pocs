package com.matson.notify.cp;

import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: Jun 2, 2010
 * Time: 4:34:08 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "ELEC_CONT_AUTH")
@Where(clause = "ECAU_DOC='ST'")
public class CustomerArolAuthorization implements Serializable{

    @Id
    @Column(name = "ECAU_FK1_ELEC_IDEN")
    Long subscriberId;

    @Id
    @Column(name = "ECAU_FK1_AROL_IDEN")
    Long addressRoleId;

    @Id
    @Column(name = "ECAU_TRADE")
    String trade;

    @Id
    @Column(name = "ECAU_PARTY_TYPE")
    String partyType;

    @ManyToOne
    @JoinColumn(name = "ECAU_FK1_ELEC_IDEN", referencedColumnName = "ELEC_IDENTIFIER", insertable = false, updatable = false)
    Customer customer;

    @ManyToOne
    @JoinColumn(name = "ECAU_FK1_AROL_IDEN", referencedColumnName = "AROL_IDENTIFIER", insertable = false, updatable = false)
    AddressRole addressRole;

    public Customer getSubscriber() {
        return customer;
    }

    public void setSubscriber(Customer customer) {
        this.customer = customer;
    }

    public AddressRole getAddressRole() {
        return addressRole;
    }

    public void setAddressRole(AddressRole addressRole) {
        this.addressRole = addressRole;
    }

    public String getTrade() {
        return trade;
    }

    public void setTrade(String trade) {
        this.trade = trade;
    }

    public String getPartyType() {
        return partyType;
    }

    public void setPartyType(String partyType) {
        this.partyType = partyType;
    }

    @Override
    public String toString() {
        return "SubscriberAddressRoleAuthorization{" +
                "subscriber=" + customer.firstName + " " + customer.lastName +
                ", Is Allowed[ trade='" + trade + '\'' +
                ", partyType='" + partyType + '\'' + "] For " +
                "address=" + addressRole.getAddress() +
                '}';
    }
}