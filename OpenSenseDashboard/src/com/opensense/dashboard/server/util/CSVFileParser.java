package com.opensense.dashboard.server.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ibm.icu.text.SimpleDateFormat;
import com.opensense.dashboard.shared.Value;


public class CSVFileParser {

	public static List<Value> parseValues() throws IOException{
		List<Value> values = new ArrayList<>();
		BufferedReader br = new BufferedReader(new FileReader(new File(System.getenv("tmp"), SessionUser.getInstance().getUserId() + "_" + SessionUser.getInstance().getUsername()+".txt")));
		String line;
		while ((line = br.readLine()) != null) {
			Value value = new Value();
			if(line.contains(";")) {
				String[] splitted = line.split(";");
				if(splitted.length != 2) {
					continue;
				}
				Date date = parseDate(splitted[0]);
				if (date == null) {
					continue;
				}
				value.setTimestamp(date);
				if(!splitted[1].matches("^[0-9|.]*$")) { // only numbers and one dot
					continue;
				}
				value.setNumberValue(Double.valueOf(splitted[1]));
				values.add(value);
			}
		}
		br.close();
		new File(System.getenv("tmp"), SessionUser.getInstance().getUserId() + "_" + SessionUser.getInstance().getUsername()+".txt").delete();
		return values;
	}

	private static Date parseDate(String string) {
		String parseFormat = DateUtils.determineDateFormat(string);
		if(parseFormat==null) {
			return null;
		}
		Date date = null;
		try {
			date = new SimpleDateFormat(parseFormat).parse(string);
		}catch(ParseException e) {
			date = null;
		}
		return date;
	}

}
