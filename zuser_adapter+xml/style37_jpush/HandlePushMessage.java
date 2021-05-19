package com.haier.wine_commen.util;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

import com.example.shining.baselibrary.toasts.ToastUtil;
import com.example.shining.libutils.utilslib.app.App;
import com.example.shining.libutils.utilslib.app.AppManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haier.wine_commen.R;
import com.haier.wine_commen.bean.RomFroceBean;
import com.haier.wine_commen.config.Config;
import com.haier.wine_commen.db_shop.WineShopDao;
import com.haier.wine_commen.widget.msgshowview.FloatWindowSmallView;
import com.haier.wine_commen.widget.msgshowview.GetWineListActivity;
import com.haier.wine_commen.widget.msgshowview.MsgViewManager;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 处理推送消息
 * Created by Shorr on 2016/11/19.
 */
public class HandlePushMessage {

    private final static String ACTION_FAILURE = "com.haier.shopcart.failure";
    private final static String ACTION_SUCCESS = "com.haier.shopcart.success";
    private final static String ACTION_SUCCESS_NEW = "com.haier.shop.new.success";
    private final static String ACTION_M_SUCCESS = "com.haier.management.success";
    private final static String ACTION_MESSAGE = "com.haier.shopcart.message";
    private final static String ACTION_RESET = "com.haier.cellarette.reset";
    private final static String ACTION_OTA_ROM_UPDATA = "com.haier.cellarette.rom";
    private final static String ACTION_OTA_APK_UPDATA = "com.haier.cellarette.apk";
    private final static String ACTION_GET_MODULE = "com.haier.cellarette.get.module";
    private Context context;
    private String kind;

    public HandlePushMessage(Context context) {
        this.context = context;
    }

