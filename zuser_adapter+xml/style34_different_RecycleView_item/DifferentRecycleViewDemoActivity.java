/**
 * Copyright  2016,  Smart  Haier <br />
 * All  rights  reserved. <br />
 * Description: 我的宅配轨迹 <br />
 * Author: qibin <br />
 * Date: 2016年10月31日13:48:57 <br />
 * FileName: MyDeliveryActivity.java <br />
 * History: <br />
 * Author: qibin <br />
 * Modification:
 */

package com.haiersmart.sfnation.demo;

import android.graphics.RectF;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.haiersmart.commonbizlib.net.Net;
import com.haiersmart.sfnation.R;
import com.haiersmart.sfnation.api.DeliveryApi;
import com.haiersmart.sfnation.application.FridgeApplication;
import com.haiersmart.sfnation.base.BaseActivity;
import com.haiersmart.sfnation.bizutils.DataProvider;
import com.haiersmart.sfnation.bizutils.ParamsUtils;
import com.haiersmart.sfnation.demo.adapter.DemoDifferentRecycleViewAdapter;
import com.haiersmart.sfnation.domain.HomedDelivery;
import com.haiersmart.sfnation.domain.HomedDeliveryMeal;
import com.haiersmart.sfnation.widget.EmptyView;
import com.haiersmart.sfnation.widget.SmartBar;
import com.haiersmart.utilslib.data.SpUtils;

import org.loader.glin.Callback;
import org.loader.glin.Result;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import zhy.com.highlight.HighLight;
import zhy.com.highlight.shape.CircleLightShape;
import zhy.com.highlight.view.HightLightView;

/**
 * <p>function: 我的宅配轨迹</p>
 * <p>description: 我的宅配轨迹</p>
 * <p>history: 2016年12月26日18:27:14
 * <p>Author: shining</p>
 * <p>modification:</p>
 */

public class DifferentRecycleViewDemoActivity extends BaseActivity implements EmptyView.RetryListener {

    public static final String SP_DELIVERY_ONCE = "delivery_once";
    @BindView(R.id.empty_view)
    EmptyView mEmptyView;
    @BindView(R.id.content_container)
    View mContentContainerView;
    @BindView(R.id.delivery_notice)
    TextView mDeliveryNoticeTextView;
    @BindView(R.id.delivery_date)
    TextView mDeliveryDateTextView;
    @BindView(R.id.smart_bar)
    SmartBar mSmartBar;
    @BindView(R.id.recyclerView1)
    RecyclerView recyclerView1;
    private DemoDifferentRecycleViewAdapter mAdapter;

    /**
     * 高亮
     */
    private HighLight mHighLight;

    @Override
    protected int getLayoutId() {
        return R.layout.demo_activity_my_delivery_layout;
    }

