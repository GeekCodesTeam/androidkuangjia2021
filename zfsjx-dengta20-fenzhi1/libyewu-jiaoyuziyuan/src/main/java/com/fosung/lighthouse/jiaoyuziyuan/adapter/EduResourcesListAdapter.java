/*
 * *********************************************************
 *   author   colin
 *   company  fosung
 *   email    wanglin2046@126.com
 *   date     17-9-12 下午3:25
 * ********************************************************
 */

package com.fosung.lighthouse.jiaoyuziyuan.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.fosung.eduapi.bean.EduResourceListBean;
import com.fosung.lighthouse.jiaoyuziyuan.R;
import com.zcolin.frame.imageloader.ImageLoaderUtils;
import com.zcolin.gui.zrecyclerview.BaseRecyclerAdapter;


/**
 * 党员教育的网上班级的通知公告列表RecyclerView适配器
 */
public class EduResourcesListAdapter extends BaseRecyclerAdapter<EduResourceListBean.DatalistBean> {

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item_edu_resources_list;
    }

    @Override
    public void setUpData(final CommonHolder holder, int position, int viewType,
                          EduResourceListBean.DatalistBean data) {

        TextView tv_title = getView(holder,R.id.tv_title);
        tv_title.setText(data.getName());

        TextView tv_status = getView(holder,R.id.tv_status);
        tv_status.setText("审核节点：" + data.getProcessNodeName());

        TextView tv_time = getView(holder,R.id.tv_time);
        tv_time.setText("提报本级日期：" + data.getCreateTime());

        TextView tv_orgname = getView(holder,R.id.tv_orgname);
        tv_orgname.setText(data.getOrgName());

        ImageView iv_type = getView(holder,R.id.iv_type);
        if ("VIDEO".equals(data.getType())) {
            iv_type.setImageResource(R.mipmap.icon_edu_list_type_video);
        } else if ("AUDIO".equals(data.getType())) {
            iv_type.setImageResource(R.mipmap.icon_edu_list_type_audio);
        } else {
            iv_type.setImageResource(R.mipmap.icon_edu_list_type_text);
        }

        ImageView iv_head = getView(holder,R.id.iv_head);
//        ImageLoaderUtils.displayImage(iv_head.getContext(),data.get);

        TextView tv_no = getView(holder,R.id.tv_no);
        if ("TO_AUDIT".equals(data.getAuditStatus())) {
            tv_no.setBackgroundResource(R.mipmap.icon_edu_no_reviewed);
        } else {
            tv_no.setBackgroundResource(R.mipmap.icon_edu_yes_reviewed);
        }

    }

}