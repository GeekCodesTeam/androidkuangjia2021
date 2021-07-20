package com.geek.libutils.shortcut.core;

import android.content.Context;

import androidx.core.content.pm.ShortcutInfoCompat;

import com.geek.libutils.shortcut.AllRequest;
import com.geek.libutils.shortcut.ShortcutPermission;
import com.geek.libutils.shortcut.broadcast.HuaweiOreoShortcut;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Shortcut {
    private List mCallback;
    private static final Shortcut singleInstance;

    public static Shortcut getSingleInstance() {
        return Shortcut.singleInstance;
    }

    public static final String TAG = "Shortcut";
    public static final String EXTRA_ID = "extra_id";
    public static final String EXTRA_LABEL = "extra_label";

    public final void requestPinShortcut(Context context, ShortcutInfoCompat shortcutInfoCompat, boolean updateIfExit, boolean fixHwOreo, ShortcutAction shortcutAction) {
        int check = ShortcutPermission.check(context);
        if (check == -1) {
            shortcutAction.showPermissionDialog(context, check, (Executor) (new DefaultExecutor(context)));
        } else {
            ShortcutCore shortcutCore = fixHwOreo ? (ShortcutCore) (new HuaweiOreoShortcut()) : new ShortcutCore();
            shortcutCore.createShortcut(context, shortcutInfoCompat, updateIfExit, shortcutAction, check);
        }

    }

    public final void addShortcutCallback(Callback callback) {
        this.mCallback.add(callback);
    }

    public final void removeShortcutCallback(Callback callback) {
        this.mCallback.remove(callback);
    }

    public final void notifyCreate(String id, String name) {
        Iterator var4 = this.mCallback.iterator();
        while (var4.hasNext()) {
            Callback callback = (Callback) var4.next();
            callback.onAsyncCreate(id, name);
        }

    }

    public final void openSetting(Context context) {
        (new AllRequest(context)).start();
    }

    private Shortcut() {
        boolean var1 = false;
        this.mCallback = (List) (new ArrayList());
    }

    static {
        singleInstance = SingleHolder.INSTANCE.getSingleHolder();
    }

    private static final class SingleHolder {

        private static final Shortcut singleHolder;

        public static final SingleHolder INSTANCE;


        public final Shortcut getSingleHolder() {
            return singleHolder;
        }

        static {
            SingleHolder var0 = new SingleHolder();
            INSTANCE = var0;
            singleHolder = new Shortcut();
        }
    }

    public interface Callback {
        void onAsyncCreate(String var1, String var2);
    }


}