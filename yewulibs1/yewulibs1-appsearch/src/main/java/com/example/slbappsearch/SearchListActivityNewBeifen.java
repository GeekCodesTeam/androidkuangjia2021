//package com.example.slbappindex.search;
//
//import android.content.Intent;
//import android.os.Build;
//import android.os.Bundle;
//
//import android.support.annotation.RequiresApi;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.OrientationHelper;
//import androidx.recyclerview.widget.RecyclerView;
//import android.text.Editable;
//import android.text.TextUtils;
//import android.text.TextWatcher;
//import android.util.Log;
//import android.view.KeyEvent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.blankj.utilcode.util.DeviceUtils;
//import com.chad.library.adapter.base.BaseQuickAdapter;
//import com.example.biz3slbappusercenter.bean.SSearchBean;
//import com.example.biz3slbappusercenter.bean.SSearchBean1;
//import com.example.biz3slbappusercenter.presenter.SSearchPresenter;
//import com.example.biz3slbappusercenter.view.SSearchView;
//import com.haier.cellarette.libutils.CommonUtils;
//import com.example.libbase.base.SlbBaseActivity;
//import com.example.slbappindex.R;
//import com.example.slbappindex.search.part1.SearchBean;
//import com.example.slbappindex.search.part1.searchhuancun.SearchCommManager;
//import com.example.slbappindex.search.part2.SearchKeyListAdapter;
//import com.example.slbappindex.search.part3.SearchListAdapter;
//import com.gongwen.marqueen.SimpleMarqueeView;
//import com.haier.cellarette.baselibrary.emptyview.NiubiEmptyView;
//import com.haier.cellarette.baselibrary.qcode.ExpandViewRect;
//import com.zhy.view.flowlayout.FlowLayout;
//import com.zhy.view.flowlayout.TagAdapter;
//import com.zhy.view.flowlayout.TagFlowLayout;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Set;
//
//public class SearchListActivityNewBeifen extends SlbBaseActivity implements View.OnClickListener, SSearchView {
//
//    private LinearLayout llbg3;
//    private TextView tv_del2;
//    private TextView tvLeft2;
//    private TextView tv_canel2;
//    private LinearLayout rlSearch1;
//    private TextView tvSearch1;
//    private SimpleMarqueeView marqueeView3;
//    private EditText edit_query1;
//    private TextView tvSearch2;
//    private LinearLayout ll1;
//    private TagFlowLayout mFlowLayout;
//    private ArrayList<SearchBean> flowBean;
//    //
//    private LinearLayout ll2;
//    private RecyclerView recyclerView2;
//    private SearchKeyListAdapter mAdapter2;
//    //
//    private LinearLayout ll3;
//    private RecyclerView recyclerView3;
//    private SearchListAdapter mAdapter3;
//    private NiubiEmptyView niubiEmptyView3;
//
//    //
//    private String search_key;
//    private int focus = 0;// 0:正常输入进2    1:从recyclerView2点击进3和从历史记录点击进3
//    private SSearchPresenter presenter;
//    private List<SSearchBean1> mList;
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.activity_search_list;
//    }
//
//    @Override
//    protected void setup(@Nullable Bundle savedInstanceState) {
//        super.setup(savedInstanceState);
////        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
////        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        search_key = getIntent().getStringExtra("search_key");
////        Toasty.normal(this, search_key).show();
//        findview();
//        onclick();
//        donetwork();
//    }
//
//    private void donetwork() {
//        tvLeft2.setVisibility(View.GONE);
//        marqueeView3.setVisibility(View.GONE);
//        tvSearch2.setVisibility(View.GONE);
//        tv_canel2.setVisibility(View.VISIBLE);
//        ExpandViewRect.expandViewTouchDelegate(tv_canel2, 10, 10, 10, 10);
//        edit_query1.setVisibility(View.VISIBLE);
//        set_ll_vis(1);
//        edit_query1.setHint(search_key);
////        edit_query1.requestFocus();
//        showInput(edit_query1);
////        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
//
//        //
//        get_flowlayout();
//
////        mFlowLayout.setAdapter(new TagAdapter<String>(mVals) {
////
////            @Override
////            public View getView(FlowLayout parent, int position, String s) {
////                TextView tv = (TextView) mInflater.inflate(R.layout.activity_search_tv,
////                        mFlowLayout, false);
////                tv.setText(s);
////                return tv;
////            }
////        });
//        //
//        mList = new ArrayList<>();
//        presenter = new SSearchPresenter();
//        presenter.onCreate(this);
//
//    }
//
//    private TextWatcher mTextWatcher = new TextWatcher() {
//        private CharSequence temp;
//        private int editStart;
//        private int editEnd;
//
//        @Override
//        public void beforeTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
//            temp = s;
//        }
//
//        @RequiresApi(api = Build.VERSION_CODES.O)
//        @Override
//        public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
////            tv_ed_content.setText("还能输入" + (MAX_TEXT - s.length()) + "字符");
//            String searchContext = edit_query1.getText().toString().trim();
//            String searchHint = edit_query1.getHint().toString().trim();
//            if (TextUtils.isEmpty(searchContext)) {
//                tvSearch2.setVisibility(View.GONE);
//                set_ll_vis(1);
//                // 少一步刷新历史记录操作bufen
//
//            } else {
//                // 调用搜索方法
//
//                if (focus == 0) {
//                    tvSearch2.setVisibility(View.VISIBLE);
//                    set_ll_vis(2);
//                    setData2(searchContext);
//                }
//                if (focus == 1) {
//                    tvSearch2.setVisibility(View.GONE);
//                    edit_query1.clearFocus();
//                    set_ll_vis(3);
//                    setData3(searchContext);
//                    focus = 0;
//                }
//
////                if (edit_query1.getFocusable() == View.FOCUSABLE) {
////                    tvSearch2.setVisibility(View.VISIBLE);
////                    set_ll_vis(2);
////                    setData2(searchContext);
////                } else {
////                    set_ll_vis(3);
////                    setData3(searchContext);
////                }
//            }
////            if (s.length() > 0) {
////                tvSearch2.setVisibility(View.VISIBLE);
////                setData2(edit_query1.getText().toString().trim());
////            } else {
////                tvSearch2.setVisibility(View.GONE);
////                set_ll_vis(1);
////            }
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//            editStart = edit_query1.getSelectionStart();
//            editEnd = edit_query1.getSelectionEnd();
//            edit_query1.setSelection(edit_query1.getText().toString().trim().length());//将光标移至文字末尾
////            if (temp.length() > MAX_TEXT) {
////                Toasty.normal(SearchListActivity.this, "限制输入" + MAX_TEXT + "字符!").show();
////                s.delete(editStart - 1, editEnd);
////                int tempSelection = editStart;
////                edt1.setText(s);
////                edt1.setSelection(tempSelection);
////            }
//        }
//    };
//
//    @Override
//    public void onClick(View v) {
//        int i = v.getId();
//        if (i == R.id.tv_canel2) {
//            //
//            onBackPressed();
//        } else if (i == R.id.tv_search2) {
//            //
//            edit_query1.getText().clear();
//            set_ll_vis(1);
//        } else if (i == R.id.tv_del2) {
//            //
//            del_history();
//            get_flowlayout();
//        } else {
//
//        }
//    }
//
//    private void onclick() {
//        tvSearch2.setOnClickListener(this);
//        tv_del2.setOnClickListener(this);
//        tv_canel2.setOnClickListener(this);
//        edit_query1.addTextChangedListener(mTextWatcher);
//        edit_query1.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View view, int keyCode, KeyEvent event) {
////                if (keyCode == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
//                if (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
//                    hideInput();
//                    String searchContext = edit_query1.getText().toString().trim();
//                    String searchHint = edit_query1.getHint().toString().trim();
//                    String content = "";
//                    if (TextUtils.isEmpty(searchContext)) {
//                        if (!TextUtils.isEmpty(searchHint)) {
//                            // 调用搜索方法
//                            set_ll_vis(3);
//                            content = searchHint;
//                            setData3(searchHint);
//                        }
//                    } else {
//                        // 调用搜索方法
//                        set_ll_vis(3);
//                        content = searchContext;
//                        setData3(searchContext);
//                    }
//                    // 保存历史记录bufen
//                    add_history(new SearchBean(content, content, false));
//
//                }
//                return false;
//            }
//        });
//        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
//            @Override
//            public boolean onTagClick(View view, int position, FlowLayout parent) {
////                Toast.makeText(getActivity(), mVals[position], Toast.LENGTH_SHORT).show();
//                //view.setVisibility(View.GONE);
////                SearchBean searchBean = (SearchBean) mFlowLayout.getAdapter().getItem(position);
////                edit_query1.setText(searchBean.getText_content());
////                edit_query1.clearFocus();
////                tvSearch2.setVisibility(View.GONE);
//                return true;
//            }
//        });
//        mFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
//            @Override
//            public void onSelected(Set<Integer> selectPosSet) {
////                getActivity().setTitle("choose:" + selectPosSet.toString());
//                Log.e("--mFlowLayout---", selectPosSet.toString());
//                String aa = "";
//                Iterator<Integer> iterator = selectPosSet.iterator();
//                while (iterator.hasNext()) {
////                    Log.e("--mFlowLayout---", iterator.next().toString());
//                    aa = iterator.next().toString();
//                }
//                SearchBean searchBean = (SearchBean) mFlowLayout.getAdapter().getItem(Integer.valueOf(aa));
//                focus = 1;
//                edit_query1.setText(searchBean.getText_content());
//                SearchBean bean2 = new SearchBean(searchBean.getText_content(), searchBean.getText_content(), false);
//                add_history(bean2);
//                hideInput();
//            }
//        });
////        mAdapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
////            @Override
////            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
////                //item click
////                SSearchBean1 bean = (SSearchBean1) adapter.getItem(position);
////                Toasty.normal(SearchListActivity.this, bean.getBookName() + "item click").show();
////            }
////        });
//        mAdapter2.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
//            @Override
//            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                SSearchBean1 bean = (SSearchBean1) adapter.getItem(position);
//                int i = view.getId();
////                Toasty.normal(SearchListActivity.this, bean.getBookName() + "    " + position).show();
//                focus = 1;
//                edit_query1.setText(bean.getBookName());
//                SearchBean bean2 = new SearchBean(bean.getBookName(), bean.getBookName(), false);
//                add_history(bean2);
//
////                if (i == R.id.ll_bg1) {
//////
////                } else {
////                }
//            }
//        });
//
//        niubiEmptyView3.setRetry(new NiubiEmptyView.RetryListener() {
//            @Override
//            public void retry() {
//                setData3(search_key);
//            }
//        });
//        mAdapter3.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                //item click
//                SSearchBean1 bean = (SSearchBean1) adapter.getItem(position);
////                Toasty.normal(SearchListActivity.this, bean.getBookName() + "item click").show();
//                if (TextUtils.equals(bean.getSourceType(), "book")) {
//                    Intent intent = new Intent("hs.act.slbapp.BooksListActivity");
//                    intent.putExtra(CommonUtils.HUIBEN_IDS, bean.getBookId());
//                    intent.putExtra(CommonUtils.HUIBEN_TITLES, bean.getBookName());
//                    startActivity(intent);
//                }
//                if (TextUtils.equals(bean.getSourceType(), "bookItem")) {
//                    Intent intent = new Intent("hs.act.slbapp.ReadBookActivity");
//                    intent.putExtra(CommonUtils.HUIBEN_IDS, bean.getBookItemId());
//                    intent.putExtra(CommonUtils.HUIBEN_TITLES, bean.getBookName());
//                    startActivity(intent);
//                }
//            }
//        });
//        mAdapter3.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
//            @Override
//            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                SSearchBean1 bean = (SSearchBean1) adapter.getItem(position);
//                int i = view.getId();
//                edit_query1.clearFocus();
//                if (TextUtils.equals(bean.getSourceType(), "book")) {
//                    Intent intent = new Intent("hs.act.slbapp.BooksListActivity");
//                    intent.putExtra(CommonUtils.HUIBEN_IDS, bean.getBookId());
//                    intent.putExtra(CommonUtils.HUIBEN_TITLES, bean.getBookName());
//                    startActivity(intent);
//                }
//                if (TextUtils.equals(bean.getSourceType(), "bookItem")) {
//                    Intent intent = new Intent("hs.act.slbapp.ReadBookActivity");
//                    intent.putExtra(CommonUtils.HUIBEN_IDS, bean.getBookItemId());
//                    intent.putExtra(CommonUtils.HUIBEN_TITLES, bean.getBookName());
//                    startActivity(intent);
//                }
////                if (i == R.id.iv1) {
////                    Toasty.normal(SearchListActivity.this, bean.getBannerImg() + "    " + position).show();
////                } else if (i == R.id.tv1) {
////                    Toasty.normal(SearchListActivity.this, bean.getBookName() + position).show();
////                } else {
////                }
//            }
//        });
//    }
//
//    private void findview() {
//        llbg3 = findViewById(R.id.llbg3);
//        tv_del2 = findViewById(R.id.tv_del2);
//        tvLeft2 = findViewById(R.id.tv_left2);
//        tv_canel2 = findViewById(R.id.tv_canel2);
//        rlSearch1 = findViewById(R.id.rl_search1);
//        tvSearch1 = findViewById(R.id.tv_search1);
//        marqueeView3 = findViewById(R.id.marqueeView3);
//        edit_query1 = findViewById(R.id.edit_query1);
//        tvSearch2 = findViewById(R.id.tv_search2);
//        ll1 = findViewById(R.id.ll1);
//        mFlowLayout = findViewById(R.id.id_flowlayout);
//        ll2 = findViewById(R.id.ll2);
//        recyclerView2 = findViewById(R.id.recycler_view2);
//        ll3 = findViewById(R.id.ll3);
//        recyclerView3 = findViewById(R.id.recycler_view3);
//        //
//        recyclerView2.setLayoutManager(new GridLayoutManager(this, 1, OrientationHelper.VERTICAL, false));
//        mAdapter2 = new SearchKeyListAdapter(R.layout.activity_search_list_item2);
//        recyclerView2.setAdapter(mAdapter2);
//        //
//        recyclerView3.setLayoutManager(new GridLayoutManager(this, 1, OrientationHelper.VERTICAL, false));
//        mAdapter3 = new SearchListAdapter(R.layout.activity_search_list_item3);
//        recyclerView3.setAdapter(mAdapter3);
//        niubiEmptyView3 = new NiubiEmptyView();
//        niubiEmptyView3.bind(this, recyclerView3, mAdapter3);
//
//    }
//
//    /**
//     * 设置页面切换 1:历史记录 2:搜索关键字 3:搜索结果列表
//     */
//    private void set_ll_vis(int which_ll) {
//        ll1.setVisibility(View.GONE);
//        ll2.setVisibility(View.GONE);
//        ll3.setVisibility(View.GONE);
//        if (which_ll == 1) {
//            ll1.setVisibility(View.VISIBLE);
//        }
//        if (which_ll == 2) {
//            ll2.setVisibility(View.VISIBLE);
//        }
//        if (which_ll == 3) {
//            ll3.setVisibility(View.VISIBLE);
//        }
//    }
//
//    private List<SearchBean> add_history(SearchBean searchBean) {
//        flowBean = new ArrayList<>();
//        flowBean = SearchCommManager.getInstance().get("search_history");
//        flowBean.add(0, searchBean);
//        SearchCommManager.getInstance().Add("search_history", flowBean);
//        return SearchCommManager.getInstance().get("search_history");
//    }
//
//    private List<SearchBean> get_history() {
//        flowBean = new ArrayList<>();
//        if (SearchCommManager.getInstance().get("search_history") != null && SearchCommManager.getInstance().get("search_history").size() > 0) {
//            flowBean = SearchCommManager.getInstance().get("search_history");
//        }
//        return flowBean;
//    }
//
//    private void del_history() {
//        if (SearchCommManager.getInstance().get("search_history") != null && SearchCommManager.getInstance().get("search_history").size() > 0) {
//            SearchCommManager.getInstance().del("search_history");
//        }
//    }
//
//    // 如果超过20条 删除21以后bufen
//    private List<SearchBean> get_history20() {
//        ArrayList<SearchBean> mlist20 = new ArrayList<>();
//        if (get_history().size() >= 20) {
//            for (int i = 19; i >= 0; i--) {
//                mlist20.add(get_history().get(i));
//            }
//            del_history();
//            for (SearchBean searchBean : mlist20) {
//                add_history(searchBean);
//            }
//        }
//        return get_history();
//    }
//
//    private void get_flowlayout() {
//        //mFlowLayout.setMaxSelectCount(3);
//        flowBean = new ArrayList<>();
////        flowBean.add(new SearchBean("1", "测试", false));
////        flowBean.add(new SearchBean("2", "测试测试", false));
////        flowBean.add(new SearchBean("3", "测试测试测试", false));
////        flowBean.add(new SearchBean("4", "测试测试", false));
////        flowBean.add(new SearchBean("5", "测试", false));
////        SearchCommManager.getInstance().Add("search_history", flowBean);
//        final LayoutInflater mInflater = LayoutInflater.from(this);
//        mFlowLayout.setAdapter(new TagAdapter(get_history20()) {
//            @Override
//            public View getView(FlowLayout parent, int position, Object bean) {
//                TextView tv = (TextView) mInflater.inflate(R.layout.activity_search_tv,
//                        mFlowLayout, false);
//                SearchBean bean1 = (SearchBean) bean;
//                tv.setText(bean1.getText_content());
//                return tv;
//            }
//        });
//    }
//
//    private void showInput(View view) {
//        // 显示键盘
//        edit_query1.requestFocus();
//        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).
//                hideSoftInputFromWindow(SearchListActivityNewBeifen.this.getCurrentFocus().getWindowToken(),
//                        InputMethodManager.HIDE_NOT_ALWAYS);
////        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
////        if (imm != null) {
////            edit_query1.requestFocus();
////            imm.showSoftInput(view, InputMethodManager.RESULT_UNCHANGED_SHOWN);
////        }
//    }
//
//    private void hideInput() {
//        // 先隐藏键盘
//        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).
//                hideSoftInputFromWindow(SearchListActivityNewBeifen.this.getCurrentFocus().getWindowToken(),
//                        InputMethodManager.HIDE_NOT_ALWAYS);
//    }
//
//    @Override
//    protected void onDestroy() {
//        presenter.onDestory();
//        super.onDestroy();
//
//    }
//
//    /**
//     * --------------------------------业务逻辑分割线----------------------------------
//     */
//
//    private void setData2(String search_key) {
//        presenter.getSearchData(DeviceUtils.getAndroidID(),"", search_key, "20", 2);
//    }
//
//    private void setData3(String search_key) {
//        hideInput();
//        niubiEmptyView3.loading("");
//        presenter.getSearchData(DeviceUtils.getAndroidID(),"", search_key, "10", 3);
//    }
//
//    @Override
//    public void OnSearchSuccess(SSearchBean sSearchBean, int i) {
//        mList = sSearchBean.getResult();
//        if (i == 2) {
//            mAdapter2.setNewData(mList);
//            if (mList.size() > 0) {
//                set_ll_vis(2);
//            }
//        }
//        if (i == 3) {
//            mAdapter3.setNewData(mList);
//            if (mList.size() == 0) {
////            mAdapter.setEmptyView(getView());
//                niubiEmptyView3.errornet("暂无数据");
//            }
//        }
//    }
//
//    @Override
//    public void OnSearchNodata(String s, int i) {
//        if (i == 3) {
//            niubiEmptyView3.errornet("暂无数据");
//        }
//    }
//
//    @Override
//    public void OnSearchFail(String s, int i) {
//        if (i == 3) {
//            niubiEmptyView3.errornet("暂无数据");
//        }
//    }
//}
