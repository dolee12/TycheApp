package com.doleestudio.tycheapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by dolee@outlook.com on 14. 12. 17..
 */
public class TicketList extends ArrayList<Ticket> {
    private static final String URL = "http://10.0.2.2:3000/lineups/";

    public TicketList() {

    }

    public void fetch() throws JSONException, IOException, NetworkConnector.NetworkConnectorException {
        String jsonText = NetworkConnector.fetchJsonText(URL);

        JSONArray jsonArray = parseJsonArray(jsonText);

        for (int i = 0; i < jsonArray.length(); i++) {
            parseJsonToAddTicket(jsonArray, i);
        }
    }

    private void parseJsonToAddTicket(JSONArray jsonArray, int i) {
        try {
            JSONObject jsonObj = jsonArray.getJSONObject(i);

            Ticket ticket = new Ticket(jsonObj);
            this.add(ticket);
        } catch (JSONException e) {
            // skip even if error happens
        }
    }

    private final JSONArray parseJsonArray(String jsonText) throws JSONException {
        return new JSONArray(jsonText);
    }
}
