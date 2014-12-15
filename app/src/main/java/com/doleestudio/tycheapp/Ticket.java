package com.doleestudio.tycheapp;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 */
public class Ticket {
    private static final String STORE_ID = "store_id";
    private static final String STORE_NAME = "store_name";
    private static final String NUMBER = "wait_number";
    private static final String WAIT_ORDER = "wait_ahead_count";
    private static final String REG_TIME = "reg_time";
    private static final String UTC_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    private long storeId;
    private String storeName;
    private long number;
    private long waitOrder;
    private Date regTime;

    private Ticket() {
    }

    public static Ticket instanceOf(JSONObject jsonObj) {
        Ticket ticket = null;

        try {
            ticket = readTicketFromJsonObj(jsonObj);
        } catch (Exception e) {

        }

        return ticket;
    }

    private static Ticket readTicketFromJsonObj(JSONObject jsonObj) throws JSONException, ParseException {
        Ticket ticket = new Ticket();

        ticket.storeId = jsonObj.getLong(Ticket.STORE_ID);
        ticket.storeName = jsonObj.getString(Ticket.STORE_NAME);
        ticket.number = jsonObj.getLong(NUMBER);
        ticket.waitOrder = jsonObj.getLong(WAIT_ORDER);

        ticket.regTime = convertFromJasonDateToDate(jsonObj);

        return ticket;
    }

    private static Date convertFromJasonDateToDate(JSONObject jsonObj) throws JSONException, ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(Ticket.UTC_FORMAT);

        String jsonDateText = jsonObj.getString(REG_TIME);
        return formatter.parse(jsonDateText);
    }

    public long getStoreId() {
        return storeId;
    }

    public String getStringStoreId() {
        return String.valueOf(storeId);
    }

    public String getStoreName() {
        return storeName;
    }

    public long getNumber() {
        return number;
    }

    public String getStringNumber() {
        return String.valueOf(number);
    }

    public long getWaitOrder() {
        return waitOrder;
    }

    public String getStringWaitOrder() {
        return String.valueOf(waitOrder);
    }

    public Date getRegTime() {
        return regTime;
    }

    public String getStringRegTime() {
        return regTime.toString();
    }
}
