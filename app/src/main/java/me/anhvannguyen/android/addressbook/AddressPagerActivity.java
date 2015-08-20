package me.anhvannguyen.android.addressbook;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;

import me.anhvannguyen.android.addressbook.data.AddressContract;

/**
 * Created by anhvannguyen on 8/18/15.
 */
public class AddressPagerActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int ADDRESS_DETAIL_LOADER = 0;

    private ViewPager mAddressViewPager;
    private Cursor mCursor;

    private static final String[] ADDRESS_DETAIL_PROJECTION = {
            AddressContract.AddressEntry._ID,
    };

    public static final int COL_ADDRESS_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_pager);

        getSupportLoaderManager().initLoader(ADDRESS_DETAIL_LOADER, null, this);

        mAddressViewPager = (ViewPager) findViewById(R.id.activity_address_detail_pager);
        mAddressViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                mCursor.moveToPosition(position);
                Uri addressUri = AddressContract.AddressEntry.buildAddressUri(mCursor.getLong(COL_ADDRESS_ID));

                Bundle arguments = new Bundle();
                arguments.putParcelable(DetailActivityFragment.ADDRESS_DETAIL_URI, addressUri);

                DetailActivityFragment fragment = new DetailActivityFragment();
                fragment.setArguments(arguments);

                return fragment;
            }

            @Override
            public int getCount() {
                if (mCursor == null) {
                    return 0;
                }
                return mCursor.getCount();
            }
        });


    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                this,
                AddressContract.AddressEntry.CONTENT_URI,
                ADDRESS_DETAIL_PROJECTION,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursor = data;
        mAddressViewPager.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursor = null;
    }
}
