package com.amitk.java.clone;

import java.sql.Date;
import java.util.ArrayList;

public class CloneTest implements Cloneable{

	private String string = "str0";
	private int intval = 0;
	private Date date = new Date(System.currentTimeMillis() - 86400000);
	private boolean bool = false;
	private long longval = 0;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CloneTest ct = new CloneTest();
		
		ct.setString("str1");
		ct.setIntval(1);
		ct.setDate(null);
		ct.setBool(true);
		ct.setLongval(1);
		ArrayList al = new ArrayList();
		al.add(ct);
		System.out.println("Orig:\n" + al);
		
		//CloneTest clct = (CloneTest) ct.clone();
		ArrayList alct = (ArrayList) al.clone();
		ct.setDate(new Date(System.currentTimeMillis() - 864000000));
		
		System.out.println("Clone:\n" + alct);
		System.out.println("Orig:\n" + al);
	}
	
	public Object clone(){
		try {
			CloneTest clone = (CloneTest) super.clone();
			//clone.setDate((null == this.getDate()) ? null : (Date)this.getDate().clone());
			return clone;
		} catch (Exception e) {
			throw new RuntimeException("Exception Cloning object!", e);
		}
	}
	
	public CloneTest(){}

	public boolean isBool() {
		return bool;
	}

	public void setBool(boolean bool) {
		this.bool = bool;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getIntval() {
		return intval;
	}

	public void setIntval(int intval) {
		this.intval = intval;
	}

	public long getLongval() {
		return longval;
	}

	public void setLongval(long longval) {
		this.longval = longval;
	}

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	public String toString() {
		return 
		new StringBuffer()
		.append("str:" +string)
		.append(", int:" + intval)
		.append(",date:"+date)
		.append(", bool:"+bool )
		.append(", long:"+longval )
		.toString();
	}
}
