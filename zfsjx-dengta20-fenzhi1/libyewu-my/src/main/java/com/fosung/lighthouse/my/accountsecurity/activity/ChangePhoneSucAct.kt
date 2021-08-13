package com.fosung.lighthouse.my.accountsecurity.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.fosung.lighthouse.my.R
import com.fosung.lighthouse.my.account.activity.UserLoginAct
import com.zcolin.frame.app.BaseActivity
import java.lang.ref.WeakReference
import android.os.CountDownTimer
import com.zcolin.frame.util.ActivityUtil


/**
 *@author: lhw
 *@date: 2021/7/29
 *@desc  修改手机号成功
 */
class ChangePhoneSucAct : BaseActivity() {

    private var tvTime: TextView? = null
    private var btnLogin: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_phone_success)
        setToolbarTitle(getString(R.string.title_change_phone))
        findView()
        setClick()
        init()
    }

    private fun findView() {
        tvTime = getView(R.id.tv_time)
        btnLogin = getView(R.id.btn_login)
    }

    private fun setClick() {
        btnLogin?.setOnClickListener(multiClick)
    }

    private fun init() {
        val timer: CountDownTimer = object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                tvTime?.text = millisUntilFinished.toString() + "s  后自动跳转到登录页"
            }

            override fun onFinish() {
                ActivityUtil.finishAllActivity()
                startActivity(Intent(this@ChangePhoneSucAct, UserLoginAct::class.java))
            }
        }
        timer.start()
    }

    override fun onMultiClick(v: View?) {
        if (v?.id == R.id.btn_login) {
            ActivityUtil.finishAllActivity()
            startActivity(Intent(this, UserLoginAct::class.java))
        }
    }

}