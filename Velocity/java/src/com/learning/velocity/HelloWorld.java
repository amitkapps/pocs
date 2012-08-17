package com.learning.velocity;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.Properties;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

public class HelloWorld {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try{
			//load the velocity properties file
		    Properties p = new Properties();
		    InputStream is = Thread.currentThread().getClass().getResourceAsStream("/conf/velocity/velocity.properties");
		    p.load(is);
			
			//initialized the velocity engine with the loaded properties
			Velocity.init(p);
			
			//Create the context and put data used by the template in it
			VelocityContext vc = new VelocityContext();
			
			vc.put("name", "Amit");
			vc.put("company", "Matson Navigation Co");
			
			//Render the template
			StringWriter sw = new StringWriter();
			Velocity.mergeTemplate("conf/velocity/templates/HelloWorld.vm", vc, sw);
			
			System.out.println(sw);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}

	}

}
