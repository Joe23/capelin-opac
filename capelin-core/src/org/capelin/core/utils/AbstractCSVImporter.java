package org.capelin.core.utils;

import java.io.FileReader;
import java.io.IOException;

import org.capelin.core.models.CapelinRecord;

import au.com.bytecode.opencsv.CSVReader;

public abstract class AbstractCSVImporter {
	protected FileReader input;

	/**
	 * Assume you are going to overwrite this method to setup the input stream
	 */
	protected abstract void setUp() throws IOException;

	protected int importData() throws IOException {
		CSVReader reader = new CSVReader(input);
		String[] nextLine;
		int index = 0;
		// Read File Line By Line
		while ((nextLine = reader.readNext()) != null) {
			buildRecord(nextLine);
			index++;
		}
		// Close the input stream
		reader.close();		
		return index;
	}

	public abstract CapelinRecord buildRecord(String[] data);

	public void run() throws Exception {
		setUp();
		long start = System.currentTimeMillis();
		int total = importData();
		input.close();
		System.out.println("Total Records: " + total);
		System.out.println("Time: " + (System.currentTimeMillis() - start));
		System.exit(0);
	}
	
	public void run(boolean flag) throws Exception {
		setUp();
		long start = System.currentTimeMillis();
		int total;
		if(flag){
			total = importData();
		}else{
			total = displayTags();
		}		 
		input.close();
		System.out.println("Total Records: " + total);
		System.out.println("Time: " + (System.currentTimeMillis() - start));
		System.exit(0);
	}
	
	protected int displayTags() throws IOException{
		CSVReader reader = new CSVReader(input);
		String[] nextLine;
		int index = 0;
		TagBuilder tagBuilder = new TagBuilder();
		while ((nextLine = reader.readNext()) != null) {
			index++;
			tagBuilder.build(nextLine);
		}
		tagBuilder.display(true);
		reader.close();
		return index;
	}
	
	@Override
	protected void finalize() throws Throwable{
		super.finalize();
		input.close();
	}
}
