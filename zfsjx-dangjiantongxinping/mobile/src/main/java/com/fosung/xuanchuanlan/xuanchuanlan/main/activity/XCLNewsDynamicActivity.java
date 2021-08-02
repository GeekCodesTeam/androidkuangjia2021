//package com.fosung.xuanchuanlan.xuanchuanlan.main.activity;
//
//import android.content.Intent;
//import android.os.Handler;
//import android.os.Bundle;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import android.view.View;
//
//import com.fosung.xuanchuanlan.R;
////import com.yuntongxun.plugin.common.adapter.recyclerview.CommonAdapter;
////import com.yuntongxun.plugin.common.adapter.recyclerview.base.ViewHolder;
////import com.yuntongxun.plugin.common.adapter.recyclerview.wrapper.LoadMoreWrapper;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class XCLNewsDynamicActivity extends AppCompatActivity {
//
//    private RecyclerView mRecyclerView;
//    private List userList = new ArrayList();//实体类
//    private CommonAdapter<User> mAdapter;
//    private LoadMoreWrapper mLoadMoreWrapper;
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.xcl_activity_news_dynamic);
//        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
//
//        LinearLayoutManager mPerfectCourse = new LinearLayoutManager(this);
//        mPerfectCourse.setOrientation(RecyclerView.VERTICAL);// 设置recyclerview布局方式
//        mRecyclerView.setLayoutManager(mPerfectCourse);
//
//        for (int i = 0; i < 10; i++) {
//            User user = new User();
//            user.name = "习近平考察山丹" + i;
//            user.people = "榆垡镇结合大兴国际机场和护城河镇特定因素，采取多项措施，确保国庆70周年维稳工作取得实效。" + i;
//            userList.add(user);
//        }
//
//        mAdapter = new CommonAdapter<User>(this, R.layout.item_news_dynamic, userList,true) {
//            @Override
//            protected void convert(ViewHolder holder, User user, int position) {
//                holder.setText(R.id.Textviewname, user.name);
//                holder.setText(R.id.Textviewcontent, user.people);
//            }
//        };
//
//
//        mLoadMoreWrapper = new LoadMoreWrapper(mAdapter);
//        mLoadMoreWrapper.setLoadMoreView(R.layout.default_loading);
//        mLoadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
//            @Override
//            public void onLoadMoreRequested() {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        for (int i = 0; i < 10; i++) {
//                            User user = new User();
//                            user.name = "我是新的---习近平考察山丹" + i;
//                            user.people = "榆垡镇结合大兴国际机场和护城河镇特定因素，采取多项措施，确保国庆70周年维稳工作取得实效。" + i;
//                            userList.add(user);
//                        }
//                        mLoadMoreWrapper.notifyDataSetChanged();
//
//                    }
//                }, 2000);
//            }
//        });
//
//        mRecyclerView.setAdapter(mLoadMoreWrapper);
//
//        mAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
//
//                Intent intent = new Intent(XCLNewsDynamicActivity.this, XCLNewsDynamicDetailActivity.class);
//                startActivity(intent);
//            }
//
//            @Override
//            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
//                return false;
//            }
//        });
//
//    }
//
//    public class User {
//
//        public String name;
//        public String people;
//    }
//}