package com.matson.notify.cp;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: May 27, 2010
 * Time: 4:05:48 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity(name = "ADDRESS_ROLE")
public class AddressRole {

    private Long id;
    private Organization organization;
    private Organization careOfOrganization;
    private Address address;
    private String qualifier;

    @Id
    @Column(name = "AROL_IDENTIFIER")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "AROL_FK1_OADD_IDEN", insertable = false, updatable = false)
    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @ManyToOne
    @JoinColumn(name = "AROL_FK1_ORGN_IDEN")
    public Organization getCareOfOrganization() {
        return careOfOrganization;
    }

    public void setCareOfOrganization(Organization careOfOrganization) {
        this.careOfOrganization = careOfOrganization;
    }

    @ManyToOne
    @JoinColumns({@JoinColumn(name = "AROL_FK1_OADD_IDEN"), @JoinColumn(name = "AROL_FK1_OADD_SNBR")})
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Column(name = "AROL_ORG_NAME_QUAL")
    public String getQualifier() {
        return qualifier;
    }

    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    @Override
    public String toString() {
        return "AddressRole{" +
                "id=" + id +
                ", organization=" + organization +
                ", careOfOrganization=" + careOfOrganization +
                ", address=" + address +
                ", qualifier='" + qualifier + '\'' +
                '}';
    }
}