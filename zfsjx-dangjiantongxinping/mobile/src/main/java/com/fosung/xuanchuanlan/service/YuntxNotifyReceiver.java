//package com.fosung.xuanchuanlan.service;
//
//import android.app.Service;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Build;
//import android.os.IBinder;
//
//import com.yuntongxun.ecsdk.ECDevice;
//import com.yuntongxun.ecsdk.ECMessage;
//import com.yuntongxun.ecsdk.booter.ECNotifyReceiver;
//import com.yuntongxun.ecsdk.im.ECMessageNotify;
//import com.yuntongxun.ecsdk.im.group.ECGroupNoticeMessage;
//import com.yuntongxun.ecsdk.platformtools.ECHandlerHelper;
//import com.yuntongxun.plugin.common.SDKCoreHelper;
//import com.yuntongxun.plugin.common.common.utils.LogUtil;
//import com.yuntongxun.plugin.common.common.utils.TextUtil;
//
////import com.yuntongxun.ecsdk.ECMessage;
////import com.yuntongxun.ecsdk.im.ECMessageNotify;
////import com.yuntongxun.ecsdk.im.group.ECGroupNoticeMessage;
////import com.yuntongxun.plugin.im.ui.chatting.helper.IMChattingHelper;
////import com.yuntongxun.plugin.voip.Voip;
////import com.yuntongxun.voipdemo.VoipImpl;
//
///**
// * SDK通知会分三种提醒方式
// * <p>1、直接通过设置的回调接口（OnChatReceiveListener）Push给应用
// * <p>2、如果应用没有设置回调接口则采用（BroadcastReceiver）广播通知（v5.1.8版本）此时如果应用处于未运行状态则会直接唤醒应用处理
// * <p>3、如果应用未处于运行状态并且不想被唤醒应用运行则采用状态栏通知处理（SDK直接提醒，不会通知应用）,比如调用
// * {@link ECDevice#logout(ECDevice.NotifyMode, ECDevice.OnLogoutListener)}退出接口传入后台接收消息才会有提醒
// *
// * @author 容联•云通讯
// * @version 5.1.8
// * @since 2015-10-23
// */
//public class YuntxNotifyReceiver extends ECNotifyReceiver {
//    private static final String TAG = ".YuntxNotifyReceiver";
//    /**
//     * 会议类型 1：语音会议   2：视频会议
//     */
//    private static final String KEY_CONFIG_TYPE = "conftype";
//
//    public static final String SERVICE_OPT_CODE = "service_opt_code";
//    public static final String MESSAGE_SUB_TYPE = "message_type";
//
//    /**
//     * 来电
//     */
//    public static final int EVENT_TYPE_CALL = 1;
//    /**
//     * 消息推送
//     */
//    public static final int EVENT_TYPE_MESSAGE = 2;
//
//    /**
//     * 创建一个服务Intent
//     *
//     * @return Intent
//     */
//    public Intent buildServiceAction(int optCode) {
//        Intent notifyIntent = new Intent(getContext(), NotifyService.class);
//        notifyIntent.putExtra(SERVICE_OPT_CODE, optCode);
//        return notifyIntent;
//    }
//
//    public Intent buildMessageServiceAction(int subOptCode) {
//        Intent intent = this.buildServiceAction(EVENT_TYPE_MESSAGE);
//        intent.putExtra(MESSAGE_SUB_TYPE, subOptCode);
//        return intent;
//    }
//
//    @Override
//    public void onReceivedMessage(Context context, ECMessage ecMessage) {
//        if (ecMessage != null && ecMessage.getType() == ECMessage.Type.CMD
//                && !TextUtil.isEmpty(ecMessage.getUserData())
//                && ecMessage.getUserData().contains("customtype=\"601\"")){
//            //开始上传日志的服务
//            // LogUtil.startLogUploadService(context);
//            return;
//        }
//        LogUtil.e(TAG, "接收消息 onReceivedMessage");
//        Intent intent = this.buildMessageServiceAction(17);
//        intent.putExtra("sdk_im_message", ecMessage);
//        context.startService(intent);
//    }
//
////    @Override
////    public void onReceivedMessage(Context context, ECMessage ecMessage) {
////
////    }
//
//    @Override
//    public void onCallArrived(Context context, Intent intent) {
//        LogUtil.e(TAG, "接收到通话");
//        if (isMeetingCall(intent)) {
//            return;
//        }
//        Intent serviceAction = buildServiceAction(EVENT_TYPE_CALL);
//        serviceAction.putExtras(intent);
//        context.startService(serviceAction);
//    }
//
//    @Override
//    public void onReceiveGroupNoticeMessage(Context context, ECGroupNoticeMessage notice) {
//        LogUtil.e(TAG, "接收消息 onReceiveGroupNoticeMessage");
//        Intent intent = this.buildMessageServiceAction(19);
//        intent.putExtra("sdk_im_message", notice);
//        context.startService(intent);
//    }
//
//    @Override
//    public void onNotificationClicked(Context context, int notifyType, String sender) {
//        LogUtil.d(TAG, "onNotificationClicked notifyType " + notifyType + " ,sender " + sender);
//    }
//
////    @Override
////    public void onReceiveMessageNotify(Context context, ECMessageNotify notify) {
////        Intent intent = this.buildMessageServiceAction(20);
////        intent.putExtra("sdk_im_message", notify);
////        context.startService(intent);
////    }
//
//    public static class NotifyService extends Service {
//        public static final String TAG = "YuntxNotifyReceiver.NotifyService";
//
//        @Override
//        public IBinder onBind(Intent intent) {
//            return null;
//        }
//
//        /**
//         * 处理通知消息
//         *
//         * @param intent
//         */
//        private void dispatchOnReceiveMessage(Intent intent) {
//            if (intent == null) {
//                LogUtil.e(TAG, "dispatch receive message fail.");
//                return;
//            }
//            int subOptCode = intent.getIntExtra(MESSAGE_SUB_TYPE, -1);
//            if (subOptCode == -1) {
//                return;
//            }
//            // TODO: 2017/4/14 0014 如果使用IM插件  请把这里打开
//            switch (subOptCode) {
//                case OPTION_SUB_NORMAL:
//                    ECMessage message = intent.getParcelableExtra(EXTRA_MESSAGE);
////                    if(EcMessageFilterImp.getInstance().filterMessage(message)){
////                        return;
////                    }
//                    //   IMChattingHelper.getInstance().OnReceivedMessage(message);
//                    break;
//                case OPTION_SUB_GROUP:
//                    ECGroupNoticeMessage notice = intent.getParcelableExtra(EXTRA_MESSAGE);
//                    //   IMChattingHelper.getInstance().OnReceiveGroupNoticeMessage(notice);
//                    break;
//                case OPTION_SUB_MESSAGE_NOTIFY:
//                    ECMessageNotify notify = intent.getParcelableExtra(EXTRA_MESSAGE);
//
//                    //   IMChattingHelper.getInstance().onReceiveMessageNotify(notify);
//                    break;
//            }
//        }
//
//        @SuppressWarnings("static-access")
//        private void receiveImp(final Intent intent) {
//
//            if (intent == null) {
//                LogUtil.e(TAG, "receiveImp receiveIntent == null");
//                return;
//            }
//            LogUtil.i(TAG, "intent:action " + intent.getAction() + "=SDK状态=" + SDKCoreHelper.isUIShowing());
//            int optCode = intent.getIntExtra(SERVICE_OPT_CODE, 0);
//            if (optCode == 0) {
//                LogUtil.e(TAG, "receiveImp invalid opcode.");
//                return;
//            }
//            if (!SDKCoreHelper.isUIShowing()) {
//                //    SDKCoreHelper.init(SDKCoreHelper.getContext());
//            } else {
//                LogUtil.d(TAG, "SDKCoreHelper is  showing, gona do nothing");
//            }
//
//            switch (optCode) {
//                case EVENT_TYPE_CALL:
//                    LogUtil.d(TAG, "receive call event ");
//                    // TODO: 2017/4/14 0014 如果使用Voip插件 请把这里打开
//                    // Voip.getCallService().startCall(intent); //这是点对点的音视频
//                    break;
//                case EVENT_TYPE_MESSAGE:
//                    if (ECDevice.isInitialized()) {
//                        dispatchOnReceiveMessage(intent);
//                    } else {
//                        LogUtil.d(TAG, "ECDevice.isInitialized() false ");
//                        ECHandlerHelper.postDelayedRunnOnUI(new Runnable() {
//                            @Override
//                            public void run() {
//                                if (ECDevice.isInitialized()) {
//                                    dispatchOnReceiveMessage(intent);
//                                } else {
//                                    LogUtil.d(TAG, "after postDelayedRunnOnUI ,ECDevice.isInitialized() false ");
//                                }
//                            }
//                        }, 500);
//                    }
//                    break;
//
//            }
//        }
//
//        // Android Version 2.0以下版本
//        @Override
//        public void onStart(Intent intent, int startId) {
//            super.onStart(intent, startId);
//            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ECLAIR) {
//                receiveImp(intent);
//            }
//        }
//
//        // Android 2.0以上版本回调/同时会执行onStart方法
//        @Override
//        public int onStartCommand(Intent intent, int flags, int startId) {
//            LogUtil.v(TAG, String.format("onStartCommand flags :%d startId :%d intent %s", flags, startId, intent));
//            receiveImp(intent);
//            return super.onStartCommand(intent, flags, startId);
//        }
//    }
//
//    /**
//     * 过滤当前是否会议邀请呼叫
//     *
//     * @param intent 呼叫透传
//     * @return 会议邀请
//     */
//    private boolean isMeetingCall(Intent intent) {
//        if (!SDKCoreHelper.isUIShowing()) {
////            SDKCoreHelper.init(SDKCoreHelper.getContext());
//        } else {
//            LogUtil.d(TAG, "SDKCoreHelper is  showing, gona do nothing");
//        }
//
//        // 透传信息
//        String[] infos = null;
//        try {
//            infos = intent.getStringArrayExtra(ECDevice.REMOTE);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (infos != null && infos.length > 0) {
//            for (String str : infos) {
//                LogUtil.e(TAG, "str==" + str);
//                if (str.startsWith(KEY_CONFIG_TYPE)) {
//                    if (str.endsWith("1")) {
//                        //语音会议
////                        VoiceMeetingService.setInComingVoiceMeeting(intent, ViewImpl.getInstance());
//                    } else {
//                        //视频会议
////                        if (!VideoMeetingService.isBusy()) {
////                            VideoMeetingService.setInComingVideoMeeting(intent, ViewImpl.getInstance());
////                        }
//                    }
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//
//
//}
