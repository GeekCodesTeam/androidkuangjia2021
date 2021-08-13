/*
 * *********************************************************
 *   author   colin
 *   company  fosung
 *   email    wanglin2046@126.com
 *   date     17-9-12 下午3:25
 * ********************************************************
 */

package com.fosung.lighthouse.jiaoyuziyuan.adapter;

import android.widget.TextView;

import com.fosung.eduapi.bean.EduResAuditRecordBean;
import com.fosung.lighthouse.jiaoyuziyuan.R;
import com.zcolin.gui.zrecyclerview.BaseRecyclerAdapter;


/**
 * 党员教育的网上班级的通知公告列表RecyclerView适配器
 */
public class EduDetailRecordAdapter extends BaseRecyclerAdapter<EduResAuditRecordBean.DatalistBean> {

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item_edu_detail_sh_record;
    }

    @Override
    public void setUpData(final CommonHolder holder, int position, int viewType,
                          EduResAuditRecordBean.DatalistBean data) {
        TextView tv_org_name = getView(holder, R.id.tv_org_name);
        tv_org_name.setText(data.getOrgName());
        TextView tv_user_name = getView(holder, R.id.tv_user_name);
        tv_user_name.setText(data.getUserName());
        TextView tv_operation = getView(holder, R.id.tv_operation);
        tv_operation.setText(data.getOperate());
        TextView tv_suggest = getView(holder, R.id.tv_suggest);
        tv_suggest.setText(data.getSuggestion());
        TextView tv_pj = getView(holder, R.id.tv_pj);
        tv_pj.setText(data.getResourceGrade());
        TextView tv_time = getView(holder, R.id.tv_time);
        tv_time.setText(data.getAuditTime());
    }

}