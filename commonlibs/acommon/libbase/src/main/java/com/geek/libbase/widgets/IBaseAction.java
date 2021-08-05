package com.geek.libbase.widgets;

import android.app.Activity;

public interface IBaseAction {
    void onBackPressed();
    void onHomePressed();
    Activity who();
}
