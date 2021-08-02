package com.fosung.lighthouse.my.setting.activity

import android.os.Bundle
import com.fosung.lighthouse.my.R
import com.zcolin.frame.app.BaseActivity

/**
 *@author: lhw
 *@date: 2021/7/27
 *@desc
 */
class PersonalInfoAct : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_info)
        setToolbarTitle(getString(R.string.title_personal_info))

    }

}