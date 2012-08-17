package hbm;

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
@Table(name = "WEB_AUTHORIZATION")
public class CustomerArolAuthorization implements Serializable{

    private Long customerArolAuthorizationId;
    private String trade;
    private String partyType;
    private String serviceType;
    private Customer customer;
    private AddressRole addressRole;

    public static final String SERVICE_TYPE_SHIPMENT_TRACKING = "ST";

    @Id
    @Column(name = "WEBA_AUTHORIZATION_ID")
    public Long getCustomerArolAuthorizationId() {
        return customerArolAuthorizationId;
    }

    public void setCustomerArolAuthorizationId(Long customerArolAuthorizationId) {
        this.customerArolAuthorizationId = customerArolAuthorizationId;
    }

    @ManyToOne
    @JoinColumn(name = "WEBA_WEB_ID", referencedColumnName = "WEB_ID")
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @ManyToOne
    @JoinColumn(name = "WEBA_AROL_ID", referencedColumnName = "ADDRESS_ROLE_ID")
    public AddressRole getAddressRole() {
        return addressRole;
    }

    public void setAddressRole(AddressRole addressRole) {
        this.addressRole = addressRole;
    }

    @Column(name = "WEBA_TRADE")
    public String getTrade() {
        return trade;
    }

    public void setTrade(String trade) {
        this.trade = trade;
    }

    @Transient
    public String getTradeName(){
        if("F".equals(trade))
            return "Far East";
        else if("G".equals(trade))
            return "Guam";
        else if("H".equals(trade))
            return "Hawaii";
        else if("M".equals(trade))
            return "Mid Pacific";
        else
            return "UNKNOWN";
    }

    @Column(name = "WEBA_SHMT_PARTY_TYPE")
    public String getPartyType() {
        return partyType;
    }

    public void setPartyType(String partyType) {
        this.partyType = partyType;
    }

    @Transient
    public String getPartyTypeName(){
        if("02".equals(partyType))
            return "Shipper";
        else if("03".equals(partyType))
            return "Consignee";
        else if("22".equals(partyType))
            return "Notify Party";
        else if("29".equals(partyType))
            return "Also Notify Party";
        else if("30".equals(partyType))//TODO: check correct code
            return "Freight Forwarder";
        else
            return "UNKNOWN";
    }

    @Column(name = "WEBA_SERVICE_CODE")
    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public static final String ANY_TRADE = "_ANY_TRADE";
    public static final String ANY_PARTY_TYPE = "_ANY_PARTY_TYPE";
    public static final String ANY_SERVICE_TYPE = "_ANY_SERVICE_TYPE";

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerArolAuthorization)) return false;

        CustomerArolAuthorization that = (CustomerArolAuthorization) o;

        if (!addressRole.equals(that.addressRole)) return false;
        //only to support collection filtering by wild card criteria- we won't be storing codes for ANY in DB
        if (!ANY_PARTY_TYPE.equals(partyType) && !ANY_PARTY_TYPE.equals(that.partyType) && !partyType.equals(that.partyType)) return false;
        if (!ANY_SERVICE_TYPE.equals(serviceType) && !ANY_SERVICE_TYPE.equals(that.serviceType) && !serviceType.equals(that.serviceType)) return false;
        if (!ANY_TRADE.equals(trade) && !ANY_TRADE.equals(that.trade) && !trade.equals(that.trade)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        //Since trade/party type/service type can be ANY we can't include it in hashcode
        return addressRole.hashCode();
    }

    @Override
    public String toString() {
        return "auth{" +
                addressRole.getId() +
                ", '" + trade + '\'' +
                ", '" + partyType + '\'' +
                '}';
    }

    public String toLongString(){
        return "Auth{" +
                "cust=" + customer.getCustomerId() +
                ", arol=" + addressRole.getId() +
                ", trade='" + trade + '\'' +
                ", partyType='" + partyType + '\'' +
                '}';
    }

}