package com.example.slbappcomm.baserecycleview.yewu3;//package com.example.slbappcomm.baserecycleview.yewu3;
//
//public class ActYewu3readme {
////    usage
//    //默认已设置LinearLayoutManager
//recyclerView.setGridLayout(true, 2);
//
////设置刷新和加载更多回调
//recyclerView.setOnPullLoadMoreListener(new ZRecyclerView.PullLoadMoreListener() {
//        @Override
//        public void onRefresh() {
//
//        }
//
//        @Override
//        public void onLoadMore() {
//
//        }
//    });
//
////设置数据为空时的EmptyView，DataObserver是注册在adapter之上的，也就是setAdapter是注册上，notifyDataSetChanged的时候才会生效
//recyclerView.setEmptyView(this, R.layout.view_recycler_empty);
//
////设置HeaderView和footerView
//recyclerView.addHeaderView(this, R.layout.view_recyclerheader);
//recyclerView.addFooterView(this, R.layout.view_recyclerfooter);
//// recyclerView.removeHeaderView(View)
//
////设置加载更多进度条样式
//recyclerView.setLoadMoreProgressStyle(ProgressStyle.LineScaleIndicator);
//
////设置加载进度条View,此处可以设置 https://github.com/81813780/AVLoadingIndicatorView 的view
//recyclerView.setLoadMoreProgressView(view);
//
////设置Item监听
//recyclerView.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<String>() {
//        @Override
//        public void onItemClick(View covertView, int position, String data) {
//            Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT)
//                    .show();
//        }
//    });
//
////所有数据加载完毕后，不显示已加载全部
//recyclerView.setIsShowNoMore(false);
//
////到底加载是否可用
//recyclerView.setIsLoadMoreEnabled(false);
//
////下拉刷新是否可用
//recyclerView.setIsRefreshEnabled(false);
//
////处理与子控件的冲突，如viewpager
//recyclerView.setIsProceeConflict(true);
//
////设置自定义的加载Footer
//recyclerView.setLoadMoreFooter(customview implements ILoadMoreFooter);
//
////设置加载文字
//recyclerView.setLoadMoreText("正在加载...", "正在加载...", "*****已加载全部*****");
//
////增加默认分割线
//recyclerView.addDefaultItemDecoration();
//
////有下拉刷新效果，手动调用刷新数据
//recyclerView.refreshWithPull();
//
////没有下拉刷新效果，直接刷新数据
//recyclerView.refresh();
//
////只有下拉刷新效果，不刷新数据
//recyclerView.setRefreshing(true);
//}
