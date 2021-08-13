package com.fosung.lighthouse.manage.index.my.adapter

import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import com.example.slbappindex.my.bean.JobStateBean
import com.fosung.lighthouse.manage.index.R
import com.fosung.lighthouse.manage.index.common.IndexConstant
import com.geek.libutils.data.MmkvUtils
import com.zcolin.gui.zrecyclerview.BaseRecyclerAdapter

/**
 *@author: lhw
 *@date: 2021/8/2
 *@desc
 */
class JobStateAdapter constructor(onClick: OclickEditextInterface) :
    BaseRecyclerAdapter<JobStateBean>() {

    private var onClick: OclickEditextInterface? = null
    private var selIndex = -1

    init {
        this.onClick = onClick
    }

    override fun getItemLayoutId(viewType: Int): Int = R.layout.index_item_job_state

    override fun setUpData(
        holder: CommonHolder?,
        position: Int,
        viewType: Int,
        data: JobStateBean?
    ) {
        val llState: LinearLayout = getView(holder, R.id.ll_state)
        val ivState: ImageView = getView(holder, R.id.iv_state)
        val tvState: TextView = getView(holder, R.id.tv_state)

        tvState.text = data!!.content
        data!!.isSel = position == selIndex

        llState.isSelected = data!!.isSel
        if (data!!.isSel) {
            ivState.setImageResource(data!!.normal_imageUrl)
        } else {
            ivState.setImageResource(data!!.active_imageUrl)
        }

        holder!!.itemView.setOnClickListener(View.OnClickListener {
            val text = MmkvUtils.getInstance()
                .get_xiancheng_string(IndexConstant.INDEX_MY_JOB_STATE_EDITEXT)
            if (TextUtils.isEmpty(text) && position >= datas.size - 1) {
                onClick?.eidtext()
            } else {
                data!!.isSel = true
                selIndex = position
                notifyDataSetChanged()
            }

        })
    }

    interface OclickEditextInterface {
        fun eidtext()
    }

}