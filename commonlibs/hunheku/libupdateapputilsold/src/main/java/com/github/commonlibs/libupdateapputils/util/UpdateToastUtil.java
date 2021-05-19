package com.github.commonlibs.libupdateapputils.util;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;


/**
 * Toast管理
 */
public class UpdateToastUtil {

    public static Toast toast;

    public static void showToast(Context context, String toastText) {
        toastText = ToastMsgRedresser.redress(toastText);
        if (toastText == null) {
            return;
        }

        if (toast == null) {
            toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
//            updToastTextSize(toast);
        }
//        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setText(toastText + "");
        toast.show();
    }

    private static void updToastTextSize(Toast toast) {
        try {
            Field f = toast.getClass().getDeclaredField("mNextView");
            f.setAccessible(true);
            ViewGroup view = (ViewGroup) f.get(toast);
            if (view == null) {
                return;
            }
            TextView tv = (TextView) view.getChildAt(0);
            if (tv == null) {
                return;
            }
            tv.setTextSize(20);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}