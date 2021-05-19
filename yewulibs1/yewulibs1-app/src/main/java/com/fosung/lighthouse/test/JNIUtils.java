package com.fosung.lighthouse.test;

public class JNIUtils {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

//    public native static FileDescriptor open(String path, int baudrate, int flags);

//    public native void close();


}
