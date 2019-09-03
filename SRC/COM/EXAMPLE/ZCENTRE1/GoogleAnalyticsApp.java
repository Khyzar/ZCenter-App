package com.example.zcentre1;

import java.util.HashMap;

import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import android.app.Application;

public class GoogleAnalyticsApp extends Application {
//    private static final String PROPERTY_ID = "UA-63200425-1";
//    public static int GENERAL_TRACKER = 0;
//    public enum TrackerName {
//        APP_TRACKER, GLOBAL_TRACKER, ECOMMERCE_TRACKER,
//    }
//    public HashMap mTrackers = new HashMap();
//    public GoogleAnalyticsApp() {
//        super();
//    }
//    public synchronized Tracker getTracker(TrackerName appTracker) {
//        if (!mTrackers.containsKey(appTracker)) {
//            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
//            Tracker t = (appTracker == TrackerName.APP_TRACKER) ? analytics.newTracker(PROPERTY_ID) : (appTracker == TrackerName.GLOBAL_TRACKER) ? analytics.newTracker(R.xml.global_tracker) : analytics.newTracker(R.xml.ecommerce_tracker);
//            mTrackers.put(appTracker, t);
//        }
//        return mTrackers.get(appTracker);
//    }
}

