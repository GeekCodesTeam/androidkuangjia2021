package com.example.liblocations;

import android.content.Context;

public interface LocManagerListener {
    void init(Context context);
    void setListener(LocListener listener);
    void onStart();
    boolean enStarting();
    void onStop();
    void onDestory();
}