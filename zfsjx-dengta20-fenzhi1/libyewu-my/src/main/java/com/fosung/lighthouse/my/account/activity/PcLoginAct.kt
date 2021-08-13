package com.fosung.lighthouse.my.account.activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import com.blankj.utilcode.util.ToastUtils
import com.fosung.lighthouse.my.R
import com.zcolin.frame.app.BaseActivity

/**
 *@author: lhw
 *@date: 2021/8/4
 *@desc pc端登录
 */
class PcLoginAct : BaseActivity() {

    private var btnLogin: Button? = null
    private var btnCancel: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pc_login)
        setToolbarTitle(getString(R.string.title_pc_login))
        findView()
        setClick()
    }

    private fun findView() {
        btnLogin = getView(R.id.btn_login)
        btnCancel = getView(R.id.btn_cancel)

        // 方法2接收
//        val appLinkIntent = intent
//        if (appLinkIntent != null) {
//            val appLinkAction = appLinkIntent.action
//            if (appLinkAction != null) {
//                val appLinkData = appLinkIntent.data
//                if (appLinkData != null) {
//                    val aaaa1 = appLinkData.getQueryParameter("query1")
//                    val bbbb1 = appLinkData.getQueryParameter("query2")
//                    val cccc1 = appLinkData.getQueryParameter("query3")
//                    ToastUtils.showLong("aaaa1->$aaaa1,bbbb1->$bbbb1,cccc1->$cccc1")
//                }
//            }
//        }
    }


    private fun setClick() {
        btnLogin?.setOnClickListener(multiClick)
        btnCancel?.setOnClickListener(multiClick)
    }

    override fun onMultiClick(v: View?) {
        if(v?.id==R.id.btn_login){

        }else if(v?.id==R.id.btn_login){

        }
    }

}