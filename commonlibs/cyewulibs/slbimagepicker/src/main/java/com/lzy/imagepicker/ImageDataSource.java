package com.lzy.imagepicker;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.blankj.utilcode.util.Utils;
import com.lzy.imagepicker.bean.ImageFolder;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.util.LoaderSpUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧 Github地址：https://github.com/jeasonlzy0216
 * 版    本：1.0
 * 创建日期：2016/5/19
 * 描    述：加载手机图片实现类
 * 修订历史：
 * ================================================
 */
public class ImageDataSource implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final int LOADER_ALL = 0;         //加载所有图片
    public static final int LOADER_CATEGORY = 1;    //分类加载图片
    private final String[] IMAGE_PROJECTION = {     //查询图片需要的数据列
            MediaStore.Images.Media.DISPLAY_NAME,   //图片的显示名称  aaa.jpg
            MediaStore.Images.Media.DATA,           //图片的真实路径  /storage/emulated/0/pp/downloader/wallpaper/aaa.jpg
            MediaStore.Images.Media.SIZE,           //图片的大小，long型  132492
            MediaStore.Images.Media.WIDTH,          //图片的宽度，int型  1920
            MediaStore.Images.Media.HEIGHT,         //图片的高度，int型  1080
            MediaStore.Images.Media.MIME_TYPE,      //图片的类型     image/jpeg
            MediaStore.Images.Media.DATE_ADDED};    //图片被添加的时间，long型  1450518608

    private FragmentActivity activity;
    private OnImagesLoadedListener loadedListener;                     //图片加载完成的回调接口
    private ArrayList<ImageFolder> imageFolders = new ArrayList<>();   //所有的图片文件夹

    /**
     * @param activity       用于初始化LoaderManager，需要兼容到2.3
     * @param path           指定扫描的文件夹目录，可以为 null，表示扫描所有图片
     * @param loadedListener 图片加载完成的监听
     */
    public ImageDataSource(FragmentActivity activity, String path, OnImagesLoadedListener loadedListener) {
        this.activity = activity;
        this.loadedListener = loadedListener;
//        LoaderManager loaderManager = activity.getSupportLoaderManager();
        LoaderManager loaderManager = LoaderManager.getInstance(activity);
        if (path == null) {
//            if (loaderManager.getLoader(LOADER_ALL) == null) {
//                loaderManager.initLoader(LOADER_ALL, null, this);
//                Log.e("--geekyun--", "LinitLoader加载图片");
//            } else {
//                loaderManager.restartLoader(LOADER_ALL, null, this);
//                Log.e("--geekyun--", "restartLoader加载图片");
//            }
            loaderManager.initLoader(LOADER_ALL, null, this);//加载所有的图片
            Log.e("--geekyun--", "加载图片");
        } else {
            //加载指定目录的图片
            Bundle bundle = new Bundle();
            bundle.putString("path", path);
            loaderManager.initLoader(LOADER_CATEGORY, bundle, this);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader = null;
        //扫描所有图片
        if (id == LOADER_ALL)
            cursorLoader = new CursorLoader(activity, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION, null, null, IMAGE_PROJECTION[6] + " DESC");
        Log.e("--geekyun--", "cursorLoader加载图片");
        //扫描某个图片文件夹
        if (id == LOADER_CATEGORY)
            cursorLoader = new CursorLoader(activity, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION, IMAGE_PROJECTION[1] + " like '%" + args.getString("path") + "%'", null, IMAGE_PROJECTION[6] + " DESC");

        return cursorLoader;
    }


    @Override
    public void onLoadFinished(final Loader<Cursor> loader, final Cursor data) {
//        if (activity.getSupportLoaderManager().getLoader(LOADER_ALL) == null) {
//            activity.getSupportLoaderManager().initLoader(LOADER_ALL, null, this);
//        } else {
//            activity.getSupportLoaderManager().restartLoader(LOADER_ALL, null, this);
//
//        }

        if ((int) (LoaderSpUtils.getInstance(activity).get("loader", -1)) == loader.getId()) {
            return;
        }
        Log.e("--geekyun--", data + "1111111");
        imageFolders.clear();
        if (data != null) {
            LoaderSpUtils.getInstance(activity).put("loader", loader.getId());
            ArrayList<ImageItem> allImages = new ArrayList<>();   //所有图片的集合,不分文件夹
            while (data.moveToNext()) {
                //查询数据
                String imageName = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]));
                String imagePath = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[1]));

                File file = new File(imagePath);
                if (!file.exists() || file.length() <= 0) {
                    continue;
                }

                long imageSize = data.getLong(data.getColumnIndexOrThrow(IMAGE_PROJECTION[2]));
                int imageWidth = data.getInt(data.getColumnIndexOrThrow(IMAGE_PROJECTION[3]));
                int imageHeight = data.getInt(data.getColumnIndexOrThrow(IMAGE_PROJECTION[4]));
                String imageMimeType = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[5]));
                long imageAddTime = data.getLong(data.getColumnIndexOrThrow(IMAGE_PROJECTION[6]));
                //封装实体
                ImageItem imageItem = new ImageItem();
                imageItem.name = imageName;
                imageItem.path = imagePath;
                imageItem.size = imageSize;
                imageItem.width = imageWidth;
                imageItem.height = imageHeight;
                imageItem.mimeType = imageMimeType;
                imageItem.addTime = imageAddTime;
                allImages.add(imageItem);
                //根据父路径分类存放图片
                File imageFile = new File(imagePath);
                File imageParentFile = imageFile.getParentFile();
                ImageFolder imageFolder = new ImageFolder();
                imageFolder.name = imageParentFile.getName();
                imageFolder.path = imageParentFile.getAbsolutePath();

                if (!imageFolders.contains(imageFolder)) {
                    ArrayList<ImageItem> images = new ArrayList<>();
                    images.add(imageItem);
                    imageFolder.cover = imageItem;
                    imageFolder.images = images;
                    imageFolders.add(imageFolder);
                } else {
                    imageFolders.get(imageFolders.indexOf(imageFolder)).images.add(imageItem);
                }
            }
            //防止没有图片报异常
            if (data.getCount() > 0 && allImages.size() > 0) {
                //构造所有图片的集合
                ImageFolder allImagesFolder = new ImageFolder();
                allImagesFolder.name = activity.getResources().getString(R.string.ip_all_images);
                allImagesFolder.path = "/";
                allImagesFolder.cover = allImages.get(0);
                allImagesFolder.images = allImages;
                imageFolders.add(0, allImagesFolder);  //确保第一条是所有图片
            }
        }
        //回调接口，通知图片数据准备完成
        createNewFolder(imageFolders);
        ImagePicker.getInstance().setImageFolders(imageFolders);
        loadedListener.onImagesLoaded(imageFolders);
