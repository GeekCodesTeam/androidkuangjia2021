package com.fosung.lighthouse.my.accountsecurity.adapter

import android.widget.TextView
import com.fosung.lighthouse.manage.myapi.bean.LoginLogsBean
import com.fosung.lighthouse.my.R
import com.zcolin.gui.zrecyclerview.BaseRecyclerAdapter

/**
 *@author: lhw
 *@date: 2021/8/2
 *@desc  登录日志
 */
class LoginLogAdapter : BaseRecyclerAdapter<LoginLogsBean.LoginLogBean>() {

    override fun getItemLayoutId(viewType: Int): Int = R.layout.item_login_log

    override fun setUpData(
        holder: CommonHolder?,
        position: Int,
        viewType: Int,
        data: LoginLogsBean.LoginLogBean?
    ) {
        val tvDate = getView<TextView>(holder, R.id.tv_date)
        val tvPhone = getView<TextView>(holder, R.id.tv_phone)
        val tvDevice = getView<TextView>(holder, R.id.tv_device)
        val tvAddress = getView<TextView>(holder, R.id.tv_address)

        tvDate.text = data?.loginDate
        tvPhone.text = data?.dateDetail
        tvDevice.text = data?.clientName
        tvAddress.text = data?.clientName

    }
}