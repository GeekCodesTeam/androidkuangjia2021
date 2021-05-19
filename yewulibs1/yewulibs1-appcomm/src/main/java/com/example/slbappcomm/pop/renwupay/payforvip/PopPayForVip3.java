package com.example.slbappcomm.pop.renwupay.payforvip;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.slbappcomm.R;
import com.haier.cellarette.baselibrary.zothers.NavigationBarUtil;

// 支付弹窗
public class PopPayForVip3 extends PopupWindow {

    private Activity activity;
    private View mMenuView;
    private TextView tv1;
    private RelativeLayout rl1;
    private RelativeLayout rl2;
    private LinearLayout ll23;

    public PopPayForVip3() {

    }

    public PopPayForVip3(final Activity activity,int drawable1, final OnFinishResultClickListener mListener) {
        super(activity);
//        AutoSizeConfig.getInstance().setCustomFragment(true);
//        AutoSize.autoConvertDensity((Activity) activity, 375, true);
        this.activity = activity;
        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.pop_payforvip, null);
        ll23 = mMenuView.findViewById(R.id.ll23);
        ll23.setBackgroundResource(drawable1);// R.drawable.slb_popup_shidu3
        tv1 = mMenuView.findViewById(R.id.tv1);
        rl1 = mMenuView.findViewById(R.id.rl1);
        rl2 = mMenuView.findViewById(R.id.rl2);
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        rl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onKaitonghuiyuan();
                }
//                activity.startActivity(new Intent("hs.act.phone.DemoMusicActivity"));
            }
        });
        rl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onGoumaihuiben();
                }
            }
        });

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
//        mMenuView.setOnTouchListener(new View.OnTouchListener() {
//
//            public boolean onTouch(View v, MotionEvent event) {
//
////				int height = mMenuView.findViewById(R.id.pop_layout).getTop();
////				int music_seek_bar_cycle = (int) event.getY();
////				if (event.getAction() == MotionEvent.ACTION_UP) {
////					if (music_seek_bar_cycle < height) {
////						dismiss();
////					}
////				}
//                dismiss();
//                return true;
//            }
//        });
        showPopupWindow();
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                //虚拟键
                if (NavigationBarUtil.hasNavigationBar(activity)) {
//            NavigationBarUtil.initActivity(getWindow().getDecorView());
                    NavigationBarUtil.hideBottomUIMenu(activity);
                }
            }
        });
//        pay_pop.showAtLocation(getWindow().getDecorView(),
//                Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL
//                , 0, 0); // 设置layout在PopupWindow中显示的位置

    }

    public void showPopupWindow() {
//        showAtLocation(activity.getWindow().getDecorView(),
//                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL
//                , 0, 0); // 设置layout在PopupWindow中显示的位置
        setFocusable(false);
        update();
        showAtLocation(activity.getWindow().getDecorView(), Gravity.NO_GRAVITY, 0, 0);
        NavigationBarUtil.fullScreenImmersive(getContentView());
        setFocusable(true);
        update();

    }

    private OnFinishResultClickListener mListener;

    public interface OnFinishResultClickListener {
        void onKaitonghuiyuan();

        void onGoumaihuiben();
    }

}
