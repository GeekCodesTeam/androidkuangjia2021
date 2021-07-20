package com.example.bizyewu2.view;

import com.example.bizyewu2.bean.SPolyvList1Detail1Bean1;
import com.haier.cellarette.libmvp.mvp.IView;

public interface HPolyvList1Detail1View extends IView {

    void OnPolyvList1Detail1Success(SPolyvList1Detail1Bean1 bean);

    void OnPolyvList1Detail1Nodata(String bean);

    void OnPolyvList1Detail1Fail(String msg);

}
