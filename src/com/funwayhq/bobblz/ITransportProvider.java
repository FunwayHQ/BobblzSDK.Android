package com.funwayhq.bobblz;

public interface ITransportProvider {

	boolean connect();

	String getOne(String className, BZCriteria criteria);

	String getOne(String className);

	String getAll(String className, BZCriteria criteria);

	String getAll(String className);

	boolean create(String className, String encodedData);

	boolean save(String className, String encodedData, BZCriteria criteria);

	boolean remove(String className, BZCriteria criteria);

	boolean close();

	boolean startListening();
}
