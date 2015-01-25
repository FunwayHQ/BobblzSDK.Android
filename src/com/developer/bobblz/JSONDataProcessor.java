package com.developer.bobblz;

import java.lang.reflect.Field;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONDataProcessor<T> implements IDataProcessor<T> {

    @Override
    public String getMIMEType() {
        // TODO implementation
        return null;
    }

    @Override
    public T decode(String data, Class<T> classInstace) throws JSONException, 
    							InstantiationException, IllegalAccessException, 
    							NoSuchFieldException, SecurityException {
    	T scope = classInstace.newInstance();
        JSONObject object = new JSONObject(data);

        Iterator<String> fieldsKeys = object.keys();
        while (fieldsKeys.hasNext()) {
            String fieldKey = fieldsKeys.next();
            String variableName = JSonFields.getNameByValue(fieldKey);
            String variableValue = object.getString(fieldKey);
            		
            Field filed = classInstace.getDeclaredField(variableName);
            filed.set(scope, variableValue);
        }
        
        return scope;
    }

    @Override
    public String encode(T scope) throws IllegalAccessException, JSONException {
        Class<? extends Object> tClass = scope.getClass();

        Field[] fields = tClass.getDeclaredFields();

        JSONObject object = new JSONObject();
        for (Field field: fields) {
            String fieldKey = fetchJsonFieldNameFromEnum(field);
            String fieldValue = (String) field.get(scope);
          
            object.put(fieldKey, fieldValue);
        }

        return object.toString();
    }

    public String fetchJsonFieldNameFromEnum(Field field) {
        String varName = field.getName();
        JSonFields jsonField = JSonFields.valueOf(varName);
        return jsonField.getJsonName();
    }

}
