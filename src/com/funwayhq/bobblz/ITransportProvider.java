package com.funwayhq.bobblz;

import java.util.List;

public interface ITransportProvider {
	
	boolean connect();
	String getOne(BZCriteria criteria);
	String getOne();
	String getAll(BZCriteria criteria);
	String getAll();
	boolean create(Object object);
	boolean save(Object object);
	boolean remove(Object object);
	boolean close();
	boolean startListening();
}
