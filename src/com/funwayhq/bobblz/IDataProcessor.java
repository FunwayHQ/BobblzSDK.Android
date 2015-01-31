package com.funwayhq.bobblz;

import org.json.JSONException;

public interface IDataProcessor {

    String getMIMEType();
    String encode(IResource scope) throws IllegalAccessException, JSONException;
	IResource decode(String data, Class<?> classInstace) throws JSONException, 
		InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException;
}
