package com.doleestudio.tycheapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by dolee@outlook.com on 14. 12. 15..
 */
public class TicketAdapter extends ArrayAdapter<Ticket> {

    private final TicketList tickets;
    private final Context context;


    public TicketAdapter(Context context, int resource) {
        super(context, resource);

        this.context = context;
        this.tickets = new TicketList();
    }

    public void fetch() throws JSONException, IOException, NetworkConnector.NetworkConnectorException {
        tickets.fetch();
    }

    @Override
    public int getCount() {
        return tickets.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View ticketView = createTicketView(parent);

        TextView tvShopName = (TextView) ticketView.findViewById(R.id.store_shopName);
        TextView tvNumber = (TextView) ticketView.findViewById(R.id.ticket_number);
        TextView tvWaitCount = (TextView) ticketView.findViewById(R.id.ticket_waitCount);
        TextView tvRegTime = (TextView) ticketView.findViewById(R.id.ticket_regTime);

        Ticket ticket = (Ticket) tickets.get(position);

        tvShopName.setText(ticket.getStoreName());
        tvNumber.setText(ticket.getStringNumber());
        tvWaitCount.setText(ticket.getStringWaitOrder());
        tvRegTime.setText(ticket.getStringRegTime());

        return ticketView;
    }

    private View createTicketView(ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        return inflater.inflate(R.layout.widget_ticket, parent, false);
    }

}
