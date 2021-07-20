package net.ossrs.yasea.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ToastUtils;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;

import net.ossrs.yasea.R;
import net.ossrs.yasea.SimplePublisher;
import net.ossrs.yasea.utils.ServiceUtil;

public class ScreenAct extends AppCompatActivity {

    private ImageView ivTouping;
    private String myUrl = "";
    private String rtmpUrl = "";
    public static final int REQUEST_MEDIA_PROJECTION_SCREEN = 1002;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screenact);
        ivTouping = (ImageView) findViewById(R.id.ivTouping);
        ivTouping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SimplePublisher.getInstance().isRecording()) {
                    SimplePublisher.getInstance().stop();
                    ServiceUtil.getInstance().mMediaProjection = null;
                    stopService(new Intent(ScreenAct.this, FadeService.class));
                    // 这里可以用通知来做业务
                    ToastUtils.showLong("结束投屏");
                    setScreen();
                } else {
                    startActivity(new Intent(ScreenAct.this, ScreenConfigActivity.class));
                }
            }
        });
        ivTouping.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new XPopup.Builder(ScreenAct.this)
                        .dismissOnTouchOutside(false)
                        .asCenterList("请选择", new String[]{"高清", "自动", "退出投屏"}, new OnSelectListener() {
                            @Override
                            public void onSelect(int position, String text) {
                                if (position == 0) {
                                    SimplePublisher.getInstance().setHighQuality(0);
                                } else if (position == 1) {
                                    SimplePublisher.getInstance().setHighQuality(1);
                                } else if (position == 2) {
                                    SimplePublisher.getInstance().stop();
                                    ServiceUtil.getInstance().mMediaProjection = null;
                                    stopService(new Intent(ScreenAct.this, FadeService.class));
                                    setScreen();
                                }
                            }
                        })
                        .show();
                return true;
            }
        });
        setScreen();
    }

    public void setScreen() {
        if (ivTouping != null) {
            if (SimplePublisher.getInstance().isRecording()) {
                ivTouping.setBackground(getResources().getDrawable(R.mipmap.toupinging_icon));
            } else {
                ivTouping.setBackground(getResources().getDrawable(R.mipmap.touping_icon));
            }
        }
    }


}
