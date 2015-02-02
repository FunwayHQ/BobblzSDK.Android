package com.funwayhq.bobblz;

public class User implements IResource {

	public String name;
	public int age;
	public int id;

	public User() {
		name = "";
		age = 0;
		id = 0;
	}

	@Override
	public ITransportProvider getTransport() {
		return TransportManager.getTransportProvider(TransportProviders.HTTP);
	}

	@Override
	public IDataProcessor getDataProcessor() {
		return DataProcessorManager.getDataProcessor(DataProcessors.JSON);
	}

	@Override
	public int getId() {
		return id;
	}
}
