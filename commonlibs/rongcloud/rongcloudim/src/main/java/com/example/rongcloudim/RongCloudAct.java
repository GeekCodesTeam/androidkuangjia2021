package com.example.rongcloudim;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;

import com.geek.libutils.app.BaseApp;
import com.geek.libutils.app.MyLogUtil;
import com.geek.libutils.data.MmkvUtils;

import io.rong.callkit.RongCallKit;
import io.rong.callkit.RongCallKit.CallMediaType;
import io.rong.imkit.RongIM;
import io.rong.imkit.utils.RouteUtils;

/**
 * 音视频通话代码流程：
 * 1. 初始化IM，本 quickdemo-callkit/src/main/java/cn/rongcloud/quickdemo_callkit/CallKitApp.java 类中
 * 2. 登录IM，{@link CallkitBaseActivity connectIM(String) }
 * 3. 登录成功后，调用Callkit中方法发起单/多人音视频通话；{@link RongCloudAct callClick(View) }
 * 4. 接听通话UI及其逻辑在callkit模块中，开发者不需要关心
 */
public class RongCloudAct extends CallkitBaseActivity {

    private RadioButton rb_videoCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayout(R.layout.activity_rongcloud);
        RongIM.init(BaseApp.get(), Utils.appKey);
        rb_videoCall = findViewById(R.id.rb_videoCall);
        rb_videoCall.setChecked(true);
//        /**
//         * 防止 voip 通话页面被会话列表、会话页面或者开发者 app 层页面覆盖。
//         * 使用 maven 接入 callkit 的开发者在 app 层主页面的 onCreate 调用此方法即可。
//         * 针对导入 callkit 源码的开发者，不使用会话列表和会话页面
//         * 我们建议在 {@link RongCallModule#onCreate(Context)}方法中设置
//         * mViewLoaded 为 true 即可。
//         */
//        RongCallKit.onViewCreated();// 4.X需要
    }

    //TODO 本demo仅展示使用calkit模块发起音视频通话，不使用callkit模块请查看开发者文档：https://docs.rongcloud.cn/v4/views/rtc/call/noui/quick/android.html
    public void callClick(View view) {
        String targetId = getTargetId();
        MyLogUtil.e("ssssssss", targetId);
        if (TextUtils.isEmpty(targetId)) {
            showToast("目标会话 id获取错误，可能没有登录！");
            return;
        }
        CallMediaType mediaType = rb_videoCall.isChecked() ? CallMediaType.CALL_MEDIA_TYPE_VIDEO : CallMediaType.CALL_MEDIA_TYPE_AUDIO;
        /**
         * 发起单人通话。
         *
         * @param context 上下文
         * @param targetId 目标会话 id ，单人通话为对方 UserId ,群组通话为 GroupId ，如果实现的是不基于群组的通话，那此参数无意义，传 null 即可
         * @param mediaType 会话媒体类型
         */
        RongCallKit.startSingleCall(this, targetId, mediaType);
    }

    public void callClick2(View view) {
        String targetId = getTargetId();
        MmkvUtils.getInstance().set_common("userId", targetId);
        if (TextUtils.isEmpty(targetId)) {
            showToast("目标会话 id获取错误，可能没有登录！");
            return;
        }
//        Map<String, Boolean> supportedConversation = new HashMap<>();
//        supportedConversation.put(Conversation.ConversationType.PRIVATE.getName(), false);
//        supportedConversation.put(Conversation.ConversationType.GROUP.getName(), false);
//        supportedConversation.put(Conversation.ConversationType.SYSTEM.getName(), true);
//        RongIM.getInstance().startConversationList(this , supportedConversation);
//        // 方式1
//        /**
//         * 注册自定义 Activity， 注册之后将替换 SDK 默认 Activity。
//         * @param activityType {@link RongActivityType} SDK 内置各 activity 类型。
//         * @param activity 自定义 activity.
//         */
//        RouteUtils.registerActivity(RouteUtils.RongActivityType.ConversationListActivity, ConversationListActivity.class);
        // 方式2
        /**
         * 启动会话列表页面
         * @param context activity 上下文。
         * @param title 会话列表页面标题，如果传空，会显示为默认标题 "会话列表"。
         **/
        RouteUtils.routeToConversationListActivity(RongCloudAct.this, "福生佳信");

    }

    @Override
    public void IMConnectSuccess(String userId) {
    }

    @Override
    public void IMConnectError() {
    }
}
