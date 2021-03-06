package com.example.slbappindex.logindemo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.slbappindex.R;
import com.geek.libbase.base.SlbBaseActivity;


public class AdLoginActDemo extends SlbBaseActivity {

    private int mAction; // default 0
    private String mSkuId = ""; // maybe null
    private String mCategoryId = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_loginactdemo3;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAction = getIntent().getIntExtra("act", 0);
        mSkuId = getIntent().getStringExtra("sku_id");
        mCategoryId = getIntent().getStringExtra("category_id");

        Toast.makeText(this, mAction + ", " + mSkuId + ", " + mCategoryId, Toast.LENGTH_LONG).show();

        findViewById(R.id.tv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //back
                finish();
            }
        });

        if (mAction == 1) {
            new AlertDialog.Builder(this)
                    .setTitle("根据业务弹出窗")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();

        }

    }


}
