package com.funwayhq.bobblzTest;


import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import com.funwayhq.bobblz.JSONDataProcessor;
import com.funwayhq.bobblz.User;

public class JSONDataProcessorTest {
    
    @Test
    public void encodeTest() throws IllegalAccessException, JSONException {
    	JSONObject expectedJson = new JSONObject();
    	expectedJson.put("name", "nameValue");
    	expectedJson.put("age", 5);
    	
    	User tClass = new User();
    	tClass.name = "nameValue";
    	tClass.age = 5;
    	
    	JSONDataProcessor dataProcessor = new JSONDataProcessor();
    	String realJsonString = dataProcessor.encode(tClass);
    	
    	assertEquals(expectedJson.toString(), realJsonString);
    }
    
    @Test
    public void decodeTest() throws JSONException, InstantiationException, 
    					IllegalAccessException, NoSuchFieldException, 
    					SecurityException {
    	JSONObject json = new JSONObject();
    	json.put("name", "nameValue");
    	json.put("age", 5);
    	
    	User expectedTestClass = new User();
    	expectedTestClass.name = "nameValue";
    	expectedTestClass.age = 5;
    	
    	JSONDataProcessor dataProcessor = new JSONDataProcessor();
    	User realTestClass = (User) dataProcessor.decode(json.toString(), User.class);
    	
    	Field[] expectedFields = expectedTestClass.getClass().getDeclaredFields();
        for (Field field: expectedFields) {
        	Object expectedValue = field.get(expectedTestClass);
        	Object realValue = field.get(realTestClass);
        	assertEquals(expectedValue, realValue);
        }
    }
}
