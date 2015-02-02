package com.funwayhq.bobblz;

public class DataProcessorManager {

	public static IDataProcessor getDataProcessor(DataProcessors providerType) {
		IDataProcessor processor = null;

		switch (providerType) {
		case JSON:
			processor = new JSONDataProcessor();
			break;
		case XML:
			break;
		}

		return processor;
	}
}
