package com.funwayhq.bobblz;

import java.util.List;

public class TransportManager {
	
	private static List<ITransportProvider> providers;
	
	public static ITransportProvider getTransportProvider(TransportProviders providerType, 
			Class<?> classObject) {
		ITransportProvider provider = findProvider(providerType);
		
		if (provider == null) {
			instantiateProvider(providerType, classObject);
		}
		
		return provider;
	}
	
	private  static ITransportProvider findProvider(TransportProviders providerType) {
		ITransportProvider provider = null;
		
		for (ITransportProvider providerIterator: providers)  {
			switch (providerType) {
				case HTTP:
					if (providerIterator instanceof HTTPTransportProvider) {
						provider = providerIterator;
					}
					break;
				case TCP:
					break;
				case SOCKETS:
					break;
			}
		}
		
		return provider;
	}
	
	private static ITransportProvider instantiateProvider(TransportProviders providerType, 
			Class<?> classObject) {
		ITransportProvider provider = null;
		
		switch (providerType) {
			case HTTP:
				provider = new HTTPTransportProvider(classObject);
				providers.add(provider);
				break;
			case TCP:
				break;
			case SOCKETS:
				break;
		}
		
		return provider;
	}
}
