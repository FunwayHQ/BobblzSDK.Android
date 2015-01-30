package com.funwayhq.bobblz;

import org.json.JSONException;

public interface IDataProcessor {

    String getMIMEType();
    String encode(Object scope) throws IllegalAccessException, JSONException;
	Object decode(String data, Class<?> classInstace) throws JSONException, 
		InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException;
}
