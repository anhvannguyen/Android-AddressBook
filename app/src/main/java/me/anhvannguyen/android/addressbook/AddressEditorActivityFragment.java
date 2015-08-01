package me.anhvannguyen.android.addressbook;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A placeholder fragment containing a simple view.
 */
public class AddressEditorActivityFragment extends Fragment {
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

    public AddressEditorActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_address_editor, container, false);

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

        mSaveButton = (Button) rootView.findViewById(R.id.save_button);
        mSaveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mNameEditText.getText().length() <= 0) {
                    mNameInputLayout.setError(getString(R.string.error_name_required));
                }
            }
        });

        return rootView;
    }
}
