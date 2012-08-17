package org.springframework.sample.batch.example;

import org.springframework.batch.item.ItemReader;

/**
 * {@link ItemReader} with hard-coded input data.
 */
public class ExampleItemReader implements ItemReader<String> {
	
	private String[] input = {"Input Data", null};
	
	private int index = 0;
	
	/**
	 * Reads next record from input
	 */
	public String read() throws Exception {
		if (index < input.length) {
			return input[index++];
		}
		else {
			return null;
		}
		
	}

}
