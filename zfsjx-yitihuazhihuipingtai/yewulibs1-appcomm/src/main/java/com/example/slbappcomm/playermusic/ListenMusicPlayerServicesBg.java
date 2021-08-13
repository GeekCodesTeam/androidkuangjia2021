//package com.example.slbappcomm.playermusic;
//
//import android.Manifest;
//import android.app.Activity;
//import android.app.AppOpsManager;
//import android.app.Notification;
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.app.Service;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.ApplicationInfo;
//import android.content.pm.PackageManager;
//import android.os.Build;
//import android.os.IBinder;
////
////import android.support.annotation.RequiresApi;
//import androidx.annotation.Nullable;
//import androidx.annotation.RequiresApi;
//import androidx.core.app.ActivityCompat;
//import androidx.core.app.NotificationCompat;
//import androidx.core.content.ContextCompat;
//
//import com.example.slbappcomm.R;
//import com.haier.cellarette.baselibrary.assetsfitandroid.fileandroid.FitAndroidAssetsToCacheUtil;
//import com.geek.libutils.app.BaseApp;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//
//public class ListenMusicPlayerServicesBg extends Service {
//
//    public static final String EXTRA_NOTIFICATION_ID = "extra_notification_id_for_tingshu";
//    public static final int TINGSHU_MANAGE_NOTIFICATION_ID = 1001711;
//    public static final String LISTEN_CHANNEL_ID = "LISTEN_NOTIFY_ID";
//    public static final String LISTEN_CHANNEL_NAME = "LISTEN_NOTIFY_NAME";
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
////        // 在API11之后构建Notification的方式
////        Notification.Builder builder = new Notification.Builder
////                (this.getApplicationContext()); //获取一个Notification构造器
////        Intent nfIntent = new Intent("hs.act.slbapp.index");
////        builder.setContentIntent(PendingIntent.
////                getActivity(this, 0, nfIntent, 0)) // 设置PendingIntent
////                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(),
////                        R.drawable.slb_logo2)) // 设置下拉列表中的图标(大图标)
////                .setContentTitle("下拉列表中的Title") // 设置下拉列表里的标题
////                .setSmallIcon(R.drawable.slb_logo2) // 设置状态栏内的小图标
////                .setContentText("要显示的内容") // 设置上下文内容
////                .setWhen(System.currentTimeMillis()); // 设置该通知发生的时间
////        Notification notification = builder.build(); // 获取构建好的Notification
////        notification.defaults = Notification.DEFAULT_SOUND; //设置为默认的声音
//
////        startForeground(intent.getIntExtra(EXTRA_NOTIFICATION_ID, 0), she_notifichanged());
////        startForeground(intent.getIntExtra(EXTRA_NOTIFICATION_ID, 0), new Notification());// 8.1 27 会崩
//
//        stopForeground(true);
//        stopSelf();
//
//        return super.onStartCommand(intent, flags, startId);
//    }
//
//    /**
//     * 至于一些ROM厂商不默认开启通知，只能去谈 让他开 不然你就手动自己开 没办法
//     *
//     * @return
//     */
//    public static Notification she_notifichanged() {
//        NotificationManager notificationManager = (NotificationManager) BaseApp.get().getSystemService(NOTIFICATION_SERVICE);
//        Notification notification = null;
//        Intent notificationIntent = new Intent("hs.act.slbapp.index");
//        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(BaseApp.get(), 0, notificationIntent, 0);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel mChannel = new NotificationChannel(LISTEN_CHANNEL_ID, LISTEN_CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW);
////            mChannel.setDescription("test");
////            mChannel.enableLights(true);
////            mChannel.setLightColor(Color.RED);
////            mChannel.enableVibration(true);
////            mChannel.setVibrationPattern(new long[]{100,200,300,400,500,400,300,200,400});
//
//            notificationManager.createNotificationChannel(mChannel);
//            notification = new NotificationCompat.Builder(BaseApp.get(), LISTEN_CHANNEL_ID)
//                    .setChannelId(LISTEN_CHANNEL_ID)
//                    .setContentIntent(pendingIntent)
//                    .setContentTitle("合象绘本")
//                    .setContentText("正在听书")
//                    .setSmallIcon(R.drawable.slb_logo3).build();
//        } else {
//            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(BaseApp.get(), LISTEN_CHANNEL_ID)
//                    .setContentTitle("合象绘本")
//                    .setContentText("正在听书")
//                    .setContentIntent(pendingIntent)
//                    .setSmallIcon(R.drawable.slb_logo3)
//                    .setOngoing(true)
//                    .setChannelId(LISTEN_CHANNEL_ID);//无效
//            notification = notificationBuilder.build();
//        }
////        if (notificationManager != null) {
////            notificationManager.notify(PUSH_NOTIFICATION_ID, notification);// 这句是显示多个通知消息
////        }
//
////        NotificationCompat.Builder builder = new NotificationCompat.Builder(BaseApp.get());
////        Intent notificationIntent = new Intent("hs.act.slbapp.index");
////        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
////        PendingIntent pendingIntent = PendingIntent.getActivity(BaseApp.get(), 0, notificationIntent, 0);
////        builder.setContentTitle("合象绘本")//设置通知栏标题
////                .setContentIntent(pendingIntent) //设置通知栏点击意图
////                .setContentText("正在听书")
//////                .setNumber(++pushNum)
//////                .setTicker("正在听书") //通知首次出现在通知栏，带上升动画效果的
////                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
////                .setSmallIcon(R.drawable.slb_logo2)//设置通知小ICON
////                .setChannelId(PUSH_CHANNEL_ID)
////                .setDefaults(Notification.DEFAULT_ALL);
////
////        Notification notification = builder.build();
////        notification.flags |= Notification.FLAG_AUTO_CANCEL;
////        if (notificationManager != null) {
////            notificationManager.notify(PUSH_NOTIFICATION_ID, notification);
////        }
//        return notification;
////        startForeground(TINGSHU_MANAGE_NOTIFICATION_ID, notification);
//    }
//
//    // 请求通知栏权限bufen
//    public static void qx_notification(Context context) {
//        boolean enabled = isNotificationEnabled(context);
//        if (!enabled) {
//            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_NOTIFICATION_POLICY) != PackageManager.PERMISSION_GRANTED) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    ActivityCompat.requestPermissions(((Activity) context), new String[]{Manifest.permission.ACCESS_NOTIFICATION_POLICY}, FitAndroidAssetsToCacheUtil.REQ_PERMISSION_CODE_SDCARD);
//                }
//            } else {
//
//            }
////            /**
////             * 跳到通知栏设置界面
////             * @param context
////             */
////            Intent localIntent = new Intent();
////            //直接跳转到应用通知设置的代码：
////            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
////                localIntent.setAction(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
//////                localIntent.putExtra("app_package", context.getPackageName());
//////                localIntent.putExtra("app_uid", context.getApplicationInfo().uid);
////            } else if (android.os.Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
////                localIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
////                localIntent.addCategory(Intent.CATEGORY_DEFAULT);
////                localIntent.setData(Uri.parse("package:" + context.getPackageName()));
////            } else {
////                //4.4以下没有从app跳转到应用通知设置页面的Action，可考虑跳转到应用详情页面,
////                localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                if (Build.VERSION.SDK_INT >= 9) {
////                    localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
////                    localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
////                } else if (Build.VERSION.SDK_INT <= 8) {
////                    localIntent.setAction(Intent.ACTION_VIEW);
////                    localIntent.setClassName("com.android.settings", "com.android.setting.InstalledAppDetails");
////                    localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
////                }
////            }
////            context.startActivity(localIntent);
//        }
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    public static boolean isNotificationEnabled(Context context) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            //8.0手机以上
//            if (((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).getImportance() == NotificationManager.IMPORTANCE_NONE) {
//                return false;
//            }
//        }
//        String CHECK_OP_NO_THROW = "checkOpNoThrow";
//        String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";
//
//        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
//        ApplicationInfo appInfo = context.getApplicationInfo();
//        String pkg = context.getApplicationContext().getPackageName();
//        int uid = appInfo.uid;
//
//        Class appOpsClass = null;
//
//        try {
//            appOpsClass = Class.forName(AppOpsManager.class.getName());
//            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE,
//                    String.class);
//            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);
//
//            int value = (Integer) opPostNotificationValue.get(Integer.class);
//            return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//}
