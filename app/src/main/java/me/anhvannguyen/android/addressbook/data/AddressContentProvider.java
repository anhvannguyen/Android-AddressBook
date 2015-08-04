package me.anhvannguyen.android.addressbook.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by anhvannguyen on 7/30/15.
 */
public class AddressContentProvider extends ContentProvider {
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private AddressDBHelper mOpenHelper;

    private static final int ADDRESS = 100;
    private static final int ADDRESS_WITH_ID = 101;

    @Override
    public boolean onCreate() {
        mOpenHelper = new AddressDBHelper(getContext());
        return true;
    }

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = AddressContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, AddressContract.PATH_ADDRESS, ADDRESS);
        matcher.addURI(authority, AddressContract.PATH_ADDRESS + "/#", ADDRESS_WITH_ID);

        return matcher;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case ADDRESS:
                return AddressContract.AddressEntry.CONTENT_TYPE;
            case ADDRESS_WITH_ID:
                return AddressContract.AddressEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final int match = sUriMatcher.match(uri);
        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();

        Cursor retCursor;
        switch (match) {
            case ADDRESS:
                retCursor = db.query(
                        AddressContract.AddressEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case ADDRESS_WITH_ID:
                String addressID = AddressContract.AddressEntry.getAddressId(uri);
                retCursor = db.query(
                        AddressContract.AddressEntry.TABLE_NAME,
                        projection,
                        AddressContract.AddressEntry._ID + " = ?",
                        new String[]{addressID},
                        null,
                        null,
                        sortOrder
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        return retCursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final int match = sUriMatcher.match(uri);
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        Uri returnUri;

        switch (match) {
            case ADDRESS: {
                long _id = db.insert(
                        AddressContract.AddressEntry.TABLE_NAME,
                        null,
                        values
                );
                if (_id > 0) {
                    returnUri = AddressContract.AddressEntry.buildAddressUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int rowsDeleted;

        // this makes delete all rows return the number of rows deleted
        if (null == selection) selection = "1";

        switch (match) {
            case ADDRESS: {
                rowsDeleted = db.delete(
                        AddressContract.AddressEntry.TABLE_NAME,
                        selection,
                        selectionArgs
                );
                break;
            }
            case ADDRESS_WITH_ID: {
                String addressId = AddressContract.AddressEntry.getAddressId(uri);
                rowsDeleted = db.delete(
                        AddressContract.AddressEntry.TABLE_NAME,
                        AddressContract.AddressEntry._ID + " = ?",
                        new String[]{addressId}
                );
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int rowsUpdated;

        switch (match) {
            case ADDRESS: {
                rowsUpdated = db.update(
                        AddressContract.AddressEntry.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs
                );
                break;
            }
            case ADDRESS_WITH_ID: {
                String addressId = AddressContract.AddressEntry.getAddressId(uri);
                rowsUpdated = db.update(
                        AddressContract.AddressEntry.TABLE_NAME,
                        values,
                        AddressContract.AddressEntry._ID + " = ?",
                        new String[]{addressId}
                );
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }

    @Override
    public void shutdown() {
        mOpenHelper.close();
        super.shutdown();
    }
}