//        activity.getSupportLoaderManager().destroyLoader(LOADER_ALL);
    }


    /**
     * 如果没有任何相册，先创建一个最近相册出来
     *
     * @param folders
     */
    public void createNewFolder(List<ImageFolder> folders) {
        if (folders.size() == 0) {
            // 没有相册 先创建一个最近相册出来
            ImageFolder newFolder = new ImageFolder();
            newFolder.name = "imagepicker";
            newFolder.path = file_url_img;
            ImageItem imageItem = new ImageItem();
            newFolder.cover = imageItem;
            newFolder.images = new ArrayList<>();
            folders.add(newFolder);
        }
    }

    public static final String gen = "geekmulu";
    public static final String FILE_SEP = System.getProperty("file.separator");
    public static final String gen_img = gen + FILE_SEP + "img";
    public static final String file_url_img = get_file_url() + FILE_SEP + gen_img + FILE_SEP;//图片全路径

    public static String get_file_url() {
        String file_apk_url;
        File file_apks = Utils.getApp().getExternalCacheDir();
        if (file_apks != null) {
            file_apk_url = file_apks.getAbsolutePath();
        } else {
            file_apk_url = Utils.getApp().getExternalFilesDir(null).getAbsolutePath();
        }
        return file_apk_url;
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        System.out.println("--------");
    }

    /**
     * 所有图片加载完成的回调接口
     */
    public interface OnImagesLoadedListener {
        void onImagesLoaded(List<ImageFolder> imageFolders);
    }
}