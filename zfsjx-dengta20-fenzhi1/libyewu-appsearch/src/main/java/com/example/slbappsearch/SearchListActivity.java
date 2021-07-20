package com.example.slbappsearch;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.bizyewu2.bean.SNew1SearchBean;
import com.example.bizyewu2.bean.SNew1SearchBean1;
import com.example.bizyewu2.bean.SNew1SearchBean2;
import com.example.bizyewu2.presenter.SNew1SearchPresenter;
import com.example.bizyewu2.view.SNew1SearchView;
import com.example.libbase.base.SlbBaseActivity;
import com.example.libbase.emptyview.NiubiEmptyViewNew;
import com.example.libbase.widgets.XRecyclerView;
import com.example.slbappsearch.part1.SearchBean;
import com.example.slbappsearch.part1.searchhuancun.SearchCommManager;
import com.example.slbappsearch.part2.SearchKeyListAdapter;
import com.example.slbappsearch.part3.SearchListAdapter;
import com.haier.cellarette.baselibrary.flowlayout.FlowLayout;
import com.haier.cellarette.baselibrary.flowlayout.TagAdapter;
import com.haier.cellarette.baselibrary.flowlayout.TagFlowLayout;
import com.haier.cellarette.baselibrary.marqueelibrary.SimpleMarqueeView;
import com.haier.cellarette.baselibrary.qcode.ExpandViewRect;
import com.haier.cellarette.libwebview.hois2.HiosHelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class SearchListActivity extends SlbBaseActivity implements View.OnClickListener, SNew1SearchView {

    private LinearLayout llbg3;
    private TextView tv_del2;
    private TextView tvLeft2;
    private TextView tv_canel2;
    private LinearLayout rlSearch1;
    private TextView tvSearch1;
    private SimpleMarqueeView marqueeView3;
    private EditText edit_query1;
    private TextView tvSearch2;
    private LinearLayout ll1;
    private TagFlowLayout mFlowLayout;
    private ArrayList<SearchBean> flowBean;
    //
    private LinearLayout ll2;
    private RecyclerView recyclerView2;
    private SearchKeyListAdapter mAdapter2;
    //
    private LinearLayout ll3;
    private XRecyclerView recyclerView3;
    private SearchListAdapter mAdapter3;
    private NiubiEmptyViewNew niubiEmptyView3;

    //
    private String search_key;
    private int focus = 0;// 0:正常输入进2    1:从recyclerView2点击进3和从历史记录点击进3
    private SNew1SearchPresenter presenter;
    private List<SNew1SearchBean1> mList;


    @Override
    protected void onResume() {
//        MobclickAgent.onEvent(this, "SearchListActivity");
        super.onResume();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_list;
    }

    @Override
    protected void setup(@Nullable Bundle savedInstanceState) {
        super.setup(savedInstanceState);
        search_key = getIntent().getStringExtra("search_key");
        findview();
        onclick();
        donetwork();
    }

    private void donetwork() {
        tvLeft2.setVisibility(View.GONE);
        marqueeView3.setVisibility(View.GONE);
        tvSearch2.setVisibility(View.GONE);
        tv_canel2.setVisibility(View.VISIBLE);
        ExpandViewRect.expandViewTouchDelegate(tv_canel2, 10, 10, 10, 10);
        edit_query1.setVisibility(View.VISIBLE);
        set_ll_vis(1);
        if (!TextUtils.isEmpty(search_key)) {
            edit_query1.setHint(search_key);
        }
//        edit_query1.requestFocus();
//        showInput(edit_query1);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        get_flowlayout();
        mList = new ArrayList<>();
        presenter = new SNew1SearchPresenter();
        presenter.onCreate(this);

    }

    private TextWatcher mTextWatcher = new TextWatcher() {
        private CharSequence temp;
        private int editStart;
        private int editEnd;

        @Override
        public void beforeTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
            temp = s;
        }

        @Override
        public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
//            tv_ed_content.setText("还能输入" + (MAX_TEXT - s.length()) + "字符");
            String searchContext = edit_query1.getText().toString().trim();
            String searchHint = edit_query1.getHint().toString().trim();
            if (TextUtils.isEmpty(searchContext)) {
                tvSearch2.setVisibility(View.GONE);
                set_ll_vis(1);
                // 少一步刷新历史记录操作bufen

            } else {
                // 调用搜索方法
//                tvSearch2.setVisibility(View.GONE);
//                edit_query1.clearFocus();
//                set_ll_vis(3);
//                setData3(searchContext);
                //
                if (focus == 0) {
                    tvSearch2.setVisibility(View.VISIBLE);
                    set_ll_vis(2);
                    setData2(searchContext);
                }
                if (focus == 1) {
                    tvSearch2.setVisibility(View.GONE);
                    edit_query1.clearFocus();
                    set_ll_vis(3);
                    setData3(searchContext);
                    focus = 0;
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            editStart = edit_query1.getSelectionStart();
            editEnd = edit_query1.getSelectionEnd();
            edit_query1.setSelection(edit_query1.getText().toString().trim().length());//将光标移至文字末尾
//            if (temp.length() > MAX_TEXT) {
//                Toasty.normal(SearchListActivity.this, "限制输入" + MAX_TEXT + "字符!").show();
//                s.delete(editStart - 1, editEnd);
//                int tempSelection = editStart;
//                edt1.setText(s);
//                edt1.setSelection(tempSelection);
//            }
        }
    };

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_canel2) {
            //
            onBackPressed();
        } else if (i == R.id.tv_search2) {
            //
            edit_query1.getText().clear();
            set_ll_vis(1);
        } else if (i == R.id.tv_del2) {
            //
            del_history();
            get_flowlayout();
        } else {

        }
    }

    private void onclick() {
        tvSearch2.setOnClickListener(this);
        tv_del2.setOnClickListener(this);
        tv_canel2.setOnClickListener(this);
        edit_query1.addTextChangedListener(mTextWatcher);
        edit_query1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
//                if (keyCode == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                if (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    hideInput();
                    String searchContext = edit_query1.getText().toString().trim();
                    String searchHint = edit_query1.getHint().toString().trim();
                    String content = "";
                    if (TextUtils.isEmpty(searchContext)) {
                        if (!TextUtils.isEmpty(searchHint)) {
                            // 调用搜索方法
                            set_ll_vis(3);
                            content = searchHint;
                            setData3(searchHint);
                        }
                    } else {
                        // 调用搜索方法
                        set_ll_vis(3);
                        content = searchContext;
                        setData3(searchContext);
                    }
                    // 保存历史记录bufen
                    add_history(new SearchBean(content, content, false));

                }
                return false;
            }
        });
        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
