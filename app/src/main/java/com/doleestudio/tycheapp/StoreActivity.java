package com.doleestudio.tycheapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class StoreActivity extends Activity {
    public static final String STORE_TAG = "storeTag";
    private Store store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        getStoreInParam();

        initializeUIComponentsWithStore();

        setGetNumButtonEventHandler();
    }

    private void setGetNumButtonEventHandler() {
        Button btnGetNum = (Button) findViewById(R.id.btnGetNum);

        btnGetNum.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                createNewTicket();
            }

        });
    }

    private void createNewTicket() {
        new NewTicketBackground().execute();
    }

    private void initializeUIComponentsWithStore() {
        TextView tvStoreName = (TextView) findViewById(R.id.store_name);
        TextView tvCategory = (TextView) findViewById(R.id.store_category);
        TextView tvAddress = (TextView) findViewById(R.id.store_addr);
        TextView tvTel = (TextView) findViewById(R.id.store_tel);
        tvStoreName.setText(store.getName());
        tvCategory.setText(store.getCategory());
        tvAddress.setText(store.getAddress());
        tvTel.setText(store.getTel());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_store, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getStoreInParam() {
        store = getIntent().getParcelableExtra(STORE_TAG);
    }

    private void setNewTicketNumTextBox(String newTicketNum) {
        TextView tvNewTicketNum = (TextView) findViewById(R.id.new_ticket_num);
        tvNewTicketNum.setText(newTicketNum);
    }

    private void showErrorToast(String msg) {
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(this, msg, duration);
        toast.show();
    }

    class NewTicketBackground extends AsyncTask<Void, Void, Ticket> {
        String msg = null;


        @Override
        protected Ticket doInBackground(Void... params) {
            Ticket ticket = null;
            String userId = "1";
            try {
                ticket = Ticket.CreateInstance(store.getId(), userId);
            } catch (Exception e) {
                msg = e.getMessage();
                cancel(true);
            }

            return ticket;
        }

        @Override
        protected void onCancelled() {
            showErrorToast(msg);
        }

        @Override
        protected void onPostExecute(Ticket ticket) {
            if (ticket == null)
                return;

            setNewTicketNumTextBox(ticket.getTicketNumber());
        }
    }
}
