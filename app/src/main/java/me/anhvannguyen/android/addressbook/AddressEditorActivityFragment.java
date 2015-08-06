package me.anhvannguyen.android.addressbook;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import me.anhvannguyen.android.addressbook.data.AddressContract;


/**
 * A placeholder fragment containing a simple view.
 */
public class AddressEditorActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int ADDRESS_DETAIL_LOADER = 0;
    public static final String ADDRESS_DETAIL_URI = "ADDRESS_EDIT_URI";

    private String mIntentString;

    private TextInputLayout mNameInputLayout;
    private TextInputLayout mPhoneInputLayout;
    private TextInputLayout mEmailInputLayout;
    private TextInputLayout mStreetInputLayout;
    private TextInputLayout mCityInputLayout;
    private TextInputLayout mStateInputLayout;
    private TextInputLayout mZipInputLayout;

    private EditText mNameEditText;
    private EditText mPhoneEditText;
    private EditText mEmailEditText;
    private EditText mStreetEditText;
    private EditText mCityEditText;
    private EditText mStateEditText;
    private EditText mZipEditText;
    private Button mSaveButton;

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

    public AddressEditorActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_address_editor, container, false);

        Bundle arguments = getArguments();
        if (arguments != null) {
            mUri = arguments.getParcelable(ADDRESS_DETAIL_URI);
        }

        if (mUri == null) {
            mIntentString = Intent.ACTION_INSERT;
        } else {
            mIntentString = Intent.ACTION_EDIT;
        }

        mNameInputLayout = (TextInputLayout) rootView.findViewById(R.id.name_textinputlayout);
        mPhoneInputLayout = (TextInputLayout) rootView.findViewById(R.id.phone_textinputlayout);
        mEmailInputLayout = (TextInputLayout) rootView.findViewById(R.id.email_textinputlayout);
        mStreetInputLayout = (TextInputLayout) rootView.findViewById(R.id.street_textinputlayout);
        mCityInputLayout = (TextInputLayout) rootView.findViewById(R.id.city_textinputlayout);
        mStateInputLayout = (TextInputLayout) rootView.findViewById(R.id.state_textinputlayout);
        mZipInputLayout = (TextInputLayout) rootView.findViewById(R.id.zip_textinputlayout);

        mNameEditText = (EditText) rootView.findViewById(R.id.name_edittext);
        mPhoneEditText = (EditText) rootView.findViewById(R.id.phone_edittext);
        mEmailEditText = (EditText) rootView.findViewById(R.id.email_edittext);
        mStreetEditText = (EditText) rootView.findViewById(R.id.street_edittext);
        mCityEditText = (EditText) rootView.findViewById(R.id.city_edittext);
        mStateEditText = (EditText) rootView.findViewById(R.id.state_edittext);
        mZipEditText = (EditText) rootView.findViewById(R.id.zip_edittext);

        mPhoneEditText.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        mNameInputLayout.setErrorEnabled(true);
        mEmailInputLayout.setErrorEnabled(true);

        mSaveButton = (Button) rootView.findViewById(R.id.save_button);
        mSaveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean inputError = inputFieldsHasError();
                if (mIntentString == Intent.ACTION_INSERT) {
                    if (inputError == false) {
                        getActivity().getContentResolver().insert(
                                AddressContract.AddressEntry.CONTENT_URI,
                                getAddressContentValue());
                        getActivity().finish();
                    }
                } else if (mIntentString == Intent.ACTION_EDIT) {
                    if (inputError == false) {
                        getActivity().getContentResolver().update(
                                mUri,
                                getAddressContentValue(),
                                null,
                                null
                        );
                        getActivity().finish();
                    }
                }
            }
        });

        return rootView;
    }

    boolean isValidEmailFormat(CharSequence email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Helper to generate the values from the EditText fields to ContentValues
    private ContentValues getAddressContentValue() {
        ContentValues addressValue = new ContentValues();
        addressValue.put(AddressContract.AddressEntry.COLUMN_NAME, mNameEditText.getText().toString());
        addressValue.put(AddressContract.AddressEntry.COLUMN_PHONE, mPhoneEditText.getText().toString());
        addressValue.put(AddressContract.AddressEntry.COLUMN_EMAIL, mEmailEditText.getText().toString());
        addressValue.put(AddressContract.AddressEntry.COLUMN_STREET, mStreetEditText.getText().toString());
        addressValue.put(AddressContract.AddressEntry.COLUMN_CITY, mCityEditText.getText().toString());
        addressValue.put(AddressContract.AddressEntry.COLUMN_STATE, mStateEditText.getText().toString());
        addressValue.put(AddressContract.AddressEntry.COLUMN_ZIPCODE, mZipEditText.getText().toString());

        return addressValue;
    }

    private boolean inputFieldsHasError() {
        boolean inputError = false;
        if (mNameEditText.getText().length() <= 0) {
            mNameInputLayout.setError(getString(R.string.error_name_required));
//                    mNameEditText.setError(getString(R.string.error_name_required));
            inputError = true;
        }
        if (mEmailEditText.length() > 0 && !isValidEmailFormat(mEmailEditText.getText())) {
            mEmailInputLayout.setError(getString(R.string.error_email_format));
            inputError = true;
        }

        return inputError;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (mUri == null) {
            return null;
        }
        return new CursorLoader(
                getActivity(),
                mUri,
                ADDRESS_DETAIL_PROJECTION,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (!data.moveToFirst()) {
            return;
        }

        mNameEditText.setText(data.getString(COL_ADDRESS_NAME));
        mPhoneEditText.setText(data.getString(COL_ADDRESS_PHONE));
        mEmailEditText.setText(data.getString(COL_ADDRESS_EMAIL));
        mStreetEditText.setText(data.getString(COL_ADDRESS_STREET));
        mCityEditText.setText(data.getString(COL_ADDRESS_CITY));
        mStateEditText.setText(data.getString(COL_ADDRESS_STATE));
        mZipEditText.setText(data.getString(COL_ADDRESS_ZIP));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
