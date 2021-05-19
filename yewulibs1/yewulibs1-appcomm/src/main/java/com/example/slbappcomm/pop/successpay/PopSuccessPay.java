package com.example.slbappcomm.pop.successpay;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.slbappcomm.R;


// 支付成功弹窗
public class PopSuccessPay extends PopupWindow {

    private Activity activity;
    private View mMenuView;
    private TextView tv1;

    public PopSuccessPay() {

    }

    public PopSuccessPay(final Activity activity) {
        super(activity);
//        AutoSizeConfig.getInstance().setCustomFragment(true);
//        AutoSize.autoConvertDensity((Activity) activity, 375, true);
        this.activity = activity;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.pop_successpay, null);
        tv1 = mMenuView.findViewById(R.id.tv1);

        // 设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimBottom);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
//        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
//        Bitmap shot = BitmapUtils.takeScreenShot(activity.getWindow().getDecorView());
//        Bitmap blur = BitmapUtils.blur(activity, shot);
//        this.setBackgroundDrawable(new BitmapDrawable(activity.getResources(), blur));
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

//				int height = mMenuView.findViewById(R.id.pop_layout).getTop();
//				int music_seek_bar_cycle = (int) event.getY();
//				if (event.getAction() == MotionEvent.ACTION_UP) {
//					if (music_seek_bar_cycle < height) {
//						dismiss();
//					}
//				}
                dismiss();
                return true;
            }
        });
        activity.getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        }, 3000);
//        pay_pop.showAtLocation(getWindow().getDecorView(),
//                Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL
//                , 0, 0); // 设置layout在PopupWindow中显示的位置

    }

}
