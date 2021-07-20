package net.ossrs.yasea.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;

import com.blankj.utilcode.util.ActivityUtils;
import com.geek.libutils.app.MyLogUtil;
import com.google.zxing.integration.android.IntentIntegrator;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnCancelListener;
import com.lxj.xpopup.interfaces.OnConfirmListener;

import net.ossrs.yasea.activity.CustomScanActivity;
import net.ossrs.yasea.activity.FadeService;

import java.util.List;

public class ServiceUtil {

    private static ServiceUtil timeBaseUtil = null;
    public MediaProjection mMediaProjection;
    public MediaProjectionManager mMediaProjectionManager;


    public static ServiceUtil getInstance() {
        synchronized (ServiceUtil.class) {
            if (timeBaseUtil == null) {
                timeBaseUtil = new ServiceUtil();
            }
        }
        return timeBaseUtil;
    }

    public void set_start_screen(Activity context) {
        mMediaProjectionManager = (MediaProjectionManager) context.getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        if (Build.VERSION.SDK_INT >= 23) {
            if (Settings.canDrawOverlays(context)) {
                MyLogUtil.i("------------------L164");
                Intent intent2 = new Intent(context, FadeService.class);
                context.startService(intent2);
            } else {
                //若没有权限，提示获取.
                MyLogUtil.i("------------------L169");
                new XPopup.Builder(context)
                        .isDestroyOnDismiss(true)
                        .asConfirm("权限提示", "开启悬浮窗权限可提升投屏体验，是否去开启?",
                                "放弃", "去开启",
                                new OnConfirmListener() {
                                    @Override
                                    public void onConfirm() {
                                        Intent intent3 = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                                        Toast.makeText(context, "需要取得权限以使用悬浮窗", Toast.LENGTH_SHORT).show();
                                        context.startActivity(intent3);
                                    }
                                },
                                new OnCancelListener() {
                                    @Override
                                    public void onCancel() {
                                        if (ActivityUtils.getActivityList() != null && ActivityUtils.getActivityList().size() > 0) {
                                            new IntentIntegrator(context)
                                                    .setOrientationLocked(false)
                                                    .setCaptureActivity(CustomScanActivity.class) // 设置自定义的activity是CustomActivity
                                                    .initiateScan(); // 初始化扫描
                                        }
                                    }
                                }, false)
                        .show();
                return;
            }
        } else {
            //SDK在23以下，不用管.
            MyLogUtil.i("------------------L196");
            Intent intent1 = new Intent(context, FadeService.class);
            MyLogUtil.i("------------------L197");
            context.startService(intent1);
        }

        if (ActivityUtils.getActivityList() != null && ActivityUtils.getActivityList().size() > 0) {
            new IntentIntegrator(context)
                    .setOrientationLocked(false)
                    .setCaptureActivity(CustomScanActivity.class) // 设置自定义的activity是CustomActivity
                    .initiateScan(); // 初始化扫描
        }
    }

    /**
     * 用来判断服务是否运行.
     *
     * @param context
     * @param className 判断的服务名字：包名+类名
     * @return true 在运行, false 不在运行
     */
    public static boolean isServiceRunning(Context context, String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager
                .getRunningServices(Integer.MAX_VALUE);
        if (!(serviceList.size() > 0)) {
            return false;
        }

        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(className) == true) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }
}
