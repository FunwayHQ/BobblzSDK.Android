package com.funwayhq.bobblz;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONParserHelper {
	
	public static Object parseOne(String jString, Class<?> classObject) {
		JSONDataProcessor dataProcessor = new JSONDataProcessor();
		try {
			Object object = dataProcessor.decode(jString, classObject);
			return object;
		} catch (JSONException | InstantiationException
				| IllegalAccessException | NoSuchFieldException
				| SecurityException e) {
			
			e.printStackTrace();
			return null;
		}
	}
	
	public static List<Object> parseAll(String jString, Class<?> classObject) {
		try {
			JSONArray jsonArray = new JSONArray(jString);
			List<Object> dataList = new ArrayList<>();
			
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject object = jsonArray.getJSONObject(i);
				Object oneItem = parseOne(object.toString(), classObject);
				
				if (oneItem != null) {
					dataList.add(parseOne(object.toString(), classObject));
				} else {
					return null;
				}
			}
			
			return dataList;
		} catch (JSONException | SecurityException e) {	
			e.printStackTrace();
			return null;
		}
	}
	
	public static String encodeOne(Object object) {
		JSONDataProcessor dataProcessor = new JSONDataProcessor();
		try {
			return dataProcessor.encode(object);
		} catch (JSONException | IllegalAccessException
				| SecurityException e) {
	
			e.printStackTrace();
			return null;
		}
	}
	
	public static int getResponseCode(String jString) {
		JSONObject object = new JSONObject(jString);
		return object.getInt("status");
	}
}
