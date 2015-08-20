package me.anhvannguyen.android.addressbook;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import me.anhvannguyen.android.addressbook.data.AddressContract;


public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String LOG_TAG = MainActivityFragment.class.getSimpleName();
    private static final int ADDRESS_LIST_LOADER = 0;

    private RecyclerView mAddressRecyclerView;
    private AddressRecyclerAdapter mRecycleAdapter;
    private FloatingActionButton mAddButton;
    private CoordinatorLayout mCoordinatorLayout;

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface AddressSelectedCallback {
        public void onItemSelected(Uri addressUri, int position);
    }


    public MainActivityFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mCoordinatorLayout = (CoordinatorLayout) rootView.findViewById(R.id.coordinator_layout);

        mRecycleAdapter = new AddressRecyclerAdapter(getActivity(),
                new AddressRecyclerAdapter.AddressAdapterOnClickHandler() {
                    @Override
                    public void onClick(AddressRecyclerAdapter.ViewHolder viewHolder) {
                        int idIndex = mRecycleAdapter.getCursor().getColumnIndex(AddressContract.AddressEntry._ID);
                        long id = mRecycleAdapter.getCursor().getLong(idIndex);
                        Uri addressUri = AddressContract.AddressEntry.buildAddressUri(id);
                        ((AddressSelectedCallback)getActivity()).onItemSelected(addressUri, mRecycleAdapter.getCursor().getPosition());
                    }
                });

        mAddButton = (FloatingActionButton) rootView.findViewById(R.id.add_fab);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddressEditorActivity.class);
                startActivity(intent);
            }
        });

        mAddressRecyclerView = (RecyclerView) rootView.findViewById(R.id.main_address_recyclerview);
        // improve performance if the content of the layout
        // does not change the size of the RecyclerView
        mAddressRecyclerView.setHasFixedSize(true);

        mAddressRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAddressRecyclerView.setAdapter(mRecycleAdapter);
        
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_delete_all:
                getActivity().getContentResolver().delete(
                        AddressContract.AddressEntry.CONTENT_URI,
                        null,
                        null
                );
                getLoaderManager().restartLoader(ADDRESS_LIST_LOADER, null, this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(ADDRESS_LIST_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(ADDRESS_LIST_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                getActivity(),
                AddressContract.AddressEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mRecycleAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mRecycleAdapter.swapCursor(null);
    }
}
