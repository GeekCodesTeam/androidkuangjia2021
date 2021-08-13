package com.example.slbappcomm.uploadimg;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.slbappcomm.R;
import com.example.slbappcomm.uploadimg.view.UploadImgClipViewLayout;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;


/**
 * 头像裁剪Activity
 */
public class ClipImageActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "ClipImageActivity";
    private UploadImgClipViewLayout uploadImgClipViewLayout1;
    private UploadImgClipViewLayout uploadImgClipViewLayout2;
    private ImageView back;
    private TextView btnCancel;
    private TextView btnOk;
    private int type;

    private String img_file_url;
    private String img_file_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadimg_clip_image);
        type = getIntent().getIntExtra("type", 1);
        img_file_url = getIntent().getStringExtra("img_file_url");
        img_file_name = getIntent().getStringExtra("img_file_name");
        initView();
    }

    /**
     * 初始化组件
     */
    public void initView() {
        uploadImgClipViewLayout1 = findViewById(R.id.clipViewLayout1);
        uploadImgClipViewLayout2 = findViewById(R.id.clipViewLayout2);
        back = findViewById(R.id.iv_back);
        btnCancel = findViewById(R.id.btn_cancel);
        btnOk = findViewById(R.id.bt_ok);
        //设置点击事件监听器
        back.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnOk.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (type == 1) {
            uploadImgClipViewLayout1.setVisibility(View.VISIBLE);
            uploadImgClipViewLayout2.setVisibility(View.GONE);
            //设置图片资源
            uploadImgClipViewLayout1.setImageSrc(getIntent().getData());
        } else {
            uploadImgClipViewLayout2.setVisibility(View.VISIBLE);
            uploadImgClipViewLayout1.setVisibility(View.GONE);
            //设置图片资源
            uploadImgClipViewLayout2.setImageSrc(getIntent().getData());
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.iv_back) {
            finish();
        } else if (i == R.id.btn_cancel) {
            finish();
        } else if (i == R.id.bt_ok) {
            generateUriAndReturn();
        }
    }


    /**
     * 生成Uri并且通过setResult返回给打开的activity
     */
    private void generateUriAndReturn() {
        //调用返回剪切图
        Bitmap zoomedCropBitmap;
        if (type == 1) {
            zoomedCropBitmap = uploadImgClipViewLayout1.clip();
        } else {
            zoomedCropBitmap = uploadImgClipViewLayout2.clip();
        }
        if (zoomedCropBitmap == null) {
            Log.e("android", "zoomedCropBitmap == null");
            return;
        }
        Uri mSaveUri = Uri.fromFile(new File(img_file_url, img_file_name));
        if (mSaveUri != null) {
            OutputStream outputStream = null;
            try {
                outputStream = getContentResolver().openOutputStream(mSaveUri);
                if (outputStream != null) {
                    zoomedCropBitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
                }
            } catch (IOException ex) {
                Log.e("android", "Cannot open file: " + mSaveUri, ex);
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            Intent intent = new Intent();
            intent.setData(mSaveUri);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
