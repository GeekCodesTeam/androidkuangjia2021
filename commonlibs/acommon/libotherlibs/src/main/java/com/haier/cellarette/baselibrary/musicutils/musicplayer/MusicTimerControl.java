package com.haier.cellarette.baselibrary.musicutils.musicplayer;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

public class MusicTimerControl implements Observer {

    private MyObservable myObservable;
    private Context context;
    private TextView tv_volume;
    private SimpleDateFormat simpleDateFormat;

    public MusicTimerControl(Context context, TextView tv_volume) {
        this.context = context;
        this.tv_volume = tv_volume;
        simpleDateFormat = new SimpleDateFormat("mm:ss", Locale.getDefault());
        myObservable = new MyObservable();
        myObservable.addObserver(this);
    }

    @Override
    public void update(Observable observable, Object o) {
        // 更新UI
        final String aaaa = (String) o;
        tv_volume.post(new Runnable() {
            @Override
            public void run() {
                tv_volume.setText(simpleDateFormat.format(aaaa));
            }
        });
    }

    public void des_ob() {
        if (myObservable != null) {
            myObservable.deleteObserver(this);
        }
    }

    public class MyObservable extends Observable {

        public void setData(String arg) {
            setChanged();
            notifyObservers(arg);
        }

        public void getData(Object obj) {
            Log.d("geek", obj + "");
        }


    }

}
