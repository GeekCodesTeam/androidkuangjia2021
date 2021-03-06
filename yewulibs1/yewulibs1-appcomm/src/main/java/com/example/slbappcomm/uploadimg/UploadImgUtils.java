package com.example.slbappcomm.uploadimg;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.slbappcomm.R;
import com.zhy.base.fileprovider.FileProvider7;

import java.io.File;


public class UploadImgUtils {

    //    public File file = new File(ConstantsUtils.file_url, "uploadimg" + ".jpg");
    //请求相机
    public static final int REQUEST_CAPTURE = 100;
    //请求相册
    public static final int REQUEST_PICK = 101;
    //请求截图
    public static final int REQUEST_CROP_PHOTO = 102;
    //请求访问外部存储
    public static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 103;
    //请求写入外部存储
    public static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 104;

    private static volatile UploadImgUtils instance;
    private Context mContext;

    private UploadImgUtils(Context context) {
        mContext = context;
    }

    public static UploadImgUtils getInstance(Context context) {
        if (instance == null) {
            synchronized (UploadImgUtils.class) {
                instance = new UploadImgUtils(context);
            }
        }
        return instance;
    }

    /**
     * 根据Uri返回文件绝对路径
     * 兼容了file:///开头的 和 content://开头的情况
     */
    public String getRealFilePathFromUri(final Context context, final Uri uri) {
        if (null == uri) {
            return null;
        }
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_FILE.equalsIgnoreCase(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equalsIgnoreCase(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    public PopupWindow popupWindow;

    public PopupWindow getPopupWindow() {
        return popupWindow;
    }

    public void setPopupWindow(PopupWindow popupWindow) {
        this.popupWindow = popupWindow;
    }

    public void diss() {
        popupWindow.dismiss();
        popupWindow = null;
    }

    /**
     * 上传头像 File file = new File(ConstantsUtils.file_url, "uploadimg" + ".jpg")  Uri.fromFile(file)
     */
    public void uploadHeadImage(final Context context, final File file, final OnPopBackListener onPopBackListener) {
        View mMenuView = LayoutInflater.from(context).inflate(R.layout.activity_uploadimg_popupwindow, null);
        TextView btnCarema = mMenuView.findViewById(R.id.btn_camera);
        TextView btnPhoto = mMenuView.findViewById(R.id.btn_photo);
        TextView btnCancel = mMenuView.findViewById(R.id.btn_cancel);
        popupWindow = new PopupWindow(mMenuView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(false);
        popupWindow.setAnimationStyle(R.style.AnimBottom);
//        ColorDrawable dw = new ColorDrawable(0xb0000000);
//        popupWindow.setBackgroundDrawable(dw);
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                hideInputMethod(context);
                popupWindow.dismiss();
                return true;
            }
        });
        popupWindow.showAtLocation(((Activity) context).getWindow().getDecorView(), Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
        //popupWindow在弹窗的时候背景半透明
//        final WindowManager.LayoutParams params = getWindow().getAttributes();
//        params.alpha = 0.5f;
//        getWindow().setAttributes(params);
//        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                params.alpha = 1.0f;
//                getWindow().setAttributes(params);
//            }
//        });

        btnCarema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onPopBackListener != null) {
                    onPopBackListener.onClick1(popupWindow);
                }
            }
        });
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onPopBackListener != null) {
                    onPopBackListener.onClick2(popupWindow);
                }

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onPopBackListener != null) {
                    onPopBackListener.onClick3(popupWindow);
                }

            }
        });
    }

    public interface OnPopBackListener {
        void onClick1(PopupWindow popupWindow);

        void onClick2(PopupWindow popupWindow);

        void onClick3(PopupWindow popupWindow);
    }

    /**
     * 跳转到相册
     */
    public void gotoPhoto(Context context) {
        Log.d("evan", "*****************打开图库********************");
        //跳转到调用系统图库
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        ((Activity) context).startActivityForResult(Intent.createChooser(intent, "请选择图片"), REQUEST_PICK);
    }

    /**
     * 跳转到照相机 File file = new File(ConstantsUtils.file_url, "uploadimg" + ".jpg")
     */
    public void gotoCamera(Context context, File file) {
        Log.d("evan", "*****************打开相机********************");
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(mContext.getPackageManager()) != null) {
            Uri fileUri1 = FileProvider7.getUriForFile(mContext, file);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri1);
            ((Activity) context).startActivityForResult(takePictureIntent, REQUEST_CAPTURE);
        }
    }

    /**
     * 打开截图界面 File file = new File(ConstantsUtils.file_url, "uploadimg" + ".jpg")  Uri.fromFile(file)
     */
    public void gotoClipActivity(Context context, int type, Uri uri) {
        if (uri == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(context, ClipImageActivity.class);
        intent.putExtra("type", type);// 默认1为圆形 2为矩形
        intent.setData(uri);
        ((Activity) context).startActivityForResult(intent, REQUEST_CROP_PHOTO);
    }

}
