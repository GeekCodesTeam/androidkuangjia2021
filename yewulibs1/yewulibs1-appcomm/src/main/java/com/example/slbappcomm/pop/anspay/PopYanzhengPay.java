package com.example.slbappcomm.pop.anspay;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
//import androidx.appcompat.widget.GridLayoutManager;
//import androidx.appcompat.widget.OrientationHelper;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.slbappcomm.R;

import java.util.ArrayList;
import java.util.List;

// 支付验证二维码
public class PopYanzhengPay extends PopupWindow {

    private Activity activity;
    private View mMenuView;
    private TextView tv1_poppay1;
    private TextView tv1_poppay2;
    private TextView tv1_poppay3;
    private TextView tv_del32;
    private RecyclerView recyclerview12;
    private PopYanzhengPayAdapter mAdapter;
    private List<PopYanzhengPayBean> mList;

    private List<PopYanzhengPayBean> getList() {
        List<PopYanzhengPayBean> list1 = new ArrayList<>();
        list1.add(new PopYanzhengPayBean(false, 1, "壹", R.drawable.slb_poppay1));
        list1.add(new PopYanzhengPayBean(false, 2, "贰", R.drawable.slb_poppay2));
        list1.add(new PopYanzhengPayBean(false, 3, "叁", R.drawable.slb_poppay3));
        list1.add(new PopYanzhengPayBean(false, 4, "肆", R.drawable.slb_poppay4));
        list1.add(new PopYanzhengPayBean(false, 5, "伍", R.drawable.slb_poppay5));
        list1.add(new PopYanzhengPayBean(false, 6, "陆", R.drawable.slb_poppay6));
        list1.add(new PopYanzhengPayBean(false, 7, "柒", R.drawable.slb_poppay7));
        list1.add(new PopYanzhengPayBean(false, 8, "捌", R.drawable.slb_poppay8));
        list1.add(new PopYanzhengPayBean(false, 9, "玖", R.drawable.slb_poppay9));
        return list1;
    }

    public PopYanzhengPay() {

    }

    public PopYanzhengPay(final Activity activity, final OnFinishResultClickListener mListener) {
        super(activity);
        this.activity = activity;
        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.pop_pay_choosetext, null);
        tv1_poppay1 = mMenuView.findViewById(R.id.tv1_poppay1);
        tv1_poppay2 = mMenuView.findViewById(R.id.tv1_poppay2);
        tv1_poppay3 = mMenuView.findViewById(R.id.tv1_poppay3);
        tv_del32 = mMenuView.findViewById(R.id.tv_del32);
        tv_del32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        //
        mList = new ArrayList<>();
        mList = getList();
        //
        mlist2 = new ArrayList<>();
        list_int = new ArrayList<>();
        list_int = getRandomNum(1, 9, 3);
        tv1_poppay1.setText(mList.get(list_int.get(0).getId() - 1).getContent() + "");
        tv1_poppay2.setText(mList.get(list_int.get(1).getId() - 1).getContent() + "");
        tv1_poppay3.setText(mList.get(list_int.get(2).getId() - 1).getContent() + "");
//        tv1_poppay1.setBackgroundResource(mList.get(list_int.get(0).getId() - 1).getAvatar());
//        tv1_poppay2.setBackgroundResource(mList.get(list_int.get(1).getId() - 1).getAvatar());
//        tv1_poppay3.setBackgroundResource(mList.get(list_int.get(2).getId() - 1).getAvatar());

        recyclerview12 = mMenuView.findViewById(R.id.recyclerview12);
        recyclerview12.setLayoutManager(new GridLayoutManager(activity, 3, RecyclerView.VERTICAL, false));
        mAdapter = new PopYanzhengPayAdapter(R.layout.pop_pay_choosetext_item);
        recyclerview12.setAdapter(mAdapter);

