package com.fosung.lighthouse.my.account.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.fosung.lighthouse.my.R
import com.zcolin.frame.app.ActivityParam
import com.zcolin.frame.app.BaseActivity
import com.zcolin.frame.app.OnMultiClickListener

/**
 *@author: lhw
 *@date: 2021/7/26
 *@desc
 */

@ActivityParam(isShowToolBar = false)
class UserLoginAct : BaseActivity() {
    private var tv_activation: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_login)
        findView()
        onclick()
    }

    private fun findView() {
        tv_activation = getView(R.id.tv_activation)
    }

    private fun onclick() {
        tv_activation?.setOnClickListener(multiClick)
    }

    override fun onMultiClick(v: View?) {
        if (v?.id == R.id.tv_activation) {
            startActivity(Intent(UserLoginAct@ this, YonghujihuoMainAct::class.java))
        }
    }


}