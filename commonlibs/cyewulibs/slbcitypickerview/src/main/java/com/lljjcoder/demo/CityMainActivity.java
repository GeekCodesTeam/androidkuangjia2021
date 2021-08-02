package com.lljjcoder.demo;

import android.os.Bundle;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lljjcoder.R;
import com.lljjcoder.style.citylist.utils.CityListLoader;

import java.util.ArrayList;
import java.util.List;

public class CityMainActivity extends AppCompatActivity {

    RecyclerView mCitypickerRv;

    CityPickerAdapter mCityPickerAdapter;

    String[] mTitle = new String[]{"城市列表", "ios选择器", "三级列表", "仿京东","自定义数据源"};

    Integer[] mIcon = new Integer[]{R.drawable.ic_citypicker_onecity, R.drawable.ic_citypicker_ios,
            R.drawable.ic_citypicker_three_city, R.drawable.ic_citypicker_jingdong, R.drawable.ic_citypicker_custome};

    private List<CityPickerStyleBean> mCityPickerStyleBeanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citymain);
        findView();
        setData();
        initRecyclerView();
        //TODO test
        /**
         * 预先加载一级列表所有城市的数据
         */
        CityListLoader.getInstance().loadCityData(this);

        /**
         * 预先加载三级列表显示省市区的数据
         */
        CityListLoader.getInstance().loadProData(this);
    }

    private void findView() {
        mCitypickerRv = (RecyclerView) findViewById(R.id.citypicker_rv);
    }

    private void setData() {
        for (int i = 0; i < mTitle.length; i++) {
            CityPickerStyleBean cityPickerStyleBean = new CityPickerStyleBean();
            cityPickerStyleBean.setTitle(mTitle[i]);
            cityPickerStyleBean.setResourId(mIcon[i]);
            mCityPickerStyleBeanList.add(cityPickerStyleBean);
        }
    }

    private void initRecyclerView() {

        mCitypickerRv.addItemDecoration(new DividerGridItemDecoration(this));
        mCitypickerRv.setLayoutManager(new GridLayoutManager(this, 3));

        mCityPickerAdapter = new CityPickerAdapter(CityMainActivity.this, mCityPickerStyleBeanList);
        mCitypickerRv.setAdapter(mCityPickerAdapter);
        mCityPickerAdapter.setmOnItemClickListener(new CityPickerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                if (null != mCityPickerAdapter && null != mCityPickerAdapter.getData()
                        && !mCityPickerAdapter.getData().isEmpty()
                        && null != mCityPickerAdapter.getData().get(position)) {
                    gotoDetail(mCityPickerAdapter.getData().get(position).getResourId());
                }
            }
        });

    }

    /**
     * 选择相关样式
     *
     * @param resourId
     */
    private void gotoDetail(int resourId) {
        if (resourId == R.drawable.ic_citypicker_onecity) {
            ActivityUtils.getInstance().showActivity(CityMainActivity.this, CitypickerListActivity.class);
        } else if (resourId == R.drawable.ic_citypicker_ios) {
            ActivityUtils.getInstance().showActivity(CityMainActivity.this, CitypickerWheelActivity.class);
        } else if (resourId == R.drawable.ic_citypicker_three_city) {
            ActivityUtils.getInstance().showActivity(CityMainActivity.this, CitypickerThreeListActivity.class);
        } else if (resourId == R.drawable.ic_citypicker_jingdong) {
            ActivityUtils.getInstance().showActivity(CityMainActivity.this, CitypickerJDActivity.class);
        } else if (resourId == R.drawable.ic_citypicker_custome) {
            ActivityUtils.getInstance().showActivity(CityMainActivity.this, CityPickerCustomeDataActivity.class);
        }
    }

}
