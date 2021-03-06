package com.example.slbappcomm.jiechangtu;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Environment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.example.slbappcomm.R;
import com.geek.libutils.app.MyLogUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShareImgCutUtil {

    private static int counter = 0;
    private static final String TAG = "ShareImgCutUtil";

    public static boolean isEmpty(String str) {
        return null == str || "".equals(str.trim());
    }

    public static boolean isEmptyMoney(String str) {
        try {
            if (null == str || "".equals(str) || "0".equals(str)
                    || "0.00".equals(str) || 0 == Double.parseDouble(str)) {
                return true;
            }

            if (Double.parseDouble(str) == 0) {
                return true;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    // String five_before = hitlistsBean.getHit_five().substring(0, hitlistsBean.getHit_five().indexOf(":"));
    //String five_after = hitlistsBean.getHit_five().substring(hitlistsBean.getHit_five().indexOf(":") + 1, hitlistsBean.getHit_five().length());

    /**
     * ???????????????????????????
     *
     * @param str
     * @return
     */
    public static String subStringBefore(String str) {
        String beforeStr = str.substring(0, str.indexOf(":"));
        return beforeStr;
    }

    /**
     * ???????????????????????????
     *
     * @param str
     * @return
     */
    public static String subStringAfter(String str) {
        String afterStr = str.substring(str.indexOf(":") + 1);
        return afterStr;
    }

    /**
     * ??????"???" ????????????????????????
     *
     * @param str
     * @return ??????????????????
     */
    public static String[] subStringArray_one(String str) {
        String[] StrArray = str.split(":");

        return StrArray;
    }

    /**
     * ??????"???" ????????????????????????
     *
     * @param str
     * @return ??????????????????
     */
    public static String[] subStringArray_two(String str) {
        String[] StrArray = str.split(",");
        return StrArray;
    }

    /**
     * ??????"???" ????????????????????????
     *
     * @param str
     * @return ??????????????????
     */
    public static String[] subStringArray_three(String str) {
        String[] StrArray = subStringArray_two(str);
        //for(){}
        return StrArray;
    }

    /**
     * ??????str1?????????str2?????????
     *
     * @param str1
     * @param str2
     * @return counter
     */
    public static int countStr(char str1, String str2) {

        int countss = 0;
        for (int i = 0; i < str2.length(); i++) {
            if (str2.charAt(i) == str1) {
                countss++;
            }
        }

        return countss;
    }

    /**
     * ??????()???????????????
     *
     * @param str2
     * @return counter
     */
    public static Pattern patterncountStrParentheses = Pattern.compile("(?<=\\()[^\\)]+");

    public static String countStrParentheses(String str2) {
        // TODO SGF ADD
        String str1 = "";
        Matcher matcher = patterncountStrParentheses.matcher(str2);
        while (matcher.find()) {
            str1 = matcher.group();
        }

        return str1;
    }

    /**
     * 1:0:1:0:1
     *
     * @param
     * @return
     */

    public static String[] getCount(String a, String b) {
        String[] arrayStr = {};
        arrayStr = a.split(b);
        return arrayStr;
    }

    public static boolean isZero(String m1) {
        if (isEmpty(m1)) {
            m1 = "0";
        }
        BigDecimal b1 = new BigDecimal(m1);
        BigDecimal b2 = new BigDecimal("0");
        return b1.compareTo(b2) == 0;
    }

    // public static boolean checkPhone(String phone) {
    // if (null == phone || !phone.matches("^1[3-8]+\\d{9}")) {
    // return false;
    // }
    // return true;
    // }
    //
    // public static boolean checkPassword(String password) {
    // if (null == password || !password.matches("[a-f A-F 0-9]{6-15}")) {
    // return false;
    // }
    // return true;
    // }

    /**
     * ??????????????????
     *
     * @param str
     * @return fen->yuan 112000(???)-> 1120.00(???)
     */
    public static String formatMoney_FenToYuan(String str) {

        try {
            MyLogUtil.d(TAG, "formatMoney_FenToYuan : " + str);
            if (!isEmpty(str)) {
                if ("0".equals(str)) {
                    return "0";
                } else if (str.endsWith("00")) {
                    MyLogUtil.d(TAG, str.substring(0, str.length() - 2));
                    return new BigDecimal(str.substring(0, str.length() - 2))
                            .toString();
                } else {
                    return BigDecimal.valueOf(new BigDecimal(str).longValue(),
                            2).toString();
                }
            }

        } catch (Exception e) {
//            GlobalException.proxy.handle(e, null);

        }

        return "0.00";
    }

    /**
     * @param str
     * @return 5->5.00
     */
    public static String formatMoney_Yuan(String str) {

        if (!isEmpty(str)) {
            return str + ".00";
        }

        return "0.00";
    }

    /**
     * @param str
     * @return 5->500
     */
    public static String formatMoney_Fen(String str) {

        if (!isEmpty(str)) {

            double ret = Double.valueOf(str) * 100;
            return String.valueOf((int) ret);
//            return str + "00";
        }

        return "0";
    }

    /**
     * @param str
     * @return 5->500
     */
    public static String format_FenToYuan(String str) {
        //TODO SGF  ADD

        if (!isEmpty(str)) {

            double ret = Double.valueOf(str) / 100;
            return String.valueOf((int) ret);
//            return str + "00";
        }

        return "0";
    }

    /**
     * @param m1
     * @param m2
     * @return true m1 > m2
     */
    public static boolean greaterThanMoney(String m1, String m2) {
        if (isEmpty(m1)) {
            m1 = "0";
        }
        if (isEmpty(m2)) {
            m2 = "0";
        }
        BigDecimal b1 = new BigDecimal(m1);
        BigDecimal b2 = new BigDecimal(m2);
        return b1.compareTo(b2) == 1;
    }

    /**
     * @param m1
     * @param m2
     * @return
     */
    public static boolean greaterEqualMoney(String m1, String m2) {
        if (isEmpty(m1)) {
            m1 = "0";
        }
        if (isEmpty(m2)) {
            m2 = "0";
        }
        BigDecimal b1 = new BigDecimal(m1);
        BigDecimal b2 = new BigDecimal(m2);
        return b1.compareTo(b2) == 1 || b1.compareTo(b2) == 0;
    }

    public static boolean greaterThanZero(String m1) {
        return greaterThan(m1, "0");
    }

    public static boolean greaterEqualZero(String m1) {
        return greaterEqual(m1, "0");
    }

    public static boolean greaterEqual(String m1, String m2) {
        if (isEmpty(m1)) {
            m1 = "0";
        }

        BigDecimal b1 = new BigDecimal(m1);
        BigDecimal b2 = new BigDecimal(m2);
        return b1.compareTo(b2) == 1 || b1.compareTo(b2) == 0;
    }

    public static boolean greaterThan(String m1, String m2) {
        if (isEmpty(m1)) {
            m1 = "0";
        }

        BigDecimal b1 = new BigDecimal(m1);
        BigDecimal b2 = new BigDecimal(m2);
        return b1.compareTo(b2) == 1 || b1.compareTo(b2) == 0;
    }

    public static boolean lessEqualZero(String m1) {
        return lessEqual(m1, "0");
    }

    public static boolean lessThanZero(String m1) {
        return lessThan(m1, "0");
    }

    public static boolean lessEqual(String m1, String m2) {
        if (isEmpty(m1)) {
            m1 = "0";
        }
        if (isEmpty(m2)) {
            m2 = "0";
        }
        BigDecimal b1 = new BigDecimal(m1);
        BigDecimal b2 = new BigDecimal(m2);
        return b1.compareTo(b2) == -1 || b1.compareTo(b2) == 0;
    }

    /**
     * ????????????
     *
     * @param m1
     * @param m2
     * @return
     */
    public static boolean lessThan(String m1, String m2) {
        if (isEmpty(m1)) {
            m1 = "0";
        }
        if (isEmpty(m2)) {
            m2 = "0";
        }
        BigDecimal b1 = new BigDecimal(m1);
        BigDecimal b2 = new BigDecimal(m2);
        return b1.compareTo(b2) == -1;
    }

    /**
     * @param m1
     * @param m2
     * @return true m1 >= m2
     */
    public static boolean compareMoneyEqual(String m1, String m2) {
        if (isEmpty(m1)) {
            m1 = "0";
        }
        if (isEmpty(m2)) {
            m2 = "0";
        }
        BigDecimal b1 = new BigDecimal(m1);
        BigDecimal b2 = new BigDecimal(m2);
        return b1.compareTo(b2) == 1 || b1.compareTo(b2) == 0;
    }

    public static boolean isMoneyZero(String m1) {
        if (isEmpty(m1)) {
            m1 = "0";
        }
        BigDecimal b1 = new BigDecimal(m1);
        BigDecimal b2 = new BigDecimal("0");
        return b1.compareTo(b2) == 0;
    }

    /**
     * ????????????
     *
     * @param m1 "1.7"
     * @param m2 "7.85"
     * @return "1.7" + "7.85" => 9.55
     */
    public static String add(String m1, String m2) {
        if (isEmpty(m1)) {
            m1 = "0";
        }
        if (isEmpty(m2)) {
            m2 = "0";
        }
        BigDecimal b1 = new BigDecimal(m1);
        BigDecimal b2 = new BigDecimal(m2);
        return b1.add(b2).toString();
    }

    public static String add(String m1, String m2, String m3) {
        if (isEmpty(m1)) {
            m1 = "0";
        }
        if (isEmpty(m2)) {
            m2 = "0";
        }
        if (isEmpty(m3)) {
            m3 = "0";
        }
        BigDecimal b1 = new BigDecimal(m1);
        BigDecimal b2 = new BigDecimal(m2);
        BigDecimal b3 = new BigDecimal(m3);
        return b1.add(b2).add(b3).toString();
    }

    /**
     * ????????????
     *
     * @param m1 2.3
     * @param m2 1.1
     * @return 1.2
     */
    public static String subtract(String m1, String m2) {
        if (isEmpty(m1)) {
            m1 = "0";
        }
        if (isEmpty(m2)) {
            m2 = "0";
        }
        BigDecimal b1 = new BigDecimal(m1);
        BigDecimal b2 = new BigDecimal(m2);
        return b1.subtract(b2).toString();
    }

    /**
     * @param m1
     * @param m2
     * @return
     */
    public static String divide(String m1, String m2) {
        MyLogUtil.d(TAG, "m1 : " + m1 + " m2 : " + m2);
        if (isEmpty(m1)) {
            m1 = "0";
        }
        if (isEmpty(m2)) {
            m2 = "1";
        }
        BigDecimal b1 = new BigDecimal(m1);
        BigDecimal b2 = new BigDecimal(m2);
        return b1.divide(b2, 2, RoundingMode.HALF_UP).toString();
    }

    /**
     * ????????????
     *
     * @param m1 ??????1
     * @param m2 ??????2
     * @return ???????????????
     */
    public static String multiply(String m1, String m2) {
        MyLogUtil.d(TAG, "m1 : " + m1 + " ; m2 : " + m2);
        if (isEmpty(m1)) {
            m1 = "0";
        }
        if (isEmpty(m2)) {
            m2 = "0";
        }
        BigDecimal b1 = new BigDecimal(m1);
        BigDecimal b2 = new BigDecimal(m2);
        return b1.multiply(b2).toString();
    }

    public static String getProgress(int progress) {
        return (progress / 20) + "";
    }

    public static String getLimit(String total_unit) {
        double l = Double.valueOf(total_unit);
        return (int) Math.max(Math.round(l * 0.05), 1) + "";
    }

    private static BigDecimal one_hundred_million = new BigDecimal("100000000");
    private static BigDecimal ten_thousand = new BigDecimal("10000");
    private static BigDecimal hundred_thousand = new BigDecimal("100000");
    private static BigDecimal one_million = new BigDecimal("1000000");
    private static BigDecimal ten_million = new BigDecimal("10000000");

    public static String formatMoney_Chinese(String m) {

        BigDecimal b1 = new BigDecimal(m);
        BigDecimal b2 = b1.divide(one_hundred_million);

        if (b2.doubleValue() >= 10) {
            return b2.toString() + "???";
        } else if (b2.doubleValue() >= 1) {
            return b1.divide(one_hundred_million).setScale(2,
                    RoundingMode.HALF_DOWN)
                    + "???";
        } else {
            b2 = b1.divide(ten_million);
            if (b2.intValue() > 0) {
                System.out.println(b2);
                return b2.multiply(new BigDecimal(1000))
                        .setScale(1, RoundingMode.HALF_DOWN).toString()
                        + "???";

            } else {
                b2 = b1.divide(one_million);
                if (b2.intValue() > 0) {

                    return b2.multiply(new BigDecimal(100))
                            .setScale(0, RoundingMode.HALF_DOWN).toString()
                            + "???";
                } else {
                    b2 = b1.divide(hundred_thousand);
                    if (b2.intValue() > 0) {
                        return b2.multiply(new BigDecimal(10))
                                .setScale(0, RoundingMode.HALF_DOWN).toString()
                                + "???";
                    } else {
                        b2 = b1.divide(ten_thousand);
                        if (b2.intValue() > 0) {
                            return b2.setScale(2, RoundingMode.HALF_DOWN) + "???";
                        }
                        return m + "???";
                    }
                }
            }
        }

    }

    /**
     * ????????????????????????
     *
     * @param issueState
     * @return
     */
    public static boolean isCanShowTicket(String issueState) {

        try {

            if (issueState.startsWith("QC@")) {
                String str = issueState.substring(3);
                if (Integer.parseInt(str) > 1) {
                    return true;
                }
                MyLogUtil.d(TAG, str);
            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean verifyPassword(String password) {

        if (isEmpty(password)) {
            return false;
        } else {
            return password.matches("^[0-9a-zA-Z]{6,15}$");
        }

    }

    /**
     * ??????????????????
     */
    public static boolean verifyPhoneNumber(String phone) {
        if (isEmpty(phone)) {
            return false;
        } else if (!phone.startsWith("1")) {
            return false;
        } else {
            return phone.length() == 11;
        }
//        if (phone
//                .matches("^((13[0-9])|(15[^4,\\D])|(18[0,5-9])|(170))\\d{8}$")) {
//            return true;
//        }

    }

    // /** ???????????? */
    // public static boolean verifyNumeric(String paramString) {
    // return Pattern.compile("[0-9]*").matcher(paramString).matches();
    // }

    /**
     * ??????????????? "^[^0-9]\\w{3,16}$"
     */
    public static boolean verifyUsername(String username) {
        if (isEmpty(username)) {
            return false;
        } else {
            return username.matches("[\u4E00-\u9FA5a-zA-Z]{1}[\u4E00-\u9FA5a-zA-Z0-9]{2,15}");
        }
    }

    // /** ???????????? */
    // public static boolean verifyPassword(String paramString) {
    // return Pattern.compile("^[A-Za-z0-9]{6,16}$")
    // .matcher(paramString)
    // .matches();
    // }

    public static Pattern patternverifyEmail = Pattern.compile("^\\s*([A-Za-z0-9_-]+(\\.\\w+)*@(\\w+\\.)+\\w+)\\s*$");

    public static boolean verifyEmail(String paramString) {
        return patternverifyEmail.matcher(paramString).matches();
    }

    public static String regex = "(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)";
    public static Pattern patternverifyIDCard = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

    public static boolean verifyIDCard(String validateStr) {
        return patternverifyIDCard.matcher(validateStr).matches();
    }


    /**
     * ??????????????? 6???18??? ??????????????? ?????? ?????????
     *
     * @param passStr
     * @return
     */
    public static boolean verifyPasswordLen(String passStr) {
        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,18}$";
        return passStr.matches(regex);
    }

    /**
     * ???????????????????????????????????????2????????? ?????????
     *
     * @param passStr
     * @return
     */
    public static boolean verifTwo(String passStr) {
        String regex = "^\\d{1,8}(\\.\\d{1,2})?$";
        return passStr.matches(regex);
    }

    /**
     * ????????????????????????????????? ?????????
     *
     * @param passStr
     * @return
     */
    public static boolean verifNum(String passStr) {
        String regex = "^[0-9]*[1-9][0-9]*$";
        return passStr.matches(regex);
    }

    public static String getLast_2(String issue) {
        if (ShareImgCutUtil.isEmpty(issue)) {
            return "";
        } else {
            return issue.substring(issue.length() - 2);
        }
    }

    public static String getLast_3(String issue) {
        if (ShareImgCutUtil.isEmpty(issue)) {
            return "";
        } else {
            return issue.substring(issue.length() - 3);
        }
    }

    public static Map<String, String> parseData(String data) {

        Map<String, String> retMap = new HashMap<String, String>(16);

        String[] items = data.split("#");
        String[] item = null;
        for (String i : items) {
            item = i.split("=");
            if (item.length >= 2) {
                retMap.put(item[0], item[1]);
            }
        }

//        for (String key : retMap.keySet()) {
//            System.out.println(key + " : " + retMap.get(key));
//        }

        return retMap;
    }

    public static String setStrRed(String str) {
        return "<font color='red'>" + str + "</font>";
    }

    //??????
    public static String setStrWhite(String str) {
        return "<font color='#ffffff'>" + str + "</font>";
    }

    public static String setStrMainRed(String str) {
        return "<font color='#f33b3b'>" + str + "</font>";
    }

    //??????
    public static String setStrGray(String str) {
        return "<font color='#888888'>" + str + "</font>";
    }

    public static String setStrOrenge(String str) {
        return "<font color='#ff9e14'>" + str + "</font>";
    }

    //????????????-??????
    public static String setStrGreen(String str) {
        return "<font color='#4bc160'>" + str + "</font>";
    }

    public static String setStrBlack(String str) {
        return "<font color='#313131'>" + str + "</font>";
    }

    public static String setStrOrange(String str) {
        return "<font color='#FF3D3D'>" + str + "</font>";
    }

    public static String setStrRedBold(String str) {
        return "<b><font color='red'>" + str + "</font></>";
    }

    public static String setStrBold(String str) {
        return "<b>" + str + "</b>";
    }

    public static List<String> getNumList(int start, int end) {

        List<String> list = new ArrayList<String>();
        for (int i = start; i <= end; i++) {
            list.add(i + "");
        }
        return list;
    }

    public static String setWinCodeRed(String drawCode, String code) {

        StringBuffer retBuf = new StringBuffer();

        String[] drawCodes = drawCode.split(",");

        List<String> codes = new ArrayList<String>();

        for (String c : code.split(",")) {
            codes.add(c);
        }

        for (String d : drawCodes) {

            if (codes.contains(d)) {
                retBuf.append(setStrRed(d));
                retBuf.append(" ");
            } else {
                retBuf.append(d);
                retBuf.append(" ");
            }
        }

        return retBuf.toString();
    }

    public static Pattern patternisNumeric = Pattern.compile("[0-9]*");

    public static boolean isNumeric(String str) {
        Matcher isNum = patternisNumeric.matcher(str);
        return isNum.matches();
    }

    public static String getString(String str) {
        if (isEmpty(str)) {
            return "";
        }
        return str.trim();
    }

    /**
     * ?????????????????????
     */
    public static Bitmap createQRImage(String url, int QR_WIDTH, int QR_HEIGHT) throws WriterException {

        // ??????URL?????????
        if (url == null || "".equals(url) || url.length() < 1) {
            return null;
        }
        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        // ??????????????????????????????????????????
        BitMatrix bitMatrix = new QRCodeWriter().encode(url,
                BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
        int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
        // ????????????????????????????????????????????????????????????????????????
        // ??????for????????????????????????????????????
        for (int y = 0; y < QR_HEIGHT; y++) {
            for (int x = 0; x < QR_WIDTH; x++) {
                if (bitMatrix.get(x, y)) {
                    pixels[y * QR_WIDTH + x] = 0xff000000;
                } else {
                    // pixels[y * QR_WIDTH + x] = 0xffffffff;
                }
            }
        }
        // ???????????????????????????????????????ARGB_8888
        Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
        //TODO WH ???????????????ImageView??????
        //iv_codes_qr.setImageBitmap(bitmap);
        return bitmap;

    }

    /**
     * ?????????????????????????????????????????????????????????
     *
     * @param content ??????????????????????????????
     * @param width   ???????????????
     * @param height  ???????????????
     * @throws WriterException ?????????????????????
     */
    public static Bitmap makeQRImage(String content, int width, int height)
            throws WriterException {
        // ??????URL?????????
        if (isNoBlankAndNoNull(content)) {
            return null;
        }

        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        // ??????????????????????????????????????????
        BitMatrix bitMatrix = new QRCodeWriter().encode(content,
                BarcodeFormat.QR_CODE, width, height, hints);
        bitMatrix = deleteWhite(bitMatrix);//????????????
        width = bitMatrix.getWidth();
        height = bitMatrix.getHeight();
        int[] pixels = new int[width * height];
        // ??????????????????????????????????????????????????????????????????for????????????????????????????????????
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (bitMatrix.get(x, y)) {
                    pixels[y * width + x] = 0xff000000;
                } else {
                    pixels[y * width + x] = 0xffffffff;
                }
            }
        }
        // ???????????????????????????????????????ARGB_8888
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);

        return bitmap;
    }

    private static BitMatrix deleteWhite(BitMatrix matrix) {
        int[] rec = matrix.getEnclosingRectangle();
        int resWidth = rec[2] + 1;
        int resHeight = rec[3] + 1;

        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
        resMatrix.clear();
        for (int i = 0; i < resWidth; i++) {
            for (int j = 0; j < resHeight; j++) {
                if (matrix.get(i + rec[0], j + rec[1])) {
                    resMatrix.set(i, j);
                }
            }
        }
        return resMatrix;
    }

    /**
     * ??????????????????????????????null
     *
     * @param strParm ????????????????????????
     * @return ??????
     */
    public static boolean isNoBlankAndNoNull(String strParm) {
        return TextUtils.isEmpty(strParm);
    }


    /**
     * ??????????????????????????????
     *
     * @param filePath ????????????+?????????
     * @param
     * @throws IOException
     */
    public static void saveAsJPEG(Bitmap bitmap, String filePath)
            throws IOException {
        FileOutputStream fos = null;

        try {
            File file = new File(filePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
    }

    /**
     * ???????????????
     *
     * @return
     */
    public static boolean isMountedSDCard() {
        return Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState());
    }

    /**
     * ??????
     *
     * @param activity
     * @return
     */
    public static Bitmap activityShot(Activity activity) {
        /*??????windows???????????????view*/
        View view = activity.getWindow().getDecorView();
        //????????????????????????????????????
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        //?????????????????????
        Rect rect = new Rect();
        view.getWindowVisibleDisplayFrame(rect);
        int statusBarHeight = rect.top;
        WindowManager windowManager = activity.getWindowManager();
        //?????????????????????
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;
        //???????????????
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache(), 0, statusBarHeight, width, height - statusBarHeight);
        //??????????????????
        view.destroyDrawingCache();
        view.setDrawingCacheEnabled(false);
        return bitmap;
    }

    //??????????????????
    private static Bitmap mergeBitmap(Bitmap firstBitmap, Bitmap secondBitmap, Bitmap threeBitmap) {
        Bitmap bitmap = Bitmap.createBitmap(firstBitmap.getWidth(), firstBitmap.getHeight() + secondBitmap.getHeight() + threeBitmap.getHeight(), firstBitmap.getConfig());
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(firstBitmap, new Matrix(), null);
        //?????????????????????????????????????????????????????????????????????????????????
        canvas.drawRGB(255, 255, 255);
        canvas.drawBitmap(secondBitmap, 0, firstBitmap.getHeight(), null);
        canvas.drawBitmap(threeBitmap, secondBitmap.getWidth(), firstBitmap.getHeight(), null);
        return bitmap;
    }

    //??????????????????
    public static Bitmap mergeBitmap(Bitmap firstBitmap, Bitmap secondBitmap) {
        Bitmap bitmap = Bitmap.createBitmap(firstBitmap.getWidth(), firstBitmap.getHeight(), firstBitmap.getConfig());
        Canvas canvas = new Canvas(bitmap);
        //?????????????????????????????????????????????????????????????????????????????????
        canvas.drawRGB(255, 255, 255);
        canvas.drawBitmap(firstBitmap, new Matrix(), null);
        MyLogUtil.e("-----geee34------", firstBitmap.getHeight() + "");
        MyLogUtil.e("-----geee34------", bitmap.getHeight() + "");
        canvas.drawBitmap(secondBitmap, 0, 10, null);
        return bitmap;
    }


    /**
     * ?????????????????????
     * ????????????  1.?????????????????????????????????????????????????????????????????????imageview?????????????????????????????????????????????????????????????????????????????????
     * 2.???????????????????????????????????????????????????????????????????????????
     *
     * @param bit1
     * @param bit2
     * @return
     */
    public static Bitmap newBitmap(Bitmap bit1, Bitmap bit2) {
        Bitmap newBit = null;
        int width = bit1.getWidth();
        if (bit2.getWidth() != width) {
            int h2 = bit2.getHeight() * width / bit2.getWidth();
            newBit = Bitmap.createBitmap(width, bit1.getHeight() + h2, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(newBit);
            Bitmap newSizeBitmap2 = getNewSizeBitmap(bit2, width, h2);
            canvas.drawBitmap(bit1, 0, 0, null);
            canvas.drawBitmap(newSizeBitmap2, 0, bit1.getHeight(), null);
        } else {
            newBit = Bitmap.createBitmap(width, bit1.getHeight() + bit2.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(newBit);
            canvas.drawBitmap(bit1, 0, 0, null);
            canvas.drawBitmap(bit2, 0, bit1.getHeight(), null);
        }
        return newBit;
    }

    public static Bitmap getNewSizeBitmap(Bitmap bitmap, int newWidth, int newHeight) {
        float scaleWidth = ((float) newWidth) / bitmap.getWidth();
        float scaleHeight = ((float) newHeight) / bitmap.getHeight();
        // ?????????????????????matrix??????
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // ??????????????????
        Bitmap bit1Scale = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix,
                true);
        return bit1Scale;
    }

    /**
     * ??????scrollview?????????
     *
     * @param scrollView
     * @return
     */
    public static Bitmap getBitmapByView(ScrollView scrollView) {
        int h = 0;
        Bitmap bitmap = null;
        // ??????listView????????????
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
            scrollView.getChildAt(i).setBackgroundResource(R.drawable.slb_btn_comm_white);
        }

        // ?????????????????????bitmap
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h,
                Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        // ????????????
        FileOutputStream out = null;
        try {
            out = new FileOutputStream("/sdcard/screen_test.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            if (null != out) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.flush();
                out.close();
            }
        } catch (IOException e) {
            // TODO: handle exception
        }
        return bitmap;
    }

    /**
     * ??????listview
     **/
    public static Bitmap getbBitmap(ListView listView) {
        int h = 0;
        Bitmap bitmap = null;
        // ??????listView????????????
        for (int i = 0; i < listView.getChildCount(); i++) {
            h += listView.getChildAt(i).getHeight();
        }

        // ?????????????????????bitmap
        bitmap = Bitmap.createBitmap(listView.getWidth(), h,
                Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        listView.draw(canvas);
        // ????????????
        FileOutputStream out = null;
        try {
            out = new FileOutputStream("/sdcard/screen_test.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            if (null != out) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.flush();
                out.close();
            }
        } catch (IOException e) {
            // TODO: handle exception
        }
        return bitmap;
    }

    /**
     * ??????RelativeLayout
     **/
    public static Bitmap getRelativeLayoutBitmap(RelativeLayout relativeLayout) {
        int h = 0;
        Bitmap bitmap;
        for (int i = 0; i < relativeLayout.getChildCount(); i++) {
            MyLogUtil.e("---geee45--h---", h + "");
            h += relativeLayout.getChildAt(i).getHeight();
        }
        // ?????????????????????bitmap
        bitmap = Bitmap.createBitmap(relativeLayout.getWidth(), h,
                Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        relativeLayout.draw(canvas);
        return bitmap;
    }

    /**
     * ??????LinearLayout
     **/
    public static Bitmap getLinearLayoutBitmap(LinearLayout linearLayout) {
        int h = 0;
        Bitmap bitmap;
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            h += linearLayout.getChildAt(i).getHeight();
        }
        // ?????????????????????bitmap
        bitmap = Bitmap.createBitmap(linearLayout.getWidth(), h,
                Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        //??????????????????
//        Paint mBitPaint = new Paint();
////            mBitPaint.setColor(Color.WHITE);
//        mBitPaint.setColor(ContextCompat.getColor(BaseApp.get(), R.color.blue33b5e5));
//        mBitPaint.setStyle(Paint.Style.FILL);
////            canvas.drawRect(0, height, width, mBgHeight, mBitPaint);
//        canvas.drawRect(0, linearLayout.getHeight(), linearLayout.getWidth(), 0, mBitPaint);
        //?????????????????????????????????????????????????????????????????????????????????
        canvas.drawRGB(255, 255, 255);
        linearLayout.draw(canvas);
        return bitmap;
    }

    /**
     * ??????????????????????????????????????????
     */
    public static Bitmap screenShotWholeScreen(Activity activity) {
        View dView = activity.getWindow().getDecorView();
        dView.setDrawingCacheEnabled(true);
        dView.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(dView.getDrawingCache());
        return bitmap;
    }
} 
