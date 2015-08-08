package me.anhvannguyen.android.addressbook;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.TextView;

import me.anhvannguyen.android.addressbook.data.AddressContract;


/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int ADDRESS_DETAIL_LOADER = 0;
    public static final String ADDRESS_DETAIL_URI = "ADDRESS_DETAIL_URI";
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

    private Button mCallButton;
    private Button mEmailButton;
    private Button mMapsButton;


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

        mCallButton = (Button) rootView.findViewById(R.id.call_button);
        mEmailButton = (Button) rootView.findViewById(R.id.email_button);
        mMapsButton = (Button) rootView.findViewById(R.id.maps_button);

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
                deleteAddress();
                break;
            case R.id.action_edit:
                Intent intent = new Intent(getActivity(), AddressEditorActivity.class).setData(mUri);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteAddress() {
        if (mUri != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(getString(R.string.dialog_message));
            builder.setPositiveButton(getString(R.string.dialog_delete), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    getActivity().getContentResolver().delete(
                            mUri,
                            null,
                            null
                    );
                    getActivity().finish();
                }
            });
            builder.setNegativeButton(getString(R.string.dialog_cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(ADDRESS_DETAIL_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(ADDRESS_DETAIL_LOADER, null, this);
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

        final String name = data.getString(COL_ADDRESS_NAME);
        mNameTextView.setText(name);

        final String phone = data.getString(COL_ADDRESS_PHONE);
        mPhoneTextView.setText(phone);

        final String email = data.getString(COL_ADDRESS_EMAIL);
        mEmailTextView.setText(email);

        final String street = data.getString(COL_ADDRESS_STREET);
        mStreetTextView.setText(street);

        final String city = data.getString(COL_ADDRESS_CITY);
        mCityTextView.setText(city);

        final String state = data.getString(COL_ADDRESS_STATE);
        mStateTextView.setText(state);

        final String zip = data.getString(COL_ADDRESS_ZIP);
        mZipTextView.setText(zip);

        if (phone.length() == 0) {
            mCallButton.setEnabled(false);
        } else {
            mCallButton.setEnabled(true);
            mCallButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + phone));
                    if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                        startActivity(intent);
                    } else {
                        Snackbar.make(getView(), getString(R.string.error_phone_dialer), Snackbar.LENGTH_SHORT).show();
                    }
                }
            });
        }
        if (email.length() == 0) {
            mEmailButton.setEnabled(false);
        } else {
            mEmailButton.setEnabled(true);
            mEmailButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                    intent.putExtra(Intent.EXTRA_EMAIL, email);
                    if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                        startActivity(intent);
                    } else {
                        Snackbar.make(getView(), getString(R.string.error_email_client), Snackbar.LENGTH_SHORT).show();
                    }
                }
            });

        }
        if (street.length() == 0 && (city.length() == 0 || zip.length() == 0)) {
            mMapsButton.setEnabled(false);
        } else {
            mMapsButton.setEnabled(true);
            mMapsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri geoLocation = Uri.parse("geo:0,0?q=" + street + "," + zip);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(geoLocation);
                    if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                        startActivity(intent);
                    }
                }
            });
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
