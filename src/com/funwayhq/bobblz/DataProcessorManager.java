package com.funwayhq.bobblz;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DataProcessorManager {
	
	public static IDataProcessor getDataProcessor(DataProcessors providerType) {
		IDataProcessor processor = null;
		
		switch (providerType) {
			case JSON:
				processor = new JSONDataProcessor();
				break;
			case XML:
				break;
		}
		
		return processor;
	}
	
	public static IResource parseOne(String jString, Class<?> classObject) {
		try {
			IResource scope = (IResource) classObject.newInstance();
			IDataProcessor dataProcessor = scope.getDataProcessor();
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
	
	public static String encodeOne(IResource scope) {
		try {
			IDataProcessor dataProcessor = scope.getDataProcessor();
			return dataProcessor.encode(scope);
		} catch (JSONException | IllegalAccessException
				| SecurityException e) {
	
			e.printStackTrace();
			return null;
		}
	}
}
