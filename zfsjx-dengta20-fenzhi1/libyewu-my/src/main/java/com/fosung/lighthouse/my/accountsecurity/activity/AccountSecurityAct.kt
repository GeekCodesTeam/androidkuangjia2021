package com.fosung.lighthouse.my.accountsecurity.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.fosung.lighthouse.my.R
import com.hjq.toast.ToastUtils
import com.zcolin.frame.app.BaseActivity
import com.zcolin.frame.permission.PermissionHelper
import com.zcolin.frame.permission.PermissionsResultAction

/**
 *@author: lhw
 *@date: 2021/7/28
 *@desc  账号安全
 */
class AccountSecurityAct : BaseActivity() {
    private var tvChangePone: TextView? = null
    private var tvChangePass: TextView? = null
    private var tvChangeVpnPass: TextView? = null
    private var tvBindAccount: TextView? = null
    private var tvLoginJournal: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_security)
        setToolbarTitle(getString(R.string.title_account_security))
        findView()
        onClick()
    }

    private fun findView() {
        tvChangePone = getView(R.id.tv_change_phone)
        tvChangePass = getView(R.id.tv_change_pass)
        tvChangeVpnPass = getView(R.id.tv_change_vpn_pass)
        tvBindAccount = getView(R.id.tv_bind_account)
        tvLoginJournal = getView(R.id.tv_login_journal)
    }

    private fun onClick() {
        tvChangePone?.setOnClickListener(multiClick)
        tvChangePass?.setOnClickListener(multiClick)
        tvChangeVpnPass?.setOnClickListener(multiClick)
        tvBindAccount?.setOnClickListener(multiClick)
        tvLoginJournal?.setOnClickListener(multiClick)
    }

    override fun onMultiClick(v: View?) {
        if (v?.id == R.id.tv_change_phone) {
            startActivity(Intent(this, ChangePhoneAct::class.java))
        } else if (v?.id == R.id.tv_change_pass) {
            startActivity(Intent(this, ChangePassAct::class.java))
        } else if (v?.id == R.id.tv_change_vpn_pass) {
            startActivity(Intent(this, ChangeVpnPassAct::class.java))
        } else if (v?.id == R.id.tv_bind_account) {
            startActivity(Intent(this, BindAccountAct::class.java))
//          startActivity(Intent(this, UnboundAccoungAct::class.java))
        } else if (v?.id == R.id.tv_login_journal) {
            startActivity(Intent(this, LoginLogAct::class.java))
        }
    }
}