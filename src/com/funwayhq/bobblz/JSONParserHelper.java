package com.funwayhq.bobblz;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONParserHelper {
	
	public static IResource parseOne(String jString, Class<?> classObject) {
		JSONDataProcessor dataProcessor = new JSONDataProcessor();
		try {
			IResource object = dataProcessor.decode(jString, classObject);
			return object;
		} catch (JSONException | InstantiationException
				| IllegalAccessException | NoSuchFieldException
				| SecurityException e) {
			
			e.printStackTrace();
			return null;
		}
	}
	
	public static List<IResource> parseAll(String jString, Class<?> classObject) {
		try {
			JSONArray jsonArray = new JSONArray(jString);
			List<IResource> dataList = new ArrayList<>();
			
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject object = jsonArray.getJSONObject(i);
				IResource oneItem = parseOne(object.toString(), classObject);
				
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
	
	public static String encodeOne(IResource object) {
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
