package com.fosung.lighthouse.jiaoyuziyuan.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.fosung.lighthouse.jiaoyuziyuan.R;
import com.fosung.lighthouse.jiaoyuziyuan.adapter.EduResourcesListAdapter;
import com.fosung.lighthouse.jiaoyuziyuan.widgets.EduScreenPopupView;
import com.fosung.lighthouse.jiaoyuziyuan.widgets.EduUserInfoPopupView;
import com.hjq.toast.ToastUtils;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.zcolin.gui.zrecyclerview.ZRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class JiaoyuziyuanAct extends AppCompatActivity implements View.OnClickListener {

    private String[] strings = new String[]{
            "com.fosung.lighthouse.jiaoyuziyuan.DefaultAlias"};

    private EduResourcesListAdapter mRecyclerViewAdapter;
    private ZRecyclerView zRecyclerView;
    private TextView tv_screen;
    private TextView tv_status;
    private TextView tv_org_name;
    private BasePopupView statusPop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_edu_resources);
        zRecyclerView = findViewById(R.id.pullLoadMoreRecyclerView);
        zRecyclerView.setEmptyView(LayoutInflater.from(this)
                .inflate(R.layout.view_pullrecycler_empty, null));//setEmptyView

        ArrayList<String> datas = new ArrayList<>();
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

        setDataToRecyclerView(datas,true);

        tv_screen = findViewById(R.id.tv_screen);
        tv_screen.setOnClickListener(this);

        tv_status = findViewById(R.id.tv_status);
        tv_status.setOnClickListener(this);

        statusPop = new XPopup.Builder(this)
                .asCenterList("请选择", new String[]{"全部", "待审核", "已审核"},
                        null, 1,
                        new OnSelectListener() {
                            @Override
                            public void onSelect(int position, String text) {
                                ToastUtils.show(text);
                            }
                        });
        tv_org_name = findViewById(R.id.tv_org_name);
        tv_org_name.setOnClickListener(this);

    }

    /**
     * @param isClear 是否清除原来的数据
     */
    public void setDataToRecyclerView(List<String> list, boolean isClear) {
        if (mRecyclerViewAdapter == null) {
            mRecyclerViewAdapter = new EduResourcesListAdapter();
            zRecyclerView.setAdapter(mRecyclerViewAdapter);
        }
        mRecyclerViewAdapter.setDatas(list);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_screen:
                new XPopup.Builder(this)
                        .hasShadowBg(true) //半透明阴影背景
                        .moveUpToKeyboard(true)
                        .autoFocusEditText(false)
                        .asCustom(new EduScreenPopupView(this)).show();
                break;
            case R.id.tv_status:
                statusPop.show();
                break;
            case R.id.tv_org_name:
                new XPopup.Builder(this)
                        .hasShadowBg(true) //半透明阴影背景
                        .moveUpToKeyboard(true)
                        .autoFocusEditText(false)
                        .asCustom(new EduUserInfoPopupView(this)).show();
                break;
        }
    }
}