package com.fosung.lighthouse.jiaoyuziyuan.activity;


import static com.fosung.lighthouse.jiaoyuziyuan.util.EduResSelectedExecutorsUtil.ACTION_SELECTED_EXECUTOR_CHANGED;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.fosung.eduapi.bean.EduResExecutorReplyBean;
import com.fosung.eduapi.presenter.EduResTerminalPresenter;
import com.fosung.eduapi.view.EduResTerminalView;
import com.fosung.lighthouse.jiaoyuziyuan.R;
import com.fosung.lighthouse.jiaoyuziyuan.util.EduResActivitysUtil;
import com.fosung.lighthouse.jiaoyuziyuan.util.EduResCommonEvent;
import com.fosung.lighthouse.jiaoyuziyuan.util.EduResSelectedExecutorsUtil;
import com.haier.cellarette.baselibrary.toasts3.utils.MSizeUtils;
import com.zcolin.frame.app.BaseActivity;
import com.zcolin.frame.app.ResultActivityHelper;
import com.zcolin.frame.util.ActivityUtil;
import com.zcolin.frame.util.ToastUtil;
import com.zcolin.gui.zrecyclerview.BaseRecyclerAdapter;
import com.zcolin.gui.zrecyclerview.ZRecyclerView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


public class EduResSelectExecutorListAct extends BaseActivity implements EduResTerminalView {

    private static final String ACTION_NAVIGATE_TO_EXECUTOR = "ACTION_NAVIGATE_TO_EXECUTOR";

    private boolean isSingleSelect = false;//是否是单选， 默认多选
    private boolean canSelectDepartment = true;//是否能选中部门， 默认能选中

    private ArrayList<EduResExecutorReplyBean.DataBean> excludeExecutors;
    private ArrayList<EduResExecutorReplyBean.DataBean> resultList;
    private EduResTerminalPresenter eduResTerminalPresenter;

    private String searchString = "";
    ZRecyclerView zRecyclerView;
    SearchView searchView;
    BaseRecyclerAdapter<EduResExecutorReplyBean.DataBean> adapter;

    EduResExecutorReplyBean.DataBean orgBean;
    ArrayList<EduResExecutorReplyBean.DataBean> executors = new ArrayList<>();
    EduResExecutorReplyBean.DataBean currentExecutor;


    LocalBroadcastManager manager;
    BroadcastReceiver broadcastReceiver;
    CheckedTextView allSelectCheckedTextView;
    TextView hasSelectTextView;
    EduResSelectedExecutorsUtil util;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edu_res_select_executor_list);
        setToolbarTitle("选择报送终端");
        util = EduResSelectedExecutorsUtil.getInstance(this);
        EduResActivitysUtil.getInstance().addActivity(this);

        isSingleSelect = getIntent().getBundleExtra("bundle").getBoolean("isSingleSelect", false);
        canSelectDepartment = getIntent().getBundleExtra("bundle").getBoolean(
                "canSelectDepartment", true);
        excludeExecutors = getIntent().getBundleExtra("bundle").getParcelableArrayList(
                "excludeExecutors");

        configHorizontalScrollViewNavigation();
        configRecyclerView();

        searchView = getView(R.id.searchView);
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

            }
        });

        //设置相应的监听,文字变化的监听
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //在文字改变的时候回调，query是改变之后的文字
//                mSearchFragment.setSearchStr(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //文字提交的时候哦回调，newText是最后提交搜索的文字
                searchString = searchView.getQuery().toString();
                getResult();
                return false;
            }
        });


        hasSelectTextView = findViewById(R.id.hasSelectTextView);
        hasSelectTextView.setText(util.getDataList().size() == 0 ? "未选择" :
                "已选择 " + util.getDataList().size() + " 人/部门");

        manager = LocalBroadcastManager.getInstance(this);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (ACTION_NAVIGATE_TO_EXECUTOR.equals(intent.getAction())) {
                    int index = intent.getIntExtra("index", 0);
                    if (executors.size() - 1 > index) {
                        finish();
                        overridePendingTransition(0, 0);
                    }
                }
                if (ACTION_SELECTED_EXECUTOR_CHANGED.equals(intent.getAction())) {
                    matchChecked(adapter.getDatas());
                    adapter.notifyDataSetChanged();
                    hasSelectTextView.setText(util.getDataList().size() == 0 ? "未选择" :
                            "已选择 " + util.getDataList().size() + " 人/部门");
                }


            }
        };
        manager.registerReceiver(broadcastReceiver, new IntentFilter(ACTION_NAVIGATE_TO_EXECUTOR));
        manager.registerReceiver(broadcastReceiver,
                new IntentFilter(ACTION_SELECTED_EXECUTOR_CHANGED));
