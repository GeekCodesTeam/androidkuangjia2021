package drawthink.expandablerecyclerview.demo.eximageview;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;

import drawthink.expandablerecyclerview.R;
import drawthink.expandablerecyclerview.demo.SimplePaddingDecoration;
import drawthink.expandablerecyclerview.demo.eximageview.adapter.ImageViewAdapter;
import drawthink.expandablerecyclerview.demo.eximageview.bean.ImageViewChildBean;
import drawthink.expandablerecyclerview.demo.eximageview.bean.ImageViewGroupBean;

import java.util.ArrayList;
import java.util.List;

import drawthink.expandablerecyclerview.bean.RecyclerViewData;
import drawthink.expandablerecyclerview.listener.OnRecyclerViewListener;

public class ImageViewAct extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<RecyclerViewData> mDatas;
    private ImageViewAdapter mAdapter;


    private void initDatas() {
        List<ImageViewChildBean> bean1 = new ArrayList<>();
        List<ImageViewChildBean> bean2 = new ArrayList<>();
        List<ImageViewChildBean> bean3 = new ArrayList<>();
        // 每个子列表长度可以不相同
        bean1.add(new ImageViewChildBean("Dog", R.mipmap.dog));
        bean1.add(new ImageViewChildBean("Dog", R.mipmap.dog));
        bean2.add(new ImageViewChildBean("Cat", R.mipmap.cat));
        bean3.add(new ImageViewChildBean("Bird", R.mipmap.bird));

        ImageViewGroupBean bean11 = new ImageViewGroupBean("Dog", R.mipmap.dog);
        ImageViewGroupBean bean22 = new ImageViewGroupBean("Cat", R.mipmap.cat);
        ImageViewGroupBean bean33 = new ImageViewGroupBean("Bird", R.mipmap.bird);
        mDatas = new ArrayList<>();
        mDatas.add(new RecyclerViewData(bean11, bean1, true));
        mDatas.add(new RecyclerViewData(bean22, bean2, true));
        mDatas.add(new RecyclerViewData(bean33, bean3, true));

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandableact_imageview);
        findview();
        donetwork();
        onclicklistener();
    }

    private void donetwork() {
        initDatas();
        mAdapter = new ImageViewAdapter(this, mDatas);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private void onclicklistener() {
        mAdapter.setOnItemClickListener(new OnRecyclerViewListener.OnItemClickListener() {
            @Override
            public void onGroupItemClick(int position, int groupPosition, View view) {
                //点击group bufen
                ImageViewGroupBean group = (ImageViewGroupBean) mDatas.get(groupPosition).getGroupData();
                ToastUtils.showLong("groupPos:" + groupPosition + " group:" + group.getName());
            }

            @Override
            public void onChildItemClick(int position, int groupPosition, int childPosition, View view) {
                //点击child bufen
                ImageViewChildBean bean = (ImageViewChildBean) mDatas.get(groupPosition).getChild(childPosition);
                ToastUtils.showLong("groupPos:" + groupPosition + " childPos:" + childPosition + " child:" + bean.getName());
            }
        });

    }

    private void findview() {
        mRecyclerView = findViewById(R.id.recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new SimplePaddingDecoration(this));

    }

}