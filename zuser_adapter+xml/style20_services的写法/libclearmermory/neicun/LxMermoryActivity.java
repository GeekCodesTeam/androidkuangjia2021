package com.example.shining.libclearmermory.neicun;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.shining.libclearmermory.R;
import com.example.shining.libclearmermory.flows.LxFlowCeng;
import com.example.shining.libclearmermory.service.LxClearMermoryService;
import com.example.shining.libclearmermory.service.LxClearMermoryUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.shining.libclearmermory.service.LxClearMermoryUtil.getProcessCpuRate;


public class LxMermoryActivity extends AppCompatActivity {

    private static LxMermoryActivity instance;

    private TextView tv1 = null;
    private TextView tv2 = null;
    private TextView tv3 = null;
    private final long neicun_update_time = 5 * 1000;
    private LxClearMermoryService timerService;
    private boolean isConnect;

    public LxMermoryActivity() {
//        this.context = context.getApplicationContext();//防止内存溢出的写法
    }

    public static LxMermoryActivity getInstance() {
        if (instance == null) {
            synchronized (LxMermoryActivity.class) {
                if (instance == null) {
                    instance = new LxMermoryActivity();
                }
            }
        }
        return instance;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_lxmermory);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
//        SmartBarInject.inject(this).show(SmartBar.HOME | SmartBar.BACK);
        tv1 = (TextView) LxFlowCeng.getLayout().findViewById(R.id.tv1);
        tv2 = (TextView) LxFlowCeng.getLayout().findViewById(R.id.tv2);
        tv3 = (TextView) LxFlowCeng.getLayout().findViewById(R.id.tv3);

        LxFlowCeng.getLayout().setVisibility(View.VISIBLE);
        init();
    }

    public void init() {
        //tv1
        tv1.setText("总内存：" + LxClearMermoryUtil.getTotalMemory());
        //tv2
        bindService(new Intent(getApplicationContext(), LxClearMermoryService.class), conn, Context.BIND_AUTO_CREATE);
        loopAdd();
        //tv3
        regReceiver();
    }

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

    private List<Bitmap> list = new ArrayList<>();

    private Timer timer;
    private TimerTask timerTask;

    private void loopAdd() {
        if (timer == null) {
            timer = new Timer();
            if (timerTask == null) {
                timerTask = new TimerTask() {
                    @Override
                    public void run() {
//                        MyLogUtil.d("LxClearMermoryService", "进来循环了");
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
                        tv2.setText("可用内存：" + afterMem +" M");
                        tv3.setText("CPU使用率变化：" + getProcessCpuRate()+"%");
                    }
                    break;
                default:
                    break;

            }
        }
    };

    /**
     * connect timerservice
     */
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LxClearMermoryService.LocalBinder binder = (LxClearMermoryService.LocalBinder) service;
            LxMermoryActivity.this.timerService = (LxClearMermoryService) binder.getService();
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
    protected void onDestroy() {
        super.onDestroy();
//        if (isConnect) {
//            unbindService(conn);
//        }
        handler.removeMessages(0x01);
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
    }
}
