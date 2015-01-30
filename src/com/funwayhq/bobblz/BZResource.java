package com.funwayhq.bobblz;

import java.util.ArrayList;
import java.util.List;

public class BZResource implements IResource {
	
	private Class<?> classObject;
	private Object scope;
	
	public BZResource(Object object) {
		this.scope = object;
		this.classObject = object.getClass();
	}
	
	public static List<BZResource> findAll(BZCriteria criteria, Class<?> classObject) {
		HTTPTransportProvider transportProvider = (HTTPTransportProvider) 
				TransportSingleton.getHTTPTransportProvider();
		String response = transportProvider.getAll();
		List<Object> objects = JSONParserHelper.parseAll(response, classObject);
		List<BZResource> resources = new ArrayList<BZResource>();
		
		for (Object object: objects) {
			BZResource resourse = new BZResource(object);
			resources.add(resourse);
		}
		
		return resources;
	}

	public static List<BZResource> findAll(Class<?> classObject) {
		return BZResource.findAll(null, classObject);
	}

	@Override
	public BZResource findOne(BZCriteria criteria) {
		HTTPTransportProvider transportProvider = (HTTPTransportProvider) 
				TransportSingleton.getHTTPTransportProvider();
		String response = transportProvider.getOne();
		Object object = JSONParserHelper.parseOne(response, classObject);
		BZResource resource = new BZResource(object);
		
		return resource;
	}
	
	@Override
	public BZResource findOne() {
		return findOne(null);
	}

	@Override
	public boolean create() {
		HTTPTransportProvider transportProvider = (HTTPTransportProvider) 
				TransportSingleton.getHTTPTransportProvider();
		return transportProvider.create(scope);
	}

	@Override
	public boolean save() {
		HTTPTransportProvider transportProvider = (HTTPTransportProvider) 
				TransportSingleton.getHTTPTransportProvider();
		return transportProvider.save(scope);
	}

	@Override
	public boolean delete() {
		HTTPTransportProvider transportProvider = (HTTPTransportProvider) 
				TransportSingleton.getHTTPTransportProvider();
		return transportProvider.remove(scope);
	}

	@Override
	public Object resourse() {
		return scope;
	}
}
