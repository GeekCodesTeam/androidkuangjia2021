package com.example.libbase.plugins2;

import android.app.Activity;
import android.os.Bundle;

public interface IPlugin2 {

    int FROM_INTERNAL = 0;
    int FORM_EXTERNAL = FROM_INTERNAL+1;

    //把Context注入到插件Activity里
    void attach(Activity proxyActivity);

    void onCreate(Bundle saveInstanceState);
}
