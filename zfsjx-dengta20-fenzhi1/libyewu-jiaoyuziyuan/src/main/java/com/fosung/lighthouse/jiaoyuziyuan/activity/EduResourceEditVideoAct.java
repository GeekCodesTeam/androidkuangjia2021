package com.fosung.lighthouse.jiaoyuziyuan.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fosung.lighthouse.jiaoyuziyuan.R;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.app.PictureAppMaster;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.entity.MediaExtraInfo;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.luck.picture.lib.tools.MediaUtils;
import com.luck.picture.lib.tools.ScreenUtils;
import com.luck.picture.lib.tools.SdkVersionUtils;
import com.luck.pictureselector.FullyGridLayoutManager;
import com.luck.pictureselector.GlideCacheEngine;
import com.luck.pictureselector.GlideEngine;
import com.luck.pictureselector.PictureSelectorAct;
import com.luck.pictureselector.adapter.GridImageAdapter;
import com.zcolin.frame.app.ActivityParam;
import com.zcolin.frame.app.BaseActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fosung
 */
@ActivityParam(isImmerse = false)
public class EduResourceEditVideoAct extends BaseActivity implements View.OnClickListener {

    public static final int IMAGE_ITEM_ADD = -1;
    private GridImageAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edu_resources_edit_video);
        setToolbarTitle("编辑");
        initView();
        initWidget(savedInstanceState);
    }

    private void initView() {

    }

    private void initWidget(Bundle savedInstanceState) {
        RecyclerView mRecyclerView = getView(R.id.rl_image);
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this,
                4, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);

        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(4,
                ScreenUtils.dip2px(this, 8), false));
        mAdapter = new GridImageAdapter(this, onAddPicClickListener);
        if (savedInstanceState != null && savedInstanceState.getParcelableArrayList("selectorList"
        ) != null) {
            mAdapter.setList(savedInstanceState.getParcelableArrayList("selectorList"));
        }

        int maxSelectNum = 1;
        mAdapter.setSelectMax(maxSelectNum);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener((v, position) -> {
            List<LocalMedia> selectList = mAdapter.getData();
            if (selectList.size() > 0) {
                PictureSelector.create(EduResourceEditVideoAct.this)
                        .themeStyle(com.luck.pictureselector.R.style.picture_default_style) // xml设置主题
                        .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)// 设置相册Activity方向，不设置默认使用系统
                        .isNotPreviewDownload(true)// 预览图片长按是否可以下载
                        //.bindCustomPlayVideoCallback(new MyVideoSelectedPlayCallback(getContext()))// 自定义播放回调控制，用户可以使用自己的视频播放界面
                        .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                        .openExternalPreview(position, selectList);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    private final GridImageAdapter.onAddPicClickListener onAddPicClickListener =
            new GridImageAdapter.onAddPicClickListener() {
                @Override
                public void onAddPicClick() {
                    // 进入相册 以下是例子：不需要的api可以不写
                    PictureSelector.create(EduResourceEditVideoAct.this)
                            .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()
                            // 、图片.ofImage()、视频.ofVideo
                            // ()、音频.ofAudio()
                            .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                            .isMaxSelectEnabledMask(true)// 选择数到了最大阀值列表是否启用蒙层效果
                            .imageSpanCount(4)// 每行显示个数
                            .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
                            .isSingleDirectReturn(true)// 单选模式下是否直接返回，PictureConfig
                            .forResult(new MyResultCallback(mAdapter));
                }
            };

    /**
     * 返回结果回调
     */
    private static class MyResultCallback implements OnResultCallbackListener<LocalMedia> {
        private WeakReference<GridImageAdapter> mAdapterWeakReference;

        public MyResultCallback(GridImageAdapter adapter) {
            super();
            this.mAdapterWeakReference = new WeakReference<>(adapter);
        }

        @Override
        public void onResult(List<LocalMedia> result) {
            for (LocalMedia media : result) {
                if (media.getWidth() == 0 || media.getHeight() == 0) {
                    if (PictureMimeType.isHasImage(media.getMimeType())) {
                        MediaExtraInfo imageExtraInfo = MediaUtils.getImageSize(media.getPath());
                        media.setWidth(imageExtraInfo.getWidth());
                        media.setHeight(imageExtraInfo.getHeight());
                    } else if (PictureMimeType.isHasVideo(media.getMimeType())) {
                        MediaExtraInfo videoExtraInfo = MediaUtils.getVideoSize(PictureAppMaster.getInstance().getAppContext(), media.getPath());
                        media.setWidth(videoExtraInfo.getWidth());
                        media.setHeight(videoExtraInfo.getHeight());
                    }
                }
            }
            if (mAdapterWeakReference.get() != null) {
                mAdapterWeakReference.get().setList(result);
                mAdapterWeakReference.get().notifyDataSetChanged();
            }
        }

        @Override
        public void onCancel() {
        }
    }
}