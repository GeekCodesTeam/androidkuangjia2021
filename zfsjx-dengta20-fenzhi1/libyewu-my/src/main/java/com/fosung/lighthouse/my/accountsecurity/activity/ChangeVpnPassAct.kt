package com.fosung.lighthouse.my.accountsecurity.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.fosung.lighthouse.manage.myapi.presenter.AccountPresenter
import com.fosung.lighthouse.manage.myapi.view.EduResAccountView
import com.fosung.lighthouse.my.R
import com.haier.cellarette.libretrofit.common.ResponseSlbBean1
import com.hjq.toast.ToastUtils
import com.zcolin.frame.app.BaseActivity
import com.zcolin.frame.util.ActivityUtil

/**
 *@author: lhw
 *@date: 2021/7/29
 *@desc 修改vpn密码
 */
class ChangeVpnPassAct : BaseActivity(), EduResAccountView<ResponseSlbBean1<*>> {

    private var edtVpnAccount: EditText? = null
    private var edtPass: EditText? = null
    private var edtNewPass: EditText? = null
    private var btnChange: Button? = null

    private var mPresenter: AccountPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_vpnpass)
        setToolbarTitle(getString(R.string.title_change_vpn_pass))
        findView()
        onClick()
        init()
    }

    private fun findView() {
        edtVpnAccount = getView(R.id.edt_vpn_account)
        edtPass = getView(R.id.edt_vpn_account)
        edtNewPass = getView(R.id.edt_vpn_account)
        btnChange = getView(R.id.btn_change)
    }

    private fun onClick() {
        btnChange?.setOnClickListener(multiClick)
    }

    private fun init() {
        mPresenter = AccountPresenter(this)
    }

    override fun onMultiClick(v: View?) {
        if (v?.id == R.id.btn_change) {
            val pass = edtPass?.text.toString()
            val rPass = edtNewPass?.text.toString()
            val vpn = edtVpnAccount?.text.toString()
            if (TextUtils.isEmpty(vpn)) {
                ToastUtils.show("请输入VPN账号")
            } else if (TextUtils.isEmpty(pass)) {
                ToastUtils.show("请输入密码")
            } else if (TextUtils.isEmpty(rPass)) {
                ToastUtils.show("请输入确认密码")
            } else if (pass != rPass) {
                ToastUtils.show("两次密码不一致")
            } else {
                showProgressDialog("修改中...")
                mPresenter?.changeVpnPass(pass, rPass, vpn)
            }
        }
    }

    override fun OnEduResCommonSuccess(bean: ResponseSlbBean1<*>?) {
        hideProgressDialog()
        ActivityUtil.finishAllActivity()
        startActivity(Intent(this, ChangePhoneSucAct::class.java))
    }

    override fun OnEduResCommonFail(msg: String?) {
        hideProgressDialog()
        ToastUtils.show(msg)
    }

}