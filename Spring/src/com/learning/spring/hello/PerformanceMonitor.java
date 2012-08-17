package com.learning.spring.hello;

import org.aspectj.lang.ProceedingJoinPoint;

public class PerformanceMonitor {

	public Object timeOperation(ProceedingJoinPoint pjp) throws Throwable{
		long startTime = System.currentTimeMillis();
		
		try {
			return pjp.proceed();
		}finally{
			System.out.println("Operation " + pjp.toLongString() + " took " + (System.currentTimeMillis() - startTime) + " millis.");
		}
		
	}
}