        mAdapter.setNewData(mList);
        mAdapter.notifyDataSetChanged();
//        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                //item click
//                Toasty.normal(activity, position + "item click").show();
//            }
//        });
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                PopYanzhengPayBean bean = mList.get(position);
                int i = view.getId();
                if (i == R.id.tv1_poppay) {
//                    Toasty.normal(activity, bean.getId() + "    " + position).show();

                    if (bean.isRetweet()) {
                        bean.setRetweet(false);
                        for (int j = 0; j < mlist2.size(); j++) {
                            PopYanzhengPayBeanInt beanInt1 = mlist2.get(j);
                            if (beanInt1.getId() == bean.getId()) {
                                if (mlist2.size() == 1) {
                                    tv1_poppay1.setText(mList.get(list_int.get(0).getId() - 1).getContent() + "");
                                }
                                if (mlist2.size() == 2) {
                                    tv1_poppay2.setText(mList.get(list_int.get(1).getId() - 1).getContent() + "");
                                }
                                if (mlist2.size() == 3) {
                                    tv1_poppay3.setText(mList.get(list_int.get(2).getId() - 1).getContent() + "");
                                }
                                mlist2.remove(beanInt1);

                            }
                        }
                    } else {
                        bean.setRetweet(true);
                        if (mlist2.size() <= 3) {
                            mlist2.add(new PopYanzhengPayBeanInt(bean.getId()));
                            if (mlist2.size() == 1) {
                                tv1_poppay1.setText(mlist2.get(0).getId() + "");
                            }
                            if (mlist2.size() == 2) {
                                tv1_poppay2.setText(mlist2.get(1).getId() + "");
                            }
                            if (mlist2.size() == 3) {
                                tv1_poppay3.setText(mlist2.get(2).getId() + "");
                            }
                        }
                    }
                    mAdapter.notifyDataSetChanged();
                    //
                    if (mlist2.size() == 3) {
                        // 返回正确结果bufen
                        for (PopYanzhengPayBean bean2 : mList) {
                            bean2.setRetweet(false);
                        }
                        mAdapter.notifyDataSetChanged();
                        //
                        if (IsEqual(list_int, mlist2)) {
                            if (mListener != null) {
                                mListener.onSuccess();
                            }
//                            Toasty.normal(activity, "恭喜你~").show();
                        } else {
                            if (mListener != null) {
                                mListener.onFail();
                            }
//                            Toasty.normal(activity, "答错了请重试").show();
                            // 重置 list_int
                            set_daxiezi();
                        }
                        mlist2.clear();

                    }
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

    }



    private OnFinishResultClickListener mListener;

    public OnFinishResultClickListener getmListener() {
        return mListener;
    }

    public void setmListener(OnFinishResultClickListener mListener) {
        this.mListener = mListener;
    }

    public interface OnFinishResultClickListener {
        void onSuccess();

        void onFail();
    }

    // 重置大写字
    private void set_daxiezi() {
        Animation cycleAnim = AnimationUtils.loadAnimation(activity, R.anim.pop_pay_shake2);
        tv1_poppay1.startAnimation(cycleAnim);
        tv1_poppay2.startAnimation(cycleAnim);
        tv1_poppay3.startAnimation(cycleAnim);
        list_int = new ArrayList<>();
        list_int = getRandomNum(1, 9, 3);
        tv1_poppay1.setText(mList.get(list_int.get(0).getId() - 1).getContent() + "");
        tv1_poppay2.setText(mList.get(list_int.get(1).getId() - 1).getContent() + "");
        tv1_poppay3.setText(mList.get(list_int.get(2).getId() - 1).getContent() + "");
//        tv1_poppay1.setBackgroundResource(mList.get(list_int.get(0).getId() - 1).getAvatar());
//        tv1_poppay2.setBackgroundResource(mList.get(list_int.get(1).getId() - 1).getAvatar());
//        tv1_poppay3.setBackgroundResource(mList.get(list_int.get(2).getId() - 1).getAvatar());

    }

    private List<PopYanzhengPayBeanInt> mlist2;
    private List<PopYanzhengPayBeanInt> list_int;


    /**
     * @param requMin      最小值
     * @param requMax      最大值
     * @param targetLength 获取随机数个数
     * @return
     */
    public List<PopYanzhengPayBeanInt> getRandomNum(int requMin, int requMax, int targetLength) {
        if (requMax - requMin < 1) {
            System.out.print("最小值和最大值数据有误");
            return null;
        } else if (requMax - requMin < targetLength) {
            System.out.print("指定随机个数超过范围");
            return null;
        }
        int target = targetLength;
        List<PopYanzhengPayBeanInt> list = new ArrayList<>();

        List<PopYanzhengPayBeanInt> requList = new ArrayList<>();
        for (int i = requMin; i <= requMax; i++) {
            requList.add(new PopYanzhengPayBeanInt(i));
        }

        for (int i = 0; i < targetLength; i++) {

            // 取出一个随机角标
            int r = (int) (Math.random() * requList.size());
            list.add(requList.get(r));

            // 移除已经取过的值
            requList.remove(r);


        }

        return list;
    }

    public boolean IsEqual(List<PopYanzhengPayBeanInt> defaultList1, List<PopYanzhengPayBeanInt> bannerBeanList1) {
        if (defaultList1.size() != bannerBeanList1.size()) {
            return false;
        }
        List<String> a = new ArrayList<>();
        List<String> b = new ArrayList<>();
        for (int i = 0; i <= defaultList1.size() - 1; i++) {
            a.add(defaultList1.get(i).getId() + "");
        }
        for (int j = 0; j <= bannerBeanList1.size() - 1; j++) {
            b.add(bannerBeanList1.get(j).getId() + "");
        }
        return compare(a, b);
    }

    public static <T extends Comparable<T>> boolean compare(List<T> a, List<T> b) {
        if (a.size() != b.size())
            return false;
        // 根据需求判断是否按照顺序比较
//        Collections.sort(a);
//        Collections.sort(b);
        for (int i = 0; i < a.size(); i++) {
            if (!a.get(i).equals(b.get(i)))
                return false;
        }
        return true;
    }


}
