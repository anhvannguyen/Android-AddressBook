package me.anhvannguyen.android.addressbook.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
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
        return false;
    }

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = AddressContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, AddressContract.PATH_ADDRESS, ADDRESS);
        matcher.addURI(authority, AddressContract.PATH_ADDRESS + "/#", ADDRESS_WITH_ID);
        
        return matcher;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
