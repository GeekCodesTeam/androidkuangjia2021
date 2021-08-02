/*
 * *********************************************************
 *   author   colin
 *   company  fosung
 *   email    wanglin2046@126.com
 *   date     17-8-15 下午2:41
 * ********************************************************
 */

package com.fosung.xuanchuanlan.common.util;

import android.Manifest;
import android.content.Intent;

import com.fosung.frameutils.app.BaseFrameActivity;
import com.fosung.frameutils.app.BaseFrameFrag;
import com.fosung.frameutils.app.ResultActivityHelper;
import com.fosung.frameutils.permission.PermissionHelper;
import com.fosung.frameutils.permission.PermissionsResultAction;
import com.fosung.frameutils.util.ToastUtil;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;


/**
 * 选取图片的工具类，因为此类使用了Matisse，所以在客户端实现
 * {@link SystemIntentUtil#selectPhoto(Object, SystemIntentUtil.OnCompleteLisenter)}} 是使用的系统选择
 */
public class PhotoSelectedUtil {

    /**
     * 选取一张图片
     *
     * @param context 只能为BaseFrameFrag或者BaseActivity的子类
     */
    public static void selectPhoto(final Object context, final ResultActivityHelper.ResultActivityListener resultListener) {
        String[] permissions;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            permissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};
        } else {
            permissions = new String[]{Manifest.permission.CAMERA};
        }

        PermissionHelper.requestPermission(context, permissions, new PermissionsResultAction() {
            @Override
            public void onGranted() {
                if (context instanceof BaseFrameActivity) {
                    Intent intent = Matisse.from((BaseFrameActivity) context)
                                           .choose(MimeType.ofImage(), true)
                                           .showSingleMediaType(true)
                                           .maxSelectable(1)
                                           .createDefaultIntent();
                    ((BaseFrameActivity) context).startActivityWithCallback(intent, resultListener);
                } else if (context instanceof BaseFrameFrag) {
                    Intent intent = Matisse.from(((BaseFrameFrag) context).getActivity())
                                           .choose(MimeType.ofImage(), true)
                                           .showSingleMediaType(true)
                                           .maxSelectable(1)
                                           .createDefaultIntent();
                    ((BaseFrameFrag) context).startActivityWithCallback(intent, resultListener);
                }
            }

            @Override
            public void onDenied(String permission) {
                ToastUtil.toastShort("请授予本程序读取SD卡和拍照权限");
            }
        });
    }

    /**
     * 选取多张图片
     *
     * @param context     只能为BaseFrameFrag或者BaseActivity的子类
     * @param photoNumber 选取的最大图片数量
     */
    public static void selectPhoto(final Object context, final int photoNumber, final ResultActivityHelper.ResultActivityListener resultListener) {
        String[] permissions;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            permissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        } else {
            permissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        }

        PermissionHelper.requestPermission(context, permissions, new PermissionsResultAction() {
            @Override
            public void onGranted() {
                if (context instanceof BaseFrameActivity) {
                    Intent intent = Matisse.from((BaseFrameActivity) context)
                                           .choose(MimeType.ofImage(), true)
                                           .showSingleMediaType(true)
                                           .maxSelectable(photoNumber)
                                           .createDefaultIntent();
                    ((BaseFrameActivity) context).startActivityWithCallback(intent, resultListener);
                } else if (context instanceof BaseFrameFrag) {
                    Intent intent = Matisse.from(((BaseFrameFrag) context).getActivity())
                                           .choose(MimeType.ofImage(), true)
                                           .showSingleMediaType(true)
                                           .maxSelectable(photoNumber)
                                           .createDefaultIntent();
                    ((BaseFrameFrag) context).startActivityWithCallback(intent, resultListener);
                }
            }

            @Override
            public void onDenied(String permission) {
                ToastUtil.toastShort("请授予本程序读取SD卡和拍照权限");
            }
        });
    }
}