//                Toast.makeText(getActivity(), mVals[position], Toast.LENGTH_SHORT).show();
                //view.setVisibility(View.GONE);
//                SearchBean searchBean = (SearchBean) mFlowLayout.getAdapter().getItem(position);
//                edit_query1.setText(searchBean.getText_content());
//                edit_query1.clearFocus();
//                tvSearch2.setVisibility(View.GONE);
                return true;
            }
        });
        mFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
//                getActivity().setTitle("choose:" + selectPosSet.toString());
                Log.e("--mFlowLayout---", selectPosSet.toString());
                String aa = "";
                hideInput();
                //
                Iterator<Integer> iterator = selectPosSet.iterator();
                while (iterator.hasNext()) {
//                    Log.e("--mFlowLayout---", iterator.next().toString());
                    aa = iterator.next().toString().trim();
                }
                if (TextUtils.isEmpty(aa)) {
                    return;
                }
                SearchBean searchBean = (SearchBean) mFlowLayout.getAdapter().getItem(Integer.valueOf(aa));
                focus = 1;
                edit_query1.setText(searchBean.getText_content());
                SearchBean bean2 = new SearchBean(searchBean.getText_content(), searchBean.getText_content(), false);
                add_history(bean2);

            }
        });
//        mAdapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                //item click
//                SSearchBean1 bean = (SSearchBean1) adapter.getItem(position);
//                Toasty.normal(SearchListActivity.this, bean.getBookName() + "item click").show();
//            }
//        });
        mAdapter2.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                SNew1SearchBean1 bean = (SNew1SearchBean1) adapter.getItem(position);
                int i = view.getId();
