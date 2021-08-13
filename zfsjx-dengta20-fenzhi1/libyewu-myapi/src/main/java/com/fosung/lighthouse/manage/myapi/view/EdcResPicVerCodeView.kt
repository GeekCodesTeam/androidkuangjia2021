package com.fosung.lighthouse.manage.myapi.view

import com.fosung.lighthouse.manage.myapi.bean.PicVerCodeBean
import com.geek.libmvp.IView

/**
 *@author: lhw
 *@date: 2021/8/6
 *@desc 图形验证码
 */
 interface EdcResPicVerCodeView :IView{

    fun OnEduResPicVerSuccess(bean: PicVerCodeBean?)

    fun OnEduResPicVerFail(msg: String?)

}