package com.stackroute.datamunger.reader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;
import com.stackroute.datamunger.query.DataTypeDefinitions;
import com.stackroute.datamunger.query.Header;

public class CsvQueryProcessor extends QueryProcessingEngine {

	private static ArrayList<String> lines = new ArrayList<String>();
	
	public CsvQueryProcessor(String fileName) throws FileNotFoundException {
		String line = null;
		BufferedReader reader = null;
		reader = new BufferedReader(new FileReader(fileName));
		try {
			while((line = reader.readLine())!=null) {
				lines.add(line);
			}
			reader.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
		}

/*
* implementation of getHeader() method. We will have to extract the headers
* from the first line of the file.
*/
@Override
	public Header getHeader() throws IOException {
		Header header = new Header();
		header.setHeader(lines.get(0).toString().split(","));
		header.getHeaders();
		return header;
	}

/**
* This method will be used in the upcoming assignments
*/
@Override
public void getDataRow() {

}

/*
* implementation of getColumnType() method. To find out the data types, we will
* read the first line from the file and extract the field values from it. In
* the previous assignment, we have tried to convert a specific field value to
* Integer or Double. However, in this assignment, we are going to use Regular
* Expression to find the appropriate data type of a field. Integers: should
* contain only digits without decimal point Double: should contain digits as
* well as decimal point Date: Dates can be written in many formats in the CSV
* file. However, in this assignment,we will test for the following date
* formats('dd/mm/yyyy',
* 'mm/dd/yyyy','dd-mon-yy','dd-mon-yyyy','dd-month-yy','dd-month-yyyy','yyyy-mm-dd')
*/
@Override
	public DataTypeDefinitions getColumnType() throws IOException {
	DataTypeDefinitions d = new DataTypeDefinitions();
	d.setDatatype(findColumnType());
	d.getDataTypes();
	return d;
	}

private String[] findColumnType(){
	String[] dtypes = LengthLevel();
	Object[] o = new Object[dtypes.length];
	for(int i=0;i<dtypes.length;i++) {
		try {
			Object I = Integer.parseInt(dtypes[i]);
			o[i] = I.getClass().getName();
		}catch(NumberFormatException e) {
			try {
				Object I = Double.parseDouble(dtypes[i]);
				o[i]=I.getClass().getName();
			}catch(NumberFormatException ex) {
				if(dtypes[i].contains("/")|| dtypes[i].contains("-")||dtypes[i].contains(".")) {
					Object I = checkdate(dtypes[i]);
					//System.out.println("In Date" +I);
					o[i] = I.getClass().getName();
				}else { 
					if(dtypes[i].equals("s")) {
						Object obj = new Object();
						o[i] = obj.getClass().getName();
					}else {
					o[i]= dtypes[i].getClass().getName();
				}
			}
			}
		}
	}
	for(int i=0;i<o.length;i++) {
		dtypes[i]= (String) o[i];
		//System.out.println(dtypes[i]);
	}
	return dtypes;
	}
	

	private String[] LengthLevel() {
		String[] head = new String[lines.get(0).toString().split(",").length];
		String[] type = lines.get(1).toString().split(",");
		for(int i =0;i<head.length;i++) {
			try {
				head[i] = type[i];
			}catch(Exception e) {
				head[i]= "s";
			}
		}
		return head;
	}
	
	private Date checkdate(String d) {
		Date date =null;
		System.out.println(d);
		try {
			if(Pattern.matches("([0-9]{2}/[0-9]{2}/[0-9]{4})", d)) {
				date = new SimpleDateFormat("dd/MM/yyyy").parse(d);
			}else if(Pattern.matches("([0-9]{2}/[0-9]{2}/[0-9]{4})", d)){
				date = new SimpleDateFormat("MM/dd/yyyy").parse(d);
			}else if(Pattern.matches("([0-9]{2}-[a-z]{3}-[0-9]{2})", d)){
				date = new SimpleDateFormat("dd-MMM-yy").parse(d);
			}else if(Pattern.matches("([0-9]{2}-[a-z]{3}-[0-9]{4})", d)){
				date = new SimpleDateFormat("dd-MMM-yyyy").parse(d);
			}else if(Pattern.matches("([0-9]{2}-[a-z]{4}-[0-9]{2})", d)){
				date = new SimpleDateFormat("dd-MMMM-yy").parse(d);
			}else if(Pattern.matches("([0-9]{2}-[a-z]{4}-[0-9]{4})", d)){
				date = new SimpleDateFormat("dd-MMMM-yyyy").parse(d);
			}else if(Pattern.matches("([0-9]{4}-[0-9]{2}-[0-9]{2})", d)) {
				date = new SimpleDateFormat("yyyy-MM-dd").parse(d);
			}else {
				return null;
			}
		}catch(Exception e) {
			//System.out.println("Check the Date Format ,achlps");
			e.printStackTrace();
		}
		return date;
	}
	
}

