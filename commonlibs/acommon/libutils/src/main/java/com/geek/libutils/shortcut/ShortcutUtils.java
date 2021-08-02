package com.geek.libutils.shortcut;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.pm.ShortcutInfoCompat;
import androidx.core.content.res.ResourcesCompat;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.geek.libutils.shortcut.core.Executor;
import com.geek.libutils.shortcut.core.Shortcut;
import com.geek.libutils.shortcut.core.ShortcutAction;
import com.geek.libutils.shortcut.core.ShortcutInfoCompatExKt;

import org.jetbrains.annotations.NotNull;

/**
 * 快捷方式创建工具类
 *
 * @author Kelly
 * @version 1.0.0
 * @filename ShortcutUtils.java
 * @time 2019/5/28 17:35
 * @copyright(C) 2019 song
 */
public class ShortcutUtils {
    private static final String TAG = "ShortcutUtils";

//    /**
//     * 添加桌面图标快捷方式
//     *
//     * @param activity Activity对象，设置要启动的activity，一般都是应用入口类
//     * @param nameId   快捷方式名称id
//     * @param iconId   图标资源id
//     */
//    public static void addShortcut(Activity activity, int nameId, int iconId) {
//        Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), iconId, null);
//        addShortcut(activity, activity.getResources().getString(nameId), bitmap);
//    }

    public static boolean setpre(Activity activity) {
        int check = ShortcutPermission.check(activity);
        String state = "未知";
        switch (check) {
            case ShortcutPermission.PERMISSION_DENIED:
                state = "已禁止";
                break;
            case ShortcutPermission.PERMISSION_GRANTED:
                state = "已同意";
                break;
            case ShortcutPermission.PERMISSION_ASK:
                state = "询问";
                break;
            case ShortcutPermission.PERMISSION_UNKNOWN:
                state = "未知";
                break;
        }
        if (check == ShortcutPermission.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    /**
     * 添加桌面图标快捷方式
     *
     * @param activity Activity对象，设置要启动的activity，一般都是应用入口类
     * @param nameId   快捷方式名称id
     * @param iconId   图标资源id
     */
    public static void addShortcut(Activity activity, boolean isOnly, String activity2, String nameId, int iconId) {
        Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), iconId, null);
        addShortcut(activity, isOnly, activity2, nameId, bitmap);
    }


