package com.example.slbappcomm.videoplay.quxiantu;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.slbappcomm.R;
import com.example.slbappcomm.base.SlbBaseActivity;
import com.example.slbappcomm.videoplay.quxiantu.bean.SpView4Bean;
import com.example.slbappcomm.xclchart.SplineView;
import com.haier.cellarette.baselibrary.assetsfitandroid.fileassets.GetAssetsFileMP3TXTJSONAPKUtil;


/**
 * BJColor
 */

public class QuxiantuActivity extends SlbBaseActivity {

    private SplineView view4;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_quxiantu;
    }

    @Override
    protected void setup(@Nullable Bundle savedInstanceState) {
        super.setup(savedInstanceState);
        view4 = findViewById(R.id.view4);
        String gson_url = GetAssetsFileMP3TXTJSONAPKUtil.getInstance(this).getJson(this, "jsonbean/spviewbean.json");
        SpView4Bean bean_gson = GetAssetsFileMP3TXTJSONAPKUtil.getInstance(this).JsonToObject(gson_url, SpView4Bean.class);
        view4.setSpView4Bean(bean_gson);
    }


}
