package com.learning.spring.hello;

import org.springframework.context.ApplicationContext;

public class HelloSpring {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext bf = SpringApplicationContext.getContext();
		System.out.println("isSingleton? " + bf.isSingleton("employee"));
		Employee employee = (Employee) bf.getBean("employee");
		employee.setFirstName("Amit");
		
		System.out.println(employee.toString());
		
		employee.sayHello();
	}

}
