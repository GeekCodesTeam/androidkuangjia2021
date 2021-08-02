package xyz.doikki.dkplayer.widget.player;


import android.content.Context;

import xyz.doikki.videoplayer.exo.ExoMediaPlayer;
import com.google.android.exoplayer2.source.MediaSource;

/**
 * 自定义ExoMediaPlayer，目前扩展了诸如边播边存，以及可以直接设置Exo自己的MediaSource。
 */
public class CustomExoMediaPlayerDk extends ExoMediaPlayer {

    public CustomExoMediaPlayerDk(Context context) {
        super(context);
    }

    public void setDataSource(MediaSource dataSource) {
        mMediaSource = dataSource;
    }
}
