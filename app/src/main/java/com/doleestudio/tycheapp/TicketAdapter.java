package com.doleestudio.tycheapp;

import android.content.Context;
import android.util.Log;
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
 * Created by dolee@outlook.com on 14. 12. 15..
 */
public class TicketAdapter extends ArrayAdapter<Ticket> {


    private final Context context;


    public TicketAdapter(Context context, int resource) {
        super(context, resource);

        this.context = context;
    }

    public void fetch() throws JSONException, IOException, NetworkConnector.NetworkConnectorException {
        String jsonText = NetworkConnector.fetchTicketList();

        JSONArray jsonArray = parseJsonArray(jsonText);

        for (int i = 0; i < jsonArray.length(); i++) {
            parseJsonToAdd(jsonArray, i);
        }
    }

    private void parseJsonToAdd(JSONArray jsonArray, int i) {
        try {
            JSONObject jsonObj = jsonArray.getJSONObject(i);

            Ticket ticket = new Ticket(jsonObj);

            add(ticket);

        } catch (JSONException e) {
            Log.e("", e.getMessage());
        }
    }

    private final JSONArray parseJsonArray(String jsonText) throws JSONException {
        return new JSONArray(jsonText);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View ticketView = createTicketView(parent);

        TextView tvShopName = (TextView) ticketView.findViewById(R.id.store_shopName);
        TextView tvNumber = (TextView) ticketView.findViewById(R.id.ticket_number);
        TextView tvWaitCount = (TextView) ticketView.findViewById(R.id.ticket_waitCount);
        TextView tvCreationTime = (TextView) ticketView.findViewById(R.id.ticket_regTime);

        Ticket ticket = (Ticket) getItem(position);

        tvShopName.setText(ticket.getStoreName());
        tvNumber.setText(ticket.getTicketNumber());
        tvWaitCount.setText(ticket.getWaitNumber());
        tvCreationTime.setText(ticket.getCreationTime());

        return ticketView;
    }

    private View createTicketView(ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        return inflater.inflate(R.layout.widget_ticket, parent, false);
    }

}
