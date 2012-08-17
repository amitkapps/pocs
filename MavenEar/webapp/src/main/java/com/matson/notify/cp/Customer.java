package com.matson.notify.cp;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: Jun 2, 2010
 * Time: 10:36:44 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "ELEC_CONTACT")
@SecondaryTables({
        @SecondaryTable(name = "CONTACT",
                        pkJoinColumns = {@PrimaryKeyJoinColumn(name = "CONT_FK1_ORGN_IDEN", referencedColumnName = "ELEC_FK1_CONT_IDEN"),
                                         @PrimaryKeyJoinColumn(name = "CONT_SEQUENCE_NBR", referencedColumnName = "ELEC_FK1_CONT_SNBR")
                                        })
/*
        ,
        @SecondaryTable(name = "PHONE_NUMBER",
                        pkJoinColumns = {@PrimaryKeyJoinColumn(name = "PHNO_FK1_CONT_ORID", referencedColumnName = "ELEC_FK1_CONT_IDEN"),
                                         @PrimaryKeyJoinColumn(name = "PHNO_FK1_CONT_SNBR", referencedColumnName = "ELEC_FK1_CONT_SNBR")
                                        })
*/
})
public class Customer implements Serializable{

    @NaturalId
    @Column(name = "ELEC_IDENTIFIER")
    Long id;

    @Id
    @Column(name = "ELEC_FK1_CONT_IDEN")
    Long orgId;

    @Id
    @Column(name = "ELEC_FK1_CONT_SNBR")
    Integer contactSequence;

    @Column(name = "ELEC_WEB_ID")
    String webId;

    @Column(name = "ELEC_EMAIL_ADDR")
    String email;

    @Column(name = "CONT_FIRST_NAME", table = "CONTACT")
    String firstName;

    @Column(name = "CONT_LAST_NAME", table = "CONTACT")
    String lastName;

    @Transient
    Long faxNo;

    @OneToMany(mappedBy = "customer", targetEntity = CustomerArolAuthorization.class)
    List<CustomerArolAuthorization> customerArolAuthorizations;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWebId() {
        return webId;
    }

    public void setWebId(String webId) {
        this.webId = webId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getFaxNo() {
        return faxNo;
    }

    public void setFaxNo(Long faxNo) {
        this.faxNo = faxNo;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Integer getContactSequence() {
        return contactSequence;
    }

    public void setContactSequence(Integer contactSequence) {
        this.contactSequence = contactSequence;
    }

    public List<CustomerArolAuthorization> getSubscriberAddressRoleAuthorizations() {
        return customerArolAuthorizations;
    }

    public void setSubscriberAddressRoleAuthorizations(List<CustomerArolAuthorization> customerArolAuthorizations) {
        this.customerArolAuthorizations = customerArolAuthorizations;
    }

    @Override
    public String toString() {
        return "Subscriber{" +
                "id=" + id +
                ", orgId=" + orgId +
                ", contactSequence=" + contactSequence +
                ", webId='" + webId + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", faxNo=" + faxNo +
                ", No of auths=" + customerArolAuthorizations.size() +
                '}';
    }
}