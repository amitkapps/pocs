package hbm;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: Jun 2, 2010
 * Time: 10:36:44 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "MT_CUSTOMER")
public class Customer implements Serializable{

    private OceanSubscriber subscriber;
    private String customerId;
    private Long organizationId;
    private String email;
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String country;
    private String faxCountryCode;
    private String faxNumber;
    private Set<CustomerArolAuthorization> customerArolAuthorizations = new HashSet<CustomerArolAuthorization>();
    private String status;

    @Id
    @Column(name = "WEB_ID")
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @OneToOne(mappedBy = "customer")
    public OceanSubscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(OceanSubscriber subscriber) {
        this.subscriber = subscriber;
    }

    @Column(name = "EMAIL_ADDRESS")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "FIRST_NAME")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "LAST_NAME")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "FAX_COUNTRY_CODE")
    public String getFaxCountryCode() {
        return faxCountryCode;
    }

    public void setFaxCountryCode(String faxCountryCode) {
        this.faxCountryCode = faxCountryCode;
    }

    @Column(name = "FAX_NUMBER")
    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    @Column(name = "ORGANIZATION_ID")
    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    @Column(name = "ADDRESS1")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "CITY")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "STATE")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Column(name = "ZIP_CODE")
    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @Column(name = "COUNTRY")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Column(name = "STATUS")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @OneToMany(mappedBy = "customer")
    public Set<CustomerArolAuthorization> getCustomerArolAuthorizations() {
        return customerArolAuthorizations;
    }

    public void setCustomerArolAuthorizations(Set<CustomerArolAuthorization> customerArolAuthorizations) {
        this.customerArolAuthorizations = customerArolAuthorizations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;

        Customer customer = (Customer) o;

        if (!customerId.equals(customer.customerId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return customerId.hashCode();
    }

    @Override
    public String toString() {
        return "Cust{" +
                "webId='" + customerId + '\'' +
                ", email='" + email + '\'' +
                ", first='" + firstName + '\'' +
                ", last='" + lastName + '\'' +
                ", fax=" + faxNumber +
//                ", customerArolAuthorizations=" + customerArolAuthorizations +
                '}';
    }

    public String toLongString() {
        return "Cust{" +
                "webId='" + customerId + '\'' +
                ", email='" + email + '\'' +
                ", first='" + firstName + '\'' +
                ", last='" + lastName + '\'' +
                ", fax=" + faxNumber +
                ", \ncustomerArolAuthorizations=" + customerArolAuthorizations +
                '}';
    }

}