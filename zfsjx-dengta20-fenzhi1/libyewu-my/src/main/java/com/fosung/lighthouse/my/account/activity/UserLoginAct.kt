package com.fosung.lighthouse.my.account.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.blankj.utilcode.util.AppUtils
import com.fosung.lighthouse.manage.common.utils.CommonModel
import com.fosung.lighthouse.manage.myapi.bean.UserLoginBean
import com.fosung.lighthouse.manage.myapi.presenter.AccountPresenter
import com.fosung.lighthouse.manage.myapi.view.EduResAccountTwoView
import com.fosung.lighthouse.manage.myapi.view.EduResAccountView
import com.fosung.lighthouse.my.R
import com.fosung.lighthouse.my.accountsecurity.activity.BindAccountAct
import com.hjq.toast.ToastUtils
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.impl.LoadingPopupView
import com.zcolin.frame.app.ActivityParam
import com.zcolin.frame.app.BaseActivity

/**
 *@author: lhw
 *@date: 2021/7/26
 *@desc 登录
 */

@ActivityParam(isShowToolBar = false)
class UserLoginAct : BaseActivity(), EduResAccountView<UserLoginBean>, EduResAccountTwoView {

    private var edt_idcard: EditText? = null
    private var edt_pass: EditText? = null
    private var tv_activation: TextView? = null
    private var btn_login: Button? = null

    private var mPresenter: AccountPresenter? = null
    private var loadingPopup: LoadingPopupView? = null

    private var vpnAccount = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_login)
        initIntentValue()
        findView()
        onclick()
        init()
    }

    private fun findView() {
        tv_activation = findViewById(R.id.tv_activation)
        edt_pass = findViewById(R.id.edt_pass)
        edt_idcard = findViewById(R.id.edt_idcard)
        btn_login = findViewById(R.id.btn_login)

        mPresenter = AccountPresenter(this,this)

        edt_idcard?.setText(CommonModel.get().getUserName())
        edt_pass?.setText(CommonModel.get().getUserPass())

        loadingPopup = XPopup.Builder(this)
            .dismissOnBackPressed(false)
            .asLoading("登录中...") as LoadingPopupView
    }

    private fun onclick() {
        tv_activation?.setOnClickListener(multiClick)
        btn_login?.setOnClickListener(multiClick)

    }

    private fun init() {

    }

    override fun onMultiClick(v: View?) {
        if (v?.id == R.id.tv_activation) {
            startActivity(Intent(UserLoginAct@ this, YonghujihuoMainAct::class.java))
        } else if (v?.id == R.id.btn_login) {
            val idcard = edt_idcard?.text.toString()
            val pass = edt_pass?.text.toString()
            if (TextUtils.isEmpty(idcard)) {
                ToastUtils.show("请输入身份证号")
            } else if (TextUtils.isEmpty(pass)) {
                ToastUtils.show("请输入密码")
            } else {
                loadingPopup?.show()
                mPresenter?.login(idcard, pass)
            }
        }
    }

    override fun OnEduResCommonSuccess(bean: UserLoginBean?) {
        loadingPopup?.dismiss()
        CommonModel.get().setToken(bean?.data!!.accessToken)
        CommonModel.get().setUsreInfo(bean.data)
        CommonModel.get().setUserName(edt_idcard?.text.toString())
        CommonModel.get().setUserPass(edt_pass?.text.toString())
        if (vpnAccount == edt_idcard?.text.toString()) {
            val checkVersionPop = XPopup.Builder(this)
                .isDestroyOnDismiss(true)
                .asConfirm(
                    "绑定账号", "绑定VPN账号与管理员账号后,移动端只需登录VPN即可同时登录管理员账号,是否绑定？",
                    "否", "立即绑定",
                    {
                        showProgressDialog("绑定中...")
                        mPresenter?.vpnBindAccount(
                            CommonModel.get().getVpnName(),
                            CommonModel.get().getVpnPass(),
                            CommonModel.get().getUserName(),
                            CommonModel.get().getUserPass()
                        )
                    },
                    {
                        startActivity(Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.ShouyeActivity"))
                        finish()

                    }, false
                )
            checkVersionPop?.show()
        } else {
            startActivity(Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.ShouyeActivity"))
        }

    }

    override fun OnEduResCommonFail(msg: String?) {
        loadingPopup?.dismiss()
        ToastUtils.show(msg)
    }

    private fun initIntentValue() {
        val appLinkIntent = intent
        if (appLinkIntent != null) {
            val appLinkAction = appLinkIntent.action
            if (appLinkAction != null) {
                val appLinkData = appLinkIntent.data
                if (appLinkData != null) {
                    vpnAccount = appLinkData.getQueryParameter("vpn_account").toString()
                }
            }
        }
    }

    override fun onEduResVpnUserInfoSuccess(bean: UserLoginBean?) {
        hideProgressDialog()
        CommonModel.get().setToken(bean?.data!!.accessToken)
        CommonModel.get().setUsreInfo(bean.data)
        CommonModel.get().setUserName(edt_idcard?.text.toString())
        CommonModel.get().setUserPass(edt_pass?.text.toString())
        startActivity(Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.ShouyeActivity"))
        finish()
    }

    override fun onEduResVpnUserInfoFail(msg: String?) {
        hideProgressDialog()
        ToastUtils.show(msg)
    }
}