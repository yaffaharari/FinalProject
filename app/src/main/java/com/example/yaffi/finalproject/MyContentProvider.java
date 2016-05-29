package com.example.yaffi.finalproject;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.SyncStateContract;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.List;

public class MyContentProvider extends ContentProvider {

    private DBOpenHelper db;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(PlacesContract.Places.AUTHORITY, PlacesContract.Places.TABLE_NAME, PlacesContract.Places.STATUS_DIR);
        uriMatcher.addURI(PlacesContract.Places.AUTHORITY, PlacesContract.Places.TABLE_NAME + "/#", PlacesContract.Places.STATUS_ITEM_ID);
    }


    @Override
    public boolean onCreate() {
        db = new DBOpenHelper(getContext(), PlacesContract.DB_NAME, null, PlacesContract.DB_VERTION);
        return true;
    }
    protected String getTableName(Uri uri) {
        List<String> pathSegments = uri.getPathSegments();
        return pathSegments.get(0);
    }

    //==========================query(.....)==============================
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(getTableName(uri));

        int uriType = uriMatcher.match(uri);
        switch (uriType) {
            case PlacesContract.Places.STATUS_DIR:
                break;
            case PlacesContract.Places.STATUS_ITEM_ID:
                queryBuilder.appendWhere(PlacesContract.Places._ID + "="
                        + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI");
        }
        Cursor cursor = queryBuilder.query(db.getWritableDatabase(),projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    //===============getType(....)================
    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    //==============insert(...)===================
    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        db.getWritableDatabase().insert(getTableName(uri), null, values);
        getContext().getContentResolver().notifyChange(uri, null);

        return null;
    }

    //======================delete(......)=================================
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        int uriType = uriMatcher.match(uri);
        int rowsAffected = 0;

        switch (uriType) {
            case PlacesContract.Places.STATUS_DIR:
                rowsAffected = db.getWritableDatabase().delete(getTableName(uri),
                        selection, selectionArgs);
                break;
            case PlacesContract.Places.STATUS_ITEM_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsAffected = db.getWritableDatabase().delete(getTableName(uri),
                            PlacesContract.Places._ID + "=" + id, null);
                } else {
                    rowsAffected = db.getWritableDatabase().delete(getTableName(uri),
                            selection + " and " + PlacesContract.Places._ID + "=" + id,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown or Invalid URI " + uri);
        }
        if (rowsAffected > 0) {

            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsAffected;
    }

    //==================update(.......)============================
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        int uriType = uriMatcher.match(uri);
        int rowsAffected = 0;
        switch (uriType) {
            case PlacesContract.Places.STATUS_DIR:
                rowsAffected = db.getWritableDatabase().update(getTableName(uri), values, selection, selectionArgs);
                break;
            case PlacesContract.Places.STATUS_ITEM_ID:
                String id = uri.getLastPathSegment();
                rowsAffected = db.getWritableDatabase()
                        .update(PlacesContract.Places.TABLE_NAME, values, PlacesContract.Places._ID + " = " + id +
                                (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown or Invalid URI " + uri);
        }
      /*  if (rowsAffected > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }*/
        return rowsAffected;
    }
}
