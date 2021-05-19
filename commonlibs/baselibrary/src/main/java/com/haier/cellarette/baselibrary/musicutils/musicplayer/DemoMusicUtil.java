package com.haier.cellarette.baselibrary.musicutils.musicplayer;

public class DemoMusicUtil {

    private static volatile DemoMusicUtil instance;


    public static DemoMusicUtil getInstance() {
        if (instance == null) {
            synchronized (DemoMusicUtil.class) {
                if (instance == null) {
                    instance = new DemoMusicUtil();
                }
            }
        }
        return instance;
    }

}

