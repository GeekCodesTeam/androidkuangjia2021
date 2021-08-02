package com.fosung.lighthouse.my.setting.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.fosung.lighthouse.my.R
import com.zcolin.frame.app.BaseActivity

/**
 *@author: lhw
 *@date: 2021/7/26
 *@desc
 */
class SettingAct : BaseActivity() {

    private var tv_personal_info: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_setting)
        setToolbarTitle(getString(R.string.title_setting))
        findView()
        onclick()
    }

    private fun findView() {
        tv_personal_info = getView(R.id.tv_personal_info);
    }

    private fun onclick() {
        tv_personal_info?.setOnClickListener(multiClick)
    }

    override fun onMultiClick(v: View?) {
        if (v?.id == R.id.tv_personal_info) {
            startActivity(Intent(SettingAct@ this, PersonalInfoAct::class.java))
        }
    }

}