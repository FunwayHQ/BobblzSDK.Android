package com.developer.bobblz;

import java.util.NoSuchElementException;

public enum JSonFields {

    prop1("prop1_json");

    private final String jsonName;

    private JSonFields(String jsonName) {
        this.jsonName = jsonName;
    }

    public String getJsonName(){
        return jsonName;
    }

    public static String getNameByValue(String value) {
        JSonFields[] fields = values();

        String name = "";
        for (JSonFields field: fields) {
            if (field.getJsonName().equalsIgnoreCase(value)) {
                name = field.name();
                break;
            }
        }
        
        if (name.equalsIgnoreCase("")) {
        	throw new NoSuchElementException();
        } else {
        	return name;
        }
    }
}
