package com.fosung.lighthouse.my.accountsecurity.activity

import android.os.Bundle
import com.fosung.lighthouse.my.R
import com.zcolin.frame.app.BaseActivity

/**
 *@author: lhw
 *@date: 2021/7/30
 *@desc 绑定账号
 */
class BindAccountAct : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bind_account)
        setToolbarTitle(getString(R.string.title_bind_account))

    }
}