    @Override
    protected void setup(@Nullable Bundle savedInstanceState) {
        super.setup(savedInstanceState);
        mEmptyView.bind(mContentContainerView).setRetryListener(this);
        mSmartBar.setOnKeyListener(new SmartBar.OnKeyListener() {
            @Override
            public void onBackPressed() {
                DifferentRecycleViewDemoActivity.this.onBackPressed();
            }

            @Override
            public void onHomePressed() {
                DifferentRecycleViewDemoActivity.this.onHomePressed();
            }
        });
        //TODO
        mAdapter = new DemoDifferentRecycleViewAdapter(this);
        LinearLayoutManager mLinearLayoutManager1 = new LinearLayoutManager(this);
        mLinearLayoutManager1.setOrientation(OrientationHelper.HORIZONTAL);
        recyclerView1.setLayoutManager(mLinearLayoutManager1);
        recyclerView1.setAdapter(mAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        retry();
    }

    public void doNetWork() {
        JSONObject p = new JSONObject();
        p.put("page_size", 9999999);
        p.put("page_no", 1);
        p.put("user_id", DataProvider.getUser_id());
        p.put("user_type", 1);
        p.put("order_source", "Fridge");
        p.put("order_mode", "HomeDelivery");
        p.put("order_status", 0);

        Net.build(DeliveryApi.class, getIdentifier()).homeDelivery(ParamsUtils.just(p))
                .enqueue(new Callback<HomedDelivery>() {
                    @Override
                    public void onResponse(Result<HomedDelivery> result) {
                        if (!result.isOK()) {
                            mEmptyView.errorNet();
                            return;
                        }

                        HomedDelivery data = result.getResult();
                        if (data == null) {
                            mEmptyView.nodata();
                            return;
                        }

                        mEmptyView.success();
                        if (data.getHome_delivery_used() == 0 && data.getHome_delivery_can_use() == 0) {
                            mDeliveryNoticeTextView.setVisibility(View.INVISIBLE);
                            mDeliveryDateTextView.setVisibility(View.INVISIBLE);
                        } else {
                            mDeliveryNoticeTextView.setVisibility(View.VISIBLE);
                            mDeliveryDateTextView.setVisibility(View.VISIBLE);
                            mDeliveryNoticeTextView.setText(Html.fromHtml("您已使用免费宅配" + data.getHome_delivery_used() + "次，剩余<font color='#FFA000'>" + data.getHome_delivery_can_use() + "</font>次数"));
                            mDeliveryDateTextView.setText("宅配有效期：" + data.getExpiry_date());
                        }

                        List<HomedDeliveryMeal> listResult = new ArrayList<>();
                        if (data.getUnlocked_meal() != null) {
                            listResult.addAll(data.getUnlocked_meal());
                        }

                        if (data.getLocked_meal() != null) {
                            listResult.addAll(data.getLocked_meal());
                        }
                        mAdapter.setContacts(listResult);
                        mAdapter.notifyDataSetChanged();
                        if (!listResult.isEmpty()) {
                            highLight();
                        }
                    }
                });
    }

    // 首次进入，高亮开始
    private boolean shouldNextClickDismiss;

    private void highLight() {
        boolean once = (boolean) SpUtils.getInstance(FridgeApplication.get()).get(SP_DELIVERY_ONCE, false);
        if (once) {
            return;
        }
        highLightChild();
    }

    private void highLightChild() {
        if (recyclerView1.getChildCount() == 0) {
            recyclerView1.post(new Runnable() {
                @Override
                public void run() {
                    highLightChild();
                }
            });
            return;
        }
        mHighLight = new HighLight(this);
        mHighLight.intercept(false)
                .addHighLight(recyclerView1.getChildAt(0).findViewById(R.id.circle_bg),
                        R.layout.delivery_highlight_layout,
                        new HighLight.OnPosCallback() {
                            @Override
                            public void getPos(float rightMargin, float bottomMargin,
                                               RectF rectF, HighLight.MarginInfo marginInfo) {
                                marginInfo.leftMargin = rectF.right - rectF.width() / 2;
                                marginInfo.topMargin = getResources().getDimension(R.dimen.y100);
                            }
                        }, new CircleLightShape())
                .show()
                .viewClick(R.id.btn, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (shouldNextClickDismiss) {
                            mHighLight.remove();
                            recyclerView1.setLayoutFrozen(false);
                            SpUtils.getInstance(FridgeApplication.get()).put(SP_DELIVERY_ONCE, true);
                            return;
                        }

                        HightLightView hightLightView = mHighLight.getHightLightView();

                        hightLightView.findViewById(R.id.notice).setVisibility(View.INVISIBLE);
                        hightLightView.findViewById(R.id.arrow).setVisibility(View.INVISIBLE);
                        hightLightView.findViewById(R.id.notice_2).setVisibility(View.VISIBLE);
                        hightLightView.findViewById(R.id.arrow_2).setVisibility(View.VISIBLE);

                        mHighLight.getHightLightView().blur(false);
                        shouldNextClickDismiss = !shouldNextClickDismiss;
                    }
                });
        recyclerView1.setLayoutFrozen(true);
    }
    // 首次进入，高亮结束

    @Override
    public void retry() {
        mEmptyView.loading();
        doNetWork();
    }
}
