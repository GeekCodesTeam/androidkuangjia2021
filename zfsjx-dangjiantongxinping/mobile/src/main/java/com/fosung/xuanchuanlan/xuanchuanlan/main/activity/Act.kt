package com.fosung.xuanchuanlan.xuanchuanlan.main.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.fosung.xuanchuanlan.R
import com.fosung.xuanchuanlan.xuanchuanlan.widget.MarqueeView

class Act : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_xcl_main_frame)
        val noticeBar = LayoutInflater.from(this).inflate(R.layout.xcl_notice_bar, null, false)
        val marqueeView: MarqueeView = noticeBar.findViewById(R.id.marqueeView)
    }
}