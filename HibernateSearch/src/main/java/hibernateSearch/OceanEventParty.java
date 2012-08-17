package hibernateSearch;

import org.hibernate.annotations.NaturalId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "MNS_OCEAN_EVENT_PARTY_MT")
public class OceanEventParty {
	protected Long oceanEventPartyId;
	protected OceanEvent oceanEvent;
	protected Long addressRoleId;
	protected String contact;
	protected String contactPhone;
	protected String typeCode;
	protected Long organizationId;
	protected String organizationName;
	protected String reference;
	protected String createUser;
	protected Date   createDate;
	protected String lastUpdateUser;
	protected Date   lastUpdateDate;
	
	@Id
    @Column(name = "OCEAN_EVENT_PARTY_ID")
    public Long getOceanEventPartyId() {
		return oceanEventPartyId;
	}
	public void setOceanEventPartyId(Long oceanEventPartyId) {
		this.oceanEventPartyId = oceanEventPartyId;
	}
	
    @NaturalId
    @ManyToOne
    @JoinColumn( name = "OCEAN_EVENT_ID", referencedColumnName = "OCEAN_EVENT_ID")
	public OceanEvent getOceanEvent() {
		return oceanEvent;
	}
	public void setOceanEvent(OceanEvent oceanEvent) {
		this.oceanEvent = oceanEvent;
	}
	
	@Column(name = "ADDRESS_ROLE_ID")
	public Long getAddressRoleId() {
		return addressRoleId;
	}
	public void setAddressRoleId(Long addressRoleId) {
		this.addressRoleId = addressRoleId;
	}
	
	@Column(name = "CONTACT")
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	
	@Column(name = "CONTACT_PHONE")
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	
	@Column(name = "ORGANIZATION_ID")
	public Long getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}
	
	@Column(name = "ORGANIZATION_NAME")
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	
	@Column(name = "REFERENCE")
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	
	@Column(name = "TYPE_CODE")
    @Field(index = Index.UN_TOKENIZED)
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	
	@Column(name = "CREATE_USER")
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	
	@Column(name = "CREATE_DATE")
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	@Column(name = "LAST_UPDATE_USER")
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
	
	@Column(name = "LAST_UPDATE_DATE")
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	
	@Override
	public int hashCode() {
		return addressRoleId.intValue()+typeCode.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof OceanEventParty) {
			if(this.addressRoleId == null || this.typeCode == null) return false;
			
			OceanEventParty party = (OceanEventParty)o;
			if(this.addressRoleId.equals(party.addressRoleId) 
					&& this.typeCode.equals(party.typeCode)) return true;
		} 
		
		return false;
		
	}
	
	@Override
	public String toString() {
		return "OceanEventParty["+typeCode+"]"+addressRoleId;
	}
}
