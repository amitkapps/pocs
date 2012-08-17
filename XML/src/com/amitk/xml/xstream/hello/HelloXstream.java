package com.amitk.xml.xstream.hello;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.thoughtworks.xstream.mapper.Mapper;

public class HelloXstream {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		toXML();
		//fromXML();
	}

	public static void fromXML(){
		try{
			String xml = "<unit vinsightId=\"10094016\">  <shipper>Amitk Shipper</shipper>  <consignee>Amitk Consignee</consignee>  <receiveDate format=\"MM/dd/yyyy\">06/08/2007</receiveDate>  <inventoryItem>    <itemId>1000</itemId>    <itemLocation>LAX</itemLocation>  </inventoryItem></unit>";
			
			XStream xs = new XStream();
			xs.registerConverter(new DateConverter());
			xs.alias("unit", Unit.class);
			//xs.aliasField("vinsight-id", Unit.class, "vinsightId");
			xs.useAttributeFor(Unit.class, "vinsightId");
			xs.alias("inventory-item", InventoryItem.class);
	
			Unit unit = (Unit)xs.fromXML(xml);
			System.out.println(xs.toXML(unit));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
		

	public static void toXML(){
		try {
			//configure xstream
			XStream xs = new XStream(null, MyMapper.getMyMapperInstance(), new XppDriver());
			
			xs.registerConverter(new DateConverter());
			xs.alias("unit", Unit.class);
			//xs.alias("inventory-item", InventoryItem.class);
			//xs.useAttributeFor(Unit.class, "vinsightId");
			//xs.aliasAttribute(Unit.class, "vinsightId", "vinsight-id");
			//xs.aliasField("receive-date", Unit.class, "receiveDate");
			
			//create objects
			Unit unit = new Unit(10094016, "Amitk Shipper", "Amitk Consignee", new Date(System.currentTimeMillis()));
			unit.inventoryItem = new InventoryItem(1000, "LAX");
			unit.holds = Arrays.asList(new Object[]{"hold1","hold2","hold3", "<what about this?!&>"});
			
			String xml = xs.toXML(unit);
			System.out.println(xml);
			//xml = "<unit>  <vinsight-id>10094016</vinsight-id>  <shipper>Amitk Shipper</shipper>  <consignee>Amitk Consignee</consignee>  <receive-date format=\"MMM/dd/yyyy hh:mm:ss\">Jun/08/2007 07:15:51</receive-date>  <inventory-item>    <item-id>1000</item-id>    <item-location>LAX</item-location>  </inventory-item>  <holds class=\"java.util.Arrays$ArrayList\">    <a>      <string>hold1</string>      <string>hold2</string>      <string>hold3</string>    </a>  </holds>  <unknown unknown-attrib=\"TBD\"/></unit>";
			//System.out.println(xs.toXML(xs.fromXML(xml)));
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
