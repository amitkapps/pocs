package hibernateSearch;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "MNS_OCEAN_EVENT_BOL_MT")
public class OceanEventBol {
	protected Long oceanEventBolId;
	protected OceanEvent oceanEvent;
	protected String sequenceNumber;
	protected String createUser;
	protected Date   createDate;
	protected String lastUpdateUser;
	protected Date   lastUpdateDate;
	
	@Id
    @Column(name = "OCEAN_EVENT_BOL_ID")
	public Long getOceanEventBolId() {
		return oceanEventBolId;
	}
	public void setOceanEventBolId(Long oceanEventBolId) {
		this.oceanEventBolId = oceanEventBolId;
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
	
	@Column(name = "SEQUENCE_NUMBER")
	public String getSequenceNumber() {
		return sequenceNumber;
	}
	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
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
	
	
}
