package com.funwayhq.bobblz;


public class User implements IResource {

    public String name;
    public String age;
    
    public User() {
    	name = "";
    	age = "";
    }

	@Override
	public ITransportProvider getTransport() {
		return TransportManager.getTransportProvider(TransportProviders.HTTP, getClass());
	}
}
