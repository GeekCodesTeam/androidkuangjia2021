package com.fosung.lighthouse.my.account.activity

import android.os.Bundle
import com.blankj.utilcode.util.ToastUtils
import com.fosung.lighthouse.my.R
import com.zcolin.frame.app.BaseActivity

/**
 *@author: lhw
 *@date: 2021/7/23
 *@desc 用户激活2
 */
class YonghuJihuoTwoAct : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_two_yonghujihuoact)
        findview()
        onclick()
        donetwork()
    }

    private fun findview() {
        setToolbarTitle("用户激活")

        // 方法2接收
        val appLinkIntent = intent
        if (appLinkIntent != null) {
            val appLinkAction = appLinkIntent.action
            if (appLinkAction != null) {
                val appLinkData = appLinkIntent.data
                if (appLinkData != null) {
                    val aaaa1 = appLinkData.getQueryParameter("query1")
                    val bbbb1 = appLinkData.getQueryParameter("query2")
                    val cccc1 = appLinkData.getQueryParameter("query3")
                    ToastUtils.showLong("aaaa1->$aaaa1,bbbb1->$bbbb1,cccc1->$cccc1")
                }
            }
        }
    }

    private fun onclick() {

    }

    private fun donetwork() {

    }

}