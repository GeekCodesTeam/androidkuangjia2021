package com.fosung.lighthouse.my.widget

import android.content.Context
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.fosung.lighthouse.manage.myapi.bean.PicVerCodeBean
import com.fosung.lighthouse.manage.myapi.presenter.PicVerCodePresenter
import com.fosung.lighthouse.manage.myapi.presenter.VercodePresenter
import com.fosung.lighthouse.manage.myapi.view.EdcResPicVerCodeView
import com.fosung.lighthouse.my.R
import com.geek.libglide47.base.GlideImageView
import com.hjq.toast.ToastUtils
import com.lxj.xpopup.core.CenterPopupView
import com.zcolin.frame.util.BitmapUtil

/**
 *@author: lhw
 *@date: 2021/8/5
 *@desc 图形验证码弹窗
 */
class MyPicVerCodeDialog(context: Context) : CenterPopupView(context), View.OnClickListener,
    EdcResPicVerCodeView {

    private var edtCode: EditText? = null
    private var ivCode: ImageView? = null
    private var btnCancle: Button? = null
    private var btnOk: Button? = null

    private var picVerCodePresenter: PicVerCodePresenter? = null
    private var picVerCodeInterface: PicVerCodeInterface? = null

    constructor(context: Context, p: PicVerCodeInterface) : this(context) {
        this.picVerCodeInterface = p
    }

    override fun getImplLayoutId(): Int = R.layout.my_view_piccode

    override fun onCreate() {
        super.onCreate()
        findView()
        setClick()
        doNetWork()
    }

    private fun findView() {
        edtCode = findViewById(R.id.edt_code)
        ivCode = findViewById(R.id.iv_code)
        btnCancle = findViewById(R.id.btn_cancel)
        btnOk = findViewById(R.id.btn_ok)

        picVerCodePresenter = PicVerCodePresenter(this)
    }

    private fun setClick() {
        ivCode?.setOnClickListener(this)
        btnCancle?.setOnClickListener(this)
        btnOk?.setOnClickListener(this)
    }

    fun doNetWork() {
        picVerCodePresenter?.getPicVerCode()
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.iv_code) {
            doNetWork()
        } else if (v?.id == R.id.btn_cancel) {
            dismiss()
        } else if (v?.id == R.id.btn_ok) {
            val picCode = edtCode?.text.toString()
            if (TextUtils.isEmpty(picCode)) {
                ToastUtils.show("请输入验证码")
                return
            }
            picVerCodeInterface?.picverCode(picCode)
            edtCode?.setText("")
            dismiss()

        }
    }

    override fun OnEduResPicVerSuccess(bean: PicVerCodeBean?) {
        ivCode?.setImageBitmap(BitmapUtil.stringToBitmap(bean?.data))
    }

    override fun OnEduResPicVerFail(msg: String?) {
        ToastUtils.show(msg)
    }

    override fun getIdentifier(): String {
        return "MyPicVerCodeDialog"
    }

    interface PicVerCodeInterface {
        fun picverCode(picVerCode: String)
    }

}