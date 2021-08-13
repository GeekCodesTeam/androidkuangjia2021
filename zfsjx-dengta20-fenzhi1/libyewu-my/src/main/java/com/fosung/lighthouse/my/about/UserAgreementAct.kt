package com.fosung.lighthouse.my.about

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.LinearLayoutCompat
import com.fosung.lighthouse.manage.common.utils.CommonJumpUtis
import com.fosung.lighthouse.manage.common.utils.CommonJumpUtis.Companion.jumpCommonWebAct
import com.fosung.lighthouse.my.R
import com.zcolin.frame.app.BaseActivity

/**
 *@author: lhw
 *@date: 2021/8/12
 *@desc 用户协议
 */
class UserAgreementAct : BaseActivity() {

    private var llUserKno: LinearLayoutCompat? = null
    private var llUser: LinearLayoutCompat? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_agreement)
        setToolbarTitle(getString(R.string.title_user_agreemtn))
        findView()
        setClick()
    }

    private fun findView() {
        llUserKno = findViewById(R.id.ll_yhxz)
        llUser = findViewById(R.id.ll_yszc)
    }

    private fun setClick() {
        llUserKno?.setOnClickListener(multiClick)
        llUser?.setOnClickListener(multiClick)
    }

    override fun onMultiClick(v: View?) {
        if (v?.id == R.id.ll_yhxz) {
            jumpCommonWebAct(this, "用户须知", "https://www.baidu.com/")
        } else if (v?.id == R.id.ll_yszc) {
            jumpCommonWebAct(this, "隐私权政策", "https://www.baidu.com/")
        }
    }

}