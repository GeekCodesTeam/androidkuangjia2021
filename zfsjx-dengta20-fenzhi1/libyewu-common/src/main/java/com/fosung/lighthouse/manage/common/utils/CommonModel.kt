package com.fosung.lighthouse.manage.common.utils

import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.SPUtils
import com.fosung.lighthouse.manage.myapi.bean.UserLoginBean
import com.fosung.lighthouse.manage.myapi.common.MyApiConstant
import com.geek.libbase.utils.CommonUtils
import com.geek.libutils.data.MmkvUtils

/**
 *@author: lhw
 *@date: 2021/8/13
 *@desc
 */
class CommonModel {
    companion object {
        private var instance: CommonModel? = null
            get() {
                if (field == null) {
                    field = CommonModel()
                }
                return field
            }

        @Synchronized
        fun get(): CommonModel {
            return instance!!
        }
    }

    fun setToken(pToken: String) {
//      MmkvUtils.getInstance().set_xiancheng(CommonUtils.MMKV_TOKEN, pToken)
        SPUtils.getInstance().put(CommonUtils.MMKV_TOKEN, pToken)
    }

    fun getToken(): String = SPUtils.getInstance().getString(CommonUtils.MMKV_TOKEN)

    fun setUsreInfo(pUserInfo: UserLoginBean.UserInfoBean) {
        val strUerInfo = GsonUtils.toJson(pUserInfo)
        MmkvUtils.getInstance().set_xiancheng(MyApiConstant.MMKV_USER_INFO, strUerInfo)
    }

    fun getUserInfo(): UserLoginBean.UserInfoBean {
        val strUerInfo = MmkvUtils.getInstance().get_xiancheng_string(MyApiConstant.MMKV_USER_INFO)
        return GsonUtils.fromJson(strUerInfo, UserLoginBean.UserInfoBean::class.java)
    }

    fun getRolerBean(): UserLoginBean.UserInfoBean.RolesBean? {
        val usreInfoBean = getUserInfo()
        usreInfoBean.roles.forEach {
            if (it.roleId.equals("1002450396420050944")) {
                return it
            }
        }
        return null
    }

    fun setVpnName(pVpnName: String) {
        MmkvUtils.getInstance().set_xiancheng(MyApiConstant.MMKV_VPN_NAME, pVpnName)
    }

    fun getVpnName(): String =
        MmkvUtils.getInstance().get_xiancheng_string(MyApiConstant.MMKV_VPN_NAME)

    fun setVpnPass(pVpnPass: String) {
        MmkvUtils.getInstance().set_xiancheng(MyApiConstant.MMKV_VPN_PASS, pVpnPass)
    }

    fun getVpnPass(): String =
        MmkvUtils.getInstance().get_xiancheng_string(MyApiConstant.MMKV_VPN_PASS)


    fun setUserName(pUserName: String) {
        MmkvUtils.getInstance().set_xiancheng(CommonUtils.MMKV_USER_NAME, pUserName)
    }

    fun getUserName(): String =
        MmkvUtils.getInstance().get_xiancheng_string(CommonUtils.MMKV_USER_NAME)

    fun setUserPass(pUserPass: String) {
        MmkvUtils.getInstance().set_xiancheng(CommonUtils.MMKV_USER_PSW, pUserPass)
    }

    fun getUserPass(): String =
        MmkvUtils.getInstance().get_xiancheng_string(CommonUtils.MMKV_USER_PSW)

}