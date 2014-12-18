package com.doleestudio.tycheapp;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by dolee@outlook.com on 14. 12. 17..
 */
public class StoreList extends ArrayList<Store> {
    private static final String URL = "http://10.0.2.2:3000/stores.json?name=";

    public final void fetch(String query) throws JSONException, IOException, NetworkConnector.NetworkConnectorException {

        String queryURL = makeEncodedURL(query);
        String jsonText = NetworkConnector.fetchJsonText(queryURL);

        JSONArray jsonArray;
        jsonArray = parseJsonArray(jsonText);

        for (int i = 0; i < jsonArray.length(); i++) {
            parseJsonToAddStore(jsonArray, i);
        }
    }

    private String makeEncodedURL(String query) {
        String encodedUrl = URL;

        if (query != null) {
            String encodedQueryString = EncodeToUTF8(query);
            encodedUrl += encodedQueryString;
        }

        return encodedUrl;
    }

    private String EncodeToUTF8(String query) {

        return Uri.encode(query, "UTF-8");
    }

    private final void parseJsonToAddStore(JSONArray jsonArray, int i) {
        try {
            JSONObject jsonObj = jsonArray.getJSONObject(i);

            Store store = new Store(jsonObj);
            this.add(store);
        } catch (JSONException e) {
            // skip even if error happens
        }
    }

    private final JSONArray parseJsonArray(String jsonText) throws JSONException {
        return new JSONArray(jsonText);
    }
}
