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
package com.fosung.xuanchuanlan.common.helper;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.fosung.xuanchuanlan.R;
import com.fosung.xuanchuanlan.common.util.InitCallBackLayout;
//import com.yuntongxun.plugin.common.common.utils.LogUtil;


/**
 * 用于初始化欢迎页面
 * @author 容联•云通讯
 */
public class APPCreateHelper {

	public static final String TAG = "RongXin.APPCreateHelper";
	public static APPCreateHelper iHelper;
	public static APPCreateHelper getHelper() {
		
		if(iHelper == null) {
			iHelper = new APPCreateHelper();
		}
		return iHelper;
	}
	
	
	private View mView;
	private WindowManager mWindowManager;
	private FrameLayout mFrameLayout;
	private WindowManager.LayoutParams mWindowManagerLayoutParams;
	private ViewGroup.LayoutParams mViewGroupLayoutParams;
	private BitmapDrawable mWelcome;
	private boolean mViewInited;
	protected Object mLock = new Object();
	
	private void addContentView(Context context , View initView) {
		
		if(context instanceof Activity) {
			Activity activity = ((Activity) context);
			activity.setContentView(initView);
			return ;
		}
		if(mFrameLayout != null && mWindowManager != null) {
			return ;
		}
		mWindowManagerLayoutParams = new WindowManager.LayoutParams();
		mWindowManagerLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
		mWindowManagerLayoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
		mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		mWindowManagerLayoutParams.x = 0;
		mWindowManagerLayoutParams.y = 0;
		mWindowManagerLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
		mView = initView;
		mWindowManagerLayoutParams.gravity = 51;
		mWindowManagerLayoutParams.format = PixelFormat.RGBA_8888;
		mFrameLayout = new FrameLayout(context);
		mFrameLayout.setPadding(0, 0, 0, 0);
		mViewGroupLayoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.MATCH_PARENT);
		mFrameLayout.addView(mView, mViewGroupLayoutParams);
		mWindowManager.addView(mFrameLayout, mWindowManagerLayoutParams);
		mViewInited = true;
    }
	
	
	public void initWindow(final Context context){
		try {
			if(mViewInited) {
				removeInitView();
			}
			
			long currentTimeMillis = System.currentTimeMillis();
			final InitCallBackLayout initCallBackLayout = (InitCallBackLayout) LayoutInflater.from(context).
					inflate(R.layout.splash_activity, null);
//			TextView appNameView = (TextView) initCallBackLayout.findViewById(R.id.ytx_app_title);
//			if(RXConfig.isHengXinProject()) {
//				appNameView.setVisibility(View.VISIBLE);
//				appNameView.setText(RXConfig.APP_NAME);
//			} else {
//				appNameView.setVisibility(View.GONE);
//			}
			initCallBackLayout.setOnInitCallBackLayoutDrawListener(new InitCallBackLayout.OnInitCallBackLayoutDrawListener() {

				@Override
				public void OnInitCallBackLayoutDraw() {
				}
			});
			Log.v(TAG , "initWindow " + (System.currentTimeMillis() - currentTimeMillis));
			addContentView(context, initCallBackLayout);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public boolean isViewInited(){
		return mViewInited;
	}
	
	public void removeInitView() {
		try {
			synchronized (mLock) {
				if(mWindowManager != null) {
					if(mFrameLayout != null) {
						mWindowManager.removeView(mFrameLayout);
					}
					mWindowManager = null;
				}
				
				if(mFrameLayout != null) {
					mFrameLayout.removeAllViews();
					mFrameLayout = null;
				}
				mView = null;
				mViewInited = false;
 			}
		} catch (Exception e) {
		}
	}
	
}
