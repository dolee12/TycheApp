package com.doleestudio.tycheapp;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;


public class MainActivity extends Activity implements OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            openTicketFragment();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        configureSearchView(menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void configureSearchView(Menu menu) {
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        MenuItem menuItem = menu.findItem(R.id.action_search);

        SearchView searchView;
        searchView = (SearchView) menuItem.getActionView();
        searchView.setSubmitButtonEnabled(true);

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_store) {
            openStoreActivity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openStoreActivity() {
        Intent intent = new Intent(Intent.ACTION_SEARCH, null, this, StoreListActivity.class);
        startActivity(intent);
    }

    private void openTicketFragment() {
        TicketFragment ticketFragment = TicketFragment.newInstance("Item 1", "Item 2");

        getFragmentManager().beginTransaction()
                .add(R.id.main_container, ticketFragment)
                .commit();
    }

    @Override
    public void onFragmentInteraction(String id) {

    }
}
