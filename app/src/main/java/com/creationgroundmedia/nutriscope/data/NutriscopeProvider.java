package com.creationgroundmedia.nutriscope.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.creationgroundmedia.nutriscope.api.SimpleSearch;
import com.creationgroundmedia.nutriscope.pojos.ApiSearchResult;

public class NutriscopeProvider extends ContentProvider {
    private static final UriMatcher mUriMatcher = buildUriMatcher();
    private static final String LOG_CAT = NutriscopeProvider.class.getSimpleName();
    private NutriscopeDbHelper mOpenHelper;

    static final int PRODUCTS = 100;
    static final int PRODUCTSEARCH = 101;
    static final int UPCSEARCH = 102;
    static final int UPC = 103;
    private Uri mLastUri = null;

    public NutriscopeProvider() {
    }

    private static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = NutriscopeContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, NutriscopeContract.PATH_PRODUCTS, PRODUCTS);
        matcher.addURI(authority, NutriscopeContract.PATH_PRODUCTS
                + "/" + NutriscopeContract.PATH_PRODUCTSEARCH , PRODUCTSEARCH);
        matcher.addURI(authority, NutriscopeContract.PATH_PRODUCTS
                + "/" + NutriscopeContract.PATH_PRODUCTSEARCH  + "/*", PRODUCTSEARCH);
        matcher.addURI(authority, NutriscopeContract.PATH_PRODUCTS
                + "/" + NutriscopeContract.PATH_UPCSEARCH , UPCSEARCH);
        matcher.addURI(authority, NutriscopeContract.PATH_PRODUCTS
                + "/" + NutriscopeContract.PATH_UPCSEARCH + "/*", UPCSEARCH);
        matcher.addURI(authority, NutriscopeContract.PATH_PRODUCTS + "/UPC/*", UPC);

        return matcher;
    }

    @Override
    public void shutdown() {
        mOpenHelper.close();
        super.shutdown();
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int retVal = -1;

        Log.d(LOG_CAT, "delete, uri: " + uri + ", uriMatch: " + mUriMatcher.match(uri));

        switch (mUriMatcher.match(uri)) {
            case PRODUCTS:
            case PRODUCTSEARCH:
            case UPCSEARCH: {
                retVal = mOpenHelper.getWritableDatabase()
                        .delete(NutriscopeContract.ProductsEntry.TABLE_NAME, selection, selectionArgs);
                break;
            }
            default: {
                throw new UnsupportedOperationException("Not yet implemented");
            }
        }
        if (retVal > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return retVal;
    }

    @Override
    public String getType(Uri uri) {
        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = mUriMatcher.match(uri);

        switch (match) {
            case PRODUCTS:
                return NutriscopeContract.ProductsEntry.CONTENT_ITEM_TYPE;
            case PRODUCTSEARCH:
                return NutriscopeContract.ProductsEntry.CONTENT_TYPE;
            case UPCSEARCH:
                return NutriscopeContract.ProductsEntry.CONTENT_TYPE;
            case UPC:
                return NutriscopeContract.ProductsEntry.CONTENT_TYPE;
           default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri returnUri = uri;

        switch (mUriMatcher.match(uri)) {
            case PRODUCTS:
            case PRODUCTSEARCH:
            case UPCSEARCH: {
                long _id = mOpenHelper.getWritableDatabase()
                        .insert(NutriscopeContract.ProductsEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = NutriscopeContract.ProductsEntry.buildProductUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default: {
                throw new UnsupportedOperationException("Not yet implemented");
            }
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        Log.d(LOG_CAT, "bulkInsert Uri = " + uri + "( " + mUriMatcher.match(uri) + ")");
        switch (mUriMatcher.match(uri)) {
            case PRODUCTS:
            case PRODUCTSEARCH:
            case UPCSEARCH: {
                db.beginTransaction();
                int count = 0;
                for (ContentValues value : values) {
                    long _id = mOpenHelper.getWritableDatabase()
                            .insert(NutriscopeContract.ProductsEntry.TABLE_NAME, null, value);
                    if (_id > 0)
                        count++;
                }
                db.setTransactionSuccessful();
                db.endTransaction();
                getContext().getContentResolver().notifyChange(uri, null);
                return count;
            }
            default: {
                return super.bulkInsert(uri, values);
            }
        }
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new NutriscopeDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor = null;
        Log.d(LOG_CAT, "query URI: " + uri);
        switch (mUriMatcher.match(uri)) {
            case PRODUCTS: {

                break;
            }
            case PRODUCTSEARCH:
            case UPCSEARCH: {
                cursor = mOpenHelper.getReadableDatabase().query(
                        NutriscopeContract.ProductsEntry.TABLE_NAME,
                        projection,
                        null,
                        null,
                        null,
                        null,
                        sortOrder);
                mLastUri = uri;
                break;
            }
            case UPC: {

                break;
            }
            default: {
                throw new UnsupportedOperationException("Not yet implemented");
            }
        }
        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
