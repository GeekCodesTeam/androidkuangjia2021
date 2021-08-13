/*
 * *********************************************************
 *   author   colin
 *   company  fosung
 *   email    wanglin2046@126.com
 *   date     17-9-12 下午3:25
 * ********************************************************
 */

package com.fosung.lighthouse.jiaoyuziyuan.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fosung.eduapi.bean.EduResInfoBean;
import com.fosung.eduapi.bean.EduResourceDetailBean;
import com.fosung.lighthouse.jiaoyuziyuan.R;
import com.fosung.lighthouse.jiaoyuziyuan.fragment.EduDetailInfoFragment;
import com.zcolin.frame.app.BaseActivity;
import com.zcolin.frame.imageloader.ImageLoaderUtils;
import com.zcolin.gui.zrecyclerview.BaseRecyclerAdapter;

import java.util.List;
import java.util.Map;


/**
 */
public class EduDetailInfoAdapter extends BaseRecyclerAdapter<EduResInfoBean.DatalistBean> {

    private Activity activity;

    public EduDetailInfoAdapter(Activity activity) {
        this.activity = activity;
    }

    private List<EduResourceDetailBean.DataBean.AttachmentBean> fileList;
    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item_edu_detail_info;
    }

    public void setFileData(List<EduResourceDetailBean.DataBean.AttachmentBean> fileList) {
        this.fileList = fileList;
    }

    @Override
    public void setUpData(final CommonHolder holder, int position, int viewType,
                          EduResInfoBean.DatalistBean data) {
        TextView tv_name = getView(holder, R.id.tv_name);
        TextView tv_content = getView(holder, R.id.tv_content);
        ImageView iv_cover = getView(holder, R.id.iv_cover);
        RecyclerView recyclerView = getView(holder, R.id.recycler);
        tv_name.setText(data.getLabel());
        if ("v-file".equals(data.getType())) {

            tv_content.setVisibility(View.GONE);
            iv_cover.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            EduResFileAdapter adapter = new EduResFileAdapter(activity);
            LinearLayoutManager inearLayoutManager = new LinearLayoutManager(holder.itemView.getContext(), LinearLayoutManager.VERTICAL, false);
            inearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(inearLayoutManager);

            recyclerView.setAdapter(adapter);
            adapter.setDatas(fileList);

        } else if ("v-uploadImage".equals(data.getType())) {

            tv_content.setVisibility(View.GONE);
            iv_cover.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            ImageLoaderUtils.displayImage(activity,data.getValue(),iv_cover);

        } else {

            tv_content.setVisibility(View.VISIBLE);
            iv_cover.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            tv_content.setText(data.getValue());

        }
    }
}