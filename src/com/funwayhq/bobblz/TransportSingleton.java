package com.funwayhq.bobblz;

public class TransportSingleton {
	
	private static HTTPTransportProvider httpTransportProvider;
	
	public static HTTPTransportProvider getHTTPTransportProvider() {
		if (httpTransportProvider == null) {
			httpTransportProvider = new HTTPTransportProvider();
		}
		
		return httpTransportProvider;
	}

}
