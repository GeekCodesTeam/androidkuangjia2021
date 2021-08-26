package com.geek.biz1.view;

import com.geek.biz1.bean.SPolyvList1Bean;
import com.geek.libmvp.IView;

public interface HPolyvList1View extends IView {

    void OnPolyvList1Success(SPolyvList1Bean bean);
    void OnPolyvList1Nodata(String bean);
    void OnPolyvList1Fail(String msg);

}
