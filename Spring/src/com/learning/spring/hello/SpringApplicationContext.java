package com.learning.spring.hello;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringApplicationContext {

	private static ApplicationContext appCtx = null;
	public static ApplicationContext getContext(){
		if(null == appCtx){
			appCtx = new ClassPathXmlApplicationContext("beans.xml");
		}
			
		return appCtx;
	}
}