    /**
     * 处理消息
     *
     * @param msg
     */
    public void handleMessage(String msg) {
        /**
         * Kind  0： 跳取酒立碑
         * Kind  1： 跳订单列表
         * Kind  2:  不做跳转
         *
         * orderType 0：线上tab（订单页面的“线上订单”）
         * orderType 1：线下tab
         * */

        try {
            JSONObject jsonObject = new JSONObject(msg);
            JSONObject partner = jsonObject.getJSONObject("partner");
            if (null != partner) {
                kind = partner.getString("kind");
                String orderType = partner.getString("orderType");
                String music;
                music = partner.getString("music");
                sendBroadcastNew(kind, orderType, msg, music);
                return;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


//------------------------------------------------------------------------下面为老版推送跳转
        try {
            Logger.e(msg);
            JSONObject jsonObject = new JSONObject(msg);
            int code = jsonObject.getInt("code");
            if (code == 1) {  //
                Intent intent = new Intent();
                intent.putExtra("msg", msg);
                intent.setAction(ACTION_MESSAGE);
                context.sendBroadcast(intent);
            } else if ((code == 2) || (code == 3) || (code == 4) || (code == 5) || (code == 8)) {
                // 2表示不支持合伙人购买;
                // 3表示购物车支付成功;
                // 4表示定制版藏品管理下单支付成功；
                // 5表示后台初始化跳转到产线选择页面；
                String str = "";
                if (code == 2) {
                    str = "不支持合伙人购买";
                } else if (code == 3) {
                    str = "订单支付成功";
                    try {
                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        int y = jsonObject1.getInt("order_class");
                        if (y == 5) {
                            sendBroadcast(str, 5);
                            return;
                        }
                        if (y == 4) {
                            sendBroadcast(str, 4);
                            return;
                        }
                        if (y == 6) {
                            sendBroadcast(str, 6);
                            return;
                        }
                    } catch (Exception e) {
                        Logger.d("极光解析错误");
                    }
                } else if (code == 4) {
                    str = "微信支付成功";
                } else if (code == 5) {
                    str = "版本初始化成功";
                } else if (code == 8) {
                    str = "1";
                }
                try {
                    String data = jsonObject.getString("data");
                    String[] goods_ids = data.split(",");

                    //删除购物车里边的数据
                    List<String> list = new ArrayList<>();
                    for (String id : goods_ids) {
                        list.add(id);
                    }
                    if (list.size() != 0) {
                        WineShopDao dao = WineShopDao.getInstance(context);
                        dao.deleteWineListByIds(list);
                    }
                    sendBroadcast(code, str);
                } catch (Exception e) {
                    Logger.e("推送消息处理异常");
                }
            } else if (code == 6) {//标识微信端餐桌支付
                if (null != AppManager.getInstance().top()) {
                    String simpleName = AppManager.getInstance().top().getClass().getSimpleName();
                    Logger.e("bjcolor_1111111:" + simpleName);
                    if (null != simpleName) {
                        if (simpleName.equals("DownloadApkActivity") || simpleName.equals("DownloadApkVersionActivity")) {
                            Logger.e("bjcolor_1111111:" + "升级退出");
                            return;
                        }
                    }
                }

                if (MsgViewManager.getFloatBtn() != null) { //餐厅版本
                    SP.put(App.get(), "order_raw_wx", true);//推送的时候跳转到订单播放声音
                    if (!FloatWindowSmallView.isExit) {//如果消息列表未展示  则展示出来刷新列表
                        FloatWindowSmallView.playEnterAnim(true);
                    } else {//如果已经展示，显示小红点   用户自己点击刷新
                        GetWineListActivity.setTipVisible();
                    }
                } else {  //商用版 扫描二维码 本柜酒品 中购买
                    SP.put(App.get(), "order_raw_wx", true);//推送的时候跳转到订单播放声音
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                    int y = jsonObject1.getInt("order_class");
                    //跳转到订单管理
                    Intent intentHome = new Intent("haier.order");
                    intentHome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intentHome.putExtra("order_class", y);
                    context.startActivity(intentHome);
                }
            } else if (code == 7) {
                Gson gson = new Gson();
                RomFroceBean romFroceBean = gson.fromJson(msg, new TypeToken<RomFroceBean>() {
                }.getType());
                if (romFroceBean != null && romFroceBean.getData() != null)
                    if (romFroceBean.getData().getFile_id() != null && !romFroceBean.getData().getFile_id().equals(""))
                        sendBroadcast(code, romFroceBean.getData().getFile_id());
            } else if (code == 9) {//状态上报的时候需要上传 如果没有则不用传
                JSONObject json = jsonObject.getJSONObject("data");
                String requestId = json.getString("requestId");
                SP.put(App.get(), "requestId", requestId);

            } else if (code == 10) {
                String simpleName = null;
                if (null != AppManager.getInstance().top()) {
                    simpleName = AppManager.getInstance().top().getClass().getSimpleName();
                } else {
                    simpleName = getTopActivity(context);
                }
                if (null != simpleName) {
                    if (simpleName.equals("com.haier.cellarette.activity.ScreenWelcomeActivity")
                            || simpleName.equals("com.haier.cellarette.activity.indexnew.MainWiteActivityIndex")
                            || simpleName.equals("ScreenWelcomeActivity")
                            || simpleName.equals("MainWiteActivityIndex")
                            ) {
                        //模块刷新
                        sendBroadcast(code, msg);
                    }
                } else {
                    ToastUtil.showToastShort(App.get().getResources().getString(R.string.app_tssbqcqsb));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Logger.e("推送数据异常");
        }
    }

    //判断当前界面显示的是哪个Activity
    public static String getTopActivity(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTasks = am.getRunningTasks(1);
        if (runningTasks != null && runningTasks.size() > 0) {
            ComponentName cn = runningTasks.get(0).topActivity;
            return cn.getClassName();
        }
        return null;
    }


    /**
     * 发送广播
     *
     * @param code
     * @param msg
     */
    private void sendBroadcast(int code, String msg) {
        Intent intent = new Intent();
        if (code == 2) {//首页消息
            intent.setAction(ACTION_FAILURE);
        } else if (code == 3) {//商城订单支付
            intent.setAction(ACTION_SUCCESS);
        } else if (code == 4) {//微信线下支付
            intent.setAction(ACTION_M_SUCCESS);
        } else if (code == 5) {//系统初始化
            intent.setAction(ACTION_RESET);
        } else if (code == 7) {//ROM升级更新
            intent.setAction(ACTION_OTA_ROM_UPDATA);
        } else if (code == 8) {//APK升级更新
            intent.setAction(ACTION_OTA_APK_UPDATA);
        } else if (code == 10) {//刷新模块
            intent.setAction(ACTION_GET_MODULE);
        }
        intent.putExtra("msg", msg);
        context.sendBroadcast(intent);
    }

    private void sendBroadcast(String msg, int x) {
        Intent intent = new Intent(ACTION_SUCCESS);
        intent.putExtra("msg", msg);
        intent.putExtra("order_class", x);
        SP.put(App.get(), "order_raw", true);//推送的时候跳转到订单播放声音
        context.sendBroadcast(intent);
    }


    //--------------------------------------------------------------------------------------------------------end 以上老版部分

    /**
     * 新推送规则
     * Kind  0： 跳取酒列表
     * Kind  1： 跳订单列表
     * Kind  2： 不做处理
     * orderType 0：线上tab（订单页面的“线上订单”）
     * orderType 1：线下tab
     * music 0 老的 1新的
     */
    private void sendBroadcastNew(String kind, String orderType, String msg, String music) {
        SP.put(App.get(), "ordermusic", music);//推送的时候不同声音响铃 OpenMusic.class

        if (kind.equals("0")) {
            pop();
        } else if (kind.equals("1")) {
            int y = 0;
            if (orderType.equals("1")) {
                boolean offline = SP.get(App.get(), Config.DESK_ENOFFLINE, false);
                if (offline) {
                    y = 5;//为了兼容老版本  不改动订单页跳转逻辑。所以进行转化  详情可看跳转页 OrderManage
                    go(y);
                } else {
                    return;
                }
            } else {
                boolean deskShop = SP.get(App.get(), Config.DESK_SHOP, false);
                if (deskShop) {
                    go(y);
                } else {
                    return;
                }
            }
        } else if (kind.equals("2")) {//不做操作
        }
    }

    /**
     * comeFromJpushKind
     * 是否息屏 跳取酒订单
     */
    private void pop() {
        PowerManager powerManager = (PowerManager) App.get()
                .getSystemService(Context.POWER_SERVICE);
        boolean ifOpen = powerManager.isScreenOn();
        //跳转到订单管理
        SP.put(App.get(), "order_raw_wx", true);//推送的时候跳转到订单播放声音
        if (ifOpen) {//如果亮屏
            if (MsgViewManager.getFloatBtn() != null) {
                if (!FloatWindowSmallView.isExit) {//如果消息列表未展示  则展示出来刷新列表
                    FloatWindowSmallView.playEnterAnim(true);
                } else {//如果已经展示，显示小红点   用户自己点击刷新
                    GetWineListActivity.setTipVisible();
                }
            }else{
                SP.put(App.get(), "order_raw_wx", false);//推送的时候跳转到订单播放声音
            }

            SP.put(App.get(), "comeFromJpushKind", false);
        } else {//如果息屏
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            PowerManager.WakeLock bright = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, "bright");
            bright.acquire();
            SP.put(App.get(), "comeFromJpushKind", true);
            bright.release();
        }

    }

    private void go(int y) {
        //跳转到订单管理
        SP.put(App.get(), "order_raw_wx", true);//推送的时候跳转到订单播放声音

        PowerManager powerManager = (PowerManager) App.get()
                .getSystemService(Context.POWER_SERVICE);
        boolean ifOpen = powerManager.isScreenOn();


        if (ifOpen) {
            Intent i = new Intent(ACTION_SUCCESS_NEW);
            i.putExtra("msg", App.get().getString(R.string.app_zfcg));
            i.putExtra("order_class", y);
            context.sendBroadcast(i);
        } else {
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            PowerManager.WakeLock bright = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, "bright");
            bright.acquire();
            //解决息屏广播问题。  首页onResume中对应
            SP.put(context, "comeFromJpush", true);
            SP.put(context, "msg", App.get().getString(R.string.app_zfcg));
            SP.put(context, "order_class", y);
            bright.release();
        }
    }

}
