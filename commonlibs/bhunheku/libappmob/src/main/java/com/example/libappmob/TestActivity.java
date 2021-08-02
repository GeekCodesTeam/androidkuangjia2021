package com.example.libappmob;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.mob.MobSDK;
import com.mob.OperationCallback;
import com.mob.pushsdk.MobPush;
import com.mob.pushsdk.MobPushCallback;

public class TestActivity extends AppCompatActivity {

    private TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_mobshare);
//        tv1 = findViewById(R.id.tv1);
        // 放在application里面
//        configMob();
        // 根据HIOS协议三方平台跳转
        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        if (appLinkIntent != null) {
//            String appLinkAction = appLinkIntent.getAction();
            setToJump(appLinkIntent);

        }
    }

    private void setToJump(Intent appLinkIntent) {
        Uri appLinkData = appLinkIntent.getData();
        if (appLinkData != null) {
            final String aaaa = appLinkData.getQueryParameter("query1");
            final String bbbb = appLinkData.getQueryParameter("query2");
            final String cccc = appLinkData.getQueryParameter("query3");
            ToastUtils.showLong("query1->" + aaaa + ",query2->" + bbbb + ",query3->" + cccc);
//                    tv1.setText("query1->" + aaaa + ",query2->" + bbbb + ",query3->" + cccc);
            // 业务逻辑
            if (TextUtils.equals(aaaa, "index")) {
                // 跳转到首页
                Intent intent1 = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.ShouyeActivity");
                intent1.putExtra("query1", bbbb);
                startActivity(intent1);
                finish();
            } else if (TextUtils.equals(aaaa, "index3")) {
                // 跳转到首页3
                Intent intent1 = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.ShouyeActivity");
                intent1.putExtra("query1", bbbb);
                startActivity(intent1);
                finish();
                //TODO 发送广播bufen
//                        Intent msgIntent = new Intent();
//                        msgIntent.setAction("ShouyeActivity");
//                        msgIntent.putExtra("query1", 2);
//                        LocalBroadcastManagers.getInstance(this).sendBroadcast(msgIntent);
            } else if (TextUtils.equals(aaaa, "login")) {
                // 跳转到首页3
                Intent intent1 = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.SlbLoginActivity");
                intent1.putExtra("query1", bbbb);
                startActivity(intent1);
                finish();
            } else if (TextUtils.equals(aaaa, "webview")) {
//                SlbLoginUtil.get().loginTowhere(TestActivity.this, new Runnable() {
//                    @Override
//                    public void run() {
//                        // 跳转到学习报告详情页
////                                try {
//////                                        HiosHelper.resolveAd(WelComeActivity.this, WelComeActivity.this, URLDecoder.decode(bbbb, "UTF-8") + MmkvUtils.getInstance().get_common(CommonUtils.MMKV_TOKEN));
////                                    HiosHelper.resolveAd(TestActivity.this, TestActivity.this, URLDecoder.decode(bbbb, "UTF-8") + MmkvUtils.getInstance().get_common(CommonUtils.MMKV_TOKEN));
////                                } catch (UnsupportedEncodingException e) {
////                                    e.printStackTrace();
////                                }
////                                finish();
//                    }
//                });
//                finish();
            } else if (TextUtils.equals(aaaa, "order")) {
                // 跳转到我的—我的订单
                Intent intent1 = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.ActYewuList1");
                intent1.putExtra("query1", bbbb);
                startActivity(intent1);
                finish();
            }
        }
    }

    private void configMob() {
        MobSDK.init(this);
        //隐私协议
        MobSDK.submitPolicyGrantResult(true, new OperationCallback<Void>() {
            @Override
            public void onComplete(Void data) {
                Log.d("MobPush", "隐私协议授权结果提交：成功");
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("MobPush", "隐私协议授权结果提交：失败");
            }
        });
        //防止多进程注册多次  可以在MainActivity或者其他页面注册MobPushReceiver
        MobPush.getRegistrationId(new MobPushCallback<String>() {

            @Override
            public void onCallback(String rid) {
                System.out.println("MobPush->RegistrationId:" + rid);
                SPUtils.getInstance().put("MOBID", rid);
                //TODO MOBID TEST
                startService(new Intent(getApplicationContext(), MOBIDservices.class));
            }
        });
//        MobSDK.init(this);
//        //防止多进程注册多次  可以在MainActivity或者其他页面注册MobPushReceiver
//        String processName = getProcessName(this);
//        MobPush.getRegistrationId(new MobPushCallback<String>() {
//
//            @Override
//            public void onCallback(String rid) {
//                System.out.println("MobPush->RegistrationId:" + rid);
//                SPUtils.getInstance().put("MOBID", rid);
//            }
//        });
//        if (getPackageName().equals(processName)) {
//            MobPush.addPushReceiver(new MobPushReceiver() {
//                @Override
//                public void onCustomMessageReceive(Context context, MobPushCustomMessage message) {
//                    //接收自定义消息(透传)
//                    System.out.println("MobPush onCustomMessageReceive:" + message.toString());
//                }
//
//                @Override
//                public void onNotifyMessageReceive(Context context, MobPushNotifyMessage message) {
//                    //接收通知消
//                    System.out.println("MobPush onNotifyMessageReceive:" + message.toString());
//                    Message msg = new Message();
//                    msg.what = 1;
//                    msg.obj = "Message Receive:" + message.toString();
//                    handler.sendMessage(msg);
//
//                }
//
//                @Override
//                public void onNotifyMessageOpenedReceive(Context context, MobPushNotifyMessage message) {
//                    //接收通知消息被点击事件
//                    System.out.println("MobPush onNotifyMessageOpenedReceive:" + message.toString());
//                    Message msg = new Message();
//                    msg.what = 1;
//                    msg.obj = "Click Message:" + message.toString();
//                    handler.sendMessage(msg);
//                }
//
//                @Override
//                public void onTagsCallback(Context context, String[] tags, int operation, int errorCode) {
//                    //接收tags的增改删查操作
//                    System.out.println("MobPush onTagsCallback:" + operation + "  " + errorCode);
//                }
//
//                @Override
//                public void onAliasCallback(Context context, String alias, int operation, int errorCode) {
//                    //接收alias的增改删查操作
//                    System.out.println("MobPush onAliasCallback:" + alias + "  " + operation + "  " + errorCode);
//                }
//            });
//
//            handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
//                @Override
//                public boolean handleMessage(@NonNull Message msg) {
//                    if (msg.what == 1) {
//                        System.out.println("MobPush Callback Data:" + msg.obj);
//                    }
//                    return false;
//                }
//            });
//        }
        // activity获取信息
//        Intent intent = getIntent();
//        Uri uri = intent.getData();
//        if (intent != null) {
//            System.out.println("MobPush linkone intent---" + intent.toUri(Intent.URI_INTENT_SCHEME));
//        }
//        StringBuilder sb = new StringBuilder();
//        if (uri != null) {
//            sb.append(" scheme:" + uri.getScheme() + "\n");
//            sb.append(" host:" + uri.getHost() + "\n");
//            sb.append(" port:" + uri.getPort() + "\n");
//            sb.append(" query:" + uri.getQuery() + "\n");
//        }
//
//        //获取link界面传输的数据
//        JSONArray jsonArray = MobPushUtils.parseSchemePluginPushIntent(intent);
//        if (jsonArray != null){
//            sb.append("\n" + "bundle toString :" + jsonArray.toString());
//        }
//        //通过scheme跳转详情页面可选择此方式
//        JSONArray var = new JSONArray();
//        var =  MobPushUtils.parseSchemePluginPushIntent(intent2);
//        //跳转首页可选择此方式
//        JSONArray var2 = new JSONArray();
//        var2 = MobPushUtils.parseMainPluginPushIntent(intent2);
    }

}
