package com.fosung.lighthouse.jiaoyuziyuan.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.fosung.eduapi.bean.EduResInfoBean;
import com.fosung.eduapi.bean.EduResourceDetailBean;
import com.fosung.lighthouse.jiaoyuziyuan.R;
import com.fosung.lighthouse.jiaoyuziyuan.adapter.EduDetailInfoAdapter;
import com.zcolin.frame.app.BaseFrameFrag;
import com.zcolin.gui.zrecyclerview.ZRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A fragment representing a list of Items.
 */
public class EduDetailInfoFragment extends BaseFrameFrag {

    private EduDetailInfoAdapter mRecyclerViewAdapter;
    private ZRecyclerView zRecyclerView;
    private ArrayList<EduResInfoBean.DatalistBean> datas = new ArrayList<>();

    @SuppressWarnings("unused")
    public static EduDetailInfoFragment newInstance() {
        return new EduDetailInfoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().removeAllStickyEvents();
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    protected int getRootViewLayId() {
        return R.layout.fragment_edu_detail_info_list;
    }

    @Override
    protected void createView(@Nullable Bundle savedInstanceState) {
        super.createView(savedInstanceState);
        zRecyclerView = getView(R.id.pullLoadMoreRecyclerView);
        zRecyclerView.setIsProceeConflict(true);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(EduResourceDetailBean reply) {
        datas.add(getMapData("资源类型", reply.getData().getType_dict(), "v-text"));
        if (reply.getData().getFbzdList().size() > 0) {
            String content = null;
            for (EduResourceDetailBean.DataBean.FbzdListBean bean :
                    reply.getData().getFbzdList()) {
                content = (content == null ? "" :  content + "\n") + bean.getShortOrgName() + "-" + bean.getParentName() + "-" + bean.getName();
            }

            datas.add(getMapData("报送终端", content, "v-text"));
        } else {
            datas.add(getMapData("报送终端", "", "v-text"));
        }

        if ("VIDEO".equals(reply.getData().getType()) || "AUDIO".equals(reply.getData().getType())) {
            datas.add(getMapData("资源名称", reply.getData().getName(), "v-text"));
            datas.add(getMapData("时长", reply.getData().getDurationDisplay(), "v-text"));
            datas.add(getMapData("学时", reply.getData().getStudyHour() + "", "v-text"));
            datas.add(getMapData("制作单位", reply.getData().getProducer(), "v-text"));
            datas.add(getMapData("制作日期", reply.getData().getProducerTime(), "v-text"));
            datas.add(getMapData("关键字", reply.getData().getWords(), "v-text"));
        }

        if ("AUDIO".equals(reply.getData().getType())) {
            datas.add(getMapData("内容摘要", reply.getData().getIntroduction(), "v-text"));
            datas.add(getMapData("附件", null, "v-file"));

        } else if ("VIDEO".equals(reply.getData().getType())) {
            datas.add(getMapData("视频类型", reply.getData().getResourceAvType(), "v-text"));
            datas.add(getMapData("内容分类", reply.getData().getContentTypeNames(), "v-text"));
            datas.add(getMapData("内容摘要", reply.getData().getIntroduction(), "v-text"));
            datas.add(getMapData("课件封面", reply.getData().getCoverUrl(), "v-uploadImage"));
            datas.add(getMapData("附件", null, "v-file"));
        } else {
            datas.add(getMapData("引题或题肩", reply.getData().getSubject(), "v-text"));
            datas.add(getMapData("主标题(资源名称)", reply.getData().getName(), "v-text"));
            datas.add(getMapData("副标题", reply.getData().getSubtitle(), "v-text"));
            datas.add(getMapData("简短标题", reply.getData().getShortTitle(), "v-text"));
            datas.add(getMapData("关键字", reply.getData().getWords(), "v-text"));
            datas.add(getMapData("内容摘要", reply.getData().getIntroduction(), "v-text"));
            datas.add(getMapData("制作单位", reply.getData().getProducer(), "v-text"));
            datas.add(getMapData("制作日期", reply.getData().getProducerTime(), "v-text"));
            datas.add(getMapData("作者", reply.getData().getAuthor(), "v-text"));
            datas.add(getMapData("封面", reply.getData().getCoverUrl(), "v-uploadImage"));
            datas.add(getMapData("来源", reply.getData().getArticleSource(), "v-text"));
            datas.add(getMapData("URL", reply.getData().getArticleUrl(), "v-text"));
            datas.add(getMapData("备注信息", reply.getData().getRemark(), "v-text"));
            datas.add(getMapData("插入视频封面", reply.getData().getVideoImg(), "v-uploadImage"));
            datas.add(getMapData("附件", null, "v-file"));
        }

        setDataToRecyclerView(datas, reply.getData().getAttachment());
    }

    private EduResInfoBean.DatalistBean getMapData(String key, String vaule, String type) {
        EduResInfoBean.DatalistBean bean = new EduResInfoBean.DatalistBean();
        bean.setLabel(key);
        bean.setValue(vaule);
        bean.setType(type);
        return bean;
    }


    /**
     *
     */
    public void setDataToRecyclerView(List<EduResInfoBean.DatalistBean> list, List<EduResourceDetailBean.DataBean.AttachmentBean> fileList) {

        if (mRecyclerViewAdapter == null) {
            mRecyclerViewAdapter = new EduDetailInfoAdapter(mActivity);
            zRecyclerView.setAdapter(mRecyclerViewAdapter);
        }
        mRecyclerViewAdapter.setFileData(fileList);
        mRecyclerViewAdapter.setDatas(list);
    }
}