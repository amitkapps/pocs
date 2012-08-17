package hibernateSearch;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;
import org.hibernate.search.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Entity
@Table(name = "MNS_OCEAN_EVENT_MT")
@Indexed
public class OceanEvent implements Comparable<OceanEvent>  {
	final static Logger log = LoggerFactory.getLogger(OceanEvent.class);

	protected List<OceanEventBol> bolList;
	protected List<OceanEventCommodity> commodityList;
	protected List<OceanEventParty> partyList;

	protected Long oceanEventId;
	protected String tradeCode;
	protected Long containerId;
	protected String containerNumber;
	protected String vesselCode;
	protected String vesselName;
	protected String voyage;
	protected String direction;
	protected String physicalLocationCode;
	protected String emptyFullFlag;
	protected String containerTypeCode;
	protected Double containerLength;
	protected String containerHeight;
	protected String checkDigit;
	protected String temperature;
	protected String temperatureUom;
	protected Double netWeight;
	protected Boolean isInBond;
	protected String releaseToPartyName;
	protected String originPortName;
	protected String destinationPortName;
	protected String originPortCode;
	protected String destinationPortCode;
	protected String bolOrigin;
	protected String bolDestination;
	protected String loadServiceCode;
	protected String dischargeServiceCode;
	protected String sealNumber;
	protected String availableDate;
	protected String locationCode;
	protected String bolDestinationCityCode;
	protected String toLocationCityCode;
	protected String sightingCityCode;
	protected String eventCode;
	protected String actionCode;
	protected String createUser;
	protected Date   createDate;
	protected String lastUpdateUser;
	protected Date   lastUpdateDate;
	//protected Date   etaDate;
	protected Date   eventDate;
	protected Date	 actualAvailableDate;
	protected String bookingNumber;
	protected Long	 bookingId;
	protected Boolean isBeyondTolerance;
	protected String eventPort;
	protected Boolean hazard;

	@OneToMany (cascade = {CascadeType.REMOVE}, orphanRemoval = true, mappedBy = "oceanEvent", targetEntity = OceanEventBol.class )
	public List<OceanEventBol> getBolList() {
		return bolList;
	}
	public void setBolList(List<OceanEventBol> bolList) {
		this.bolList = bolList;
	}

	@OneToMany(cascade = {CascadeType.REMOVE}, orphanRemoval = true, mappedBy = "oceanEvent", targetEntity = OceanEventCommodity.class)

	public List<OceanEventCommodity> getCommodityList() {
		return commodityList;
	}
	public void setCommodityList(List<OceanEventCommodity> commodityList) {
		this.commodityList = commodityList;
	}

	@OneToMany(cascade = {CascadeType.REMOVE}, orphanRemoval = true, mappedBy = "oceanEvent", targetEntity = OceanEventParty.class)
	@BatchSize(size=10)
    @IndexedEmbedded
	public List<OceanEventParty> getPartyList() {
		return partyList;
	}
	public void setPartyList(List<OceanEventParty> partyList) {
		this.partyList = partyList;
	}

	@Id
    @Column(name = "OCEAN_EVENT_ID")
	public Long getOceanEventId() {
		return oceanEventId;
	}
	public void setOceanEventId(Long oceanEventId) {
		this.oceanEventId = oceanEventId;
	}

