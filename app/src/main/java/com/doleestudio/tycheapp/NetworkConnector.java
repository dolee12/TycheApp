package com.doleestudio.tycheapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * To use this class, needs two permissions in AndroidManifest file for networking.
 * <uses-permission android:name="android.permission.INTERNET" />
 * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
 */
public class NetworkConnector {

    private static final int BUFFER_SIZE = 1024;

    public static boolean isConnected(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public static String fetchJsonText(String urlText) throws IOException, NetworkConnectorException {

        HttpURLConnection conn = connect(urlText);

        if (isResponseOkay(conn)) {
            return fetchJsonTextFromServer(conn);
        } else {
            throw new NetworkConnectorException();
        }

    }

    private static boolean isResponseOkay(HttpURLConnection conn) throws IOException {
        return conn.getResponseCode() == 200;
    }

    private static String fetchJsonTextFromServer(HttpURLConnection conn) throws IOException {
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

    private static HttpURLConnection connect(String urlText) throws MalformedURLException, IOException {
        URL url = new URL(urlText);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);

        conn.connect();

        return conn;
    }

    public static class NetworkConnectorException extends Exception {

    }
}
