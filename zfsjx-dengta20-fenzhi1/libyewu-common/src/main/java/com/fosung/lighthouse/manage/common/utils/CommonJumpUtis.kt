package com.fosung.lighthouse.manage.common.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.blankj.utilcode.util.AppUtils

/**
 *@author: lhw
 *@date: 2021/8/12
 *@desc
 */
class CommonJumpUtis {
    companion object {

        fun jumpCommonWebAct(cotext: Context, title: String, url: String) {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(
                    "dataability://cs.znclass.com/" +
                            AppUtils.getAppPackageName() + ".hs.act.slbapp.FosungCommonWebAct?" +
                            "query_title=" + title + "&query_url=" + url

                )
            )
            cotext.startActivity(intent)
        }


    }
}