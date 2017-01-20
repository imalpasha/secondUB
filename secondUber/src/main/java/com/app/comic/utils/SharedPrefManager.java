package com.app.comic.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import java.util.HashMap;

public class SharedPrefManager {
    private static final String PREF_NAME = "AndroidHivePref";

    public static final String USER_TYPE = "UT";
    public static final String LANGUAGE_CODE = "en";


    int PRIVATE_MODE = 0;
    Context _context;
    private SharedPreferences _sharedPrefs;
    private Editor _prefsEditor;

    public SharedPrefManager(Context context) {
        this._context = context;
        _sharedPrefs = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        _prefsEditor = _sharedPrefs.edit();
    }


    public HashMap<String, String> getUserType() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(USER_TYPE, _sharedPrefs.getString(USER_TYPE, null));
        return init;
    }

    /*ForceLogout*/
    public void setUserType(String code) {
        _prefsEditor.putString(USER_TYPE, code);
        _prefsEditor.apply();
    }

}