package com.doleestudio.tycheapp;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dolee@outlook.com on 14. 12. 17..
 */
public class Store implements Parcelable {
    public static final Parcelable.Creator<Store> CREATOR = new Parcelable.Creator<Store>() {

        @Override
        public Store createFromParcel(Parcel source) {
            return new Store(source);
        }

        @Override
        public Store[] newArray(int size) {
            return new Store[size];
        }
    };
    private static final String JSON_STORE_ID = "id";
    private static final String JSON_STORE_NAME = "name";
    private static final String JSON_STORE_CATEGORY = "category";
    private static final String JSON_STORE_TEL = "tel";
    private static final String JSON_STORE_ADDRESS = "addr";
    private static final String JSON_STORE_LOCATION = "location";
    private static final String JSON_STORE_NUMBER = "cnt";
    private static final String JSON_STORE_WAIT_COUNT = "wait";
    private String id;
    private String name;
    private String category;
    private String tel;
    private String address;
    private String location;
    private String currentNumber;
    private String waitCount;

    public Store(JSONObject jsonObj) {
        initializeFromJson(jsonObj);
    }

    public Store(Parcel parcel) {
        String[] data = new String[8];

        parcel.readStringArray(data);

        id = data[0];
        name = data[1];
        category = data[2];
        tel = data[3];
        address = data[4];
        location = data[5];
        currentNumber = data[6];
        waitCount = data[7];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeStringArray(new String[]{
                id,
                name,
                category,
                tel,
                address,
                location,
                currentNumber,
                waitCount});
    }

    public String getId() {
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

    public String getAddress() {
        return address;
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
            id = jsonObj.getString(JSON_STORE_ID);
            name = jsonObj.getString(JSON_STORE_NAME);
            category = jsonObj.getString(JSON_STORE_CATEGORY);
            tel = jsonObj.getString(JSON_STORE_TEL);
            address = jsonObj.getString(JSON_STORE_ADDRESS);
            location = jsonObj.getString(JSON_STORE_LOCATION);
            currentNumber = jsonObj.getString(JSON_STORE_NUMBER);
            waitCount = jsonObj.getString(JSON_STORE_WAIT_COUNT);
        } catch (JSONException je) {

        }
    }

    public void getNewTicket() {
        String jsonNewForm = createNewTicketForm();

        if (jsonNewForm == null) return;


    }

    private String createNewTicketForm() {
        JSONObject jsonObj = new JSONObject();

        try {
            jsonObj.put("store_id", id);
            jsonObj.put("user_id", 1);
        } catch (JSONException je) {

        }

        return jsonObj.toString();
    }
}
