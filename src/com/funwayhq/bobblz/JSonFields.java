package com.funwayhq.bobblz;

import java.util.NoSuchElementException;

public enum JSonFields {

    name("name"),
    age("age");

    private final String jsonName;

    private JSonFields(String jsonName) {
        this.jsonName = jsonName;
    }

    public String getJsonName(){
        return jsonName;
    }

    public static String getNameByValue(String value) {
        JSonFields[] fields = values();

        String name = null;
        for (JSonFields field: fields) {
            if (field.getJsonName().equalsIgnoreCase(value)) {
                name = field.name();
                break;
            }
        }
        
        return name;
    }
}