//                Toasty.normal(SearchListActivity.this, bean.getBookName() + "    " + position).show();
                focus = 1;
                edit_query1.setText(bean.getTitle());
                SearchBean bean2 = new SearchBean(bean.getTitle(), bean.getTitle(), false);
                add_history(bean2);

//                if (i == R.id.ll_bg1) {
////
//                } else {
//                }
            }
        });

        niubiEmptyView3.setRetry(new NiubiEmptyViewNew.RetryListener() {
            @Override
            public void retry() {
                if (!TextUtils.isEmpty(edit_query1.getText().toString().trim())) {
                    search_key = edit_query1.getText().toString().trim();
                }
                setData3(search_key);
            }
        });
        mAdapter3.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SNew1SearchBean1 bean = (SNew1SearchBean1) adapter.getItem(position);
                HiosHelper.resolveAd(SearchListActivity.this, SearchListActivity.this, bean.getHios());
            }
        });
        mAdapter3.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                SNew1SearchBean1 bean = (SNew1SearchBean1) adapter.getItem(position);
                int i = view.getId();
                edit_query1.clearFocus();
                HiosHelper.resolveAd(SearchListActivity.this, SearchListActivity.this, bean.getHios());
            }
        });
    }

    private void findview() {
        llbg3 = findViewById(R.id.llbg3);
        tv_del2 = findViewById(R.id.tv_del2);
        tvLeft2 = findViewById(R.id.tv_left2);
        tv_canel2 = findViewById(R.id.tv_canel2);
        rlSearch1 = findViewById(R.id.rl_search1);
        tvSearch1 = findViewById(R.id.tv_search1);
        marqueeView3 = findViewById(R.id.marqueeView3);
        edit_query1 = findViewById(R.id.edit_query1);
        tvSearch2 = findViewById(R.id.tv_search2);
        ll1 = findViewById(R.id.ll1);
        mFlowLayout = findViewById(R.id.id_flowlayout);
        ll2 = findViewById(R.id.ll2);
        recyclerView2 = findViewById(R.id.recycler_view2);
        ll3 = findViewById(R.id.ll3);
        recyclerView3 = findViewById(R.id.recycler_view3);
        //
        recyclerView2.setLayoutManager(new GridLayoutManager(this, 1, RecyclerView.VERTICAL, false));
        mAdapter2 = new SearchKeyListAdapter(R.layout.activity_search_list_item2);
        recyclerView2.setAdapter(mAdapter2);
        //
        recyclerView3.setLayoutManager(new GridLayoutManager(this, 1, RecyclerView.VERTICAL, false));
        mAdapter3 = new SearchListAdapter(R.layout.activity_search_list_item3);
        recyclerView3.setAdapter(mAdapter3);
        niubiEmptyView3 = new NiubiEmptyViewNew();
        niubiEmptyView3.bind(this, recyclerView3, mAdapter3);

    }

    /**
     * 设置页面切换 1:历史记录 2:搜索关键字 3:搜索结果列表
     */
    private void set_ll_vis(int which_ll) {
        ll1.setVisibility(View.GONE);
        ll2.setVisibility(View.GONE);
        ll3.setVisibility(View.GONE);
        if (which_ll == 1) {
            ll1.setVisibility(View.VISIBLE);
        }
        if (which_ll == 2) {
            ll2.setVisibility(View.VISIBLE);
        }
        if (which_ll == 3) {
            ll3.setVisibility(View.VISIBLE);
        }
    }

    private List<SearchBean> add_history(SearchBean searchBean) {
        flowBean = new ArrayList<>();
        flowBean = SearchCommManager.getInstance().get("search_history");
        flowBean.add(0, searchBean);
        SearchCommManager.getInstance().Add("search_history", flowBean);
        return SearchCommManager.getInstance().get("search_history");
    }

    private List<SearchBean> get_history() {
        flowBean = new ArrayList<>();
        if (SearchCommManager.getInstance().get("search_history") != null && SearchCommManager.getInstance().get("search_history").size() > 0) {
            flowBean = SearchCommManager.getInstance().get("search_history");
        }
        return flowBean;
    }

    private void del_history() {
        if (SearchCommManager.getInstance().get("search_history") != null && SearchCommManager.getInstance().get("search_history").size() > 0) {
            SearchCommManager.getInstance().del("search_history");
        }
    }

    // 如果超过20条 删除21以后bufen
    private List<SearchBean> get_history20() {
        ArrayList<SearchBean> mlist20 = new ArrayList<>();
        if (get_history().size() >= 20) {
            for (int i = 19; i >= 0; i--) {
                mlist20.add(get_history().get(i));
            }
            del_history();
            for (SearchBean searchBean : mlist20) {
                add_history(searchBean);
            }
        }
        return get_history();
    }

    private void get_flowlayout() {
        flowBean = new ArrayList<>();
        final LayoutInflater mInflater = LayoutInflater.from(this);
        mFlowLayout.setAdapter(new TagAdapter(get_history20()) {
            @Override
            public View getView(FlowLayout parent, int position, Object bean) {
                TextView tv = (TextView) mInflater.inflate(R.layout.activity_search_tv,
                        mFlowLayout, false);
                SearchBean bean1 = (SearchBean) bean;
                tv.setText(bean1.getText_content());
                return tv;
            }
        });
    }

    private void showInput(View view) {
        // 显示键盘
        edit_query1.requestFocus();
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).
                hideSoftInputFromWindow(SearchListActivity.this.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        if (imm != null) {
//            edit_query1.requestFocus();
//            imm.showSoftInput(view, InputMethodManager.RESULT_UNCHANGED_SHOWN);
//        }
    }

    private void hideInput() {
        // 先隐藏键盘
        if (getCurrentFocus() != null) {
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    protected void onDestroy() {
        presenter.onDestory();
        super.onDestroy();

    }

    /**
     * --------------------------------业务逻辑分割线----------------------------------
     */

    private void setData2(String search_key) {
        presenter.getNew1SearchData(search_key, "99", 2);
    }

    private void setData3(String search_key) {
        hideInput();
        niubiEmptyView3.loading("");
        presenter.getNew1SearchData(search_key, "99", 3);
    }

    @Override
    public void OnNew1SearchSuccess(SNew1SearchBean sSearchBean, int i) {
//        mList = sSearchBean.getResult();
        mList = new ArrayList<>();
        //TODO test
        mList.add(new SNew1SearchBean1("https://sairobo-edu-elephant.oss-cn-hangzhou.aliyuncs.com/picbook/16/54/coverA.jpg?x-oss-process=image/resize,m_lfit,h_600,w_600", "https://sairobo-edu-elephant.oss-cn-hangzhou.aliyuncs.com/picbook/16/54/coverB.jpg?x-oss-process=image/resize,m_lfit,h_600,w_600", new SNew1SearchBean2(), "年的故事", false, "across", "", "54", "bookItem", "", "", "", "", "", "", "", ""));
        mList.add(new SNew1SearchBean1("https://sairobo-edu-elephant.oss-cn-hangzhou.aliyuncs.com/picbook/16/54/coverA.jpg?x-oss-process=image/resize,m_lfit,h_600,w_600", "https://sairobo-edu-elephant.oss-cn-hangzhou.aliyuncs.com/picbook/16/54/coverB.jpg?x-oss-process=image/resize,m_lfit,h_600,w_600", new SNew1SearchBean2(), "年的故事", false, "across", "", "54", "bookItem", "", "", "", "", "", "", "", ""));
        mList.add(new SNew1SearchBean1("https://sairobo-edu-elephant.oss-cn-hangzhou.aliyuncs.com/picbook/16/54/coverA.jpg?x-oss-process=image/resize,m_lfit,h_600,w_600", "https://sairobo-edu-elephant.oss-cn-hangzhou.aliyuncs.com/picbook/16/54/coverB.jpg?x-oss-process=image/resize,m_lfit,h_600,w_600", new SNew1SearchBean2(), "年的故事", false, "across", "", "54", "bookItem", "", "", "", "", "", "", "", ""));
        mList.add(new SNew1SearchBean1("https://sairobo-edu-elephant.oss-cn-hangzhou.aliyuncs.com/picbook/16/54/coverA.jpg?x-oss-process=image/resize,m_lfit,h_600,w_600", "https://sairobo-edu-elephant.oss-cn-hangzhou.aliyuncs.com/picbook/16/54/coverB.jpg?x-oss-process=image/resize,m_lfit,h_600,w_600", new SNew1SearchBean2(), "年的故事", false, "across", "", "54", "bookItem", "", "", "", "", "", "", "", ""));
        mList.add(new SNew1SearchBean1("https://sairobo-edu-elephant.oss-cn-hangzhou.aliyuncs.com/picbook/16/54/coverA.jpg?x-oss-process=image/resize,m_lfit,h_600,w_600", "https://sairobo-edu-elephant.oss-cn-hangzhou.aliyuncs.com/picbook/16/54/coverB.jpg?x-oss-process=image/resize,m_lfit,h_600,w_600", new SNew1SearchBean2(), "年的故事", false, "across", "", "54", "bookItem", "", "", "", "", "", "", "", ""));
        mList.add(new SNew1SearchBean1("https://sairobo-edu-elephant.oss-cn-hangzhou.aliyuncs.com/picbook/16/54/coverA.jpg?x-oss-process=image/resize,m_lfit,h_600,w_600", "https://sairobo-edu-elephant.oss-cn-hangzhou.aliyuncs.com/picbook/16/54/coverB.jpg?x-oss-process=image/resize,m_lfit,h_600,w_600", new SNew1SearchBean2(), "年的故事", false, "across", "", "54", "bookItem", "", "", "", "", "", "", "", ""));
        mList.add(new SNew1SearchBean1("https://sairobo-edu-elephant.oss-cn-hangzhou.aliyuncs.com/picbook/16/54/coverA.jpg?x-oss-process=image/resize,m_lfit,h_600,w_600", "https://sairobo-edu-elephant.oss-cn-hangzhou.aliyuncs.com/picbook/16/54/coverB.jpg?x-oss-process=image/resize,m_lfit,h_600,w_600", new SNew1SearchBean2(), "年的故事", false, "across", "", "54", "bookItem", "", "", "", "", "", "", "", ""));
        mList.add(new SNew1SearchBean1("https://sairobo-edu-elephant.oss-cn-hangzhou.aliyuncs.com/picbook/16/54/coverA.jpg?x-oss-process=image/resize,m_lfit,h_600,w_600", "https://sairobo-edu-elephant.oss-cn-hangzhou.aliyuncs.com/picbook/16/54/coverB.jpg?x-oss-process=image/resize,m_lfit,h_600,w_600", new SNew1SearchBean2(), "年的故事", false, "across", "", "54", "bookItem", "", "", "", "", "", "", "", ""));
        if (i == 2) {
            mAdapter2.setNewData(mList);
            if (mList.size() > 0) {
                set_ll_vis(2);
            }
        }
        if (i == 3) {
            mAdapter3.setNewData(mList);
            if (mList.size() == 0) {
//            mAdapter.setEmptyView(getView());
                niubiEmptyView3.nodata("暂无数据");
            }
        }
    }

    @Override
    public void OnNew1SearchNodata(String s, int i) {
        if (i == 3) {
            niubiEmptyView3.nodata("暂无数据");
        }
    }

    @Override
    public void OnNew1SearchFail(String s, int i) {
        if (i == 3) {
            niubiEmptyView3.errornet("网络异常，请检查网络连接！");
        }
    }
}
