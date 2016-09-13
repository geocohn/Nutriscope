/*
 * Copyright 2016 George Cohn III
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.creationgroundmedia.nutriscope.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.creationgroundmedia.nutriscope.MainActivity;
import com.creationgroundmedia.nutriscope.R;
import com.creationgroundmedia.nutriscope.data.NutriscopeContract;
import com.creationgroundmedia.nutriscope.detail.DetailActivity;


/**
 * Created by George Cohn III on 9/11/2016.
 *
 * Implements a collection widget as a ListView
 * It uses the app's content provider as its data source.
 * When an item is pressed, it launches the app's DetailActivity to show the selected item
 */
class NutriscopeRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private static final String LOG_TAG = NutriscopeRemoteViewsFactory.class.getSimpleName();

    // Even though a list item only displays the product name,
    // we'll need the other columns to launch the app detail activity
    // if the item is clicked
    private static final String[] PROJECTION = new String[] {
            NutriscopeContract.ProductsEntry._ID,
            NutriscopeContract.ProductsEntry.COLUMN_IMAGE,
            NutriscopeContract.ProductsEntry.COLUMN_NAME,
            NutriscopeContract.ProductsEntry.COLUMN_PRODUCTID
    };
    // The following must agree with the PROJECTION above
    private static final int ID = 0;
    private static final int IMAGE = 1;
    private static final int NAME = 2;
    private static final int PRODUCTID = 3;

    private Context mContext;
    private Cursor mCursor = null;

    public NutriscopeRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        int mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    public void onCreate() {
        // In onCreate() you setup any connections / cursors to your data source. Heavy lifting,
        // for example downloading or creating content etc, should be deferred to onDataSetChanged()
        // or getViewAt(). Taking more than 20 seconds in this call will result in an ANR.

        // In this case the only thing to be done is loading the database,
        // which is done in onDataSetChanged()
}

    public void onDestroy() {
        // In onDestroy() you should tear down anything that was setup for your data source,
        // eg. cursors, connections, etc.
        if (mCursor != null) {
            mCursor.close();
        }
    }

    public int getCount() {
        return mCursor.getCount();
    }

    public RemoteViews getViewAt(int position) {
        // position will always range from 0 to getCount() - 1.

        // We construct a remote views item based on our widget item xml file, and set up the
        // views based on the position.
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.nutriscope_widget_item);
        mCursor.moveToPosition(position);

//        Log.d(LOG_TAG, "getViewAt(" + position + ") setting text to \'" + mCursor.getString(NAME) + "\'");
        rv.setTextViewText(R.id.widget_row_name, mCursor.getString(NAME));

        // Next, we set a fill-intent which will be used to fill-in the pending intent template
        // which is set on the collection view in NutriscopeWidgetProvider.
        // In this case, the entire intent will be filled in, including both activity and extras

        rv.setOnClickFillInIntent(R.id.widget_row,
                DetailActivity.instanceIntent(mContext,
                        mCursor.getLong(ID),
                        mCursor.getString(NAME),
                        mCursor.getString(PRODUCTID),
                        mCursor.getString(IMAGE)));

        // Return the remote views object.
        return rv;
    }

    public RemoteViews getLoadingView() {
        // You can create a custom loading view (for instance when getViewAt() is slow.) If you
        // return null here, you will get the default loading view.
        return null;
    }

    public int getViewTypeCount() {
        return 1;
    }

    public long getItemId(int position) {
        return position;
    }

    public boolean hasStableIds() {
        return true;
    }

    public void onDataSetChanged() {
        // This is triggered when you call AppWidgetManager notifyAppWidgetViewDataChanged
        // on the collection view corresponding to this factory. You can do heaving lifting in
        // here, synchronously. For example, if you need to process an image, fetch something
        // from the network, etc., it is ok to do it here, synchronously. The widget will remain
        // in its current state while work is being done here, so you don't need to worry about
        // locking up the widget.
//        Log.d(LOG_TAG, "onDataSetChanged()");
        if (mCursor != null) {
            mCursor.close();
        }
        mCursor = mContext.getContentResolver().query(
                NutriscopeContract.ProductsEntry.PRODUCTSEARCH_URI,
                PROJECTION,
                null,
                null,
                MainActivity.SORT_NAME);
    }
}
