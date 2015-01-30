package com.funwayhq.bobblz;

public enum Urls {
	
	BASE_URL("some_nice_base_url"),
	LIST_URL(BASE_URL + "/test/resourse/list"),
	ONE_ITEM_URL(BASE_URL + "/test/resourse/"),
	CREATE_URL(BASE_URL + "/test/resource/create"),
	SAVE_URL(BASE_URL + "/test/resource/save"),
	REMOVE_URL(BASE_URL + "/test/resource/remove");
	
    private final String url;

    private Urls(String url) {
        this.url = url;
    }
    
    public String toString(){
        return url;
    }
}
