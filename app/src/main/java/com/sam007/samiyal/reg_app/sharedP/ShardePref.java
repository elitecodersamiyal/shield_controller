package com.sam007.samiyal.reg_app.sharedP;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class ShardePref {

    private static ShardePref store;
    private SharedPreferences SP;
    private static String filename="Keys";

    private ShardePref(Context context) {
        SP = context.getApplicationContext().getSharedPreferences(filename,0);
    }

    public static ShardePref getInstance(Context context) {
        if (store == null) {
            Log.v("Keystore","NEW STORE");
            store = new ShardePref(context);
        }
        return store;
    }

    public void put(String key, String value) {//Log.v("Keystore","PUT "+key+" "+value);
        SharedPreferences.Editor editor = SP.edit();
        editor.putString(key, value);
        editor.commit(); // Stop everything and do an immediate save!
        // editor.apply();//Keep going and save when you are not busy - Available only in APIs 9 and above.  This is the preferred way of saving.
    }

    public String get(String key) {//Log.v("Keystore","GET from "+key);
        return SP.getString(key, "noNOAppSelecter");

    }

    public int getInt(String key) {//Log.v("Keystore","GET INT from "+key);
        return SP.getInt(key, 0);
    }

    public void putInt(String key, int num) {//Log.v("Keystore","PUT INT "+key+" "+String.valueOf(num));
        SharedPreferences.Editor editor = SP.edit();

        editor.putInt(key, num);
        editor.commit();
    }


    public void clear(){ // Delete all shared preferences
        SharedPreferences.Editor editor = SP.edit();

        editor.clear();
        editor.commit();
    }

    public void remove(){ // Delete only the shared preference that you want
        SharedPreferences.Editor editor = SP.edit();

        editor.remove(filename);
        editor.commit();
    }

    public Boolean getbool(String key) {//Log.v("Keystore","GET INT from "+key);
        return SP.getBoolean(key, false);
    }

    public void putbool(String key, Boolean val) {//Log.v("Keystore","PUT INT "+key+" "+String.valueOf(num));
        SharedPreferences.Editor editor = SP.edit();

        editor.putBoolean(key, val);
        editor.commit();
    }

}
