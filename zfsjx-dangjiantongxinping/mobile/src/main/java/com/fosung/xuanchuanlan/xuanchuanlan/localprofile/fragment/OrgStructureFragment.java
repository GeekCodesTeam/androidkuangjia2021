package com.fosung.xuanchuanlan.xuanchuanlan.localprofile.fragment;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.fosung.frameutils.http.response.ZResponse;
import com.fosung.frameutils.imageloader.ImageLoaderUtils;
import com.fosung.xuanchuanlan.R;
import com.fosung.xuanchuanlan.common.base.BaseFragment;
import com.fosung.xuanchuanlan.xuanchuanlan.main.http.XCLHttp;
import com.fosung.xuanchuanlan.xuanchuanlan.main.http.XCLHttpUrlMaster;
import com.fosung.xuanchuanlan.xuanchuanlan.notice.http.entity.NoticeListReply;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrgStructureFragment extends BaseFragment {

    private ImageView orgImageView;
    private String[] requestTag = new String[1];

    public OrgStructureFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getRootViewLayId() {
        return R.layout.fragment_org_structure;
    }

    @Override
    protected void createView(@Nullable Bundle savedInstanceState) {
        super.createView(savedInstanceState);
        orgImageView = getView(R.id.id_org_imageview);
        requestResources();
    }

    private void requestResources() {

        Map<String, String> map = new HashMap<String, String>();

        requestTag[0] = XCLHttp.postJson(XCLHttpUrlMaster.ORGSTRUCTURE, map, new ZResponse<NoticeListReply>(NoticeListReply.class) {

            @Override
            public void onSuccess(Response response, final NoticeListReply resObj) {
                try {
                    if (resObj.datalist != null && resObj != null && !resObj.datalist.isEmpty()) {
                        String url = resObj.datalist.get(0).imgurl;
                        ImageLoaderUtils.displayImage(mActivity, url, orgImageView);
                    }else {
                        Toast.makeText(mActivity,"暂无数据",Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int code, String error) {
                super.onError(code, error);
            }
        });

    }

    @Override
    public void onDestroy() {
        XCLHttp.cancelRequest(requestTag);
        super.onDestroy();
    }

}
