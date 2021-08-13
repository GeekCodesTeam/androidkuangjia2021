package com.fosung.lighthouse.my.accountsecurity.fragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.NavHostFragment
import com.fosung.lighthouse.my.R
import com.zcolin.frame.app.BaseFrameFrag
import com.zcolin.frame.app.OnMultiClickListener

/**
 *@author: lhw
 *@date: 2021/7/30
 *@desc 绑定账号1
 */
class BindAccoungOneFrag : BaseFrameFrag() {

    private var btnNext: Button? = null
    private var edtIdcard: EditText? = null
    private var edtIdPass: EditText? = null
    private val onMultiClick = OnMultiClick()

    override fun getRootViewLayId(): Int {
        return R.layout.fragment_bind_account_one
    }

    override fun createView(savedInstanceState: Bundle?) {
        super.createView(savedInstanceState)
        findView()
        onClick()
    }

    private fun findView() {
        btnNext = getView(R.id.btn_next)
        edtIdcard = getView(R.id.edt_idcard)
        edtIdPass = getView(R.id.edt_pass)
    }

    private fun onClick() {
        btnNext?.setOnClickListener(onMultiClick)
    }


    inner class OnMultiClick : OnMultiClickListener() {
        override fun onMultiClick(v: View?) {
            if (v?.id == R.id.btn_next) {
                NavHostFragment
                    .findNavController(this@BindAccoungOneFrag)
                    .navigate(R.id.my_bind_account_one_next)
            }
        }
    }

}