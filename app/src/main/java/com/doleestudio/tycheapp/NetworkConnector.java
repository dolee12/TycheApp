package com.doleestudio.tycheapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;

import java.io.DataOutputStream;
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

    // This IP (10.0.3.2) is required to get access to localhost from GenyHost
    private static final String SERVER_URL = "http://10.0.3.2:3000";
    private static final String TICKET_LIST_URL = SERVER_URL + "/tickets.json/";
    private static final String STORE_LIST_URL = SERVER_URL + "/stores.json?name=";

    private static final int BUFFER_SIZE = 1024;

    public static boolean isConnected(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public static String fetchJsonText(String urlText, String method, String params) throws IOException, NetworkConnectorException {

        HttpURLConnection conn = connect(urlText, method, params);
        String jsonText = null;

        if (isResponseOkay(conn)) {
            jsonText = fetchJsonTextFromServer(conn);
            conn.disconnect();
        } else {
            conn.disconnect();
            throw new NetworkConnectorException();
        }

        return jsonText;
    }

    private static boolean isResponseOkay(HttpURLConnection conn) throws IOException {
        int responseCode = conn.getResponseCode();
        return responseCode == 200;
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
        } catch (Exception e) {
            Log.d("", e.getMessage());
        } finally {
            if (inputStream != null)
                inputStream.close();
        }

        return builder.toString();
    }

    private static HttpURLConnection connect(String urlText, String method, String params) throws MalformedURLException, IOException {
        URL url = new URL(urlText);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod(method);
        conn.setDoInput(true);
        //conn.setDoOutput(true);

        setDataIfPostMethod(conn, method, params);

        conn.connect();
        return conn;
    }

    private static void setDataIfPostMethod(HttpURLConnection conn, String method, String params) throws IOException {
        if (method != "POST")
            return;

        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");

        conn.setRequestProperty("Content-Length", "" +
                Integer.toString(params.getBytes().length));
        conn.setRequestProperty("Content-Language", "en-US");
        conn.setUseCaches(false);

        try {
            DataOutputStream wr = new DataOutputStream(
                    conn.getOutputStream());
            wr.writeBytes(params);
            wr.flush();
            wr.close();
        } catch (IOException e) {
            Log.d("", e.getMessage());
        }
    }

    public static String fetchTicketList() throws IOException, NetworkConnectorException {
        return fetchJsonText(TICKET_LIST_URL, "GET", null);
    }

    public static String fetchStoreList(String query) throws IOException, NetworkConnectorException {

        String url = makeEncodedURL(STORE_LIST_URL, query);
        return NetworkConnector.fetchJsonText(url, "GET", null);
    }

    private static String makeEncodedURL(String url, String query) {
        String encodedUrl = url;

        if (query != null) {
            String encodedQueryString = EncodeToUTF8(query);
            encodedUrl += encodedQueryString;
        }

        return encodedUrl;
    }

    private static String EncodeToUTF8(String query) {

        return Uri.encode(query, "UTF-8");
    }

    public static String createNewTicket(String storeId, String userId) throws IOException, NetworkConnectorException {
        String url = TICKET_LIST_URL;
        String params = "store_id=" + storeId + "&user_id=" + userId;

        return fetchJsonText(url, "POST", params);
    }


    public static class NetworkConnectorException extends Exception {

    }
}
