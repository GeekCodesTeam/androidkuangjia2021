package com.fosung.xuanchuanlan.xuanchuanlan.notice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.fosung.frameutils.http.response.ZResponse;
import com.fosung.xuanchuanlan.R;
import com.fosung.xuanchuanlan.common.base.ActivityParam;
import com.fosung.xuanchuanlan.common.base.BaseActivity;
import com.fosung.xuanchuanlan.xuanchuanlan.main.http.XCLHttp;
import com.fosung.xuanchuanlan.xuanchuanlan.main.http.XCLHttpUrlMaster;
import com.fosung.xuanchuanlan.xuanchuanlan.notice.adapter.RedBackRecyclerViewAdapter;
import com.fosung.xuanchuanlan.xuanchuanlan.notice.http.entity.NoticeListReply;
import com.fosung.xuanchuanlan.xuanchuanlan.notice.http.entity.NoticeTypeReply;
import com.zcolin.gui.zrecyclerview.BaseRecyclerAdapter;
import com.zcolin.gui.zrecyclerview.ZRecyclerView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

@ActivityParam(isShowToolBar = false)
public class RedBackplaneListActivity extends BaseActivity {

    private ZRecyclerView mRecyclerView;
    private RadioGroup mRadioGroup;
    private String noticeID;
    private List<NoticeTypeReply.DatalistBean> typeList;
    private RedBackRecyclerViewAdapter redBackRecyclerViewAdapter;
    private String[] requestTag = new String[1];
    private int mPage = 0;//当前页
    private int mPageSize = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redbackplane_list);
        mRadioGroup = (RadioGroup) findViewById(R.id.id_red_radiogroup);
        requestTypeData();
        initRecyclerView();
    }

    public void initRecyclerView() {

        mRecyclerView = (ZRecyclerView) findViewById(R.id.id_red_recyclerview);
        mRecyclerView.setGridLayout(true, 3);
        mRecyclerView.setIsLoadMoreEnabled(false);
        mRecyclerView.setOnPullLoadMoreListener(new ZRecyclerView.PullLoadMoreListener() {

            @Override
            public void onRefresh() {
                mPage = 0;
                requestResources(true);
            }

            @Override
            public void onLoadMore() {
//                mPage++;
//                requestResources(false);
            }
        });

    }

    /**
     * 分类专题
     */

    private void requestResources(final boolean isClear) {

        JSONObject obj = new JSONObject();
        try {
            obj.put("id",noticeID);
        }catch (Exception e){}

        requestTag[0] = XCLHttp.postJson(XCLHttpUrlMaster.REDLIST, obj.toString(), new ZResponse<NoticeListReply>(NoticeListReply.class) {

            @Override
            public void onSuccess(Response response, NoticeListReply resObj) {
                try {
                    if (resObj.datalist == null||resObj == null||resObj.datalist.isEmpty()) {
                        mRecyclerView.setNoMore(false);
                        mRecyclerView.setIsShowNoMore(true);
                        setDataToRecyclerView(null, isClear);
                        return;
                    }
                    if (mPage == 0)
                        setDataToRecyclerView(resObj.datalist, isClear);
                    else
                        setDataToRecyclerView(resObj.datalist, isClear);

                } catch (Exception e) {
                    e.printStackTrace();
                    setDataToRecyclerView(null, isClear);
                }
            }

            @Override
            public void onFinished() {
                super.onFinished();
                mRecyclerView.setPullLoadMoreCompleted();
            }

            @Override
            public void onError(int code, String error) {
                super.onError(code, error);
                setDataToRecyclerView(null, isClear);
            }
        });

    }


    /**
     * @param isClear 是否清除原来的数据
     */
    public void setDataToRecyclerView(List<NoticeListReply.DatalistBean> list, boolean isClear) {
        if (redBackRecyclerViewAdapter == null) {
            redBackRecyclerViewAdapter = new RedBackRecyclerViewAdapter(mActivity);
            mRecyclerView.setAdapter(redBackRecyclerViewAdapter);
        }

        mRecyclerView.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<NoticeListReply.DatalistBean>() {

            @Override
            public void onItemClick(View covertView, int position, NoticeListReply.DatalistBean data) {
                Intent intent = new Intent(RedBackplaneListActivity.this, RedBackplaneDetailActivity.class);
                intent.putExtra("url",data.img);
                startActivity(intent);
            }
        });

        if (list != null) {
            if (isClear) {
                redBackRecyclerViewAdapter.setDatas(list);
            } else {
                redBackRecyclerViewAdapter.addDatas(list);
            }
            if (list.size() < mPageSize) {
                mRecyclerView.setNoMore(true);
                mRecyclerView.setIsShowNoMore(false);
            }else{
                mRecyclerView.setNoMore(false);
                mRecyclerView.setIsShowNoMore(true);
            }
            redBackRecyclerViewAdapter.notifyDataSetChanged();
        } else {
            redBackRecyclerViewAdapter.setDatas(list);
            mRecyclerView.setNoMore(false);
            mRecyclerView.setIsShowNoMore(true);
            redBackRecyclerViewAdapter.notifyDataSetChanged();
        }

    }

    //请求类型
    private void requestTypeData() {

        Map<String, String> map = new HashMap<String, String>();

        XCLHttp.post(XCLHttpUrlMaster.REDTYPE, map, new ZResponse<NoticeTypeReply>(NoticeTypeReply.class) {

            @Override
            public void onSuccess(Response response, NoticeTypeReply resObj) {
                if (resObj.datalist != null && resObj.datalist.size() > 0) {
                    typeList = resObj.datalist;
                    addRadioButtons();
                    noticeID = typeList.get(0).code;
                    mRecyclerView.refreshWithPull();
                }else {
                    Toast.makeText(RedBackplaneListActivity.this,"暂无数据",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(int code, String error) {
                super.onError(code, error);
            }
        });

    }

    //动态添加视图
    public void addRadioButtons(){

        int index = 0;
        for(NoticeTypeReply.DatalistBean data:typeList){
            setRaidBtnAttribute(data,index);
            index++;
        }

    }

    private void setRaidBtnAttribute(NoticeTypeReply.DatalistBean data, final int id){

        final RadioButton radioButton = (RadioButton) LayoutInflater.from(this).inflate(R.layout.custom_radiobutton_layout,null);
        radioButton.setId( id );
        radioButton.setText(data.name);
        if (id == 0) {
            radioButton.setChecked(true);
        }else {
            radioButton.setChecked(false);
        }

        radioButton.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                noticeID = typeList.get(id).code;
                mRecyclerView.refreshWithPull();
            }
        });

        mRadioGroup.addView(radioButton);

        if (id == typeList.size() - 1) {
            return;
        }

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) radioButton
                .getLayoutParams();
        layoutParams.setMargins(0, 0,  30, 0);
        radioButton.setLayoutParams(layoutParams);

    }
}
