//package com.example.slbappcomm.pop.bottompay.wechatutils;
//
//import android.content.Context;
//import android.content.pm.PackageInfo;
//import android.content.pm.PackageManager;
//
//import com.tencent.mm.opensdk.openapi.IWXAPI;
//import com.tencent.mm.opensdk.openapi.WXAPIFactory;
//
//import java.util.List;
//
//public class WeChat1Utils {
//
//    private static volatile WeChat1Utils instance;
//    private Context mContext;
//
//    private WeChat1Utils(Context context) {
//        this.mContext = context;
//    }
//
//    public static WeChat1Utils getInstance(Context context) {
//        if (instance == null) {
//            synchronized (WeChat1Utils.class) {
//                instance = new WeChat1Utils(context);
//            }
//        }
//        return instance;
//    }
//
//    private static IWXAPI api; // 相应的包，请集成SDK后自行引入
//
//    /**
//     * 判断微信客户端是否存在
//     *
//     * @return true安装, false未安装
//     */
//    public boolean isWeChatAppInstalled() {
//        api = WXAPIFactory.createWXAPI(mContext, JPushShareUtils.APP_ID);
//        if (api.isWXAppInstalled() && api.isWXAppInstalled()) {
//            return true;
//        } else {
//            final PackageManager packageManager = mContext.getPackageManager();// 获取packagemanager
//            List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
//            if (pinfo != null) {
//                for (int i = 0; i < pinfo.size(); i++) {
//                    String pn = pinfo.get(i).packageName;
//                    if (pn.equalsIgnoreCase("com.tencent.mm")) {
//                        return true;
//                    }
//                }
//            }
//            return false;
//        }
//    }
//
//
//}
