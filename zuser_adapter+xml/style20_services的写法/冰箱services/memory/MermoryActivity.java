package com.haiersmart.sfnation.ui.mermory;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.haiersmart.sfnation.R;
import com.haiersmart.sfnation.base.BaseActivity;
import com.haiersmart.sfnation.service.ClearMermoryService;
import com.haiersmart.sfnation.widget.SmartBar;
import com.haiersmart.sfnation.widget.SmartBarInject;
import com.haiersmart.utilslib.app.MyLogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

import static com.haiersmart.sfnation.bizutils.ClearMermoryUtil.getProcessCpuRate;
import static com.haiersmart.sfnation.bizutils.ClearMermoryUtil.getTotalMemory;

/**
 * Created by shining on 2017/3/22 0022.
 */

public class MermoryActivity extends BaseActivity {

    private static final long neicun_update_time = 5 * 1000;

    private static MermoryActivity instance;

    public MermoryActivity() {
//        this.context = context.getApplicationContext();//防止内存溢出的写法
    }

    public static MermoryActivity getInstance() {
        if (instance == null) {
            synchronized (MermoryActivity.class) {
                if (instance == null) {
                    instance = new MermoryActivity();
                }
            }
        }
        return instance;
    }

    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.btn1)
    Button btn1;

    private ClearMermoryService timerService;
    private boolean isConnect;


    @Override
    public void onResume() {
        super.onResume();

    }

    /**
     * connect timerservice
     */
    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            timerService = ((ClearMermoryService.MsgBinder) service).getService();
            Timer timer = ((ClearMermoryService.MsgBinder) service).getTimer();
            isConnect = true;
//            long afterMem = timerService.getAfterMem();
//            tv2.setText("" + afterMem);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isConnect = false;

        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_demo_mermory;
    }

    @Override
    protected void setup(@Nullable Bundle savedInstanceState) {
        super.setup(savedInstanceState);
        SmartBarInject.inject(this).show(SmartBar.HOME | SmartBar.BACK);
        //tv1
        tv1.setText("总内存：" + getTotalMemory());
        //tv2
        bindService(new Intent(this, ClearMermoryService.class), conn, Context.BIND_AUTO_CREATE);
        loopAdd();
        //tv3
        regReceiver();
        //btn1
        btn1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                MyLogUtil.d("BitmapFactory", "click");
                BitmapFactory.Options options = new BitmapFactory.Options();
                for (int i = 0; i < 20; i++) {
                    MyLogUtil.d("BitmapFactory", "i=" + i);
                    try {
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_search, options);
                        int bytes = bitmap.getAllocationByteCount();//Returns the size of the allocated memory used to store this bitmap's pixels.
                        MyLogUtil.d("BitmapFactory", "bytes=" + bytes);
                        list.add(bitmap);
                    } catch (Exception e) {

                    }
                }

//                byte[] bytes = new byte[10 * 1024 * 1024];
//                new Thread() {
//                    @Override
//                    public void run() {
//                        while (true) ;
//                    }
//                }.start();
//                new Timer().schedule(new TimerTask() {
//                    @Override
//                    public void run() {
//                        while (true) ;
//                    }
//                }, Long.MAX_VALUE >> 1);
//                new AsyncTask<Void, Void, Void>() {
//                    @Override protected Void doInBackground(Void... params) {
//                        while(true);
//                    }
//                }.execute();


            }
        });

    }

    public List<Bitmap> list = new ArrayList<>();

    private Timer timer;
    private TimerTask timerTask;

    private void loopAdd() {
        if (timer == null) {
            timer = new Timer();
            if (timerTask == null) {
                timerTask = new TimerTask() {
                    @Override
                    public void run() {
//                        MyLogUtil.d("ClearMermoryService", "进来循环了");
                        handler.sendEmptyMessage(0x01);
                    }
                };
                timer.schedule(timerTask, 0, neicun_update_time);
            }
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x01:
                    if (isConnect) {
                        long afterMem = timerService.getAfterMem();
                        tv2.setText("可用内存：" + afterMem + "    CPU使用率变化：" + getProcessCpuRate());
                    }
                    break;
                default:
                    break;

            }
        }
    };


    private void regReceiver() {
        IntentFilter intentFiltervolInput = new IntentFilter();
        intentFiltervolInput.addAction("beats_end");
        registerReceiver(receiver, intentFiltervolInput);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("beats_end")) {
                String message = intent.getStringExtra("data");
                tv3.setText("BeatService状态：" + message);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (isConnect) {
//            unbindService(conn);
//        }
        handler.removeMessages(0x01);
        unregisterReceiver(receiver);
    }
}
