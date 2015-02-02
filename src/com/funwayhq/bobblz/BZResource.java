package com.funwayhq.bobblz;

import java.util.ArrayList;
import java.util.List;

public class BZResource {

	private IResource scope;

	public BZResource(IResource object) {
		this.scope = object;
	}

	public static List<BZResource> findAll(BZCriteria criteria,
			Class<?> classObject) throws InstantiationException,
			IllegalAccessException, NoSuchFieldException, SecurityException,
			IllegalArgumentException {
		IResource scope = (IResource) classObject.newInstance();
		ITransportProvider transportProvider = scope.getTransport();
		String response = transportProvider.getAll(classObject.getName(),
				criteria);

		IDataProcessor dataProcessor = scope.getDataProcessor();
		List<IResource> objects = dataProcessor
				.decodeAll(response, classObject);
		List<BZResource> resources = new ArrayList<BZResource>();

		for (IResource object : objects) {
			BZResource resourse = new BZResource(object);
			resources.add(resourse);
		}

		return resources;
	}

	public static List<BZResource> findAll(Class<?> classObject)
			throws InstantiationException, IllegalAccessException,
			NoSuchFieldException, SecurityException, IllegalArgumentException {
		return BZResource.findAll(null, classObject);
	}

	public static BZResource findOne(BZCriteria criteria, Class<?> classObject)
			throws InstantiationException, IllegalAccessException,
			NoSuchFieldException, SecurityException, IllegalArgumentException {
		IResource scope = (IResource) classObject.newInstance();
		ITransportProvider transportProvider = scope.getTransport();
		String response = transportProvider.getOne(classObject.getName(),
				criteria);

		IDataProcessor dataProcessor = scope.getDataProcessor();
		IResource object = dataProcessor.decodeOne(response, classObject);
		BZResource resource = new BZResource(object);

		return resource;
	}

	public static BZResource findOne(Class<?> classObject)
			throws InstantiationException, IllegalAccessException,
			NoSuchFieldException, SecurityException, IllegalArgumentException {
		return findOne(null, classObject);
	}

	public boolean create() {
		ITransportProvider transportProvider = scope.getTransport();
		String params = getEncodedString();
		return transportProvider.create(scope.getClass().getName(), params);
	}

	public boolean save() {
		ITransportProvider transportProvider = scope.getTransport();
		String params = getEncodedString();
		BZCriteria criteria = formCriteria();
		return transportProvider.save(scope.getClass().getName(), params,
				criteria);
	}

	public boolean delete() {
		ITransportProvider transportProvider = scope.getTransport();
		BZCriteria criteria = formCriteria();
		return transportProvider.remove(scope.getClass().getName(), criteria);
	}

	public IResource resourse() {
		return scope;
	}

	private String getEncodedString() {
		IDataProcessor dataProcessor = scope.getDataProcessor();

		try {
			return dataProcessor.encode(scope);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			return "";
		}
	}

	private BZCriteria formCriteria() {
		BZCriteria criteria = new BZCriteria();
		criteria.setId(scope.getId());

		return criteria;
	}
}
