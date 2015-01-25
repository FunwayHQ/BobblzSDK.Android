package com.developer.bobblz;

public enum Urls {
	
	BASE_URL("some_nice_base_url"),
	LIST_URL(BASE_URL + "/test/resourse/list"),
	ONE_ITEM_URL(BASE_URL + "/test/resourse/");

    private final String url;

    private Urls(String url) {
        this.url = url;
    }

}
