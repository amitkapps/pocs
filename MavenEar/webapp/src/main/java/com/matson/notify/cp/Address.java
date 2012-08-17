package com.matson.notify.cp;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: May 27, 2010
 * Time: 2:22:43 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity(name = "ORGANIZATION_ADDR")
public class Address implements Serializable{
    private Organization organization;

    private Long organizationId;
    private Integer addressSequence;
    private String addressLine1;
    private String city;
    private String state;
    private String country;
    private String zipCode;

    @ManyToOne
    @JoinColumn(name = "OADD_FK1_ORGN_IDEN", insertable = false, updatable = false)
    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @Id
    @Column(name = "OADD_FK1_ORGN_IDEN")
    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    @Id
    @Column(name = "OADD_SEQUENCE_NBR")
    public Integer getAddressSequence() {
        return addressSequence;
    }

    public void setAddressSequence(Integer addressSequence) {
        this.addressSequence = addressSequence;
    }

    @Column(name = "OADD_ADDR_LINE_1")
    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    @Column(name = "OADD_CITY")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "OADD_STATE")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Column(name = "OADD_COUNTRY")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Column(name = "OADD_ZIP_CODE")
    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        return "Address{" +
                "organization=" + organization.getOrganizationName() +
                ", organizationId=" + organizationId +
                ", addressSequence=" + addressSequence +
                ", addressLine1='" + addressLine1 + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }
}