package com.developer.bobblzTest;


import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.util.NoSuchElementException;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import com.developer.bobblz.JSONDataProcessor;
import com.developer.bobblz.JSonFields;
import com.developer.bobblz.TestClass;

public class JSONDataProcessorTest {

    @Test
    public void fetchJsonFieldNameFromEnumTest() throws NoSuchFieldException {
        JSONDataProcessor<TestClass> dataProcessor = new JSONDataProcessor<>();

        String fieldName = "prop1";
        String expectedJsonFieldName = "prop1_json";
        TestClass testClass = new TestClass();
        Field field = testClass.getClass().getDeclaredField(fieldName);
        String realJsonFieldName = dataProcessor.fetchJsonFieldNameFromEnum(field);
        
        assertEquals(expectedJsonFieldName, realJsonFieldName);
    }
    
    @Test
    public void getNameByValueTest() {
    	String expectedVariableName = "prop1";
    	String realVariableName = JSonFields.getNameByValue("prop1_json");
    	
    	assertEquals(expectedVariableName, realVariableName);
    }
    
    @Test (expected = NoSuchElementException.class)
    public void getUnexistingNameByValueTest() {
    	JSonFields.getNameByValue("prop_json");
    }
    
    @Test
    public void encodeTest() throws IllegalAccessException, JSONException {
    	JSONObject expectedJson = formJson();
    	
    	TestClass tClass = new TestClass();
    	JSONDataProcessor<TestClass> dataProcessor = new JSONDataProcessor<>();
    	String realJsonString = dataProcessor.encode(tClass);
    	
    	assertEquals(expectedJson.toString(), realJsonString);
    }

	private JSONObject formJson() {
		JSONObject expectedJson = new JSONObject();
    	expectedJson.put("prop1_json", "value1");
		return expectedJson;
	}
    
    @Test
    public void decodeTest() throws JSONException, InstantiationException, 
    					IllegalAccessException, NoSuchFieldException, 
    					SecurityException {
    	JSONObject json = formJson();
    	
    	TestClass expectedTestClass = new TestClass();
    	JSONDataProcessor<TestClass> dataProcessor = new JSONDataProcessor<>();
    	TestClass realTestClass = dataProcessor.decode(json.toString(), TestClass.class);
    	
    	Field[] expectedFields = expectedTestClass.getClass().getDeclaredFields();
        for (Field field: expectedFields) {
        	String expectedValue = (String) field.get(expectedTestClass);
        	String realValue = (String) field.get(realTestClass);
        	assertEquals(expectedValue, realValue);
        }
    }
}
