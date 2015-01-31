package com.funwayhq.bobblz;


public class Urls {
	
	private static String BASE_URL = "http://api.mycab24.ru/";
	private static String LIST_URL_ENDING = "/resourse/list";
	private static String ONE_ITEM_URL_ENDING = "/resourse";
	private static String CREATE_URL_ENDING = "/resource/create";
	private static String SAVE_URL_ENDING = "/resource/save";
	private static String REMOVE_URL_ENDING = "/resource/remove";
	
	public static String getListUrl(Class<?> classObject) {
		return BASE_URL + classObject.getName().toLowerCase() + LIST_URL_ENDING;
	}
	
	public static String getOneItemUrl(Class<?> classObject) {
		return BASE_URL + classObject.getName().toLowerCase() + ONE_ITEM_URL_ENDING;
	}
	
	public static String getCreateUrl(IResource scope) {
		Class<?> classObject = scope.getClass();
		return BASE_URL + classObject.getName().toLowerCase() + CREATE_URL_ENDING;
	}
	
	public static String getSaveUrl(IResource scope) {
		Class<?> classObject = scope.getClass();
		return BASE_URL + classObject.getName().toLowerCase() + SAVE_URL_ENDING;
	}
	
	public static String getRemoveUrl(IResource scope) {
		Class<?> classObject = scope.getClass();
		return BASE_URL + classObject.getName().toLowerCase() + REMOVE_URL_ENDING;
	}
}
