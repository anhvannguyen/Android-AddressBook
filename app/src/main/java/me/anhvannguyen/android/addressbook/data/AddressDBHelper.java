package me.anhvannguyen.android.addressbook.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by anhvannguyen on 7/30/15.
 */
public class AddressDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "addressbook.db";

    public AddressDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_ADDRESS_TABLE = "CREATE TABLE " + AddressContract.AddressEntry.TABLE_NAME + " (" +
                AddressContract.AddressEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                AddressContract.AddressEntry.COLUMN_ALIAS + " TEXT, " +
                AddressContract.AddressEntry.COLUMN_FIRST_NAME + " TEXT, " +
                AddressContract.AddressEntry.COLUMN_MIDDLE_NAME + " TEXT, " +
                AddressContract.AddressEntry.COLUMN_LAST_NAME + " TEXT, " +
                AddressContract.AddressEntry.COLUMN_PHONE + " TEXT, " +
                AddressContract.AddressEntry.COLUMN_EMAIL + " TEXT, " +
                AddressContract.AddressEntry.COLUMN_STREET + " TEXT, " +
                AddressContract.AddressEntry.COLUMN_CITY + " TEXT, " +
                AddressContract.AddressEntry.COLUMN_STATE + " TEXT, " +
                AddressContract.AddressEntry.COLUMN_ZIPCODE + " INTEGER" +
                ")";


        db.execSQL(SQL_CREATE_ADDRESS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + AddressContract.AddressEntry.TABLE_NAME);
        onCreate(db);
    }
}
