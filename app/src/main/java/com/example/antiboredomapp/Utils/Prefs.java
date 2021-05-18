package com.example.antiboredomapp.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.antiboredomapp.R;

public class Prefs {
    private SharedPreferences preferences;

    public Prefs(Context context) {
        this.preferences = context.getSharedPreferences("PREF_NAME",Context.MODE_PRIVATE);
    }

    public void saveLastActivity(String name)
    {
        preferences.edit().putString("lastSeen", name).apply();
    }

    public String getLastActivity(){
        return preferences.getString("lastSeen", "None Yet");
    }

    public void saveSecondLastSeen(String name){
        preferences.edit().putString("secLastSeen", name).apply();
    }

    public String getSecondLastSeen(){
        return preferences.getString("secLastSeen", "None yet");
    }

    public void saveThirdLastSeen(String name){
        preferences.edit().putString("thirdLastSeen", name).apply();
    }

    public String getThirdLastSeen(){
        return preferences.getString("thirdLastSeen", "None yet");
    }

}
