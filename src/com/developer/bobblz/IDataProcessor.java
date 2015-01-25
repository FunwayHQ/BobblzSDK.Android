package com.developer.bobblz;

import org.json.JSONException;

public interface IDataProcessor<T> {

    String getMIMEType();
    String encode(T scope) throws IllegalAccessException, JSONException;
	T decode(String data, Class<T> classInstace) throws JSONException, 
		InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException;
}
