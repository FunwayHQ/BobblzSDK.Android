package com.funwayhq.bobblz;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONDataProcessor implements IDataProcessor {

	@Override
	public String getMIMEType() {
		return "application/json";
	}

	@Override
	public IResource decodeOne(String data, Class<?> classObject)
			throws InstantiationException, IllegalAccessException,
			NoSuchFieldException, SecurityException {

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
	public List<IResource> decodeAll(String data, Class<?> classObject)
			throws InstantiationException, IllegalAccessException,
			NoSuchFieldException, SecurityException {

		JSONArray jsonArray = new JSONArray(data);
		List<IResource> dataList = new ArrayList<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject object = jsonArray.getJSONObject(i);
			IResource oneItem = decodeOne(object.toString(), classObject);

			if (oneItem != null) {
				dataList.add(decodeOne(object.toString(), classObject));
			} else {
				return null;
			}
		}

		return dataList;
	}

	@Override
	public String encode(IResource scope) throws IllegalArgumentException,
			IllegalAccessException {
		Class<?> tClass = scope.getClass();
		Field[] fields = tClass.getDeclaredFields();
		JSONObject object = new JSONObject();

		for (Field field : fields) {
			String fieldKey = fetchJsonFieldNameFromEnum(field);

			if (fieldKey != null) {
				Object fieldValue = field.get(scope);
				object.put(fieldKey, fieldValue);
			}
		}

		return object.toString();
	}

	private String fetchJsonFieldNameFromEnum(Field field) {
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
