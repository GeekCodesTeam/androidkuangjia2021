package com.example.libappmob;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.mob.MobSDK;

import cn.sharesdk.onekeyshare.OnekeyShare;

public class MobshareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobshare);
        findViewById(R.id.tv1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnekeyShare oks = new OnekeyShare();
                // title标题，微信、QQ和QQ空间等平台使用
                oks.setTitle("分享");
                // titleUrl QQ和QQ空间跳转链接
                oks.setTitleUrl("https://blog.51cto.com/liangxiao");
                // text是分享文本，所有平台都需要这个字段
                oks.setText("我是分享文本");
                // setImageUrl是网络图片的url
                oks.setImageUrl("https://hmls.hfbank.com.cn/hfapp-api/9.png");
                // url在微信、Facebook等平台中使用
                oks.setUrl("https://blog.51cto.com/liangxiao");
                // 启动分享GUI
                oks.show(MobSDK.getContext());
            }
        });

    }

}
