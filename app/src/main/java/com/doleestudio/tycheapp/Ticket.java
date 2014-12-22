package com.doleestudio.tycheapp;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Ticket {
    private static final String STORE_ID = "store_id";
    private static final String STORE_NAME = "store_name";
    private static final String NUMBER = "wait_number";
    private static final String WAIT_ORDER = "wait_ahead_count";
    private static final String REG_TIME = "reg_time";
    private static final String UTC_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    private String storeId;
    private String storeName;
    private String ticketNumber;
    private String waitNumber;
    private String creationTime;

    public Ticket(JSONObject jsonObj) {
        initializeFromJson(jsonObj);
    }

    ;

    public static Ticket CreateInstance(String storeId, String userId) {
        JSONObject jsonObj = null;
        try {
            String jsonText = NetworkConnector.createNewTicket(storeId, userId);
            jsonObj = new JSONObject(jsonText);
            return new Ticket(jsonObj);
        } catch (Exception e) {
            Log.d("", e.getMessage());
        }

        return new Ticket(jsonObj);
    }

    public String getStoreId() {
        return storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public String getWaitNumber() {
        return waitNumber;
    }

    public String getCreationTime() {
        return creationTime;
    }

    private final void initializeFromJson(JSONObject jsonObj) {
        try {
            storeId = jsonObj.getString(Ticket.STORE_ID);
            storeName = jsonObj.getString(Ticket.STORE_NAME);
            ticketNumber = jsonObj.getString(NUMBER);
            waitNumber = jsonObj.getString(WAIT_ORDER);
            creationTime = jsonObj.getString(REG_TIME);
        } catch (Exception e) {
            Log.d("", e.getMessage());
        }
    }

    private Date convertFromJasonDateToDate(JSONObject jsonObj) throws JSONException, ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(Ticket.UTC_FORMAT);

        String jsonDateText = jsonObj.getString(REG_TIME);
        return formatter.parse(jsonDateText);
    }
}
