/*
 * *********************************************************
 *   author   colin
 *   company  fosung
 *   email    wanglin2046@126.com
 *   date     17-5-18 上午9:47
 * ********************************************************
 */

package com.fosung.xuanchuanlan.mian.http.entity;

import java.util.ArrayList;

/**
 * 调用天气接口返回的信息
 */
public class WeatherInfo {
    public String status;
    public String info;
    public String count;
    public String infocode;
    public ArrayList<Lives> forecasts;

    public static class Lives {
        public String province;
        public String city;
        public String adcode;
        public String weather;
        public String humidity;
        public String reporttime;
        public ArrayList<Casts> casts;
    }
    public static class Casts {
        public String daytemp;
        public String nighttemp;
        public String dayweather;
        public String daywind;
        public String daypower;
    }
}
