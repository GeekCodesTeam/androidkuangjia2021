package com.example.rongcloudim;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import io.rong.imkit.config.ConversationListBehaviorListener;
import io.rong.imkit.config.RongConfigCenter;
import io.rong.imkit.conversationlist.ConversationListFragment;
import io.rong.imkit.conversationlist.model.BaseUiConversation;
import io.rong.imkit.utils.RouteUtils;
import io.rong.imlib.model.Conversation;

public class ConversationListActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_list);

        ConversationListFragment conversationListFragment = new ConversationListFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container, conversationListFragment);
        transaction.commit();
        //
        RongConfigCenter.conversationListConfig().setBehaviorListener(new ConversationListBehaviorListener() {
            /**
             * 会话头像点击监听
             *
             * @param context          上下文。
             * @param conversationType 会话类型。
             * @param targetId         被点击的用户id。
             * @return true 拦截事件, false 执行融云 SDK 内部默认处理逻辑
             */
            @Override
            public boolean onConversationPortraitClick(Context context, Conversation.ConversationType conversationType, String targetId) {
                return false;
            }

            /**
             * 会话头像长按监听
             *
             * @param context          上下文。
             * @param conversationType 会话类型。
             * @param targetId         被点击的用户id。
             * @return true 拦截事件, false 执行融云 SDK 内部默认处理逻辑
             */
            @Override
            public boolean onConversationPortraitLongClick(Context context, Conversation.ConversationType conversationType, String targetId) {
                return false;
            }

            /**
             * 会话列表中的 Item 长按监听
             *
             * @param context      上下文。
             * @param view         触发点击的 View。
             * @param conversation 长按时的会话条目
             * @return true 拦截事件, false 执行融云 SDK 内部默认处理逻辑
             */
            @Override
            public boolean onConversationLongClick(Context context, View view, BaseUiConversation conversation) {
                return false;
            }

            /**
             * 会话列表中的 Item 点击监听
             *
             * @param context      上下文。
             * @param view         触发点击的 View。
             * @param conversation 长按时的会话条目
             * @return true 拦截事件, false 执行融云 SDK 内部默认处理逻辑
             */
            @Override
            public boolean onConversationClick(Context context, View view, BaseUiConversation conversation) {
//                RouteUtils.registerActivity(RouteUtils.RongActivityType.ConversationActivity, ConversationActivity.class);
//                Conversation.ConversationType conversationType = Conversation.ConversationType.PRIVATE;
//                String targetId = "接收方 ID";
//                String title = "这里可以填写名称";
//                Bundle bundle = new Bundle();
//                if (!TextUtils.isEmpty(title)) {
//                    bundle.putString(RouteUtils.TITLE, title); //会话页面标题
////                    bundle.putLong(RouteUtils.INDEX_MESSAGE_TIME, fixedMsgSentTime); //打开会话页面时的默认跳转位置，如果不配置将跳转到消息列表底部
//                }
//                RouteUtils.routeToConversationActivity(context, conversationType, targetId, bundle);
                return false;
            }
        });


    }
}