package com.fosung.lighthouse.my.accountsecurity.activity

import android.os.Bundle
import android.view.LayoutInflater
import com.fosung.lighthouse.manage.myapi.bean.LoginLogsBean
import com.fosung.lighthouse.manage.myapi.presenter.MyPresenter
import com.fosung.lighthouse.manage.myapi.view.EduResLoginLogView
import com.fosung.lighthouse.my.R
import com.fosung.lighthouse.my.accountsecurity.adapter.LoginLogAdapter
import com.hjq.toast.ToastUtils
import com.zcolin.frame.app.BaseActivity
import com.zcolin.gui.zrecyclerview.ZRecyclerView

/**
 *@author: lhw
 *@date: 2021/7/30
 *@desc 登录日志
 */
class LoginLogAct : BaseActivity(), EduResLoginLogView {

    private var rcyLoginLog: ZRecyclerView? = null
    private var loginLogAdapter: LoginLogAdapter? = null

    private var mPresenter: MyPresenter? = null

    private var pageNo = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_log)
        setToolbarTitle(getString(R.string.title_login_log))

        findView()
        init()
    }

    private fun findView() {
        rcyLoginLog = getView(R.id.rcy_login_log)

        rcyLoginLog?.emptyView =
            LayoutInflater.from(this).inflate(R.layout.my_view_pullrecycler_empty, null)
        loginLogAdapter = LoginLogAdapter()
        rcyLoginLog?.setAdapter(loginLogAdapter)
        rcyLoginLog?.setOnPullLoadMoreListener(object : ZRecyclerView.PullLoadMoreListener {
            override fun onRefresh() {
                rcyLoginLog?.isNoMore = false
                mPresenter?.getLoginLog(pageNo)
            }

            override fun onLoadMore() {
                mPresenter?.getLoginLog(pageNo)
            }
        })
    }

    private fun init() {
        mPresenter = MyPresenter();
        mPresenter?.setEduLoginLogView(this)

        rcyLoginLog?.refreshWithPull()

    }

    override fun onLoginLogSuccess(bean: LoginLogsBean) {
        rcyLoginLog?.setPullLoadMoreCompleted()
        loginLogAdapter?.setDatas(bean.data)
        rcyLoginLog?.isNoMore = true
        rcyLoginLog?.setIsShowNoMore(true)

    }

    override fun onLoginLogFail(msg: String) {
        rcyLoginLog?.setPullLoadMoreCompleted()
        ToastUtils.show(msg)
    }
}