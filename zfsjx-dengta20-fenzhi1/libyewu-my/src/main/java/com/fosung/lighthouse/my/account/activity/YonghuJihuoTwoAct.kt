package com.fosung.lighthouse.my.account.activity

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
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.impl.LoadingPopupView
import com.zcolin.frame.app.BaseActivity
import com.zcolin.frame.util.ActivityUtil

/**
 *@author: lhw
 *@date: 2021/7/23
 *@desc 用户激活2
 */
class YonghuJihuoTwoAct : BaseActivity(),
    EduResAccountView<ResponseSlbBean1<*>> {

    private var edtPass: EditText? = null
    private var edtPassTwo: EditText? = null
    private var edtVpnPass: EditText? = null
    private var edtVpnPassTwo: EditText? = null
    private var btnOk: Button? = null

    private var accountPresenter: AccountPresenter? = null

    private var loadingPopup: LoadingPopupView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_two_yonghujihuoact)
        setToolbarTitle("用户激活")

        findview()
        setClick()
    }

    private fun findview() {
        edtPass = findViewById(R.id.edt_user_pas)
        edtPassTwo = findViewById(R.id.edt_user_pas_two)
        edtVpnPass = findViewById(R.id.edt_vpn_pas)
        edtVpnPassTwo = findViewById(R.id.edt_vpn_pas_two)
        btnOk = findViewById(R.id.btn_yhjh_next)

        accountPresenter = AccountPresenter(this)

        loadingPopup = XPopup.Builder(this)
            .dismissOnBackPressed(false)
            .asLoading("激活中") as LoadingPopupView

    }

    private fun setClick() {
        btnOk?.setOnClickListener(multiClick)
    }

    override fun onMultiClick(v: View?) {
        if (v?.id == R.id.btn_yhjh_next) {
            val pass = edtPass?.text.toString()
            val passTwo = edtPassTwo?.text.toString()
            val vpnPass = edtVpnPass?.text.toString()
            val vpnPassNew = edtVpnPassTwo?.text.toString()

            if (TextUtils.isEmpty(pass)) {
                ToastUtils.show("请输入管理员密码")
            } else if (TextUtils.isEmpty(passTwo)) {
                ToastUtils.show("请再次输入管理员密码")
            } else if (pass.trim() != passTwo.trim()) {
                ToastUtils.show("管理员密码不统一")
            } else if (TextUtils.isEmpty(vpnPass)) {
                ToastUtils.show("请输入VPN密码")
            } else if (TextUtils.isEmpty(vpnPassNew)) {
                ToastUtils.show("请再次输入VPN密码")
            } else if (vpnPass.trim() != vpnPassNew.trim()) {
                ToastUtils.show("VPN密码不统一")
            } else {
                val name = intent.getStringExtra("name")
                val idcard = intent.getStringExtra("idcard")
                val phone = intent.getStringExtra("phone")
                val verCode = intent.getStringExtra("verCode")
                val picVerCode = intent.getStringExtra("picVerCode")
                val authorizationCode = intent.getStringExtra("authorizationCode")
                loadingPopup?.show()
                accountPresenter?.accountActivition(
                    phone, picVerCode, idcard, name, authorizationCode, verCode, pass, vpnPass
                )
            }
        }
    }

    override fun OnEduResCommonSuccess(bean: ResponseSlbBean1<*>?) {
        ToastUtils.show("激活成功")
        ActivityUtil.finishActivity(YonghujihuoMainAct::class.java)
        finish()
    }

    override fun OnEduResCommonFail(msg: String?) {
        ToastUtils.show(msg)
    }


}