package me.anhvannguyen.android.addressbook;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.anhvannguyen.android.addressbook.data.AddressContract;


/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int ADDRESS_DETAIL_LOADER = 0;
    public static final String ADDRESS_DETAIL_URI = "ADDRESS_URI";
    private Uri mUri;

    private static final String[] ADDRESS_DETAIL_PROJECTION = {
            AddressContract.AddressEntry._ID,
            AddressContract.AddressEntry.COLUMN_NAME,
            AddressContract.AddressEntry.COLUMN_PHONE,
            AddressContract.AddressEntry.COLUMN_EMAIL,
            AddressContract.AddressEntry.COLUMN_STREET,
            AddressContract.AddressEntry.COLUMN_CITY,
            AddressContract.AddressEntry.COLUMN_STATE,
            AddressContract.AddressEntry.COLUMN_ZIPCODE
    };

    public static final int COL_ADDRESS_ID = 0;
    public static final int COL_ADDRESS_NAME = 1;
    public static final int COL_ADDRESS_PHONE = 2;
    public static final int COL_ADDRESS_EMAIL = 3;
    public static final int COL_ADDRESS_STREET = 4;
    public static final int COL_ADDRESS_CITY = 5;
    public static final int COL_ADDRESS_STATE = 6;
    public static final int COL_ADDRESS_ZIP = 7;


    private TextView mNameTextView;
    private TextView mPhoneTextView;
    private TextView mEmailTextView;
    private TextView mStreetTextView;
    private TextView mCityTextView;
    private TextView mStateTextView;
    private TextView mZipTextView;


    public DetailActivityFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        Bundle arguments = getArguments();
        if (arguments != null) {
            mUri = arguments.getParcelable(ADDRESS_DETAIL_URI);
        }

        mNameTextView = (TextView) rootView.findViewById(R.id.name_detail_textview);
        mPhoneTextView = (TextView) rootView.findViewById(R.id.phone_detail_textview);
        mEmailTextView = (TextView) rootView.findViewById(R.id.email_detail_textview);
        mStreetTextView = (TextView) rootView.findViewById(R.id.street_detail_textview);
        mCityTextView = (TextView) rootView.findViewById(R.id.city_detail_textview);
        mStateTextView = (TextView) rootView.findViewById(R.id.state_detail_textview);
        mZipTextView = (TextView) rootView.findViewById(R.id.zip_detail_textview);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_detail_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_delete:
                Snackbar.make(getView(), "Delete...", Snackbar.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(ADDRESS_DETAIL_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (mUri != null) {
            return new CursorLoader(
                    getActivity(),
                    mUri,
                    ADDRESS_DETAIL_PROJECTION,
                    null,
                    null,
                    null
            );
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (!data.moveToFirst()) {
            return;
        }

        mNameTextView.setText(data.getString(COL_ADDRESS_NAME));
        mPhoneTextView.setText(data.getString(COL_ADDRESS_PHONE));
        mEmailTextView.setText(data.getString(COL_ADDRESS_EMAIL));
        mStreetTextView.setText(data.getString(COL_ADDRESS_STREET));
        mCityTextView.setText(data.getString(COL_ADDRESS_CITY));
        mStateTextView.setText(data.getString(COL_ADDRESS_STATE));
        mZipTextView.setText(data.getString(COL_ADDRESS_ZIP));

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
