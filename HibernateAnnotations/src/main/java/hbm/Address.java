package hbm;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: May 27, 2010
 * Time: 2:22:43 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity(name = "MT_ORGANIZATION_ADDRESS")
public class Address implements Serializable{
    private Organization organization;

    private Long addressId;
    private String addressLine1;
    private String city;
    private String state;
    private String country;
    private String zipCode;

    @Id
    @Column(name = "ORGANIZATION_ADDRESS_ID")
    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    @ManyToOne
    @JoinColumn(name = "ORGANIZATION_ID")
    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @Column(name = "ADDRESS_LINE")
    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    @Column(name = "CITY")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "STATE_CODE")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Column(name = "COUNTRY")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Column(name = "ZIP_CODE")
    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;

        Address address = (Address) o;

        if (!addressId.equals(address.addressId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return addressId.hashCode();
    }

    @Override
    public String toString() {
        return "Addr{" +
                "org=" + organization.getOrganizationName() +
                ", st='" + addressLine1 + '\'' +
                ", city='" + city + '\'' +
                ", st='" + state + '\'' +
                ", Cntry='" + country + '\'' +
                ", zip='" + zipCode + '\'' +
                '}';
    }
}