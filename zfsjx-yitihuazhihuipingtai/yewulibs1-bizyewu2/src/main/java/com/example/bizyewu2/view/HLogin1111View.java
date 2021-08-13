package com.example.bizyewu2.view;

import com.example.bizyewu2.bean.HLogin1111Bean;
import com.geek.libmvp.IView;

public interface HLogin1111View extends IView {

    void OnLogin1111Success(HLogin1111Bean bean);

    void OnLogin1111Nodata(String bean);

    void OnLogin1111Fail(String msg);

}
