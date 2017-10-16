package com.stackroute.datamunger;

import java.io.IOException;
import java.util.Scanner;
import com.stackroute.datamunger.query.DataTypeDefinitions;
import com.stackroute.datamunger.query.Header;
import com.stackroute.datamunger.reader.CsvQueryProcessor;


public class DataMunger {
	
	public static void main(String[] args) throws IOException{
		
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter a File Name:");
		String file = scan.nextLine();
		scan.close();
		//read the file name from the user
		
		CsvQueryProcessor cqp = new CsvQueryProcessor(file);
		/*
		 * create object of CsvQueryProcessor. We are trying to read from a file inside
		 * the constructor of this class. Hence, we will have to handle exceptions.
		 */
					
		Header headers = cqp.getHeader();
		DataTypeDefinitions types = cqp.getColumnType();
		//call getHeader() method to get the array of headers
		
		
		/*
		 * call getColumnType() method of CsvQueryProcessor class to retrieve the array
		 * of column data types which is actually the object of DataTypeDefinitions
		 * class
		 */
			for(String h: headers.getHeaders()) {
				System.out.println(h);
			}
		
		/*
		 * display the columnName from the header object along with its data type from
		 * DataTypeDefinitions object
		 */
			for(String h: types.getDataTypes()) {
				System.out.println(h);
			}
		
		
	}

}
