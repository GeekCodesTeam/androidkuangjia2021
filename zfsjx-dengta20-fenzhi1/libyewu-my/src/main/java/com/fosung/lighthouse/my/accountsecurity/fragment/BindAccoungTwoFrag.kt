package com.fosung.lighthouse.my.accountsecurity.fragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.NavHostFragment
import com.fosung.lighthouse.manage.myapi.bean.UserLoginBean
import com.fosung.lighthouse.manage.myapi.presenter.AccountPresenter
import com.fosung.lighthouse.manage.myapi.view.EduResAccountTwoView
import com.geek.libbase.base.SlbBaseFragment
import com.fosung.lighthouse.my.R
import com.hjq.toast.ToastUtils
import com.zcolin.frame.app.BaseFragment
import com.zcolin.frame.app.BaseFrameFrag
import com.zcolin.frame.app.OnMultiClickListener

/**
 *@author: lhw
 *@date: 2021/7/30
 *@desc
 */
class BindAccoungTwoFrag : BaseFragment(), EduResAccountTwoView {
    private var btnNext: Button? = null
    private var edtIdcard: EditText? = null
    private var edtIdPass: EditText? = null
    private val onMultiClick = OnMultiClick()

    private var mAccountPresenter: AccountPresenter? = null

    override fun getRootViewLayId(): Int {
        return R.layout.fragment_bind_account_two
    }

    override fun createView(savedInstanceState: Bundle?) {
        super.createView(savedInstanceState)
        findView()
        onClick()
        init()
    }

    private fun findView() {
        btnNext = getView(R.id.btn_next)
        edtIdcard = getView(R.id.edt_idcard)
        edtIdPass = getView(R.id.edt_pass)
    }

    private fun onClick() {
        btnNext?.setOnClickListener(onMultiClick)
    }

    private fun init() {
        mAccountPresenter = AccountPresenter()
        mAccountPresenter?.setViewTwo(this)
    }


    inner class OnMultiClick : OnMultiClickListener() {
        override fun onMultiClick(v: View?) {
            if (v?.id == R.id.btn_next) {

            }
        }
    }

    override fun onEduResVpnUserInfoSuccess(bean: UserLoginBean?) {
        hideProgressDialog()
        ToastUtils.show("绑定成功")
    }

    override fun onEduResVpnUserInfoFail(msg: String?) {
        ToastUtils.show(msg)
    }


}