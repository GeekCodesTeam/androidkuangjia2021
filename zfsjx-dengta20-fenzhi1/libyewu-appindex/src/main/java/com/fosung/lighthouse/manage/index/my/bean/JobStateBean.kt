package com.example.slbappindex.my.bean

import com.fosung.lighthouse.manage.index.R


/**
 *@author: lhw
 *@date: 2021/8/2
 *@desc 工作状态
 */
data class JobStateBean(

    var normal_imageUrl: Int = R.drawable.default_unknow_image,
    var active_imageUrl: Int = R.drawable.default_unknow_image,
    var content: String? = null,
    var isSel: Boolean = false,

    ) {}