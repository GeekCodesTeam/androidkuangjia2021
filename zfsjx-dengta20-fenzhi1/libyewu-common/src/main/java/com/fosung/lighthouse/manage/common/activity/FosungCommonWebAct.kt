package com.fosung.lighthouse.manage.common.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.fosung.lighthouse.manage.common.R
import com.hjq.toast.ToastUtils
import com.just.agentweb.AgentWeb
import com.just.agentweb.BaseIndicatorView
import com.zcolin.frame.app.BaseActivity

/**
 *@author: lhw
 *@date: 2021/8/12
 *@desc
 */
class FosungCommonWebAct : BaseActivity() {

    private var parentView: View? = null
    private var mAgentWeb: AgentWeb? = null

    private var title: String? = null
    private var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lighthouse_common_web)
        initQuery()
        setToolbarTitle(title)
        findView()
        setting()


    }

    private fun findView() {
        parentView = getView(R.id.parent)


    }

    private fun setting() {


        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent((parentView as LinearLayout?)!!, LinearLayout.LayoutParams(-1, -1))
            .useDefaultIndicator(ContextCompat.getColor(this,R.color.colorPrimary))
            .createAgentWeb()
            .ready()
            .go(url)
    }


    private fun initQuery() {
        val appLinkIntent = intent
        if (appLinkIntent != null && appLinkIntent.action != null && appLinkIntent.data != null) {
            val appLinkData = appLinkIntent.data
            title = appLinkData!!.getQueryParameter("query_title")
            url = appLinkData!!.getQueryParameter("query_url")
            if (TextUtils.isEmpty(url)) {
                ToastUtils.show("参数错误")
                finish()
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (mAgentWeb!!.handleKeyEvent(keyCode, event)) {
            true
        } else super.onKeyDown(keyCode, event)
    }

    override fun onPause() {
        mAgentWeb!!.webLifeCycle.onPause()
        super.onPause()
    }

    override fun onResume() {
        mAgentWeb!!.webLifeCycle.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        mAgentWeb!!.webLifeCycle.onDestroy()
        super.onDestroy()
    }
}