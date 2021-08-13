package com.fosung.lighthouse.manage.myapi.view

import com.haier.cellarette.libretrofit.common.ResponseSlbBean1

/**
 *@author: lhw
 *@date: 2021/8/12
 *@desc
 */
interface EduResLogouttView {
    fun onEduResLogoutSuccess(bean: ResponseSlbBean1<*>?)
    fun OnEduResLogoutFail(msg: String?)
}