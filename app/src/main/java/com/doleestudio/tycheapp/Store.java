package com.doleestudio.tycheapp;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dolee on 14. 12. 17..
 */
public class Store {

    private static final String JSON_STORE_ID = "id";
    private static final String JSON_STORE_NAME = "name";
    private static final String JSON_STORE_CATEGORY = "category";
    private static final String JSON_STORE_TEL = "tel";
    private static final String JSON_STORE_ADDR = "addr";
    private static final String JSON_STORE_LOCATION = "location";
    private static final String JSON_STORE_NUMBER = "cnt";
    private static final String JSON_STORE_WAIT_COUNT = "wait";


    private int id;
    private String name;
    private String category;
    private String tel;
    private String addr;
    private String location;
    private String currentNumber;
    private String waitCount;

    public Store(JSONObject jsonObj) {
        initializeFromJson(jsonObj);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getTel() {
        return tel;
    }

    public String getAddr() {
        return addr;
    }

    public String getLocation() {
        return location;
    }

    public String getCurrentNumber() {
        return currentNumber;
    }

    public String getWaitCount() {
        return waitCount;
    }

    private void initializeFromJson(JSONObject jsonObj) {
        try {
            id = jsonObj.getInt(JSON_STORE_ID);
            name = jsonObj.getString(JSON_STORE_NAME);
            category = jsonObj.getString(JSON_STORE_CATEGORY);
            tel = jsonObj.getString(JSON_STORE_TEL);
            addr = jsonObj.getString(JSON_STORE_ADDR);
            location = jsonObj.getString(JSON_STORE_LOCATION);
            currentNumber = jsonObj.getString(JSON_STORE_NUMBER);
            waitCount = jsonObj.getString(JSON_STORE_WAIT_COUNT);
        } catch (JSONException je) {

        }
    }
}
