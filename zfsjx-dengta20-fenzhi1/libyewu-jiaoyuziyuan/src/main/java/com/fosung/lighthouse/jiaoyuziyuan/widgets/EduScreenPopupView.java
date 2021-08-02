package com.fosung.lighthouse.jiaoyuziyuan.widgets;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fosung.lighthouse.jiaoyuziyuan.R;
import com.geek.libglide47.base.util.DisplayUtil;
import com.lxj.xpopup.core.BottomPopupView;
import com.zcolin.frame.util.ScreenUtil;
import com.zcolin.gui.zrecyclerview.BaseRecyclerAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class EduScreenPopupView extends BottomPopupView implements View.OnClickListener {

    private RecyclerView rl_app;
    private ImageView iv_arrow;
    private BaseRecyclerAdapter adapter;
    private Context context;
    private boolean isOpen;
    ArrayList<String> datas = new ArrayList();

    public EduScreenPopupView(@NonNull @NotNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.view_screen_popup;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        iv_arrow = findViewById(R.id.iv_arrow);
        iv_arrow.setOnClickListener(this);

        rl_app = findViewById(R.id.rl_app);
        rl_app.setLayoutManager(new GridLayoutManager(getContext(), 3));
        rl_app.setNestedScrollingEnabled(false);
        rl_app.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull @NotNull Rect outRect, @NonNull @NotNull View view, @NonNull @NotNull RecyclerView parent, @NonNull @NotNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.right = DisplayUtil.dip2px(getContext(),5f);
                outRect.bottom = DisplayUtil.dip2px(getContext(),5f);
            }
        });
        initAdapter();
        rl_app.setAdapter(adapter);

        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        adapter.setDatas(datas);
    }

    @Override
    protected int getMaxHeight() {
        return (int) (ScreenUtil.getWindowHeight((Activity) context) * 0.8);
    }

    private void initAdapter() {
        adapter = new BaseRecyclerAdapter<String>() {
            @Override
            public int getItemLayoutId(int viewType) {
                return R.layout.item_edu_resources_screen_app;
            }

            @Override
            public void setUpData(CommonHolder holder, int position, int viewType, String data) {

            }
        };
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_arrow) {
            datas.clear();
            if (!isOpen) {
                datas.add("1");
                datas.add("1");
                datas.add("1");
                datas.add("1");
                datas.add("1");
                datas.add("1");
                datas.add("1");
                datas.add("1");
                datas.add("1");
                datas.add("1");
                datas.add("1");
                datas.add("1");
                datas.add("1");
                datas.add("1");
                datas.add("1");
                datas.add("1");
                datas.add("1");
                datas.add("1");
                datas.add("1");
                datas.add("1");
                datas.add("1");
                iv_arrow.animate().rotation(180);
                isOpen = true;
            } else {
                datas.add("1");
                datas.add("1");
                datas.add("1");
                datas.add("1");
                datas.add("1");
                datas.add("1");
                iv_arrow.animate().rotation(0);
                isOpen = false;
            }
            adapter.setDatas(datas);
        }
    }
}
