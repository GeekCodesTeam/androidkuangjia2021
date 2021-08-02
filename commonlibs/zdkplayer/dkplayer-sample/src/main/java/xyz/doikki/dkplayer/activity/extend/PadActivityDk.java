package xyz.doikki.dkplayer.activity.extend;

import android.view.View;
import android.widget.Toast;

import xyz.doikki.dkplayer.R;
import xyz.doikki.dkplayer.activity.BaseActivityDk;
import xyz.doikki.dkplayer.util.DataUtilDk;
import xyz.doikki.videocontroller.StandardVideoController;

public class PadActivityDk extends BaseActivityDk {

    private StandardVideoController mController;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_paddk;
    }

    @Override
    protected void initView() {
        super.initView();
        mVideoView = findViewById(R.id.video_view);

        mVideoView.setUrl(DataUtilDk.SAMPLE_URL);

        mController = new StandardVideoController(this);
        mController.addDefaultControlComponent("pad", false);

        mController.findViewById(R.id.fullscreen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mVideoView.isFullScreen()) {
                    mVideoView.stopFullScreen();
                } else {
                    mVideoView.startFullScreen();
                }
            }
        });

        mController.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVideoView.stopFullScreen();
            }
        });

        mVideoView.setVideoController(mController);

        mVideoView.start();
    }


    @Override
    public void onBackPressed() {
        if (mController.isLocked()) {
            mController.show();
            Toast.makeText(this, R.string.dkplayer_lock_tip, Toast.LENGTH_SHORT).show();
            return;
        }
        if (mVideoView.isFullScreen()) {
            mVideoView.stopFullScreen();
            return;
        }
        finish();
    }
}
