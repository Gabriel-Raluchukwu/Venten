package com.android.udacity.utilities;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;

public class Persist {

    private static SharedPreferences.Editor getSharedPreference(Context context){
        SharedPreferences preferences  = context.getSharedPreferences(Constants.PREFERENCE,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        return editor;
    }

    public static void SetUpPreferences(Context context){
       SharedPreferences.Editor editor = getSharedPreference(context);

       editor.putString(Constants.PREFERENCE_DATE,Constants.DEFAULT_DATE);
       editor.putString(Constants.PREFERENCE_TIME,Constants.DEFAULT_TIME);
       editor.putString(Constants.PREFERENCE_CODED_MESSAGE,Constants.DEFAULT_CODED_MESSAGE);
       editor.putString(Constants.PREFERENCE_WIDTH,Constants.DEFAULT_WIDTH);
       editor.putString(Constants.PREFERENCE_LENGTH,Constants.DEFAULT_LENGTH);
       editor.putString(Constants.PREFERENCE_COLOR1,Constants.DEFAULT_COLOR1);
       editor.putString(Constants.PREFERENCE_COLOR2,Constants.DEFAULT_COLOR2);
       editor.putBoolean(Constants.RESET,false);
       editor.apply();
    }

    public static void SetDate(Context context, String date){
        SharedPreferences.Editor editor = getSharedPreference(context);
        editor.putString(Constants.PREFERENCE_DATE,date);
        editor.apply();
    }

    public static void SetTime(Context context, String time){
        SharedPreferences.Editor editor = getSharedPreference(context);
        editor.putString(Constants.PREFERENCE_TIME,time);
        editor.apply();
    }
    public static void SetCodedMessage(Context context, String message){
        SharedPreferences.Editor editor = getSharedPreference(context);
        editor.putString(Constants.PREFERENCE_CODED_MESSAGE,message);
        editor.apply();
    }
    public static void SetWidth(Context context, String width){
        SharedPreferences.Editor editor = getSharedPreference(context);
        editor.putString(Constants.PREFERENCE_WIDTH,width);
        editor.apply();
    }
    public static void SetLength(Context context, String length){
        SharedPreferences.Editor editor = getSharedPreference(context);
        editor.putString(Constants.PREFERENCE_LENGTH,length);
        editor.apply();
    }
    public static void SetColor1(Context context, String color){
        SharedPreferences.Editor editor = getSharedPreference(context);
        editor.putString(Constants.PREFERENCE_COLOR1,color);
        editor.apply();
    }
    public static void SetColor2(Context context, String color){
        SharedPreferences.Editor editor = getSharedPreference(context);
        editor.putString(Constants.PREFERENCE_DATE,color);
        editor.apply();
    }
}
