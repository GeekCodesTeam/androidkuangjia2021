package com.haier.cellarette.baselibrary.coordinatorlayout;

import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.appbar.AppBarLayout;
import com.haier.cellarette.baselibrary.R;

import java.lang.reflect.Field;

public class CoordinatorLayoutAct7 extends AppCompatActivity {

    private CollapsingToolbarLayoutState state;
    private AppBarLayout app_bar;
    private TextView tv1;
    private SwipeRefreshLayout mSwipeLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_coordinatorlayoutact7);
        refresh();
        onclick();
    }

    private void onclick() {
        tv1 = findViewById(R.id.tv1);
        app_bar = findViewById(R.id.app_bar);
        app_bar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                //计算进度百分比
                float percent = Float.valueOf(Math.abs(verticalOffset)) / Float.valueOf(appBarLayout.getTotalScrollRange());
                //根据百分比做你想做的
                if (verticalOffset == 0) {
                    if (state != CollapsingToolbarLayoutState.EXPANDED) {
                        state = CollapsingToolbarLayoutState.EXPANDED;//修改状态标记为展开
//                        collapsingToolbarLayout.setTitle("EXPANDED");//设置title为EXPANDED
                    }
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    if (state != CollapsingToolbarLayoutState.COLLAPSED) {
//                        collapsingToolbarLayout.setTitle("");//设置title不显示
                        tv1.setText("悬浮中");//隐藏播放按钮
                        state = CollapsingToolbarLayoutState.COLLAPSED;//修改状态标记为折叠
                    }
                } else {
                    if (state != CollapsingToolbarLayoutState.INTERNEDIATE) {
                        if (state == CollapsingToolbarLayoutState.COLLAPSED) {
                            tv1.setText("测试");//由折叠变为中间状态时隐藏播放按钮
                        }
//                        collapsingToolbarLayout.setTitle("INTERNEDIATE");//设置title为INTERNEDIATE
                        state = CollapsingToolbarLayoutState.INTERNEDIATE;//修改状态标记为中间
                    }
                }
            }
        });
    }


    private void refresh() {
        mSwipeLayout = findViewById(R.id.refresh);
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeLayout.setRefreshing(false);
                    }
                }, 5000);
            }
        });
        mSwipeLayout.setColorSchemeColors(getResources().getIntArray(R.array.swipeRefreshColors));
//        mSwipeLayout.setColorScheme(getResources().getColor(android.R.color.holo_blue_bright),
//                getResources().getColor(android.R.color.holo_green_light),
//                getResources().getColor(android.R.color.holo_orange_light),
//                getResources().getColor(android.R.color.holo_red_light));
        ViewTreeObserver vto = mSwipeLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                final DisplayMetrics metrics = getResources()
                        .getDisplayMetrics();
                Float mDistanceToTriggerSync = Math.min(
                        ((View) mSwipeLayout.getParent()).getHeight() * 0.6f,
                        150 * metrics.density);
                try {
                    Field field = SwipeRefreshLayout.class
                            .getDeclaredField("mDistanceToTriggerSync");
                    field.setAccessible(true);
                    field.setFloat(mSwipeLayout, mDistanceToTriggerSync);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ViewTreeObserver obs = mSwipeLayout.getViewTreeObserver();
                obs.removeOnGlobalLayoutListener(this);
            }
        });
    }
}