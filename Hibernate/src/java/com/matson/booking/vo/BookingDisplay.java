/**
 * MATSON Application Copyright (c) Matson Shipping and Navigation. All rights reserved.
 * This software is the confidential and proprietary information of Matson Shipping and Navigation,
 * Inc. ("Confidential Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered into with Matson.
 */
package com.matson.booking.vo;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * This value object is used to hold Booking display information for the booking dashbord page.
 * @author Kumar Ganapathy
 * @date 04/17/2007 
 * 
 */
public class BookingDisplay implements Serializable{

	private int 	bookingId;
	private String 	bookingNumber;
	private Date 	bookedDate;
	private String 	origin;
	private String 	destination;
	private String 	vvd;
	private Date 	sailDate;
	private String 	consignee;
	
	
	
	/**
	 * @return the bookingId
	 */
	public int getBookingId() {
		return bookingId;
	}
	/**
	 * @param bookingId the bookingId to set
	 */
	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}
	/**
	 * @return the bookedDate
	 */
	public Date getBookedDate() {
		return bookedDate;
	}
	/**
	 * @param bookedDate the bookedDate to set
	 */
	public void setBookedDate(Date bookedDate) {
		this.bookedDate = bookedDate;
	}
	/**
	 * @return the bookingNumber
	 */
	public String getBookingNumber() {
		return bookingNumber;
	}
	/**
	 * @param bookingNumber the bookingNumber to set
	 */
	public void setBookingNumber(String bookingNumber) {
		this.bookingNumber = bookingNumber;
	}
	/**
	 * @return the destination
	 */
	public String getDestination() {
		return destination;
	}
	/**
	 * @param destination the destination to set
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}
	/**
	 * @return the origin
	 */
	public String getOrigin() {
		return origin;
	}
	/**
	 * @param origin the origin to set
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	/**
	 * @return the sailDate
	 */
	public Date getSailDate() {
		return sailDate;
	}
	/**
	 * @param sailDate the sailDate to set
	 */
	public void setSailDate(Date sailDate) {
		this.sailDate = sailDate;
	}
	/**
	 * @return the vvd
	 */
	public String getVvd() {
		return vvd;
	}
	/**
	 * @param vvd the vvd to set
	 */
	public void setVvd(String vvd) {
		this.vvd = vvd;
	}
	
	/**
	 * @return the consignee
	 */
	public String getConsignee() {
		return consignee;
	}
	/**
	 * @param consignee the consignee to set
	 */
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public String toString() {
	    return ToStringBuilder.reflectionToString(this,
	        ToStringStyle.MULTI_LINE_STYLE);
	  }
	
}
