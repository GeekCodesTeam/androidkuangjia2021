package com.fosung.lighthouse.manage.index.widget

import android.content.Context
import android.text.TextUtils
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.slbappindex.my.bean.JobStateBean
import com.fosung.lighthouse.manage.index.R
import com.fosung.lighthouse.manage.index.common.IndexConstant
import com.fosung.lighthouse.manage.index.my.adapter.JobStateAdapter
import com.geek.libutils.data.MmkvUtils
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.core.BottomPopupView
import com.lxj.xpopup.interfaces.XPopupCallback

/**
 *@author: lhw
 *@date: 2021/8/2
 *@desc 切换工作状态
 */
class IndexJobStateDialog(context: Context) : BottomPopupView(context) {

    private var indexEditJobStateDialog: IndexEditJobStateDialog? = null
    private var mListView: RecyclerView? = null
    private var adapter: JobStateAdapter? = null

    override fun getImplLayoutId(): Int = R.layout.index_view_job_state

    override fun onCreate() {
        super.onCreate()
        findView()
    }

    private fun findView() {
        mListView = findViewById(R.id.rcy_job_state)

        indexEditJobStateDialog = XPopup.Builder(context)
            .enableDrag(true)
            .setPopupCallback(object : XPopupCallback {
                override fun onCreated(popupView: BasePopupView?) {

                }

                override fun beforeShow(popupView: BasePopupView?) {

                }

                override fun onShow(popupView: BasePopupView?) {

                }

                override fun onDismiss(popupView: BasePopupView?) {
                    val text = MmkvUtils.getInstance()
                        .get_xiancheng_string(IndexConstant.INDEX_MY_JOB_STATE_EDITEXT)
                    if (!TextUtils.isEmpty(text)) {
                        val bean = adapter?.getItem(adapter?.datas?.lastIndex!!)
                        bean?.content = text
                        adapter?.notifyDataSetChanged()
                    }
                }

                override fun beforeDismiss(popupView: BasePopupView?) {

                }

                override fun onBackPressed(popupView: BasePopupView?): Boolean {
                   return true
                }


                override fun onKeyBoardStateChanged(popupView: BasePopupView?, height: Int) {

                }

                override fun onDrag(
                    popupView: BasePopupView?,
                    value: Int,
                    percent: Float,
                    upOrLeft: Boolean
                ) {

                }
            })
            .asCustom(IndexEditJobStateDialog(context)) as IndexEditJobStateDialog?

        mListView?.layoutManager = GridLayoutManager(context, 3)
        adapter = JobStateAdapter(object : JobStateAdapter.OclickEditextInterface {
            override fun eidtext() {
                indexEditJobStateDialog?.show()
            }
        })
        mListView?.adapter = adapter

        adapter?.setDatas(initData())

    }

    private fun initData(): ArrayList<JobStateBean> {
        val datas = ArrayList<JobStateBean>()
        datas.add(JobStateBean(R.mipmap.index_my_qjz, R.mipmap.index_my_qjz_sel, "请假中", false))
        datas.add(JobStateBean(R.mipmap.index_my_ccz, R.mipmap.index_my_ccz_sel, "出差中", false))
        datas.add(JobStateBean(R.mipmap.index_my_hyz, R.mipmap.index_my_hyz_sel, "会议中", false))
        datas.add(JobStateBean(R.mipmap.index_my_wcz, R.mipmap.index_my_wcz_sel, "外出中", false))
        datas.add(JobStateBean(R.mipmap.index_my_zzz, R.mipmap.index_my_zzz_sel, "专注中", false))
        datas.add(JobStateBean(R.mipmap.index_my_xxz, R.mipmap.index_my_xxz_sel, "休息中", false))
        datas.add(JobStateBean(R.mipmap.index_my_jcz, R.mipmap.index_my_jcz_sel, "驾车中", false))
        datas.add(JobStateBean(R.mipmap.index_my_mlz, R.mipmap.index_my_mlz_sel, "忙碌中", false))
        datas.add(JobStateBean(R.mipmap.index_my_ydz, R.mipmap.index_my_ydz_sel, "运动中", false))
        datas.add(JobStateBean(R.mipmap.index_my_study, R.mipmap.index_my_study_sel, "学习中", false))
        datas.add(JobStateBean(R.mipmap.index_my_txz, R.mipmap.index_my_txz_sel, "调休中", false))
        datas.add(JobStateBean(R.mipmap.index_my_wu, R.mipmap.index_my_wu_sel, "无", false))
        var text = MmkvUtils.getInstance()
            .get_xiancheng_string(IndexConstant.INDEX_MY_JOB_STATE_EDITEXT)
        if (TextUtils.isEmpty(text)) {
            text = "自定义"
        }
        datas.add(JobStateBean(R.mipmap.index_my_zdy, R.mipmap.index_my_zdy_sel, text, false))
        return datas
    }
}