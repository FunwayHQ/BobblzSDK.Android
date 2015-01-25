package com.developer.bobblz;

import java.util.List;

public interface ITransportProvider<T> {
	
	boolean connect();
	T getOne(BZCriteria criteria);
	T getOne();
	List<T> getAll(BZCriteria criteria);
	List<T> getAll();
	boolean create(T object);
	boolean save(T object);
	boolean remove(T object);
	boolean close();
	boolean startListening();
}
