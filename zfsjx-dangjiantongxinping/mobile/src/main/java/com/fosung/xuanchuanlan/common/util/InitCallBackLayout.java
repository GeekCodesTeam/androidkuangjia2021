/*
 *  Copyright (c) 2013 The CCP project authors. All Rights Reserved.
 *
 *  Use of this source code is governed by a Beijing Speedtong Information Technology Co.,Ltd license
 *  that can be found in the LICENSE file in the root of the web site.
 *
 *   http://www.cloopen.com
 *
 *  An additional intellectual property rights grant can be found
 *  in the file PATENTS.  All contributing project authors may
 *  be found in the AUTHORS file in the root of the source tree.
 */
package com.fosung.xuanchuanlan.common.util;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * 用于欢迎页面
 * @author 容联•云通讯
 */
public class InitCallBackLayout extends FrameLayout {

	private boolean hasDrawed;
	private OnInitCallBackLayoutDrawListener mBackLayoutDrawListener;
	
	/**
	 * @param context
	 */
	public InitCallBackLayout(Context context) {
		super(context);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public InitCallBackLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public InitCallBackLayout(Context context , OnInitCallBackLayoutDrawListener l) {
		super(context);
		mBackLayoutDrawListener = l;
	}
	
	public void setOnInitCallBackLayoutDrawListener(OnInitCallBackLayoutDrawListener l) {
		mBackLayoutDrawListener = l;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if(hasDrawed) {
			return ;
		}
		hasDrawed = true;
		
		if(mBackLayoutDrawListener != null) {
			mBackLayoutDrawListener.OnInitCallBackLayoutDraw();
		}
	}
	
	
	public interface OnInitCallBackLayoutDrawListener {
		void OnInitCallBackLayoutDraw();
	}
}
