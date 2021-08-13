package com.example.slbappcomm.jiechangtu;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;


/**
 * @author 工藤
 * @email gougou@16fan.com
 * com.fan16.cn.util.picture
 * create at 2018/5/4  16:34
 * description:图片下载工具类
 */
public class DownloadShareImgUtil {

    public void downloadPicture(boolean is_toast, final Context context, final Bitmap bitmap, final String path,
                                final String name) {
//        Toasty.normal(context, "开始下载...").show();
        FileUtils.createFileByDeleteOldFile(path + name);
        String cropImagePath = getRealFilePathFromUri(context, (Uri.fromFile(new File(path, name))));
        Bitmap bitMap = BitmapFactory.decodeFile(cropImagePath);
        //
        cropImagePath = saveBitmap(context, path, name, bitmap);
        if (!TextUtils.isEmpty(cropImagePath)) {
            if (is_toast) {
//                Toasty.normal(context, "成功保存到 ".concat(path).concat(name)).show();
//                Toasty.normal(context, "成功保存到相册 ".concat(path).concat(name)).show();
                ToastUtils.showLong("成功保存到相册 ".concat(path).concat(name));
            }
            new SingleMediaScanner(context, path.concat(name), new SingleMediaScanner.ScanListener() {
                @Override
                public void onScanFinish() {
                    // scanning...
                }
            });
        }
//        SimpleTarget<File> target = new SimpleTarget<File>() {
//
//            @Override
//            public void onLoadFailed(@Nullable Drawable errorDrawable) {
//                super.onLoadFailed(errorDrawable);
//                Toasty.normal(context, "保存失败").show();
//            }
//
//            @Override
//            public void onResourceReady(@NonNull File resource,
//                                        @Nullable Transition<? super File> transition) {
//                boolean result = copyFile(resource, path, name);
//                if (result) {
//                    Toasty.normal(context, "成功保存到 ".concat(path).concat(name)).show();
//                    new SingleMediaScanner(context, path.concat(name), new SingleMediaScanner.ScanListener() {
//                        @Override
//                        public void onScanFinish() {
//                            // scanning...
//                        }
//                    });
//                } else {
//                    Toasty.normal(context, "保存失败").show();
//                }
//            }
//        };
//    Bitmap bitmap = null;
//        Glide.with(context).downloadOnly().load(bitmap).into(target);
//    Glide.with(context).downloadOnly().load(url).into(target);
        //
//        Glide.with(context).asBitmap().load(bitmap)
//                .into(new Target<Bitmap>() {
//                    @Override
//                    public void onLoadStarted(@Nullable Drawable placeholder) {
//                        Log.e("geek", "onLoadStarted");
//                    }
//
//                    @Override
//                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
//                        Log.e("geek", "onLoadFailed");
//                    }
//
//                    @Override
//                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                        Log.e("geek", "onResourceReady");
//                        boolean result = copyFile(resource, path, name);
//                        if (result) {
//                            Toasty.normal(context, "成功保存到 ".concat(path).concat(name)).show();
//                            new SingleMediaScanner(context, path.concat(name), new SingleMediaScanner.ScanListener() {
//                                @Override
//                                public void onScanFinish() {
//                                    // scanning...
//                                }
//                            });
//                        } else {
//                            Toasty.normal(context, "保存失败").show();
//                        }
//                    }
//
//                    @Override
//                    public void onLoadCleared(@Nullable Drawable placeholder) {
//                        Log.e("geek", "onLoadCleared");
//                    }
//
//                    @Override
//                    public void getSize(@NonNull SizeReadyCallback cb) {
//                        Log.e("geek", "getSize");
//                        cb.onSizeReady(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
//                    }
//
//                    @Override
//                    public void removeCallback(@NonNull SizeReadyCallback cb) {
//                        Log.e("geek", "removeCallback");
//                    }
//
//                    @Override
//                    public void setRequest(@Nullable Request request) {
//                        Log.e("geek", "setRequest");
//                    }
//
//                    @Nullable
//                    @Override
//                    public Request getRequest() {
//                        return null;
//                    }
//
//                    @Override
//                    public void onStart() {
//                        Log.e("geek", "onStart");
//                    }
//
//                    @Override
//                    public void onStop() {
//                        Log.e("geek", "onStop");
//                    }
//
//                    @Override
//                    public void onDestroy() {
//                        Log.e("geek", "onDestroy");
//                    }
//                });
    }

    /**
     * 根据文件路径拷贝文件
     *
     * @param resourceFile 源文件
     * @param targetPath   目标路径（包含文件名和文件格式）
     * @return boolean 成功true、失败false
     */
    public boolean copyFile(File resourceFile, String targetPath, String fileName) {
        boolean result = false;
        if (resourceFile == null || TextUtils.isEmpty(targetPath)) {
            return result;
        }
        File target = new File(targetPath);
        if (target.exists()) {
            target.delete(); // 已存在的话先删除
        } else {
            try {
                target.mkdirs();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        File targetFile = new File(targetPath.concat(fileName));
        if (targetFile.exists()) {
            targetFile.delete();
        } else {
            try {
                targetFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileChannel resourceChannel = null;
        FileChannel targetChannel = null;
        try {
            resourceChannel = new FileInputStream(resourceFile).getChannel();
            targetChannel = new FileOutputStream(targetFile).getChannel();
            resourceChannel.transferTo(0, resourceChannel.size(), targetChannel);
            result = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return result;
        }
        try {
            resourceChannel.close();
            targetChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据Uri返回文件绝对路径
     * 兼容了file:///开头的 和 content://开头的情况
     */
    public static String getRealFilePathFromUri(final Context context, final Uri uri) {
        if (null == uri) return null;
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

    /**
     * 根据bitmap 返回保存本地地址
     *
     * @param context
     * @param path
     * @param name
     * @param mBitmap
     * @return
     */
    public String saveBitmap(Context context, String path, String name, Bitmap mBitmap) {
        File filePic;
        try {
            filePic = new File(path + name);//保存的格式为jpg
            if (!filePic.exists()) {
                filePic.getParentFile().mkdirs();
                filePic.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(filePic);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        return filePic.getAbsolutePath();
    }

}