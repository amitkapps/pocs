package com.learning.spring.hello;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class CreatePostProcessor implements BeanPostProcessor {

	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		System.out.println("Bean " + beanName + " initialized: " );
		return bean;
	}

	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		// TODO Auto-generated method stub
		return bean;
	}

}
