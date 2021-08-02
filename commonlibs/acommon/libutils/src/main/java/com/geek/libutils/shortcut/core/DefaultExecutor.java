package com.geek.libutils.shortcut.core;

import android.content.Context;

import com.geek.libutils.shortcut.AllRequest;

public class DefaultExecutor implements Executor {

    private final Context context;

    public void executeSetting() {
        (new AllRequest(this.context)).start();
    }

    public final Context getContext() {
        return this.context;
    }

    public DefaultExecutor(Context context) {
        super();
        this.context = context;
    }
}