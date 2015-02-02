package com.funwayhq.bobblz;


public class UrlBuilder {

	public static String BASE_URL;
	public static String LIST_URL_ENDING;
	public static String CREATE_URL_ENDING;
	public static String SAVE_URL_ENDING;
	public static String REMOVE_URL_ENDING;

	public static String getListUrl(String className) {
		if (BASE_URL == null || LIST_URL_ENDING == null) {
			SettingsParser parser = new SettingsParser();
			parser.parseUrl();
		}

		return BASE_URL + className.toLowerCase() + LIST_URL_ENDING;
	}

	public static String getListUrl(String className, BZCriteria criteria) {
		if (BASE_URL == null || LIST_URL_ENDING == null) {
			SettingsParser parser = new SettingsParser();
			parser.parseUrl();
		}

		return BASE_URL + className.toLowerCase() + LIST_URL_ENDING;
	}

	public static String getOneItemUrl(String className, BZCriteria criteria) {
		if (BASE_URL == null) {
			SettingsParser parser = new SettingsParser();
			parser.parseUrl();
		}

		return BASE_URL + className.toLowerCase() + "/" + criteria.getId();
	}

	public static String getOneItemUrl(String className) {
		if (BASE_URL == null) {
			SettingsParser parser = new SettingsParser();
			parser.parseUrl();
		}

		return BASE_URL + className.toLowerCase();
	}

	public static String getCreateUrl(String className) {
		if (BASE_URL == null) {
			SettingsParser parser = new SettingsParser();
			parser.parseUrl();
		}

		return BASE_URL + className.toLowerCase() + CREATE_URL_ENDING;
	}

	public static String getSaveUrl(String className, BZCriteria criteria) {
		if (BASE_URL == null || SAVE_URL_ENDING == null) {
			SettingsParser parser = new SettingsParser();
			parser.parseUrl();
		}

		return BASE_URL + className.toLowerCase() + SAVE_URL_ENDING + "/"
				+ criteria.getId();
	}

	public static String getRemoveUrl(String className, BZCriteria criteria) {
		if (BASE_URL == null || REMOVE_URL_ENDING == null) {
			SettingsParser parser = new SettingsParser();
			parser.parseUrl();
		}

		return BASE_URL + className.toLowerCase() + REMOVE_URL_ENDING + "/"
				+ criteria.getId();
	}
}
