package com.fosung.lighthouse.jiaoyuziyuan.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fosung.eduapi.bean.EduResourceDetailBean;
import com.fosung.lighthouse.jiaoyuziyuan.R;
import com.zcolin.frame.http.ZHttp;
import com.zcolin.frame.http.response.ZFileResponse;
import com.zcolin.frame.permission.PermissionHelper;
import com.zcolin.frame.permission.PermissionsResultAction;
import com.zcolin.frame.util.ActivityUtil;
import com.zcolin.frame.util.FileOpenUtil;
import com.zcolin.frame.util.SDCardUtil;
import com.zcolin.frame.util.ToastUtil;
import com.zcolin.gui.zrecyclerview.BaseRecyclerAdapter;

import java.io.File;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

public class EduResFileAdapter extends BaseRecyclerAdapter<EduResourceDetailBean.DataBean.AttachmentBean> {

    private Activity activity;

    public EduResFileAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item_edu_detail_file;
    }


    @Override
    public void setUpData(CommonHolder holder, int position, int viewType, EduResourceDetailBean.DataBean.AttachmentBean data) {
        ImageView ivIcon = getView(holder, R.id.iv_icon);
        TextView tvName = getView(holder, R.id.tv_name);
        TextView tvPrview = getView(holder, R.id.tv_prview);
        if (!TextUtils.isEmpty(data.getUrl())) {
            if (data.getUrl().contains(".png")) {
                ivIcon.setImageResource(R.mipmap.appoint_png);
            } else if (data.getUrl().contains(".jp") || data.getUrl().contains(".JP")) {
                ivIcon.setImageResource(R.mipmap.appoint_jpg);
            } else if (data.getUrl().contains(".xls")) {
                ivIcon.setImageResource(R.mipmap.appoint_excel);
            } else if (data.getUrl().contains(".doc")) {
                ivIcon.setImageResource(R.mipmap.appoint_world);
            } else if (data.getUrl().contains(".ppt")) {
                ivIcon.setImageResource(R.mipmap.appoint_pdf);
            } else if (data.getUrl().contains(".pdf")) {
                ivIcon.setImageResource(R.mipmap.appoint_ppt);
            } else {
                ivIcon.setImageResource(R.mipmap.appoint_world);
            }
            tvName.setText(data.getName());
        } else {
            ivIcon.setImageResource(R.mipmap.appoint_world);
        }

        tvPrview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fileIsExists(AppPath() + data.getName())) {
                    FileOpenUtil.openFile(view.getContext(), new File(AppPath() + data.getName()));
                } else {
                    downloadStudyMaterial(view.getContext(), data.getUrl(), data.getName());
                }
            }
        });
    }

    private void downloadStudyMaterial(Context context, String url, String fileName) {

        PermissionHelper.requestReadWriteSdCardPermission(activity, new PermissionsResultAction() {
            @Override
            public void onGranted() {

                if (fileName != null) {
                    ZHttp.downLoadFile(url, new ZFileResponse(AppPath() + fileName, activity, "正在下载……") {
                        @Override
                        public void onError(int code, Call call, Exception e) {
                            ToastUtil.toastShort("下载失败！");
                        }

                        @Override
                        public void onSuccess(Response response, File resObj) {
                            FileOpenUtil.openFile(context, resObj);
                        }

                        @Override
                        public void onProgress(float progress, long total) {
                            super.onProgress(progress, total);
                            setBarMsg("正在下载……" + (int) (progress * 100) + "/" + 100);
                        }
                    });
                } else {
                    ToastUtil.toastShort("下载URL不合法");
                }
            }

            @Override
            public void onDenied(String permission) {

            }
        });
    }

    private boolean fileIsExists(String strFile) {
        try {
            File f = new File(strFile);
            if(!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private String AppPath(){
        boolean sdCardExist = SDCardUtil.isSDCardEnable();
        String SDCARD = null;
        if (sdCardExist) {
            SDCARD = SDCardUtil.getSDCardPath() + File.separator;// 获取SD卡根目录
        } else {
            SDCARD = SDCardUtil.getRootDirectoryPath() + File.separator;// 获取根目录
        }

        return SDCARD + "ziyuanku/";
    }

}
