//package com.example.slbappcomm.uploadimg;
//
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.net.Uri;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.example.slbappcomm.R;
//import com.example.slbappcomm.uploadimg.view.UploadImgClipViewLayout;
//import com.example.slbappcomm.uploadimg.view.UploadImgGetFileUtil;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.OutputStream;
//
//
///**
// * 头像裁剪Activity
// */
//public class ClipImageActivitybeiben extends AppCompatActivity implements View.OnClickListener {
//    private static final String TAG = "ClipImageActivity";
//    private UploadImgClipViewLayout uploadImgClipViewLayout1;
//    private UploadImgClipViewLayout uploadImgClipViewLayout2;
//    private ImageView back;
//    private TextView btnCancel;
//    private TextView btnOk;
//    //类别 1: qq, 2: weixin
//    private int type;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_uploadimg_clip_image);
//        type = getIntent().getIntExtra("type", 1);
//        initView();
//    }
//
//    /**
//     * 初始化组件
//     */
//    public void initView() {
//        uploadImgClipViewLayout1 = (UploadImgClipViewLayout) findViewById(R.id.clipViewLayout1);
//        uploadImgClipViewLayout2 = (UploadImgClipViewLayout) findViewById(R.id.clipViewLayout2);
//        back = (ImageView) findViewById(R.id.iv_back);
//        btnCancel = (TextView) findViewById(R.id.btn_cancel);
//        btnOk = (TextView) findViewById(R.id.bt_ok);
//        //设置点击事件监听器
//        back.setOnClickListener(this);
//        btnCancel.setOnClickListener(this);
//        btnOk.setOnClickListener(this);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (type == 1) {
//            uploadImgClipViewLayout1.setVisibility(View.VISIBLE);
//            uploadImgClipViewLayout2.setVisibility(View.GONE);
//            //设置图片资源
//            uploadImgClipViewLayout1.setImageSrc(getIntent().getData());
//        } else {
//            uploadImgClipViewLayout2.setVisibility(View.VISIBLE);
//            uploadImgClipViewLayout1.setVisibility(View.GONE);
//            //设置图片资源
//            uploadImgClipViewLayout2.setImageSrc(getIntent().getData());
//        }
//    }
//
//    @Override
//    public void onClick(View v) {
//        int i = v.getId();
//        if (i == R.id.iv_back) {
//            finish();
//        } else if (i == R.id.btn_cancel) {
//            finish();
//        } else if (i == R.id.bt_ok) {
//            generateUriAndReturn();
//        }
//    }
//
//
//    /**
//     * 生成Uri并且通过setResult返回给打开的activity
//     */
//    private void generateUriAndReturn() {
//        //调用返回剪切图
//        Bitmap zoomedCropBitmap;
//        if (type == 1) {
//            zoomedCropBitmap = uploadImgClipViewLayout1.clip();
//        } else {
//            zoomedCropBitmap = uploadImgClipViewLayout2.clip();
//        }
//        if (zoomedCropBitmap == null) {
//            Log.e("android", "zoomedCropBitmap == null");
//            return;
//        }
////        Uri mSaveUri = Uri.fromFile(new File(getCacheDir(), "cropped_" + System.currentTimeMillis() + ".jpg"));
//        UploadImgGetFileUtil uploadImgGetFileUtil = new UploadImgGetFileUtil();
////        uploadImgGetFileUtil.file_img_name = "geek_img" + type + UploadImgGetFileUtil.file_name_geshi;
//        uploadImgGetFileUtil.file_img = new File(UploadImgGetFileUtil.file_img_url, "geek_img" + type + UploadImgGetFileUtil.file_name_geshi);
//        Uri mSaveUri = Uri.fromFile(uploadImgGetFileUtil.file_img);
//        if (mSaveUri != null) {
//            OutputStream outputStream = null;
//            try {
//                outputStream = getContentResolver().openOutputStream(mSaveUri);
//                if (outputStream != null) {
//                    zoomedCropBitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
//                }
//            } catch (IOException ex) {
//                Log.e("android", "Cannot open file: " + mSaveUri, ex);
//            } finally {
//                if (outputStream != null) {
//                    try {
//                        outputStream.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//            Intent intent = new Intent();
//            intent.setData(mSaveUri);
//            setResult(RESULT_OK, intent);
//            finish();
//        }
//    }
//}
