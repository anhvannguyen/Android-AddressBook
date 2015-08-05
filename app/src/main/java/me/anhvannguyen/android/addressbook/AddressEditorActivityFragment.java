package me.anhvannguyen.android.addressbook;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
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
public class AddressEditorActivityFragment extends Fragment {
    private static final int ADDRESS_DETAIL_LOADER = 0;
    public static final String ADDRESS_DETAIL_URI = "ADDRESS_URI";

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
                if (mIntentString == Intent.ACTION_INSERT) {
                    insertAddress();
                } else {
                    // TODO: Update Address
                }
            }
        });

        return rootView;
    }

    boolean isValidEmailFormat(CharSequence email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void insertAddress() {
        boolean inputError = inputFieldsHasError();

        if (inputError ==  false) {
            getActivity().getContentResolver().insert(AddressContract.AddressEntry.CONTENT_URI, getAddressContentValue());
            getActivity().finish();
        }
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
            mEmailInputLayout.setError("Invalid Email Format");
            inputError = true;
        }

        return inputError;
    }

}
