package com.doleestudio.tycheapp;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class StoreListFragment extends Fragment implements AbsListView.OnItemClickListener {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String STORE_QUERY = "query";

    // TODO: Rename and change types of parameters
    private String query;

    private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private StoreAdapter mAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public StoreListFragment() {
    }

    public static StoreListFragment newInstance(String query) {
        StoreListFragment fragment = new StoreListFragment();
        Bundle args = new Bundle();
        args.putString(STORE_QUERY, query);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            query = getArguments().getString(STORE_QUERY);
        }

        mAdapter = new StoreAdapter(getActivity(), android.R.layout.simple_list_item_1);
        new Downloader().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        mListView.setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            Store store = mAdapter.getItem(position);

            openStoreActivity(store);

            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            // mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
        }
    }

    private void openStoreActivity(Store store) {
        Intent intent = new Intent(getActivity(), StoreActivity.class);
        intent.putExtra(StoreActivity.STORE_TAG, store);
        startActivity(intent);
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    private class Downloader extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                mAdapter.fetch(query);
            } catch (Exception e) {
                Log.e("StoreListFragment", e.getMessage());
                cancel(true);
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            if (!NetworkConnector.isConnected(getActivity())) {
                showErrorToast("네트워크에 연결되지 않았습니다. 네트워크에 연결해 주십시오.");
            }
        }

        @Override
        protected void onPostExecute(Void v) {
            notifyDataChanged();
        }

        @Override
        protected void onCancelled() {
            showErrorToast("서버에 이상이 발생했습니다. 앱을 다시 실행해 주시고 그래도 이상이 있을 경우엔 회사 고객지원 센터에 연락주시기 바랍니다.");
        }

        private void notifyDataChanged() {
            mListView.setAdapter(mAdapter);
        }

        private void showErrorToast(String msg) {
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(getActivity(), msg, duration);
            toast.show();
        }
    }
}
