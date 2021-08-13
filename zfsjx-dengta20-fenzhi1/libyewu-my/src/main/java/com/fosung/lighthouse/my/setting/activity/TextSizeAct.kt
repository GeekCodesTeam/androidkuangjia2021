package com.fosung.lighthouse.my.setting.activity

import android.os.Bundle
import android.widget.TextView
import com.fosung.lighthouse.my.R
import com.zcolin.frame.app.BaseActivity

/**
 *@author: lhw
 *@date: 2021/7/28
 *@desc  正文字号
 */
class TextSizeAct : BaseActivity() {
    private var tvExtremely: TextView? = null
    private var tvBig: TextView? = null
    private var tvStandard: TextView? = null
    private var tvSmall: TextView? = null
    private var tvMinimum: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_size)
        setToolbarTitle(getString(R.string.title_text_size))
        findView()
    }

    private fun findView() {
        tvExtremely = getView(R.id.tv_extremely)
        tvBig = getView(R.id.tv_extremely)
        tvStandard = getView(R.id.tv_extremely)
        tvSmall = getView(R.id.tv_extremely)
        tvMinimum = getView(R.id.tv_extremely)
    }

}