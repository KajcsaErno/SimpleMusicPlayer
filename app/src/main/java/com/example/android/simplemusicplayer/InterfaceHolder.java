package com.example.android.simplemusicplayer;

public class InterfaceHolder {

    static  MyInterface myInterface;

    public static MyInterface getMyInterface() {
        return myInterface;
    }

    public static void setMyInterface(MyInterface myInterface) {
        InterfaceHolder.myInterface = myInterface;
    }
}
