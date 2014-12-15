package com.doleestudio.tycheapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * To use this class, needs two permissions in AndroidManifest file for networking.
 * <uses-permission android:name="android.permission.INTERNET" />
 * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
 */
public class NetworkConnector {

    private static final int BUFFER_SIZE = 1024;


    public ArrayList<Ticket> fetchJson(String urlText) throws MalformedURLException, IOException, NetworkConnectorException, JSONException {

        HttpURLConnection conn = connect(urlText);

        if (isResponseOkay(conn)) {
            String jsonText = readJson(conn);
            return parseJsonTextToTicketList(jsonText);
        } else {
            throw new NetworkConnectorException();
        }
    }

    private ArrayList<Ticket> parseJsonTextToTicketList(String jsonText) throws JSONException {
        JSONArray jsonArray = new JSONArray(jsonText);

        ArrayList<Ticket> ticketArray = new ArrayList<Ticket>();

        for (int i = 0; i < jsonArray.length(); i++) {
            Ticket newTicket = Ticket.instanceOf(jsonArray.getJSONObject(i));
            ticketArray.add(newTicket);
        }

        return ticketArray;
    }

    private boolean isResponseOkay(HttpURLConnection conn) throws IOException {
        return conn.getResponseCode() == 200;
    }

    private String readJson(HttpURLConnection conn) throws IOException {
        InputStream inputStream = null;
        StringBuilder builder = new StringBuilder();

        try {
            inputStream = conn.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream, "UTF8");
            char[] buffer = new char[BUFFER_SIZE];
            int length;
            while ((length = reader.read(buffer)) != -1) {
                builder.append(buffer, 0, length);
            }
        } finally {
            if (inputStream != null)
                inputStream.close();
        }

        return builder.toString();
    }


    private HttpURLConnection connect(String urlText) throws MalformedURLException, IOException {
        URL url = new URL(urlText);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);

        conn.connect();

        return conn;
    }

    public boolean isConnected(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    class NetworkConnectorException extends Exception {

    }
}
