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
import com.fosung.lighthouse.my.account.activity.UserLoginAct
import com.haier.cellarette.libretrofit.common.ResponseSlbBean1
import com.hjq.toast.ToastUtils
import com.zcolin.frame.app.BaseActivity
import com.zcolin.frame.util.ActivityUtil

/**
 *@author: lhw
 *@date: 2021/7/29
 *@desc   修改管理员密码
 */
class ChangePassAct : BaseActivity(), EduResAccountView<ResponseSlbBean1<*>> {

    private var edtPass: EditText? = null
    private var edtNewPass: EditText? = null
    private var edtNewPassTwo: EditText? = null
    private var btnChange: Button? = null

    private var mPresenter: AccountPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_pass)
        setToolbarTitle(getString(R.string.title_change_pass))
        findView()
        setClick()
        init()
    }

    private fun findView() {
        edtPass = getView(R.id.edt_pass)
        edtNewPass = getView(R.id.edt_newpass)
        edtNewPassTwo = getView(R.id.edt_newpass_two)
        btnChange = findViewById(R.id.btn_change)
    }

    private fun setClick() {
        btnChange?.setOnClickListener(multiClick)
    }

    private fun init() {
        mPresenter = AccountPresenter(this)
    }

    override fun onMultiClick(v: View?) {
        if (v?.id == R.id.btn_change) {
            val pass = edtPass?.text.toString()
            val newPass = edtNewPass?.text.toString()
            val newPassTwo = edtNewPassTwo?.text.toString()
            if (TextUtils.isEmpty(pass)) {
                ToastUtils.show("请输入原密码")
            } else if (TextUtils.isEmpty(newPass)) {
                ToastUtils.show("请输入新密码")
            } else if (TextUtils.isEmpty(newPassTwo)) {
                ToastUtils.show("请输入确认新密码")
            } else if (newPass != newPassTwo) {
                ToastUtils.show("新密码不一致")
            } else {
                showProgressDialog("修改中...")
                mPresenter?.changePass(newPass, newPassTwo, pass)
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