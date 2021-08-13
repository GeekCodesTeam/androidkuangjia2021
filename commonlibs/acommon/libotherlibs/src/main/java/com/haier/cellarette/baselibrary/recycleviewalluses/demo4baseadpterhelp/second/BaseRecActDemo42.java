package com.haier.cellarette.baselibrary.recycleviewalluses.demo4baseadpterhelp.second;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haier.cellarette.baselibrary.R;
import com.haier.cellarette.baselibrary.recycleviewalluses.demo4baseadpterhelp.second.adapter.BaseRecActDemo42Adapter;
import com.haier.cellarette.baselibrary.recycleviewalluses.demo4baseadpterhelp.second.bean.BaseRecActDemo42Bean;
import com.haier.cellarette.baselibrary.recycleviewalluses.demo4baseadpterhelp.second.bean.BaseRecActDemo42ChildBean;

import java.util.ArrayList;
import java.util.List;

public class BaseRecActDemo42 extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private BaseRecActDemo42Adapter mAdapter;
    private List<BaseRecActDemo42Bean> mList;


    public static List<BaseRecActDemo42Bean> getMultipleItemData(int lenth) {
        List<BaseRecActDemo42Bean> list = new ArrayList<>();
        for (int i = 0; i < lenth; i++) {
            BaseRecActDemo42ChildBean status = new BaseRecActDemo42ChildBean();
            status.setUserName("Chad" + i);
            status.setCreatedAt("04/05/" + i);
            status.setRetweet(i % 2 == 0);
            status.setUserAvatar("https://avatars1.githubusercontent.com/u/7698209?v=3&s=460");
            status.setText("BaseRecyclerViewAdpaterHelper https://www.recyclerview.org");

            list.add(new BaseRecActDemo42Bean(BaseRecActDemo42Bean.style1));
            list.add(new BaseRecActDemo42Bean(BaseRecActDemo42Bean.style2, status));
            list.add(new BaseRecActDemo42Bean(BaseRecActDemo42Bean.style3, status));
            list.add(new BaseRecActDemo42Bean(BaseRecActDemo42Bean.style3, status));
            list.add(new BaseRecActDemo42Bean(BaseRecActDemo42Bean.style3, status));
            list.add(new BaseRecActDemo42Bean(BaseRecActDemo42Bean.style3, status));
            list.add(new BaseRecActDemo42Bean(BaseRecActDemo42Bean.style3, status));
            list.add(new BaseRecActDemo42Bean(BaseRecActDemo42Bean.style3, status));
        }

        return list;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleviewallsuses_demo42);
        findview();
        donetwork();
        onclicklistener();

    }

    private void donetwork() {
        mList = new ArrayList<>();
        mList = getMultipleItemData(2);
        mAdapter = new BaseRecActDemo42Adapter(mList);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mAdapter.setNotDoAnimationCount(3);// mFirstPageItemCount

        mAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                int type = mList.get(position).type;
                if (type == BaseRecActDemo42Bean.style1) {
                    return 4;
                } else if (type == BaseRecActDemo42Bean.style2) {
                    return 4;
                } else {
                    return 1;
                }
            }
        });

        mRecyclerView.setAdapter(mAdapter);

    }

    private void onclicklistener() {
        /**
         *      The click event is distributed to the BaseItemProvider and can be overridden.
         *      if you need register itemchild click longClick
         *      you need to use https://github.com/CymChad/BaseRecyclerViewAdapterHelper/wiki/Add-OnItemClickLister#use-it-item-child-long-click
         */
  /*      @Override
        protected void convert(BaseViewHolder helper, Status item) {
            helper.setText(R.id.tweetName, item.getUserName())
                    .setText(R.id.tweetText, item.getText())
                    .setText(R.id.tweetDate, item.getCreatedAt())
                    .setVisible(R.id.tweetRT, item.isRetweet())
                    .addOnLongClickListener(R.id.tweetText)
                    .linkify(R.id.tweetText);

        }
        adapter.setOnItemChildLongClickListener(new BaseQuickAdapter.OnItemChildLongClickListener() {
            @Override
            public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
                Log.d(TAG, "onItemChildLongClick: ");
                Toast.makeText(ItemClickActivity.this, "onItemChildLongClick" + position, Toast.LENGTH_SHORT);
            }
        });*/
//        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//
//            }
//        });
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                BaseRecActDemo42Bean addressBean = mList.get(position);
                int i = view.getId();
                if (i == R.id.tv_provider2) {
                    ToastUtils.showLong(position + "item click=" + addressBean.getmBean().getUserName());
                } else if (i == R.id.tv1_provider2) {
                    ToastUtils.showLong(position + "item click=" + addressBean.getmBean().getText());
                } else if (i == R.id.iv_provider3) {
                    ToastUtils.showLong(position + "item click=" + addressBean.getmBean().getUserAvatar());
                } else if (i == R.id.tv_provider3) {
                    ToastUtils.showLong(position + "item click=" + addressBean.getmBean().getUserName());
                } else {
                }
            }
        });
        mAdapter.setOnItemChildLongClickListener(new BaseQuickAdapter.OnItemChildLongClickListener() {
            @Override
            public boolean onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
                BaseRecActDemo42Bean addressBean = mList.get(position);
                int i = view.getId();
                if (i == R.id.iv_provider3) {
                    mAdapter.getData().remove(addressBean);
                    mAdapter.notifyItemRemoved(position);

                }
                return true;
            }
        });
    }

    private void findview() {
        mRecyclerView = findViewById(R.id.rv_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4, RecyclerView.VERTICAL, false));


    }

}
