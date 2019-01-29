package com.opensense.dashboard.server.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.opensense.dashboard.shared.Value;


public class CSVFileParser {

	private static final String YEAR_REGEX = "^(19|20)\\d{2}$";
	private static final String MONTH_REGEX = "^(0+[1-9]|1[012])$";
	private static final String DAY_REGEX = "^(0+[1-9]|1[0-9]|2[0-9]|3[001])$";
	private static final String HOUR_REGEX = "^(0+[1-9]|1[0-9]|2[0-3]|00)$";
	private static final String MIN_SEC_REGEX = "^(0+[1-9]|1[1-9]|2[1-9]|3[1-9]|4[1-9]|5[1-9]|00)$";

	public static List<Value> parseValues() throws IOException{
		List<Value> values = new ArrayList<>();
		BufferedReader br = new BufferedReader(new FileReader(new File(System.getenv("tmp"), SessionUser.getInstance().getUserId() + "_" + SessionUser.getInstance().getUsername()+".txt")));
		String line;
		while ((line = br.readLine()) != null) {
			Value value = new Value();
			if(line.contains(";")) {
				String[] splitted = line.split(";");
				if(splitted.length != 2) {
					System.out.println("continue1");
					continue;
				}
				Date date = parseDate(splitted[0]);
				if (date == null) {
					System.out.println("continue2");
					continue;
				}
				value.setTimestamp(date);
				if(!splitted[1].matches("^[0-9|.]*$")) { // only numbers and one dot
					System.out.println("continue3");
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

	@SuppressWarnings("deprecation")
	private static Date parseDate(String string) {
		String[] strings = string.split(",");
		if((strings.length != 2) || (strings[0].length() != 10)) {
			return null;
		}
		String[] dateStrings = {strings[0].substring(0, 2), strings[0].substring(3, 5), strings[0].substring(6, 10)};
		if((dateStrings.length != 3) || dateStrings[0].isEmpty() || !dateStrings[0].matches(DAY_REGEX) ||
				dateStrings[1].isEmpty() || !dateStrings[1].matches(MONTH_REGEX) ||
				dateStrings[2].isEmpty() || !dateStrings[2].matches(YEAR_REGEX)) {
			return null;
		}
		Integer day = Integer.valueOf(dateStrings[0]);
		Integer month = Integer.valueOf(dateStrings[1]);
		Integer year = Integer.valueOf(dateStrings[2]);
		String[] timeStrings = strings[1].split(":");
		if((timeStrings.length != 3) || timeStrings[0].isEmpty() || !timeStrings[0].matches(HOUR_REGEX) ||
				timeStrings[1].isEmpty() || !timeStrings[1].matches(MIN_SEC_REGEX) ||
				timeStrings[2].isEmpty() || !timeStrings[2].matches(MIN_SEC_REGEX)) {
			return null;
		}
		Integer hour = Integer.valueOf(timeStrings[0]);
		Integer minute = Integer.valueOf(timeStrings[1]);
		Integer second = Integer.valueOf(timeStrings[2]);
		return new Date(year, month, day, hour, minute, second);
	}

}
