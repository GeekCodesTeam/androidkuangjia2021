package com.example.libappservices;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.AppUtils;

import org.greenrobot.eventbus.EventBus;

public class AppServicesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appservices);
        findViewById(R.id.tv1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO 1
                startService(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.MobUpdateServices"));
                // TODO 2
                Intent intent1 = new Intent(AppServicesActivity.this, MobUpdateServices.class);
                intent1.putExtra(MobUpdateServices.time, System.currentTimeMillis() + "");
                startService(intent1);
                // TODO 3
//        EventBus.getDefault().post(new MemberHrView(hrCardView, member));
                EventBus.getDefault().post("geek");
                // TODO 4
                Intent msgIntent = new Intent();
                msgIntent.setAction(MobUpdateServices.ActYewu1);
                msgIntent.putExtra(MobUpdateServices.time, System.currentTimeMillis() + "");
                MobLocalBroadcastManagers.getInstance(AppServicesActivity.this).sendBroadcast(msgIntent);
            }
        });


    }

}
