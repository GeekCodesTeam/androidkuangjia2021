package com.fosung.lighthouse.manage.index.widget

import android.content.Context
import android.text.TextUtils
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import com.fosung.lighthouse.manage.index.R
import com.fosung.lighthouse.manage.index.common.IndexConstant
import com.geek.libutils.data.MmkvUtils
import com.lxj.xpopup.core.CenterPopupView

/**
 *@author: lhw
 *@date: 2021/8/3
 *@desc  自定义工作状态
 */
class IndexEditJobStateDialog(context: Context) : CenterPopupView(context) {

    private var edtContent: EditText? = null
    private var btnCancel: Button? = null
    private var btnOk: Button? = null

    override fun getImplLayoutId(): Int = R.layout.index_view_edit_jobstate

    override fun onCreate() {
        super.onCreate()
        findView()
        setOnclick()
    }

    private fun findView() {
        edtContent = findViewById(R.id.edt_content)
        btnCancel = findViewById(R.id.btn_cancel)
        btnOk = findViewById(R.id.btn_ok)

    }

    private fun setOnclick() {
        btnCancel!!.setOnClickListener(OnClickListener {
            dismiss()
        })

        btnOk!!.setOnClickListener(OnClickListener {
            val content = edtContent?.text.toString()
            if (TextUtils.isEmpty(content)) {
                edtContent?.error = "请输入状态"
                return@OnClickListener
            }

            MmkvUtils.getInstance().set_xiancheng(IndexConstant.INDEX_MY_JOB_STATE_EDITEXT, content)
            dismiss()
        })
    }


}