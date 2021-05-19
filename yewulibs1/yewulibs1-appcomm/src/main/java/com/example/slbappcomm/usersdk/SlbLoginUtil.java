//package com.example.slbappcomm.usersdk;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.text.TextUtils;
//
//import com.blankj.utilcode.util.SPUtils;
//import com.haier.cellarette.libutils.CommonUtils;
//
//// 教你怎么写登录bufen
//public class SlbLoginUtil {
//
//    public static final int LOGIN_REQUEST_CODE = 301;
//    public static final int LOGINOUT_REQUEST_CODE = 302;
//    public static final int LOGIN_RESULT_OK = 101;
//    public static final int LOGIN_RESULT_CANCELED = 102;
//    public static final int LOGINOUT_RESULT_OK = 201;
//    public static final int LOGINOUT_RESULT_CANCELED = 202;
//
//    private static SlbLoginUtil sInstance;
//    private static final Object lock = new Object();
//    private Runnable mLastRunnnable;
//
//
//    public SlbLoginUtil() {
//    }
//
//    public static SlbLoginUtil get() {
//        if (sInstance == null) {
//            synchronized (lock) {
//                sInstance = new SlbLoginUtil();
//            }
//        }
//        return sInstance;
//    }
//
//    /**
//     * 用户是否登录
//     *
//     * @return
//     */
//    public boolean isUserLogin() {
//        // step 1 判断内存中是否有user_id
//        if (!TextUtils.isEmpty(SPUtils.getInstance().getString(CommonUtils.USER_TOKEN))) {
//            return true;
//        }
////        // step 2 如果内存中没有， 则去文件中找
////        String uid = (String) SpUtils.get(get()).get(ConstantUtil.USER_ID, null);
////        // step 3 如果文件中有， 则提到内存中
////        if (!TextUtils.isEmpty(uid)) {
////            DataProvider.setUser_id(uid);
////            return true;
////        }
//        // 未登录
//        return false;
//    }
//
//
//    public void loginTowhere(Activity activity, Runnable runnable) {
//        if (isUserLogin()) {
//            if (runnable != null) {
//                runnable.run();
//                return;
//            }
//        }
//        mLastRunnnable = runnable;
//        login(activity);
//    }
//
//    public void login(Activity activity) {
//        Intent intent = new Intent("hs.act.slbapp.SlbLoginActivity");
//        if (intent.resolveActivity(activity.getPackageManager()) != null) {
//            activity.startActivityForResult(intent, LOGIN_REQUEST_CODE);
//        }
//    }
//
//    public void loginOutTowhere(Activity activity, Runnable runnable) {
////        if (!isUserLogin()) {
////            if (runnable == null) {
////                runnable.run();
////                return;
////            }
////        }
//        mLastRunnnable = runnable;
//        loginOut(activity);
//    }
//
//    public void loginOut(Activity activity) {
//        Intent intent = new Intent("hs.act.slbapp.SlbLoginOutActivity");
//        if (intent.resolveActivity(activity.getPackageManager()) != null) {
//            activity.startActivityForResult(intent, LOGINOUT_REQUEST_CODE);
//        }
//    }
//
//    public boolean login_activity_result(int requestCode, int resultCode, Intent data) {
//        Runnable runnable = mLastRunnnable;
//        mLastRunnnable = null;
//        //已登录
//        if (requestCode == LOGIN_REQUEST_CODE) {
//            if (resultCode == LOGIN_RESULT_OK && runnable != null) {
//                runnable.run();
//            }
//            return true;
//        }
//        //未登录
//        if (requestCode == LOGINOUT_REQUEST_CODE) {
//            if (resultCode == LOGINOUT_RESULT_OK && runnable != null) {
//                runnable.run();
//            }
//            return true;
//        }
//
//        return false;
//    }
//
//}
