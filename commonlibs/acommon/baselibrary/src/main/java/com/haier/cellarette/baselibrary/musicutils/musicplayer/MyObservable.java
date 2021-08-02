package com.haier.cellarette.baselibrary.musicutils.musicplayer;

import android.util.Log;

import java.util.Observable;

public class MyObservable extends Observable {

    public void setData(String arg) {
        setChanged();
        notifyObservers(arg);
    }

    public void getData(Object obj) {
        Log.d("geek", obj + "");
    }


}
