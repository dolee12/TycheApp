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
 * Created by dolee@outlook.com on 14. 12. 17..
 */
public class StoreAdapter extends ArrayAdapter<Ticket> {
    private Context context;
    private StoreList stores;

    public StoreAdapter(Context context, int resource) {
        super(context, resource);

        this.context = context;
        this.stores = new StoreList();
    }

    public final void fetch(String query) throws JSONException, IOException, NetworkConnector.NetworkConnectorException {
        stores.fetch(query);
    }

    @Override
    public final int getCount() {
        return stores.size();
    }

    @Override
    public final View getView(int position, View convertView, ViewGroup parent) {

        View storeView = createStoreView(parent);

        TextView tvShopName = (TextView) storeView.findViewById(R.id.store_shopName);
        TextView tvShopCategory = (TextView) storeView.findViewById(R.id.store_category);
        TextView tvWaitCount = (TextView) storeView.findViewById(R.id.store_waitCount);

        Store store = (Store) stores.get(position);

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
