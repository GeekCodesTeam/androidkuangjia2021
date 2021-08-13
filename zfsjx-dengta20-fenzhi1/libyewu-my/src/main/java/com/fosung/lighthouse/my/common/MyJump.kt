package com.fosung.lighthouse.my.common

import android.content.Context
import android.content.Intent
import com.fosung.lighthouse.my.account.activity.UserLoginAct
import com.zcolin.frame.util.ActivityUtil

/**
 *@author: lhw
 *@date: 2021/8/12
 *@desc
 */
class MyJump {
    companion object {
        fun userLogOut(context: Context) {
            ActivityUtil.finishAllActivity()
            context.startActivity(Intent(context, UserLoginAct::class.java))
        }

    }
}