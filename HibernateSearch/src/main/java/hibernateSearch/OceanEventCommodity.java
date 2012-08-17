package hibernateSearch;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "MNS_OCEAN_COMMODITY_MT")
public class OceanEventCommodity {
	protected Long oceanCommodityId;
	protected OceanEvent oceanEvent;
	protected String description;
	protected String createUser;
	protected Date   createDate;
	protected String lastUpdateUser;
	protected Date   lastUpdateDate;
	
	@Id
    @Column(name = "OCEAN_COMMODITY_ID")
    public Long getOceanCommodityId() {
		return oceanCommodityId;
	}
	public void setOceanCommodityId(Long oceanCommodityId) {
		this.oceanCommodityId = oceanCommodityId;
	}
	
    @NaturalId
    @ManyToOne
    @JoinColumn( name = "OCEAN_EVENT_ID", referencedColumnName = "OCEAN_EVENT_ID")
	public OceanEvent getOceanEvent() {
		return oceanEvent;
	}
	
    @Column(name = "DESCRIPTION")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setOceanEvent(OceanEvent oceanEvent) {
		this.oceanEvent = oceanEvent;
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
	public String toString() {
		return description;
	}
	
}
