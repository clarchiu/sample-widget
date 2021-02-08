package com.example.widgettest;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class SampleWidget extends AppWidgetProvider {

    private static boolean status = true;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.sample_widget);

        Intent intentUpdate = new Intent(context, SampleWidget.class);
        intentUpdate.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

        int[] idArray = new int[]{appWidgetId};

        intentUpdate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, idArray);

        PendingIntent pendingUpdate = PendingIntent.getBroadcast(
                context, appWidgetId, intentUpdate,
                PendingIntent.FLAG_UPDATE_CURRENT);

        views.setOnClickPendingIntent(R.id.button2, pendingUpdate);

        if(status){
            views.setViewVisibility(R.id.button6, View.GONE);
            views.setViewVisibility(R.id.button7, View.GONE);
            views.setViewVisibility(R.id.button8, View.GONE);
            status = false;
        }else {
            views.setViewVisibility(R.id.button6, View.VISIBLE);
            views.setViewVisibility(R.id.button7, View.VISIBLE);
            views.setViewVisibility(R.id.button8, View.VISIBLE);
            status = true;
        }

        Intent intentOne = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.hobuonline.com/services/home-services#"));
        Intent intentTwo = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.hobuonline.com/services/delivery#"));
        Intent intentThree = new Intent(Intent.ACTION_VIEW, Uri.parse("https://calendly.com/hobu/a-call-with-hobu?month=2021-02"));

        // In widget we are not allowing to use intents as usually. We have to use PendingIntent instead of 'startActivity'
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intentOne, 0);
        PendingIntent pendingIntentTwo = PendingIntent.getActivity(context, 0, intentTwo, 0);
        PendingIntent pendingIntentThree = PendingIntent.getActivity(context, 0, intentThree, 0);

        // Here the basic operations the remote view can do.
        views.setOnClickPendingIntent(R.id.button6, pendingIntent);
        views.setOnClickPendingIntent(R.id.button7, pendingIntentTwo);
        views.setOnClickPendingIntent(R.id.button8, pendingIntentThree);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
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