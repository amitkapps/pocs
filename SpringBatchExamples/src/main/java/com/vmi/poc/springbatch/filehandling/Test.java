package com.vmi.poc.springbatch.filehandling;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class Test {


	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		
		System.out.println(System.getProperty("user.dir"));
		FileSystemXmlApplicationContext ctx = new FileSystemXmlApplicationContext("src/main/resources/vmi-context.xml");
		
		FlatFileItemReader<FieldSet> fileReader = (FlatFileItemReader<FieldSet>)ctx.getBean("fileReader");
		fileReader.open(new ExecutionContext());
		FieldSet str = null;
		while (null != (str = fileReader.read())){
			System.out.println(str);	
		}
		
		
	}

}
