package com.fosung.lighthouse.manage.myapi.view

import com.fosung.lighthouse.manage.myapi.bean.LoginLogsBean
import com.fosung.lighthouse.manage.myapi.bean.UserLoginBean
import com.geek.libmvp.IView

/**
 *@author: lhw
 *@date: 2021/8/12
 *@desc
 */
interface EdcResMyView : IView {
    fun onEduResMySuccess(bean: UserLoginBean?)
    fun onEduResMyFail(msg: String?)
}

interface EduResLoginLogView : IView {
    fun onLoginLogSuccess(bean: LoginLogsBean)
    fun onLoginLogFail(msg: String)
}

