package com.funwayhq.bobblz;


public interface IResource {
	
	public ITransportProvider getTransport();
	public IDataProcessor getDataProcessor();
	public int getId();
}
