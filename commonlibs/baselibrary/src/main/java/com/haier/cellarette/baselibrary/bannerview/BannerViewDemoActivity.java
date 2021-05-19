package com.haier.cellarette.baselibrary.bannerview;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ToastUtils;
import com.haier.cellarette.baselibrary.R;
import com.haier.cellarette.baselibrary.bannerview.banner.BannerAdapter;
import com.haier.cellarette.baselibrary.bannerview.banner.BannerView;

import java.util.ArrayList;
import java.util.List;

public class BannerViewDemoActivity extends AppCompatActivity implements BannerView.OnBannerChangeListener {

    private BannerView mBannerView;
    private BannerAdapter mBannerAdapter;
    private List<Biaoge_listBean> mList1;

    private TextView tv_left;
    private TextView tv_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        tv_left = (TextView) findViewById(R.id.tv_left11);
        tv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //左←
//                ToastUtil.showToastShort(MainActivity.this, "左");

                mBannerView.setCurrent(mBannerView.getCurrent() - 1);
            }
        });
        tv_right = (TextView) findViewById(R.id.tv_right11);
        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //右→
//                ToastUtil.showToastShort(MainActivity.this, "右");

                mBannerView.setCurrent(mBannerView.getCurrent() + 1);
            }
        });
        mBannerView = (BannerView) findViewById(R.id.banner);

        Data1();
        mBannerAdapter = new BannerAdapter(this, mList1);
        mBannerView.setAdapter(mBannerAdapter);

        mBannerView.stopScroll();
        mBannerView.startScroll();
        mBannerView.setmOnBannerChangeListener(this);
    }

    private void Data1() {
        mList1 = new ArrayList<Biaoge_listBean>();
//        mList1.add(new Biaoge_listBean("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3441245796,6185313&fm=26&gp=0.jpg", "小姐姐1"));
//        mList1.add(new Biaoge_listBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1597727779948&di=0a97d0861d490309c526503a9ddfa571&imgtype=0&src=http%3A%2F%2Fimg1d.xgo-img.com.cn%2Fpics%2F1545%2F1544789.jpg", "小姐姐2"));
//        mList1.add(new Biaoge_listBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1597727827948&di=63f6995f07f85682cfab93df826dce11&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fpic%2F4%2F26%2F1179487672.jpg", "小姐姐3"));
//        mList1.add(new Biaoge_listBean("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=297759806,1174507829&fm=26&gp=0.jpg", "小姐姐4"));
//        mList1.add(new Biaoge_listBean("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3441245796,6185313&fm=26&gp=0.jpg", "小姐姐5"));
//        mList1.add(new Biaoge_listBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1597727779948&di=0a97d0861d490309c526503a9ddfa571&imgtype=0&src=http%3A%2F%2Fimg1d.xgo-img.com.cn%2Fpics%2F1545%2F1544789.jpg", "小姐姐6"));
//        mList1.add(new Biaoge_listBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1597727827948&di=63f6995f07f85682cfab93df826dce11&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fpic%2F4%2F26%2F1179487672.jpg", "小姐姐7"));

        mList1.add(new Biaoge_listBean(R.drawable.animation_img1, "小姐姐1"));
        mList1.add(new Biaoge_listBean(R.drawable.animation_img2, "小姐姐2"));
        mList1.add(new Biaoge_listBean(R.drawable.animation_img3, "小姐姐3"));
        mList1.add(new Biaoge_listBean(R.drawable.animation_img1, "小姐姐4"));
        mList1.add(new Biaoge_listBean(R.drawable.animation_img2, "小姐姐5"));
        mList1.add(new Biaoge_listBean(R.drawable.animation_img3, "小姐姐6"));
        mList1.add(new Biaoge_listBean(R.drawable.animation_img1, "小姐姐7"));
    }

    @Override
    public void onPageSelected(int pos) {

    }

    @Override
    public void onPageScrolled(int pos) {

    }

    @Override
    public void onPageScrollStateChanged(int pos) {
        Biaoge_listBean item = (Biaoge_listBean) mBannerAdapter.getBannerAdapterItem(pos);
//        ToastUtils.showShort(pos + "," + item.getText_content());
    }

    @Override
    protected void onPause() {
        if (mBannerView != null) {
            mBannerView.stopScroll();
        }
        super.onPause();

    }

    @Override
    protected void onResume() {
        if (mBannerView != null) {
            mBannerView.startScroll();
        }
        super.onResume();

    }
}
