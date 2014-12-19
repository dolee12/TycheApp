package com.doleestudio.tycheapp;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by dolee@outlook.com on 14. 12. 17..
 */
public class StoreAdapter extends ArrayAdapter<Store> {
    private static final String URL = "http://10.0.2.2:3000/stores.json?name=";
    private Context context;

    public StoreAdapter(Context context, int resource) {
        super(context, resource);

        this.context = context;
    }

    public final void fetch(String query) throws JSONException, IOException, NetworkConnector.NetworkConnectorException {

        String queryURL = makeEncodedURL(query);
        String jsonText = NetworkConnector.fetchJsonText(queryURL);

        JSONArray jsonArray;
        jsonArray = parseJsonArray(jsonText);

        for (int i = 0; i < jsonArray.length(); i++) {
            parseJsonToAdd(jsonArray, i);
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

    private final void parseJsonToAdd(JSONArray jsonArray, int i) {
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

    @Override
    public final View getView(int position, View convertView, ViewGroup parent) {

        View storeView = createStoreView(parent);

        TextView tvShopName = (TextView) storeView.findViewById(R.id.store_shopName);
        TextView tvShopCategory = (TextView) storeView.findViewById(R.id.store_category);
        TextView tvWaitCount = (TextView) storeView.findViewById(R.id.store_waitCount);

        Store store = (Store) getItem(position);

        tvShopName.setText(store.getName());
        tvShopCategory.setText(store.getCategory());
        tvWaitCount.setText(store.getCurrentNumber());

        return storeView;
    }

    private final View createStoreView(ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        return inflater.inflate(R.layout.widget_store, parent, false);
    }
}
