package com.example.bizyewu2.view;

import com.example.bizyewu2.bean.HMobid2Bean;
import com.haier.cellarette.libmvp.mvp.IView;

public interface HMobID2View extends IView {

    void OnMobID2Success(HMobid2Bean bean);
    void OnMobID2Nodata(String bean);
    void OnMobID2Fail(String msg);

}