//        hasSelectTextView.setText(util.getDataList().size() == 0 ? "未选择" : "已选择 " + util
//        .getDataList().size() + " 人/部门");


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        manager.unregisterReceiver(broadcastReceiver);
    }

    private void configRecyclerView() {
        zRecyclerView = findViewById(R.id.zRecyclerview);
        zRecyclerView.setIsRefreshEnabled(false);
        if (!isSingleSelect) {
            View headerView =
                    getLayoutInflater().inflate(R.layout.recycler_header_edu_select_executor_department, null);
            allSelectCheckedTextView = headerView.findViewById(R.id.allSelectCheckedTextView);
            zRecyclerView.addHeaderView(headerView);
        }
        adapter = new BaseRecyclerAdapter<EduResExecutorReplyBean.DataBean>() {
            @Override
            public int getItemLayoutId(int viewType) {
                return R.layout.item_edu_select_executor_department;
            }

            @Override
            public void setUpData(CommonHolder holder, int position, int viewType,
                                  EduResExecutorReplyBean.DataBean data) {
                CheckedTextView checkedTextView = getView(holder, R.id.titleCheckedTextView);
                checkedTextView.setText(data.name);
                checkedTextView.setEnabled(data.enable);
                checkedTextView.setChecked(data.checked);

                TextView lowerLevelTextView = getView(holder, R.id.lowerLevelTextView);
//                lowerLevelTextView.setEnabled(!data.checked);
                if (data.hasChildren) {
                    //有下级
                    lowerLevelTextView.setVisibility(View.VISIBLE);
                } else {
                    //无
                    lowerLevelTextView.setVisibility(View.GONE);
                }
                checkedTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        data.checked = !data.checked;
                        notifyItemChanged(position);
                        if (isSingleSelect) {
                            //单选
                            if (data.checked) {
                                util.clear();
                                util.add(data);
                            } else {
                                util.remove(data);
                            }
                        } else {
                            //多选
                            if (data.checked) {
                                util.add(data);
                            } else {
                                util.remove(data);
                            }
                        }
                    }
                });

                lowerLevelTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(EduResSelectExecutorListAct.this,
                                EduResSelectExecutorListAct.class);
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("org", orgBean);
                        bundle.putParcelableArrayList("executors", executors);
                        bundle.putParcelable("nextExecutor", data);
                        bundle.putBoolean("isSingleSelect", isSingleSelect);
                        bundle.putBoolean("canSelectDepartment", canSelectDepartment);
                        bundle.putParcelableArrayList("excludeExecutors", excludeExecutors);
                        intent.putExtra("bundle", bundle);

                        startActivityWithCallback(intent,
                                new ResultActivityHelper.ResultActivityListener() {
                                    @Override
                                    public void onResult(int resultCode, Intent data) {
                                        if (resultCode == RESULT_OK) {
                                            setResult(RESULT_OK);
                                            finish();
                                        }
                                    }
                                });
