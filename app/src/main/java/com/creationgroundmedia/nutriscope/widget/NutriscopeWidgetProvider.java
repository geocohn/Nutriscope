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

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import com.creationgroundmedia.nutriscope.R;
import com.creationgroundmedia.nutriscope.detail.DetailActivity;
/**
 * Created by George Cohn III on 9/11/2016.
 * Implementation of App Widget functionality.
 */

public class NutriscopeWidgetProvider extends AppWidgetProvider {

    private static final String LOG_TAG = NutriscopeWidgetProvider.class.getSimpleName();

    public static final String APPWIDGET_UPDATE = "android.appwidget.action.APPWIDGET_UPDATE";

    // updateWidgets is called by the app
    // when the data appears as the result of a search (in onLoadFinished()).
    // It sends a broadcast that's intercepted by onReceive() below
    public static void updateWidgets(Context context) {
        Intent updateIntent = new Intent();
        updateIntent.setAction(APPWIDGET_UPDATE);
//        Log.d(LOG_TAG, "updateWidgets()");
        context.getApplicationContext().sendBroadcast(updateIntent);
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Intent intent = new Intent(context, NutriscopeWidgetListviewService.class);
        // pass in the widget id as an extra
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        // indicate that the extra is significant
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.nutriscope_widget);
        views.setRemoteAdapter(R.id.widget_list_view, intent);
        views.setEmptyView(R.id.widget_list_view, R.id.empty_view);

        // Set it up to launch the detail activity when a listview item is clicked
        PendingIntent detailLaunchPendingIntent = PendingIntent.getActivity(context,
                0,
                new Intent(context, DetailActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.widget_list_view, detailLaunchPendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
//        Log.d(LOG_TAG, "onUpdate()");
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
//        Log.d(LOG_TAG, "onReceive(" + intent.getAction() + ")");
        if (APPWIDGET_UPDATE.equals(intent.getAction())) {
            AppWidgetManager appWidgetManager =
                    AppWidgetManager.getInstance(context.getApplicationContext());
            ComponentName thisWidget = new ComponentName(context.getApplicationContext(),
                    NutriscopeWidgetProvider.class);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list_view);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

