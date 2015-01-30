package com.funwayhq.bobblz;

import java.util.List;

public interface IResource {
	
	/*List<BZResource> findAll(BZCriteria criteria, Class<?> classObject);
	List<BZResource> findAll();*/
	Object findOne(BZCriteria criteria);
	Object findOne();
	boolean create();
	boolean save();
	boolean delete();
	Object resourse();
}