//                        ActivityUtil.startActivity(LHGLSelectExecutorListActivity.this,
//                        LHGLSelectExecutorListActivity.class,"bundle",bundle);
                        overridePendingTransition(0, 0);

                    }
                });
            }

        };
        zRecyclerView.setAdapter(adapter);

        eduResTerminalPresenter = new EduResTerminalPresenter();
        eduResTerminalPresenter.onCreate(this);
        eduResTerminalPresenter.getEduResTerminalRequest("terminal", currentExecutor.parentId, currentExecutor.orgId, "");
    }

    @Override
    public void OnEduResTerminalSuccess(EduResExecutorReplyBean resObj) {
        ArrayList<EduResExecutorReplyBean.DataBean> temList = new ArrayList<>();

        if (resObj.datalist != null && resObj.datalist.size() > 0) {
            for (EduResExecutorReplyBean.DataBean bean : resObj.datalist) {
                bean.enable = canSelectDepartment;
            }
            temList.addAll(resObj.datalist);
        }

        resultList = temList;

        matchChecked(temList);

        adapter.setDatas(temList);
    }

    @Override
    public void OnEduResTerminalFail(String msg) {
        ToastUtil.toastShort(msg);
    }

    public void matchChecked(List<EduResExecutorReplyBean.DataBean> sourceList) {
//        int size = sourceList.size();
        int size = 0;
        for (EduResExecutorReplyBean.DataBean bean : sourceList) {
            if (bean.enable) {
                size++;
            }
        }
        if (size > 0) {//所有enable=true的才进行是否选中的匹配
            boolean allSelected = true;

            for (EduResExecutorReplyBean.DataBean bean : sourceList) {
                bean.checked = util.getDataList().contains(bean);
                if (bean.enable && !bean.checked) {
                    allSelected = false;
                }
            }
            if (allSelectCheckedTextView != null) {
                allSelectCheckedTextView.setChecked(allSelected);
            }
        }
    }


    private void configHorizontalScrollViewNavigation() {


        Bundle bundle = getIntent().getBundleExtra("bundle");
        if (bundle != null) {
            orgBean = bundle.getParcelable("org");
            ArrayList<EduResExecutorReplyBean.DataBean> superExecutors =
                    bundle.getParcelableArrayList("executors");//所有上级
            currentExecutor = bundle.getParcelable("nextExecutor");//当前执行人


            if (superExecutors != null && currentExecutor != null) {
                executors.addAll(superExecutors);
                executors.add(currentExecutor);
            } else {
                currentExecutor = orgBean;
            }
        }

        LinearLayout linearLayout = findViewById(R.id.horizontalScrollViewLinearLayout);
        TextView orgTextView = new TextView(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            orgTextView.setTextColor(getResources().getColorStateList(R.color.lower_level_text_color, getTheme()));
        }
        int padding10 = MSizeUtils.dp2px(this, 10);
        int padding15 = MSizeUtils.dp2px(this, 15);
        orgTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        orgTextView.setPaddingRelative(padding10, padding15, padding10, padding15);
        orgTextView.setEnabled(orgBean != currentExecutor);
        orgTextView.setText(orgBean.name);

        orgTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ACTION_NAVIGATE_TO_EXECUTOR);
                intent.putExtra("index", -1);
                manager.sendBroadcast(intent);
            }
        });
        linearLayout.addView(orgTextView,
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));

        for (int i = 0; i < executors.size(); i++) {

            TextView arrowTextView = new TextView(this);
            arrowTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            arrowTextView.setText(">");
            linearLayout.addView(arrowTextView,
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));

            TextView executorTextView = new TextView(this);
            executorTextView.setEnabled(executors.get(i) != currentExecutor);//最后一个设置为不可用
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                executorTextView.setTextColor(getResources().getColorStateList(R.color.lower_level_text_color, getTheme()));
            }
            executorTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            executorTextView.setPaddingRelative(padding10, padding15, padding10, padding15);
            EduResExecutorReplyBean.DataBean bean = executors.get(i);
            executorTextView.setText(bean.name);
            int finalI = i;
            executorTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ACTION_NAVIGATE_TO_EXECUTOR);
                    intent.putExtra("index", finalI);
                    manager.sendBroadcast(intent);
                }
            });
            linearLayout.addView(executorTextView,
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }


//    public void onItemCheckedTextViewClick(View view){
//        CheckedTextView checkedTextView = (CheckedTextView)view;
//        checkedTextView.setChecked(!checkedTextView.isChecked());
//
//
//    }

    public void onHeaderAllSelectCheckedTextViewClick(View view) {
        CheckedTextView checkedTextView = (CheckedTextView) view;
        checkedTextView.setChecked(!checkedTextView.isChecked());

        ArrayList<EduResExecutorReplyBean.DataBean> list = adapter.getDatas();
        for (int i = 0; i < list.size(); i++) {
            EduResExecutorReplyBean.DataBean bean = list.get(i);
            if (bean.enable) {
                if (checkedTextView.isChecked() && !bean.checked) {
                    util.add(bean);
                } else if (!checkedTextView.isChecked() && bean.checked) {
                    util.remove(bean);
                }

                bean.checked = checkedTextView.isChecked();
            }
        }
        adapter.notifyDataSetChanged();

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onToolBarLeftBtnClick() {
        super.onToolBarLeftBtnClick();
        overridePendingTransition(0, 0);
    }


    public void onConfirmButtonClick(View view) {
//        setResult(RESULT_OK);
//        finish();
        EventBus.getDefault().post(new EduResCommonEvent(EduResCommonEvent.BSZD));
        EduResActivitysUtil.getInstance().exit();
    }

    public void onHasSelectedViewClick(View view) {

        ActivityUtil.startActivity(this, EduResHasSelectedExecutorListAct.class);
    }

    public static void setSearchViewOnClickListener(View v, View.OnClickListener listener) {
        if (v instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) v;
            int count = group.getChildCount();
            for (int i = 0; i < count; i++) {
                View child = group.getChildAt(i);
                if (child instanceof LinearLayout || child instanceof RelativeLayout) {
                    setSearchViewOnClickListener(child, listener);
                }

                if (child instanceof TextView) {
                    TextView text = (TextView) child;
                    text.setFocusable(false);
                }
                child.setOnClickListener(listener);
            }
        }
    }

    private void getResult() {

        ArrayList<EduResExecutorReplyBean.DataBean> temSearchList = new ArrayList<>();
        for (EduResExecutorReplyBean.DataBean dataBean : resultList) {
            if (dataBean.name.contains(searchString)) {
                temSearchList.add(dataBean);
            }
        }
        adapter.setDatas(temSearchList);
    }

}
