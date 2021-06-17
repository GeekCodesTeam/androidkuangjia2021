package com.fosung.xuanchuanlan.xuanchuanlan.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

import com.fosung.xuanchuanlan.common.app.ConfApplication;


public class UserInfoContentProvider extends ContentProvider {

    private SharedPreferences userinfoSP;
    public UserInfoContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.

        SharedPreferences userinfoSP = ConfApplication.APP_CONTEXT.getSharedPreferences("DTDJUserInfo", Context.MODE_PRIVATE);
        String username = userinfoSP.getString("dtdj_username",null);
        String password = userinfoSP.getString("dtdj_password",null);

        MatrixCursor cursor = new MatrixCursor(new String[]{"dtdj_username","dtdj_password"});
        cursor.addRow(new Object[]{username,password});
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
