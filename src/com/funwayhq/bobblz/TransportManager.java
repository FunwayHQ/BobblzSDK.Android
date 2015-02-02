package com.funwayhq.bobblz;

import java.util.HashMap;

public class TransportManager {

	private static HashMap<TransportProviders, ITransportProvider> providers;

	public static ITransportProvider getTransportProvider(
			TransportProviders providerType) {
		ITransportProvider provider = providers.get(providerType);

		if (provider == null) {
			instantiateProvider(providerType);
		}

		return provider;
	}

	private static ITransportProvider instantiateProvider(
			TransportProviders providerType) {
		ITransportProvider provider = null;

		switch (providerType) {
		case HTTP:
			provider = new HTTPTransportProvider();
			providers.put(providerType, provider);
			break;
		case TCP:
			break;
		case SOCKETS:
			break;
		}

		return provider;
	}
}
