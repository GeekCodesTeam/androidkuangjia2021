package com.haier.cellarette.baselibrary.assetsfitandroid.playlistmusic;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

//<service
//        android:name=".MusicPlayerService"
//        android:exported="true" />
public class ActPlayMusic {

    private ActMusicPlayerService.MyBinder mBinder;
    private boolean flag;

    private void set() {


        if (mBinder != null && !mBinder.getService().getmPlayer().isPlaying()) {
            // 打开
//            iv_stop1.setImageResource(R.drawable.slb_playrb22);
            mBinder.musicContinue();
        }
        // 后台
        if (mBinder != null && mBinder.getService().getmPlayer().isPlaying()) {
            // 关了
//            iv_stop1.setImageResource(R.drawable.slb_playrb2);
            mBinder.musicPause();
        }
        //
        if (mBinder != null && mBinder.isService()) {
//            mBinder.musicDestroy();
//            unbindService(conn);
//            stopService(new Intent(this, MusicPlayerService.class));
//            mBinder = null;
        }
    }


    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = (ActMusicPlayerService.MyBinder) service;
            flag = true;
            mBinder.getService().setOnPrepared(pcb);
            // 数据显示bufen
//            tv_strings.setText(getString(R.string.image_counts, current + 1, adapter.getCount()));
//            sb_progressbar.setProgress(current);
            // 音乐bufen
            if (flag) {
//                mBinder.musicStart(ReadBookActivity.this, "mp3/" + adapter.getContacts(current).getId() + ".mp3");
                // wangluo
                mBinder.musicStart(null, "");
            }
            // 音乐bufen
            if (mBinder != null) {
//                        mBinder.musicNext(ReadBookActivity.this, "mp3/" + model.getId() + ".mp3");
                // wangluo
                mBinder.musicNext(null, "");
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private PreparedCallBack pcb = new PreparedCallBack() {
        @Override
        public void isPrepared(boolean prepared) {
            if (!prepared) {
                //next
//                if (current + 1 > adapter.getCount() - 1) {
////                    ToastUtil.showToastLong("最后一页");
//
//                    return;
//                }
//                SHuibenDetailBean2 model = adapter.getNext(current);
//                vp.setCurrentItem(current + 1);

            }
        }
    };

}
