package com.fosung.lighthouse.my.about

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.LinearLayoutCompat
import com.fosung.lighthouse.my.R
import com.zcolin.frame.app.BaseActivity

/**
 *@author: lhw
 *@date: 2021/8/4
 *@desc 关于我们
 */
class MyAboutUsAct : BaseActivity() {

    private var llUserAgrement: LinearLayoutCompat? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_about_us)
        setToolbarTitle(getString(R.string.title_about_us))
        findView()
        setClick()
    }

    private fun findView() {
        llUserAgrement = findViewById(R.id.ll_yhxy)
    }

    private fun setClick() {
        llUserAgrement?.setOnClickListener(multiClick)
    }

    override fun onMultiClick(v: View?) {
        if (v?.id == R.id.ll_yhxy) {
            startActivity(Intent(this, UserAgreementAct::class.java))
        }
    }

}