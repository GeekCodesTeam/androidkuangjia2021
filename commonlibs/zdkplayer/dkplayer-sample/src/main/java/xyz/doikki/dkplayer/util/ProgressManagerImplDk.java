package xyz.doikki.dkplayer.util;

import android.text.TextUtils;

import androidx.collection.LruCache;

import xyz.doikki.videoplayer.player.ProgressManager;

public class ProgressManagerImplDk extends ProgressManager {

    //保存100条记录
    private static LruCache<Integer, Long> mCache = new LruCache<>(100);

    @Override
    public void saveProgress(String url, long progress) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        if (progress == 0) {
            clearSavedProgressByUrl(url);
            return;
        }
        mCache.put(url.hashCode(), progress);
    }

    @Override
    public long getSavedProgress(String url) {
        Long pro = null;
        if (TextUtils.isEmpty(url)) {
            return 0;
        }
        pro = mCache.get(url.hashCode());
        if (pro == null) {
            return 0;
        }
        return pro;
    }

    public void clearAllSavedProgress() {
        mCache.evictAll();
    }

    public void clearSavedProgressByUrl(String url) {
        mCache.remove(url.hashCode());
    }
}
