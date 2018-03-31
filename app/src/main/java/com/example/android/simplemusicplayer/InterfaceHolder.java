package com.example.android.simplemusicplayer;

import android.util.Log;

public class InterfaceHolder {

    private static final String TAG = "InterfaceHolder";
    private static MyInterface myInterface;

    public static MyInterface getMyInterface() {

        Log.d(TAG, "getMyInterface: ");
        return myInterface;
    }

    public static void setMyInterface(MyInterface myInterface) {
        InterfaceHolder.myInterface = myInterface;
        Log.d(TAG, "setMyInterface: " + myInterface);
    }
}
