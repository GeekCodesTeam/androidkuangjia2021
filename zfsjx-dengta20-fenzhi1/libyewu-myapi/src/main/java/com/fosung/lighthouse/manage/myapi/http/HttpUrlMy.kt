package com.fosung.lighthouse.manage.myapi.http

import com.fosung.lighthouse.app.BuildConfigyewu

/**
 *@author: lhw
 *@date: 2021/7/26
 *@desc
 */
class HttpUrlMy {
    companion object {
         private val BASE_MY_URL_RELEASE: String = BuildConfigyewu.SERVER_ISERVICE_NEW1


         val USER_LOGIN =                       "$BASE_MY_URL_RELEASE/sso/app/login"                          //登录
         val ACCOUNT_CHECK =                    "$BASE_MY_URL_RELEASE/sso/app/checkAccount"                   //激活前校验
         val ACCOUNT_ACTIVITYTION =             "$BASE_MY_URL_RELEASE/sso/app/register"                       //激活
         val USER_INFO =                        "$BASE_MY_URL_RELEASE/sso/app/getUserInfoByToken"             //获取用户信息
         val CHANGE_PHONE =                     "$BASE_MY_URL_RELEASE/sso/app/modifyTelephone"                //修改手机号
         val CHANGE_LOGIN_PASS =                "$BASE_MY_URL_RELEASE/sso/app/modifyPassword"                 //修改管理员密码
         val CHANGE_VPN_PASS =                  "$BASE_MY_URL_RELEASE/sso/app/modifyVpnPassword"              //修改VPN密码
         val CHECK_VPN_BIND =                   "$BASE_MY_URL_RELEASE/sso/app/checkImeiLogin"                 //绑定校验
         val VPN_BING_USER_INFO =               "$BASE_MY_URL_RELEASE/sso/app/imeiLinkLogin"                  //密码绑定登录 登录后绑定 绑定后自动登录
         val LOGIN_LOG =                        "$BASE_MY_URL_RELEASE/sso/app/loginLog"                       //登录日志
         val USER_LOGOUT =                      "$BASE_MY_URL_RELEASE/app/deleteAppLogin"                       //退出登录
    }
}