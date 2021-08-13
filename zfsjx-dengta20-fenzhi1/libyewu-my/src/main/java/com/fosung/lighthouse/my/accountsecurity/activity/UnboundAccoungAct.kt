package com.fosung.lighthouse.my.accountsecurity.activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.fosung.lighthouse.my.R
import com.zcolin.frame.app.BaseActivity

/**
 *@author: lhw
 *@date: 2021/7/30
 *@desc 解绑账号
 */
class UnboundAccoungAct : BaseActivity() {
    private var btnNext: Button? = null
    private var edtIdcard: EditText? = null
    private var edtIdPass: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unbond_account)
        setToolbarTitle(getString(R.string.title_unbind_account))
        findView()
    }

    private fun findView() {
        btnNext = getView(R.id.btn_next)
        edtIdcard = getView(R.id.edt_idcard)
        edtIdPass = getView(R.id.edt_pass)
    }

    private fun onClick() {
        btnNext?.setOnClickListener(multiClick)
    }

    override fun onMultiClick(v: View?) {
        if (v?.id == R.id.btn_next) {

        }
    }

}