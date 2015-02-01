package com.funwayhq.bobblz;

import org.json.JSONException;

public interface IDataProcessor {

    String getMIMEType();
    String encode(IResource scope) throws IllegalAccessException, JSONException;
	IResource decode(String data, Class<?> classObject) throws JSONException,
			NoSuchFieldException, SecurityException, IllegalArgumentException,
			IllegalAccessException, InstantiationException;
}
