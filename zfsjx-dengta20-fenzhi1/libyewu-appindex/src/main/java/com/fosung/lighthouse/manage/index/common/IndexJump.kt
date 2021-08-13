package com.fosung.lighthouse.manage.index.common

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.blankj.utilcode.util.AppUtils

/**
 *@author: lhw
 *@date: 2021/8/4
 *@desc
 */
class IndexJump {
    companion object {
        fun jumpPcLoginAct(cotext: Context, query: String) {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(
                    "dataability://cs.znclass.com/" +
                            AppUtils.getAppPackageName() + ".hs.act.slbapp.PcLoginAct?query_text=" + query
                )
            )
            cotext.startActivity(intent)
        }

        fun jumpAccountSecurityAct(cotext: Context) {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(
                    "dataability://cs.znclass.com/" +
                            AppUtils.getAppPackageName() + ".hs.act.slbapp.AccountSecurityAct"
                )
            )
            cotext.startActivity(intent)
        }

        fun jumpSettingAct(cotext: Context) {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(
                    "dataability://cs.znclass.com/" +
                            AppUtils.getAppPackageName() + ".hs.act.slbapp.SettingAct"
                )
            )
            cotext.startActivity(intent)
        }

        fun jumpMyAboutUsAct(cotext: Context) {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(
                    "dataability://cs.znclass.com/" +
                            AppUtils.getAppPackageName() + ".hs.act.slbapp.MyAboutUsAct"

                )
            )
            cotext.startActivity(intent)
        }

        fun jumpLoginAct(cotext: Context) {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(
                    "dataability://cs.znclass.com/" +
                            AppUtils.getAppPackageName() + ".hs.act.slbapp.UserLoginAct"
                )
            )
            cotext.startActivity(intent)
        }

    }
}