    /**
     * 添加桌面图标快捷方式
     *
     * @param activity Activity对象
     * @param name     快捷方式名称
     * @param icon     快捷方式图标
     */
    public static void addShortcut(Activity activity, boolean isOnly, String activity2, String name, Bitmap icon) {
        //
        if (!setpre(activity)) {
            ToastUtils.showLong("请设置桌面快捷方式权限");
            new AllRequest(activity).start();
            return;
        }
        //
        Intent shortcutInfoIntent = new Intent(Intent.ACTION_MAIN);
        /**
         * 点击快捷方式回到应用，而不是重新启动应用,解决系统一级菜单和二级菜单进入应用不一致问题
         */
        shortcutInfoIntent.setClassName(AppUtils.getAppPackageName(), activity2);
        shortcutInfoIntent.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        shortcutInfoIntent.addFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
        shortcutInfoIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        if (!isOnly) {
            Bundle bundle = new Bundle();
            bundle.putString("extra_id", activity2);
            bundle.putString("extra_label", activity2);
            shortcutInfoIntent.putExtras(bundle);
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            if (isShortCutExist(activity, name)) {
                Log.w(TAG, "shortcut already exist.");
                return;
            }
            //  创建快捷方式的intent广播
            Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
            // 添加快捷名称
            shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);
            //  快捷图标是允许重复(不一定有效)
            shortcut.putExtra("duplicate", false);
            // 快捷图标
            // 使用资源id方式
//            Intent.ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(activity, R.mipmap.icon);
//            shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);
            // 使用Bitmap对象模式
            shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON, icon);
            // 添加携带的下次启动要用的Intent信息
            shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutInfoIntent);
            // 发送广播
            activity.sendBroadcast(shortcut);
        } else {
            ShortcutManager shortcutManager = (ShortcutManager) activity.getSystemService(Context.SHORTCUT_SERVICE);
            if (null == shortcutManager) {
                // 创建快捷方式失败
                Log.e(TAG, "Create shortcut failed.ShortcutManager is null.");
                return;
            }
            shortcutInfoIntent.setAction(Intent.ACTION_VIEW); //action必须设置，不然报错
            ShortcutInfo shortcutInfo = new ShortcutInfo.Builder(activity, name)
                    .setShortLabel(name)
                    .setIcon(Icon.createWithBitmap(icon))
                    .setIntent(shortcutInfoIntent)
                    .setLongLabel(name)
                    .build();
            shortcutManager.requestPinShortcut(shortcutInfo, PendingIntent.getActivity(activity, 0, shortcutInfoIntent, PendingIntent.FLAG_UPDATE_CURRENT).getIntentSender());
        }
    }


    /**
     * 判断快捷方式是否存在
     *
     * @param context 上下文
     * @param title   快捷方式标志，不能和其它应用相同
     * @return
     */
    public static boolean isShortCutExist(Context context, String title) {

        boolean isInstallShortcut = false;

        if (null == context || TextUtils.isEmpty(title))
            return isInstallShortcut;
        String authority = getAuthority();
        final ContentResolver cr = context.getContentResolver();
        if (!TextUtils.isEmpty(authority)) {
            try {
                final Uri CONTENT_URI = Uri.parse(authority);

                //  Cursor c = cr.query(CONTENT_URI, new String[]{"title", "iconResource"}, "title=?", new String[]{title.trim()},null);

                Cursor c = cr.query(CONTENT_URI, new String[]{"title"}, "title=?", new String[]{title.trim()},
                        null);

                // XXX表示应用名称。
                if (c != null && c.getCount() > 0) {
                    isInstallShortcut = true;
                }
                if (null != c && !c.isClosed())
                    c.close();
            } catch (Exception e) {
                Log.e(TAG, "isShortCutExist:" + e.getMessage());
            }
        }
        return isInstallShortcut;
    }

    public static String getAuthority() {
        String authority;
        int sdkInt = android.os.Build.VERSION.SDK_INT;
        if (sdkInt < 8) { // Android 2.1.x(API 7)以及以下的
            authority = "com.android.launcher.settings";
        } else if (sdkInt <= 19) {// Android 4.4及以下
            authority = "com.android.launcher2.settings";
        } else {// 4.4以上
            authority = "com.android.launcher3.settings";
        }
        return "content://" + authority + "/favorites?notify=true";
    }

    public static ShortcutPermissionTipDialog mTryTipDialog;

    public static ShortcutPermissionTipDialog mPermissionDialog;

    public static void addIcon(AppCompatActivity activity, String activity2, String uid, String label, int drawble) {
        //
        //
        Intent intentForShortcut = new Intent(Intent.ACTION_VIEW);
        /**
         * 点击快捷方式回到应用，而不是重新启动应用,解决系统一级菜单和二级菜单进入应用不一致问题
         */
        intentForShortcut.setClassName(AppUtils.getAppPackageName(), activity2);
        intentForShortcut.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        intentForShortcut.addFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
        intentForShortcut.addCategory(Intent.CATEGORY_LAUNCHER);
        //
//        Intent intentForShortcut = new Intent(activity, TransparentActivity.class);
//        intentForShortcut.putExtra("name", uid);
//        intentForShortcut.putExtra("id", label);
//        intentForShortcut.putExtra("isShortcut", true);
        // 设置属性bufen
        ShortcutInfoCompat.Builder builder = new ShortcutInfoCompat.Builder(activity, uid)
                .setShortLabel(label);
        ShortcutInfoCompatExKt.setIcon(builder, ResourcesCompat.getDrawable(activity.getResources(), drawble, activity.getTheme()),
                activity, true);
        ShortcutInfoCompatExKt.setIntent(builder, intentForShortcut, Intent.ACTION_VIEW);
        //
        ShortcutInfoCompat shortcutInfoCompat = builder.build();
        Shortcut.getSingleInstance().requestPinShortcut(activity,
                shortcutInfoCompat,
                true,
                true,
                new ShortcutAction() {
                    @Override
                    public void showPermissionDialog(@NotNull Context context, int check, @NotNull Executor executor) {
                        showPermissionTipDialog(activity, executor);
                    }

                    @Override
                    public void onCreateAction(boolean requestPinShortcut, int check, @NotNull Executor executor) {
                        showTryTipDialog(activity, executor);
                    }

                    @Override
                    public void onUpdateAction(boolean updatePinShortcut) {
                        Toast.makeText(activity, "更新成功", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public static void showPermissionTipDialog(AppCompatActivity activity, Executor executor) {
        if (mPermissionDialog == null) {
            mPermissionDialog = new ShortcutPermissionTipDialog();
            mPermissionDialog.setTitle("快捷方式未开启");
            mPermissionDialog.setTvContentTip("检测到权限未开启，请前往系统设置，为此应用打开\"创建桌面快捷方式\"的权限。");
            mPermissionDialog.setOnConfirmClickListener(v -> executor.executeSetting());
        }
        if (mPermissionDialog != null && !mPermissionDialog.isVisible()) {
            mPermissionDialog.show(activity.getSupportFragmentManager(), "shortcut");
        }
    }

    public static void dismissPermissionTipDialog() {
        if (mPermissionDialog != null && mPermissionDialog.isVisible()) {
            mPermissionDialog.dismiss();
            mPermissionDialog = null;
        }
    }


    public static void showTryTipDialog(AppCompatActivity activity, Executor executor) {
        if (mTryTipDialog == null) {
            mTryTipDialog = new ShortcutPermissionTipDialog();
            mTryTipDialog.setTitle("已尝试添加到桌面");
            mTryTipDialog.setTvContentTip("若添加失败，请前往系统设置，为此应用打开\"创建桌面快捷方式\"的权限。");
            mTryTipDialog.setOnConfirmClickListener(v -> executor.executeSetting());
        }
        if (mTryTipDialog != null && !mTryTipDialog.isVisible()) {
            mTryTipDialog.show(activity.getSupportFragmentManager(), "shortcut");
        }
    }

    public static void dismissTryTipDialog() {
        if (mTryTipDialog != null && mTryTipDialog.isVisible()) {
            mTryTipDialog.dismiss();
            mTryTipDialog = null;
        }
    }


}