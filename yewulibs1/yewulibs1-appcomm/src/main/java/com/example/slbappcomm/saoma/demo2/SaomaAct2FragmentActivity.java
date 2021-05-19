package com.example.slbappcomm.saoma.demo2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.slbappcomm.R;
import com.yzq.zxinglibrary.common.Constant;

public class SaomaAct2FragmentActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private SaomaAct2Fragment saomaAct2Fragment =new SaomaAct2Fragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saoma2_fragment);
        initView();


    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("在Fragment中使用扫一扫");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, saomaAct2Fragment).commit();


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==1111){
            if (data != null) {

                String content = data.getStringExtra(Constant.CODED_CONTENT);
               Log.i("扫描结果为:", content);

               saomaAct2Fragment.onActivityResult(requestCode,resultCode,data);

            }
        }

    }
}
