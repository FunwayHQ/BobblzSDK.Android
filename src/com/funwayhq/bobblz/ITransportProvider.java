package com.funwayhq.bobblz;


public interface ITransportProvider {
	
	boolean connect();
	String getOne(BZCriteria criteria);
	String getOne();
	String getAll(BZCriteria criteria);
	String getAll();
	boolean create(IResource object);
	boolean save(IResource object);
	boolean remove(IResource object);
	boolean close();
	boolean startListening();
}
