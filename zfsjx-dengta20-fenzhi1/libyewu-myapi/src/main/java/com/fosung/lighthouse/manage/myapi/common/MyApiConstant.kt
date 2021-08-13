package com.fosung.lighthouse.manage.myapi.common

/**
 *@author: lhw
 *@date: 2021/8/6
 *@desc
 */
class MyApiConstant {
    companion object {
        const val PAGE_SIZE = 20


        const val GET_CODE_TYPE_REGISTER = 2000             //注册
        const val GET_CODE_TYPE_FINDPAS = 2001                 //找回密码
        const val GET_CODE_TYPE_VERPHONE = 2002                 //验证原手机号
        const val GET_CODE_TYPE_VERNEWPHONW = 2003                 //验证新手机号


        const val MMKV_USER_INFO = "mmkvUserInfo"           //用户信息
        const val MMKV_VPN_NAME =  "mmkvVpnName"
        const val MMKV_VPN_PASS =  "mmkvVpnPass"
    }
}