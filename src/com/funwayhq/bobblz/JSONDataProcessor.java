package com.funwayhq.bobblz;

import java.lang.reflect.Field;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONDataProcessor implements IDataProcessor {

	@Override
	public String getMIMEType() {
		return "application/json";
	}

	@Override
	public IResource decode(String data, Class<?> classObject) throws JSONException, NoSuchFieldException, 
		SecurityException, IllegalArgumentException, IllegalAccessException, InstantiationException {
		JSONObject object = new JSONObject(data);
		Iterator<String> fieldsKeys = object.keys();
		IResource scope = (IResource) classObject.newInstance();
				
		while (fieldsKeys.hasNext()) {
			String fieldKey = fieldsKeys.next();
			String variableName = JSonFields.getNameByValue(fieldKey);
			
			if (variableName != null) {
				Object variableValue = object.get(fieldKey);
				Field filed = classObject.getDeclaredField(variableName);
				filed.set(scope, variableValue);
			}
		}
		
		return scope;
	}
		
	@Override
	public String encode(IResource scope) throws IllegalAccessException, JSONException {
		Class<?> tClass = scope.getClass();
		Field[] fields = tClass.getDeclaredFields();
		JSONObject object = new JSONObject();
		
		for (Field field: fields) {
			String fieldKey = fetchJsonFieldNameFromEnum(field);
			
			if (fieldKey != null) {
				Object fieldValue = field.get(scope);
				object.put(fieldKey, fieldValue);
			}
		}
		
		return object.toString();
	}

	public String fetchJsonFieldNameFromEnum(Field field) {
		try {
			String varName = field.getName();
			JSonFields jsonField = JSonFields.valueOf(varName);
			return jsonField.getJsonName();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
