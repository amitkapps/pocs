package com.learning.spring.hello;

import java.lang.reflect.Field;

public class Employee {

	int id = -1;
	String firstName = "No First Name";
	String lastName = "No Last Name";
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer(super.toString());
		Field[] fields = getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			String fieldName = fields[i].getName();
			String fieldValue = "?";
			try {
				fieldValue = fields[i].get(this).toString();
			} catch (Exception e) {
				// ignore
			}
			sb.append("\n").append(fieldName).append(":").append(fieldValue);
		}
		
		return sb.toString();
	}
	
	public void sayHello(){
		System.out.println("Hello, my name is " + firstName + " " + lastName);
		try {
			Thread.sleep(1015);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
