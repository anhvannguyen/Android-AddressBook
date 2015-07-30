package me.anhvannguyen.android.addressbook.data;

import android.provider.BaseColumns;

/**
 * Created by anhvannguyen on 7/29/15.
 */
public class AddressContract {

    public static final class AddressEntry implements BaseColumns {
        public static final String TABLE_NAME = "address";

        // Person nickname or alias
        public static final String COLUMN_ALIAS = "alias";

        // First name
        public static final String COLUMN_FIRST_NAME = "first_name";

        // Middle name
        public static final String COLUMN_MIDDLE_NAME = "middle_name";

        // Last Name
        public static final String COLUMN_LAST_NAME = "last_name";

        // Primary phone number
        public static final String COLUMN_PHONE = "phone";

        // Primary email
        public static final String COLUMN_EMAIL = "email";

        // Street number and name
        public static final String COLUMN_STREET = "street";

        // City
        public static final String COLUMN_CITY = "city";

        // State
        public static final String COLUMN_STATE = "state";

        // Postal zip code
        public static final String COLUMN_ZIPCODE = "zipcode";


    }

    // Creating phone table for for possible future use.
    // People have multiple phone numbers (home/cell/work etc)
    public static final class PhoneEntry implements BaseColumns {
        public static final String TABLE_NAME = "phone";

        // Person id from address table
        public static final String COLUMN_PERSON_ID = "person_id";

        // Phone number
        public static final String COLUMN_PHONE_NUMBER = "phone_number";

        // Phone type (home/cell/work)
        public static final String COLUMN_PHONE_TYPE = "phone_type";
    }
}
