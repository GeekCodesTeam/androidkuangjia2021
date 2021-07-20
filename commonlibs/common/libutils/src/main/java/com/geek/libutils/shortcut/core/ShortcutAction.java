package com.geek.libutils.shortcut.core;

import android.content.Context;

public abstract class ShortcutAction {
    public abstract void showPermissionDialog(Context var1, int var2, Executor var3);

    public abstract void onCreateAction(boolean var1, int var2, Executor var3);

    public abstract void onUpdateAction(boolean var1);
}