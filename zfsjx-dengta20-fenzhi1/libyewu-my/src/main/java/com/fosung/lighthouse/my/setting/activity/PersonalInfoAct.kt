package com.fosung.lighthouse.my.setting.activity

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.fosung.lighthouse.my.R
import com.geek.libglide47.base.CircleImageView
import com.zcolin.frame.app.BaseActivity

/**
 *@author: lhw
 *@date: 2021/7/27
 *@desc 个人信息
 */
class PersonalInfoAct : BaseActivity() {

    private var ivHead: CircleImageView? = null
    private var tvName: TextView? = null
    private var tvPhone: TextView? = null
    private var tvState: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_info)
        setToolbarTitle(getString(R.string.title_personal_info))
        findView()
        setclick()
        init()
    }

    private fun findView() {
        ivHead = getView(R.id.iv_head)
        tvName = getView(R.id.tv_name)
        tvPhone = getView(R.id.tv_phone)
        tvState = getView(R.id.tv_state)
    }

    private fun setclick() {
        ivHead?.setOnClickListener(multiClick)
    }

    private fun init() {

    }

    override fun onMultiClick(v: View?) {
        if (v?.id == R.id.iv_head) {

        }
    }

}