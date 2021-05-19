package com.haier.cellarette.baselibrary.zothers;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Environment;
import android.util.Log;

import com.blankj.utilcode.util.ToastUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ComeraAutomation {

    private Camera myCamera;
    private static volatile ComeraAutomation instance;

    public static ComeraAutomation getInstance() {
        if (instance == null) {
            synchronized (ComeraAutomation.class) {
                if (instance == null) {
                    instance = new ComeraAutomation();
                }
            }
        }
        return instance;
    }

    // 初始化摄像头
    public void initCamera(Context context) {
        // 如果存在摄像头
        if (checkCameraHardware(context)) {
            // 获取摄像头（首选前置，无前置选后置）
            if (openFacingFrontCamera()) {
                Log.i("initCamera", "openCameraSuccess");
                // 进行对焦
                autoFocus();
            } else {
                Log.i("initCamera", "openCameraFailed");
            }
        }
    }

    // 对焦并拍照
    public void autoFocus() {
        try {
            // 因为开启摄像头需要时间，这里让线程睡两秒
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i("测试线程拍照", Thread.currentThread().getId() + "");

        // 自动对焦
        myCamera.autoFocus(myAutoFocus);

        // 对焦后拍照
        myCamera.takePicture(null, null, myPicCallback);
    }

    // 判断是否存在摄像头
    public boolean checkCameraHardware(Context context) {

        if (context.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // 设备存在摄像头
            return true;
        } else {
            // 设备不存在摄像头
            return false;
        }
    }

    // 得到后置摄像头
    public boolean openFacingFrontCamera() {
        // 尝试开启前置摄像头
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int camIdx = 0, cameraCount = Camera.getNumberOfCameras(); camIdx < cameraCount; camIdx++) {
            Camera.getCameraInfo(camIdx, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                try {
                    Log.i("openFacingFrontCamera", "tryToOpenCamera");
                    myCamera = Camera.open(camIdx);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }

        // 如果开启前置失败（无前置）则开启后置
        if (myCamera == null) {
            for (int camIdx = 0, cameraCount = Camera.getNumberOfCameras(); camIdx < cameraCount; camIdx++) {
                Camera.getCameraInfo(camIdx, cameraInfo);
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                    try {
                        myCamera = Camera.open(camIdx);
                    } catch (RuntimeException e) {
                        return false;
                    }
                }
            }
        }

        /*try { // 这里的myCamera为已经初始化的Camera对象 myCamera.setPreviewDisplay(myHolder); } catch (IOException e) { e.printStackTrace(); myCamera.stopPreview(); myCamera.release(); myCamera = null; } */

        myCamera.startPreview();

        return true;
    }

    // 自动对焦回调函数(空实现)
    public Camera.AutoFocusCallback myAutoFocus = new Camera.AutoFocusCallback() {
        @Override
        public void onAutoFocus(boolean success, Camera camera) {
        }
    };

    // 拍照成功回调函数
    public Camera.PictureCallback myPicCallback = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            Log.i("测试线程处理拍照", Thread.currentThread().getId() + "");
            // 将得到的照片进行270°旋转，使其竖直
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            Matrix matrix = new Matrix();
            matrix.preRotate(270);
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            // 创建并保存图片文件
//            String filePath = getDir().getPath() + File.separator + "xinlv" + File.separator + "Screenshoots" + File.separator + timeStamp + ".png";
            File pictureFile = new File(getDir(), "IMG_" + timeStamp + ".png");
            FileOutputStream fos = null;
//            File tempFile = new File(filePath);
            try {
                fos = new FileOutputStream(pictureFile);
                fos.write(data);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
            }
            Log.i("onPictureTaken", "获取照片成功");
            ToastUtils.showShort("获取照片成功" + pictureFile);
            myCamera.stopPreview();
            myCamera.release();
            myCamera = null;
        }
    };

    // 获取文件夹
    public File getDir() {
        // 得到SD卡根目录
        File dir = Environment.getExternalStorageDirectory();
        /* * copy过来的代码。。。懒得删。 * 有根目录吗？ * 没有。 * 那就创建一个根目录！ */
        if (dir.exists()) {
            return dir;
        } else {
            dir.mkdirs();
            return dir;
        }
    }
}
