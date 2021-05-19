package com.example.slbappcomm.viewpager.transformer;

//import androidx.core.view.ViewPager;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

public class DepthPageTransformer implements ViewPager.PageTransformer {

	private static final float MIN_SCALE = 0.75F;

	@Override
	public void transformPage(View page, float position) {
        if(position < -1){
        }else if (position <= 0) {
            page.setAlpha(1.0F + position);
            page.setTranslationX(0F);
            page.setScaleX(1F);
            page.setScaleY(1F);
		} else if (position <= 1) {
            page.setTranslationX(page.getWidth() * -position);
            page.setAlpha(1F-position);
            final float scaleFactor = MIN_SCALE + (1F - MIN_SCALE) * (1F - Math.abs(position));
            page.setPivotX(page.getWidth()/2);
            page.setPivotY(page.getHeight()/2);
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
		}else {
        }
	}
}
