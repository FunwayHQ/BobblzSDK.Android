package com.funwayhq.bobblz;

import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.List;

public class BZResource {
	
	private IResource scope;
	
	public BZResource(IResource object) {
		this.scope = object;
	}
	
	public static List<BZResource> findAll(BZCriteria criteria, Class<?> classObject) 
			throws InstantiationException, IllegalAccessException {
		IResource scope = (IResource) classObject.newInstance();
		ITransportProvider transportProvider = scope.getTransport();
		String response = transportProvider.getAll();
		
		List<IResource> objects = DataProcessorManager.parseAll(response, classObject);
		List<BZResource> resources = new ArrayList<BZResource>();
		
		for (IResource object: objects) {
			BZResource resourse = new BZResource(object);
			resources.add(resourse);
		}
		
		return resources;
	}

	public static List<BZResource> findAll(Class<?> classObject) 
			throws InstantiationException, IllegalAccessException {
		return BZResource.findAll(null, classObject);
	}

	public static BZResource findOne(BZCriteria criteria, Class<?> classObject) 
			throws InstantiationException, IllegalAccessException {
		IResource scope = (IResource) classObject.newInstance();
		ITransportProvider transportProvider = scope.getTransport();
		String response = transportProvider.getOne();
		IResource object = DataProcessorManager.parseOne(response, classObject);
		BZResource resource = new BZResource(object);
		
		return resource;
	}
	
	public static BZResource findOne(Class<?> classObject) 
			throws InstantiationException, IllegalAccessException {
		return findOne(null, classObject);
	}

	public boolean create() {
		ITransportProvider transportProvider = scope.getTransport();
		return transportProvider.create(scope);
	}

	public boolean save() {
		ITransportProvider transportProvider = scope.getTransport();
		return transportProvider.save(scope);
	}

	public boolean delete() {
		ITransportProvider transportProvider = scope.getTransport();
		return transportProvider.remove(scope);
	}

	public IResource resourse() {
		return scope;
	}
}
