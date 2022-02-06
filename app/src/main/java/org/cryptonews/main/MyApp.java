package org.cryptonews.main;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDexApplication;

import org.cryptonews.main.network.Client;

import java.util.Locale;

public class MyApp extends MultiDexApplication {

    public final static String prefs = "PREFS",
            type_sort = "TYPE",
            order_sort = "ORDER",
            checked_index = "CHECKED_INDEX",
            marketInfo = "MARKET_INFO",
            type_sort_fav = "TYPE_FAV",
            order_sort_fav = "ORDER_FAV",
            checked_index_fav = "CHECKED_INDEX_FAV",
            marketInfo_fav = "MARKET_INFO_FAV",
            theme="THEME",
            favorites="FAVORITES",
            language = "LANGUAGE",
            changes = "CHANGES";
    public final static int hour = 0, day = 1, week = 2;

    private static Client client;
    private static Utils utils;

    @Override
    public void onCreate() {
        SharedPreferences preferences = getSharedPreferences(prefs,MODE_PRIVATE);
        AppCompatDelegate.setDefaultNightMode(preferences.getInt(theme,0)==0 ?
                AppCompatDelegate.MODE_NIGHT_NO : AppCompatDelegate.MODE_NIGHT_YES);
        super.onCreate();
        client = new Client();
        utils = new Utils(getApplicationContext());
        Log.d("TAG",getResources().getConfiguration().getLocales().get(0).getDisplayLanguage());
    }
    public static Utils getUtils() {return utils;}
    public static Client getClient() {
        return client;
    }
}