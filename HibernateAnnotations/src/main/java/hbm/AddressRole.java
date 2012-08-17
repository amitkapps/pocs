package hbm;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: May 27, 2010
 * Time: 4:05:48 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity(name = "MT_ADDRESS_ROLE")
public class AddressRole implements Serializable{

    private Long id;
    private Organization organization;
    private Organization careOfOrganization;
    private Address address;
    private String qualifier;
    private String farEastTrade;
    private String guamTrade;
    private String hawaiiTrade;
    private String midPacTrade;
    private String pcsTrade;
    private String isActive;

    @Id
    @Column(name = "ADDRESS_ROLE_ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This is the Organization that is using the address (but may not own it if its not the same as
     * {@link #getCareOfOrganization()}
     * @return
     */
    @ManyToOne()
    @JoinColumn(name = "ORGANIZATION_ID")
    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    /**
     * This is the organization that owns the Address
     * @return
     */
    @ManyToOne
    @JoinColumn(name = "CO_ORGANIZATION")
    public Organization getCareOfOrganization() {
        return careOfOrganization;
    }

    public void setCareOfOrganization(Organization careOfOrganization) {
        this.careOfOrganization = careOfOrganization;
    }

    @ManyToOne
    @JoinColumn(name = "ORGANIZATION_ADDRESS_ID")
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Column(name = "ORG_NAME_QUALIFIER")
    public String getQualifier() {
        return qualifier;
    }

    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    @Column(name = "FAR_EAST_TRADE")
    public String getFarEastTrade() {
        return farEastTrade;
    }

    public void setFarEastTrade(String farEastTrade) {
        this.farEastTrade = farEastTrade;
    }

    @Column(name = "GUAM_TRADE")
    public String getGuamTrade() {
        return guamTrade;
    }

    public void setGuamTrade(String guamTrade) {
        this.guamTrade = guamTrade;
    }

    @Column(name = "HAWAII_TRADE")
    public String getHawaiiTrade() {
        return hawaiiTrade;
    }

    public void setHawaiiTrade(String hawaiiTrade) {
        this.hawaiiTrade = hawaiiTrade;
    }

    @Column(name = "MID_PAC_TRADE")
    public String getMidPacTrade() {
        return midPacTrade;
    }

    public void setMidPacTrade(String midPacTrade) {
        this.midPacTrade = midPacTrade;
    }

    @Column(name = "PCS_TRADE")
    public String getPcsTrade() {
        return pcsTrade;
    }

    public void setPcsTrade(String pcsTrade) {
        this.pcsTrade = pcsTrade;
    }

    @Column(name = "IS_ACTIVE")
    public String isActive() {
        return isActive;
    }

    public void setActive(String active) {
        isActive = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AddressRole)) return false;

        AddressRole that = (AddressRole) o;

        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Arol{" +
                "id=" + id +
                ", org=" + organization.getId() +
                ", COOrg=" + careOfOrganization.getId() +
                ", addr=" + address.getAddressId() +
                ", qual='" + qualifier + '\'' +
                '}';
    }
}