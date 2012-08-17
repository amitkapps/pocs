package com.amitk.xml.xstream.hello;

import java.util.Date;
import java.util.List;

public class Unit {

	int vinsightId;
	String shipper;
	String consignee;
	Date receiveDate;
	InventoryItem inventoryItem;
	List holds;
	
	public Unit(int vinsightId, String shipper, String consignee, Date receiveDate){
		this.vinsightId = vinsightId;
		this.shipper = shipper;
		this.consignee = consignee;
		this.receiveDate = receiveDate;
	}
}