	@Column(name = "TRADE_CODE")
	public String getTradeCode() {
		return tradeCode;
	}
	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}

	@Column(name = "CONTAINER_ID")
	public Long getContainerId() {
		return containerId;
	}
	public void setContainerId(Long containerId) {
		this.containerId = containerId;
	}

	@Column(name = "CONTAINER_NUMBER")
	public String getContainerNumber() {
		return containerNumber;
	}
	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}

	@Column(name = "VESSEL_CODE")
	public String getVesselCode() {
		return vesselCode;
	}
	public void setVesselCode(String vesselCode) {
		this.vesselCode = vesselCode;
	}

	@Column(name = "VESSEL_NAME")
	public String getVesselName() {
		return vesselName;
	}
	public void setVesselName(String vesselName) {
		this.vesselName = vesselName;
	}

	@Column(name = "VOYAGE")
	public String getVoyage() {
		return voyage;
	}
	public void setVoyage(String voyage) {
		this.voyage = voyage;
	}

	@Column(name = "DIRECTION")
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}

	@Column(name = "PHYSICAL_LOCATION_CODE")
	public String getPhysicalLocationCode() {
		return physicalLocationCode;
	}
	public void setPhysicalLocationCode(String physicalLocationCode) {
		this.physicalLocationCode = physicalLocationCode;
	}

	@Column(name = "EMPTY_FULL")
	public String getEmptyFullFlag() {
		return emptyFullFlag;
	}
	public void setEmptyFullFlag(String emptyFullFlag) {
		this.emptyFullFlag = emptyFullFlag;
	}

	@Column(name = "CONTAINER_TYPE_CODE")
	public String getContainerTypeCode() {
		return containerTypeCode;
	}
	public void setContainerTypeCode(String containerTypeCode) {
		this.containerTypeCode = containerTypeCode;
	}

	@Column(name = "CONTAINER_LENGTH_FEET")
	public Double getContainerLength() {
		return containerLength;
	}
	public void setContainerLength(Double containerLength) {
		this.containerLength = containerLength;
	}

	@Column(name = "CONTAINER_HEIGHT_CODE")
	public String getContainerHeight() {
		return containerHeight;
	}
	public void setContainerHeight(String containerHeight) {
		this.containerHeight = containerHeight;
	}

	@Column(name = "CHECK_DIGIT")
	public String getCheckDigit() {
		return checkDigit;
	}
	public void setCheckDigit(String checkDigit) {
		this.checkDigit = checkDigit;
	}

	@Column(name = "TEMPERATURE")
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	@Column(name = "TEMPERATURE_UOM")
	public String getTemperatureUom() {
		return temperatureUom;
	}
	public void setTemperatureUom(String temperatureUom) {
		this.temperatureUom = temperatureUom;
	}

	@Column(name = "NET_WEIGHT_POUNDS")
	public Double getNetWeight() {
		return netWeight;
	}
	public void setNetWeight(Double netWeight) {
		this.netWeight = netWeight;
	}

	@Column(name = "IS_IN_BOND")
	@Type(type = "org.hibernate.type.YesNoType")
	public Boolean getIsInBond() {
		return isInBond;
	}
	public void setIsInBond(Boolean isInBond) {
		this.isInBond = isInBond;
	}

	@Column(name = "RELEASE_TO_PARTY_NAME")
	public String getReleaseToPartyName() {
		return releaseToPartyName;
	}
	public void setReleaseToPartyName(String releaseToPartyName) {
		this.releaseToPartyName = releaseToPartyName;
	}

	@Column(name = "ORIGIN_PORT_NAME")
	public String getOriginPortName() {
		return originPortName;
	}
	public void setOriginPortName(String originPortName) {
		this.originPortName = originPortName;
	}

	@Column(name = "DESTINATION_PORT_NAME")
	public String getDestinationPortName() {
		return destinationPortName;
	}
	public void setDestinationPortName(String destinationPortName) {
		this.destinationPortName = destinationPortName;
	}

	@Column(name = "ORIGIN_PORT_CODE")
	public String getOriginPortCode() {
		return originPortCode;
	}
	public void setOriginPortCode(String originPortCode) {
		this.originPortCode = originPortCode;
	}

	@Column(name = "DESTINATION_PORT_CODE")
	public String getDestinationPortCode() {
		return destinationPortCode;
	}
	public void setDestinationPortCode(String destinationPortCode) {
		this.destinationPortCode = destinationPortCode;
	}

	@Column(name = "BOL_ORIGIN")
	public String getBolOrigin() {
		return bolOrigin;
	}
	public void setBolOrigin(String bolOrigin) {
		this.bolOrigin = bolOrigin;
	}

	@Column(name = "BOL_DESTINATION")
	public String getBolDestination() {
		return bolDestination;
	}
	public void setBolDestination(String bolDestination) {
		this.bolDestination = bolDestination;
	}

	@Column(name = "LOAD_SERVICE_CODE")
	public String getLoadServiceCode() {
		return loadServiceCode;
	}
	public void setLoadServiceCode(String loadServiceCode) {
		this.loadServiceCode = loadServiceCode;
	}

	@Column(name = "DISCHARGE_SERVICE_CODE")
	public String getDischargeServiceCode() {
		return dischargeServiceCode;
	}
	public void setDischargeServiceCode(String dischargeServiceCode) {
		this.dischargeServiceCode = dischargeServiceCode;
	}

	@Column(name = "SEAL_NUMBER")
	public String getSealNumber() {
		return sealNumber;
	}
	public void setSealNumber(String sealNumber) {
		this.sealNumber = sealNumber;
	}

	@Column(name = "AVAILABLE_DATE")
	public String getAvailableDate() {
		return availableDate;
	}
	public void setAvailableDate(String availableDate) {
		this.availableDate = availableDate;
	}

	@Column(name = "LOCATION_CODE", insertable=false, updatable=false)
	public String getLocationCode() {
		return locationCode;
	}
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	@Column(name = "ACTION_CODE")
	public String getActionCode() {
		return actionCode;
	}
	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}


	@Column(name = "BOL_DESTINATION_CITY_CODE")
	public String getBolDestinationCityCode() {
		return bolDestinationCityCode;
	}
	public void setBolDestinationCityCode(String bolDestinationCityCode) {
		this.bolDestinationCityCode = bolDestinationCityCode;
	}

	@Column(name = "TO_LOCATION_CITY_CODE")
	public String getToLocationCityCode() {
		return toLocationCityCode;
	}
	public void setToLocationCityCode(String toLocationCityCode) {
		this.toLocationCityCode = toLocationCityCode;
	}

	@Column(name = "SIGHTING_CITY_CODE")
	public String getSightingCityCode() {
		return sightingCityCode;
	}
	public void setSightingCityCode(String sightingCityCode) {
		this.sightingCityCode = sightingCityCode;
	}

	@Column(name = "EVENT_CODE")
    @Field(index= Index.UN_TOKENIZED, store= Store.NO)
	public String getEventCode() {
		return eventCode;
	}
	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}


	@Column(name = "EVENT_DATE")
    @Field(index= Index.UN_TOKENIZED, store= Store.NO)
    @DateBridge(resolution=Resolution.SECOND)
	public Date getEventDate() {
		return eventDate;
	}
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	@Column(name = "ACTUAL_AVAILABLE_DATE")
	public Date getActualAvailableDate() {
		return actualAvailableDate;
	}
	public void setActualAvailableDate(Date actualAvailableDate) {
		this.actualAvailableDate = actualAvailableDate;
	}

	@Column(name = "BOOKING_NUMBER")
	public String getBookingNumber() {
		return bookingNumber;
	}
	public void setBookingNumber(String bookingNumber) {
		this.bookingNumber = bookingNumber;
	}

	@Column(name = "BOOKING_ID")
	public Long getBookingId() {
		return bookingId;
	}
	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}

	@Column(name = "IS_BEYOND_TOLERANCE")
	@Type(type = "org.hibernate.type.YesNoType")
	public Boolean isBeyondTolerance() {
		return isBeyondTolerance;
	}
	public void setBeyondTolerance(Boolean isBeyondTolerance) {
		this.isBeyondTolerance = isBeyondTolerance;
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

	@Column(name = "hazardous_ind")
	@Type(type = "org.hibernate.type.YesNoType")
	public Boolean getHazardInd() {
		return hazard;
	}
	public void setHazardInd(Boolean hazard) {
		this.hazard = hazard;
	}


	@Transient
	public String getHazard() {
		if(hazard != null && hazard) return "HAZ";
		return null;
	}
	public void setHazard(String hazard) {

	}

	@Transient
	public OceanEventParty getShipper() {
		if(getPartyList() == null) return null;
		Iterator<OceanEventParty> iter = getPartyList().iterator();
		while(iter.hasNext()) {
			OceanEventParty party = iter.next();
			if("02".equals(party.typeCode)) {
				return party;
			}
		}

		return null;
	}
	public void setShipper() {
		throw new UnsupportedOperationException();
	}

	@Transient
	public OceanEventParty getCnee() {
		if(getPartyList() == null) return null;
		Iterator<OceanEventParty> iter = getPartyList().iterator();
		while(iter.hasNext()) {
			OceanEventParty party = iter.next();
			if("03".equals(party.typeCode)) {
				return party;
			}
		}

		return null;
	}
	public void setCnee() {
		throw new UnsupportedOperationException();
	}

	@Transient
	public String getShipperOrgName() {
		OceanEventParty party = getShipper();
		if(party == null ) return null;
		return party.getOrganizationName();
	}
	public void setShipperOrgName(String org) {
		OceanEventParty party = getShipper();
		if(party != null ) party.setOrganizationName(org);
	}

	@Transient
	public String getCneeOrgName() {
		OceanEventParty party = getCnee();
		if(party == null ) return null;
		return party.getOrganizationName();
	}
	public void setCneeOrgName(String org) {
		OceanEventParty party = getCnee();
		if(party != null ) party.setOrganizationName(org);
	}

	@Transient
	public String getShipperReference() {
		OceanEventParty party = getShipper();
		if(party == null ) return null;
		return party.getReference();
	}
	public void setShipperReference(String reference) {
		OceanEventParty party = getShipper();
		if(party != null ) party.setReference(reference);
	}

	@Transient
	public String getCneeReference() {
		OceanEventParty party = getCnee();
		if(party == null ) return null;
		return party.getReference();
	}
	public void setCneeReference(String reference) {
		OceanEventParty party = getCnee();
		if(party != null ) party.setReference(reference);
	}

	@Transient
	public String getCommodity() {
		if(getCommodityList() == null) return null;
		StringBuffer buffer = new StringBuffer();
		Iterator<OceanEventCommodity> iter = getCommodityList().iterator();
		if(iter.hasNext()) buffer.append(iter.next());
		while(iter.hasNext()) {
			buffer.append(",");
			buffer.append(iter.next());
		}

		return buffer.toString();
	}
	public void setCommodity() {
		throw new UnsupportedOperationException();
	}
	
	@Transient
	public String getVvd() {
		return getVesselCode() + getVoyage() + getDirection();
	}
	
	public String toString() {
		return "OceanEvent"+this.getOceanEventId();
	}
	
	@Transient
	public String getMaxSequence() {
		//log.debug("bolList ="+bolList+" for "+this.containerNumber);
		if(bolList == null ) return null;
		
		String seq = null;
		int seqNum = -1;
		try {
		Iterator<OceanEventBol> iter = bolList.iterator();
		while(iter.hasNext()) {
			OceanEventBol bol = iter.next();
			String currentSeq = bol.getSequenceNumber();
			int currentNum = Integer.parseInt(currentSeq);
			if(currentNum > seqNum) {
				//log.debug("currentNum ="+currentSeq);
				seq = currentSeq;
				seqNum = currentNum;
				
			}
		}
		} catch (Exception e) {
			log.error("Error reading bol",e);
		}
		//log.debug("seq ="+seq);
		return seq;
	}
	

	public void setMaxSequence() {
		
	}
	
	public int compareTo(OceanEvent event) {
		return this.getOceanEventId().intValue() - event.getOceanEventId().intValue();
	}
	
	public boolean equals(Object o) {
		if(o instanceof OceanEvent && compareTo((OceanEvent)o) == 0 ) return true;
		return false;
	}
	
	public int hashCode() {
		return this.getOceanEventId().hashCode();
	}
	
}
