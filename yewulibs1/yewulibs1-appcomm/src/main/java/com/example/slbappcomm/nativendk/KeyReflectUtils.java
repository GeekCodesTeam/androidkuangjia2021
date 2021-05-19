package com.example.slbappcomm.nativendk;

import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class KeyReflectUtils {

    private static KeyReflectUtils aMapUtils;

    private KeyReflectUtils() {

    }

    public synchronized static KeyReflectUtils getInstance() {
        synchronized (KeyReflectUtils.class) {
            if (aMapUtils == null) {
                aMapUtils = new KeyReflectUtils();
            }
        }
        return aMapUtils;
    }

    public boolean settag = false;

    public boolean isSettag() {
        return settag;
    }

    public void setSettag(boolean settag) {
        this.settag = settag;
    }

    /**
     * 反射调用静态方法
     *
     * @param className//类名
     * @param methodName//方法名
     * @param args//调用方法传的参数
     * @return
     */
    public Object invokeStatic(String className, String methodName, Object[] args) {
        //调用方法传的参数
        Object returnObj = null;
        Class[] argsClass = new Class[args.length];
        for (int i = 0, j = args.length; i < j; i++) {
            argsClass[i] = args[i].getClass();
        }

        try {
            Method[] methods = Class.forName(className).getDeclaredMethods();
            returnObj = Class.forName(className).getMethod(methodName, argsClass).invoke(null, args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return returnObj;
    }

    /**
     * 反射调用静态方法
     *
     * @param className//类名
     * @param methodName//方法名
     * @return
     */
    public static Object invokeStatic(String className, String methodName) {
        //调用方法传的参数
        Object returnObj = null;
        try {
            returnObj = Class.forName(className).getMethod(methodName).invoke(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return returnObj;
    }


    public Object invokeMethod(String ownerName, String methodName, Object[] args) throws Exception {
        Class ownerClass = Class.forName(ownerName);
        Class[] argsClass = new Class[args.length];
        Object owner = ownerClass.newInstance();

        for (int i = 0, j = args.length; i < j; i++) {

            argsClass[i] = args[i].getClass();

        }
        Method method = null;
        try {
            method = ownerClass.getDeclaredMethod(methodName, argsClass);
        } catch (Exception e) {
            Log.d("myinvokeMethod", e.toString());
        }

        return method.invoke(owner, args);

    }

}
