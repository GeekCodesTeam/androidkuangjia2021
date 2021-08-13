package com.fosung.lighthouse.my.accountsecurity.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.fosung.lighthouse.manage.common.utils.CommonModel
import com.fosung.lighthouse.manage.myapi.bean.PicVerCodeBean
import com.fosung.lighthouse.manage.myapi.common.MyApiConstant
import com.fosung.lighthouse.manage.myapi.presenter.AccountPresenter
import com.fosung.lighthouse.manage.myapi.presenter.PicVerCodePresenter
import com.fosung.lighthouse.manage.myapi.presenter.VercodePresenter
import com.fosung.lighthouse.manage.myapi.view.EdcResPicVerCodeView
import com.fosung.lighthouse.manage.myapi.view.EduResAccountView
import com.fosung.lighthouse.manage.myapi.view.EduResVerCodeView
import com.fosung.lighthouse.my.R
import com.haier.cellarette.libretrofit.common.ResponseSlbBean1
import com.hjq.toast.ToastUtils
import com.zcolin.frame.app.BaseActivity
import com.zcolin.frame.util.BitmapUtil

/**
 *@author: lhw
 *@date: 2021/7/29
 *@desc 修改手机号
 */
class ChangePhoneAct : BaseActivity(), EduResAccountView<ResponseSlbBean1<*>>,
    EdcResPicVerCodeView, EduResVerCodeView {

    private var edtPhone: EditText? = null
    private var edtPhoneTwo: EditText? = null
    private var edtCode: EditText? = null
    private var edtMsgCode: EditText? = null
    private var tvCode: TextView? = null
    private var ivVerCode: ImageView? = null
    private var btnChange: Button? = null

    private var timer: CountDownTimer? = null
    private var mPresrnter: AccountPresenter? = null
    private var mPicverCodePresenter: PicVerCodePresenter? = null
    private var mVercodePresenter: VercodePresenter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_phone)
        setToolbarTitle(getString(R.string.title_change_phone))
        findView()
        init()
        onClick()
    }

    private fun findView() {
        edtPhone = getView(R.id.edt_phone)
        edtPhoneTwo = getView(R.id.edt_phone_two)
        edtCode = getView(R.id.edt_code)
        edtMsgCode = getView(R.id.edt_msg_code)
        tvCode = getView(R.id.tv_getcode)
        ivVerCode = getView(R.id.iv_vercode)
        btnChange = getView(R.id.btn_change)
    }

    private fun init() {
        timer = MyCountDownTimer(60000, 1000)

        mPresrnter = AccountPresenter(this)
        mPicverCodePresenter = PicVerCodePresenter(this)
        mVercodePresenter = VercodePresenter(this)

        mPicverCodePresenter?.getPicVerCode()
    }

    private fun onClick() {
        tvCode?.setOnClickListener(multiClick)
        ivVerCode?.setOnClickListener(multiClick)
        btnChange?.setOnClickListener(multiClick)
    }

    override fun onMultiClick(v: View?) {
        if (v?.id == R.id.tv_getcode) {
            val validateCode = edtCode?.text.toString()
            val telePhone = edtPhone?.text.toString()
            if (TextUtils.isEmpty(validateCode)) {
                ToastUtils.show("请输入图形验证码")
            } else if (TextUtils.isEmpty(telePhone)) {
                ToastUtils.show("请输入原手机号")
            } else {
                showProgressDialog("验证码获取中...")
                mVercodePresenter?.getVerCode(
                    MyApiConstant.GET_CODE_TYPE_VERPHONE,
                    validateCode,
                    telePhone,
                    CommonModel.get().getUserName()
                )
            }

        } else if (v?.id == R.id.iv_vercode) {
            mPicverCodePresenter?.getPicVerCode()

        } else if (v?.id == R.id.btn_change) {
            val phone = edtPhone?.text.toString()
            val phoneNew = edtPhoneTwo?.text.toString()
            val vode = edtMsgCode?.text.toString()

            if (TextUtils.isEmpty(phone)) {
                ToastUtils.show("请输入原手机号")
            } else if (TextUtils.isEmpty(phoneNew)) {
                ToastUtils.show("请输入新手机号")
            } else if (TextUtils.isEmpty(vode)) {
                ToastUtils.show("请输入短信验证码")
            } else {
                mPresrnter?.changePhone(vode, phone, phoneNew)
            }
        }
    }

    /**
     * 获取验证码按钮在发送60秒之后可以重新获取
     */
    @SuppressLint("HandlerLeak")
    inner class MyCountDownTimer(millisInFuture: Long, countDownInterval: Long) :
        CountDownTimer(millisInFuture, countDownInterval) {
        override fun onTick(millisUntilFinished: Long) {
            tvCode?.isEnabled = false
            tvCode?.text = (millisUntilFinished / 1000).toString() + "S"
            tvCode?.setTextColor(ContextCompat.getColor(this@ChangePhoneAct, R.color.gray_mid))
        }

        override fun onFinish() {
            tvCode?.isEnabled = true
            tvCode?.text = "重新获取"
            tvCode?.setTextColor(ContextCompat.getColor(this@ChangePhoneAct, R.color.colorPrimary))
        }
    }

    override fun OnEduResCommonFail(msg: String?) {
        hideProgressDialog()
        ToastUtils.show(msg)
    }

    override fun OnEduResCommonSuccess(bean: ResponseSlbBean1<*>?) {
        hideProgressDialog()
        ToastUtils.show("修改成功")
        finish()
    }

    override fun OnEduResPicVerSuccess(bean: PicVerCodeBean?) {
        ivVerCode?.setImageBitmap(BitmapUtil.stringToBitmap(bean?.data))
    }

    override fun OnEduResPicVerFail(msg: String?) {
        ToastUtils.show(msg)
    }

    override fun OnEduResVerCodeSuccess() {
        hideProgressDialog()
        timer!!.start()
    }

    override fun OnEduResVerCodeFail(msg: String?) {
        hideProgressDialog()
        ToastUtils.show(msg)
    }

}