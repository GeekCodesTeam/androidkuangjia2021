package com.haier.cellarette.baselibrary.recycleviewalluses.demo3baseadpterhelp;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haier.cellarette.baselibrary.R;

import java.util.ArrayList;
import java.util.List;

public class BaseRecActDemo3 extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private BaseRecActDemo3Adapter mAdapter;
    private List<BaseRecActDemo3BeanHead> mList;

    private static final String HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK = "https://avatars1.githubusercontent.com/u/7698209?v=3&s=460";
    private static final String CYM_CHAD = "CymChad";

    public static List<BaseRecActDemo3BeanHead> getSampleData() {
        List<BaseRecActDemo3BeanHead> list = new ArrayList<>();
        list.add(new BaseRecActDemo3BeanHead(true, "Section 1", true));
        list.add(new BaseRecActDemo3BeanHead(new BaseRecActDemo3Bean(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD)));
        list.add(new BaseRecActDemo3BeanHead(new BaseRecActDemo3Bean(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD)));
        list.add(new BaseRecActDemo3BeanHead(new BaseRecActDemo3Bean(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD)));
        list.add(new BaseRecActDemo3BeanHead(new BaseRecActDemo3Bean(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD)));
        list.add(new BaseRecActDemo3BeanHead(true, "Section 2", false));
        list.add(new BaseRecActDemo3BeanHead(new BaseRecActDemo3Bean(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD)));
        list.add(new BaseRecActDemo3BeanHead(new BaseRecActDemo3Bean(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD)));
        list.add(new BaseRecActDemo3BeanHead(new BaseRecActDemo3Bean(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD)));
        list.add(new BaseRecActDemo3BeanHead(true, "Section 3", false));
        list.add(new BaseRecActDemo3BeanHead(new BaseRecActDemo3Bean(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD)));
        list.add(new BaseRecActDemo3BeanHead(new BaseRecActDemo3Bean(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD)));
        list.add(new BaseRecActDemo3BeanHead(true, "Section 4", false));
        list.add(new BaseRecActDemo3BeanHead(new BaseRecActDemo3Bean(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD)));
        list.add(new BaseRecActDemo3BeanHead(new BaseRecActDemo3Bean(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD)));
        list.add(new BaseRecActDemo3BeanHead(true, "Section 5", true));
        list.add(new BaseRecActDemo3BeanHead(new BaseRecActDemo3Bean(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD)));
        list.add(new BaseRecActDemo3BeanHead(new BaseRecActDemo3Bean(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD)));
        return list;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleviewalluses_demo3);
        findview();
        donetwork();
        onclicklistener();
    }

    private void donetwork() {
        mList = new ArrayList<>();
        mList = getSampleData();
//        mAdapter.setNewData(mList);
        mAdapter = new BaseRecActDemo3Adapter(R.layout.baserecactdemo3_item, R.layout.baserecactdemo3_item_head, mList);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mAdapter.setNotDoAnimationCount(3);// mFirstPageItemCount

        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

    }

    private void onclicklistener() {
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //item click
                BaseRecActDemo3BeanHead addressBean = mList.get(position);
                if (addressBean.isHeader) {
                    //头部点击bufen
                    ToastUtils.showLong(addressBean.header);
                } else {
                    //内容item点击bufen
                    ToastUtils.showLong(addressBean.t.getName() + " " + addressBean.t.getImg());
                }
            }
        });
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                BaseRecActDemo3BeanHead addressBean = mList.get(position);
                int i = view.getId();
                if (i == R.id.demo3more) {
                    ToastUtils.showLong("more>");
                } else if (i == R.id.tv) {
                    ToastUtils.showLong(addressBean.t.getName());
                }
            }
        });

    }

    private void findview() {
        mRecyclerView = findViewById(R.id.rvlist);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false));

    }

}
