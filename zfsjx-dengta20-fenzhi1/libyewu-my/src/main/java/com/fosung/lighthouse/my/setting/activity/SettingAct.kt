package com.fosung.lighthouse.my.setting.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import com.blankj.utilcode.util.ToastUtils
import com.fosung.lighthouse.manage.myapi.presenter.AccountPresenter
import com.fosung.lighthouse.manage.myapi.view.EduResLogouttView
import com.fosung.lighthouse.my.R
import com.fosung.lighthouse.my.common.MyJump
import com.haier.cellarette.baselibrary.cacheutil.CacheUtil
import com.haier.cellarette.libretrofit.common.ResponseSlbBean1
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.enums.PopupAnimation
import com.lxj.xpopup.interfaces.OnSelectListener
import com.zcolin.frame.app.BaseActivity


/**
 *@author: lhw
 *@date: 2021/7/26
 *@desc 设置
 */
class SettingAct : BaseActivity(), EduResLogouttView {

    private var tv_personal_info: TextView? = null
    private var ll_text_size: LinearLayoutCompat? = null
    private var ll_clear_cancel: LinearLayoutCompat? = null
    private var tv_check_version: TextView? = null
    private var ll_remind: LinearLayoutCompat? = null
    private var btnLogout: TextView? = null
    private var tvCancle: TextView? = null

    private var mPresenter: AccountPresenter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_setting)
        setToolbarTitle(getString(R.string.title_setting))
        findView()
        onclick()
        init()
    }

    private fun findView() {
        tv_personal_info = getView(R.id.tv_personal_info)
        ll_text_size = getView(R.id.ll_text_size)
        ll_clear_cancel = getView(R.id.ll_clear_cancel)
        tv_check_version = getView(R.id.tv_check_version)
        ll_remind = getView(R.id.ll_remind)
        btnLogout = findViewById(R.id.btn_logout)
        tvCancle = findViewById(R.id.tv_cancle)
    }

    private fun onclick() {
        tv_personal_info?.setOnClickListener(multiClick)
        ll_text_size?.setOnClickListener(multiClick)
        ll_clear_cancel?.setOnClickListener(multiClick)
        tv_check_version?.setOnClickListener(multiClick)
        ll_remind?.setOnClickListener(multiClick)
        btnLogout?.setOnClickListener(multiClick)

    }

    private fun init() {
        tvCancle?.text = CacheUtil.getTotalCacheSize(this)

        mPresenter = AccountPresenter()
        mPresenter?.setViewLogout(this)
    }

    override fun onMultiClick(v: View?) {
        if (v?.id == R.id.tv_personal_info) {
            startActivity(Intent(SettingAct@ this, PersonalInfoAct::class.java))
        } else if (v?.id == R.id.ll_text_size) {
            startActivity(Intent(SettingAct@ this, TextSizeAct::class.java))
        } else if (v?.id == R.id.ll_clear_cancel) {
            clearCancel()
        } else if (v?.id == R.id.tv_check_version) {
            showCheckVersionDialog()
        } else if (v?.id == R.id.ll_remind) {
            showRemindDialog()
        } else if (v?.id == R.id.btn_logout) {
            val clearPopupView = XPopup.Builder(this)
                .isDestroyOnDismiss(true)
                .asConfirm(
                    "", "是否退出？", "取消", "退出",
                    {
                        showProgressDialog("注销中...")
                        mPresenter?.logout()
                    }, null, false
                )
//        }
            clearPopupView?.show()
        }
    }

    private fun clearCancel() {
//        if (clearPopupView == null) {
        val clearPopupView = XPopup.Builder(this)
            //.hasNavigationBar(false)
            .isDestroyOnDismiss(true)
            //                        .navigationBarColor(Color.BLUE)
            //                        .hasBlurBg(true)
            //                        .dismissOnTouchOutside(false)
            //                        .autoDismiss(false)
            .popupAnimation(PopupAnimation.ScaleAlphaFromCenter)
            //                        .isLightStatusBar(true)
            //                        .setPopupCallback(new DemoXPopupListener())
            //                        .asCustom(new LoginPopup(getContext()));
            .asConfirm(
                "清除缓存", "是否清除缓存？",
                "取消", "确定",
                {
                    CacheUtil.clearAllCache(this)
                    tvCancle?.text = CacheUtil.getTotalCacheSize(this)
                }, null, false
            )
//        }
        clearPopupView?.show()
    }

    private fun showCheckVersionDialog() {
        val checkVersionPop = XPopup.Builder(this)
            .isDestroyOnDismiss(true)
            .asConfirm(
                "版本更新", "发现新版本可升级，是否立即更新？",
                "否", "立即更新",
                { ToastUtils.showShort("click confirm") }, null, false
            )
        checkVersionPop?.show()
    }

    private fun showRemindDialog() {
        XPopup.Builder(this)
            .isDarkTheme(false)
            .hasShadowBg(true) //
            //.hasBlurBg(true)
            //.isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
            .asBottomList(
                null, arrayOf("不提醒", "每天只提醒一次", "每次提醒")
            ) { position, text ->

            }.show()
    }

    override fun onEduResLogoutSuccess(bean: ResponseSlbBean1<*>?) {
        hideProgressDialog()
        MyJump.userLogOut(this)
    }

    override fun OnEduResLogoutFail(msg: String?) {
        hideProgressDialog()
        ToastUtils.showLong(msg)
    }

}