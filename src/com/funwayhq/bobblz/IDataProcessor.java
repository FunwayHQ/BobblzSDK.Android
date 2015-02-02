package com.funwayhq.bobblz;

import java.util.List;

public interface IDataProcessor {

	String getMIMEType();

	String encode(IResource scope) throws IllegalArgumentException,
			IllegalAccessException;

	IResource decodeOne(String data, Class<?> classObject)
			throws InstantiationException, IllegalAccessException,
			NoSuchFieldException, SecurityException;

	List<IResource> decodeAll(String data, Class<?> classObject)
			throws InstantiationException, IllegalAccessException,
			NoSuchFieldException, SecurityException;
}
