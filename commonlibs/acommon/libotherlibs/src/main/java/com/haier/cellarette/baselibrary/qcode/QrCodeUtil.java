package com.haier.cellarette.baselibrary.qcode;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.blankj.utilcode.util.ToastUtils;
import com.bolex.pressscan.ScanTools;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.Hashtable;


public class QrCodeUtil {
    private static int IMAGE_HALFWIDTH = 50;//宽度值，影响中间图片大小
    private static final int DEFAULT_SIZE = 500;

    /**
     * 生成二维码，默认大小为500*500
     *
     * @param text 需要生成二维码的文字、网址等
     * @return bitmap
     */
    public static void createQRCode(ImageView iv, String text) {
        createQRCode(iv, text, DEFAULT_SIZE);
    }

    /**
     * 生成二维码
     *
     * @param text 需要生成二维码的文字、网址等
     * @param size 需要生成二维码的大小（）
     * @return bitmap
     */
    public static void createQRCode(final ImageView iv, final String text, final int size) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Hashtable<EncodeHintType, String> hints = new Hashtable<>();
                    hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
                    BitMatrix bitMatrix = new QRCodeWriter().encode(text,
                            BarcodeFormat.QR_CODE, size, size, hints);
                    int[] pixels = new int[size * size];
                    for (int y = 0; y < size; y++) {
                        for (int x = 0; x < size; x++) {
                            if (bitMatrix.get(x, y)) {
                                pixels[y * size + x] = 0xff000000;
                            } else {
                                pixels[y * size + x] = 0xffffffff;
                            }

                        }
                    }
                    sleep(500);
                    final Bitmap bitmap = Bitmap.createBitmap(size, size,
                            Bitmap.Config.ARGB_8888);
                    bitmap.setPixels(pixels, 0, size, 0, 0, size, size);

                    iv.post(new Runnable() {
                        @Override
                        public void run() {
                            if (iv != null) {
                                iv.setImageBitmap(bitmap);
                            }
                        }
                    });
                } catch (WriterException e) {
                    e.printStackTrace();
                    ToastUtils.showLong("creat code err");
                } catch (InterruptedException e) {
                    ToastUtils.showLong("creat code err");
                    e.printStackTrace();
                }
            }
        }.start();
    }


    public static Bitmap create2DCode(String str, int width, int height) {
        try {
            Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            hints.put(EncodeHintType.MARGIN, 1);
            BitMatrix matrix = new QRCodeWriter().encode(str, BarcodeFormat.QR_CODE, width, height);
            matrix = deleteWhite(matrix);//删除白边
            width = matrix.getWidth();
            height = matrix.getHeight();
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (matrix.get(x, y)) {
                        pixels[y * width + x] = Color.BLACK;
                    } else {
                        pixels[y * width + x] = Color.WHITE;
                    }
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;
        } catch (Exception e) {
            return null;
        }
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

    public static Bitmap createQRImage(String str, int width, int height) throws WriterException {
        return createQRImage(str, width, height, null);
    }

//    public static Bitmap createQRImage(String str, int width, int height, Bitmap logoBm) {
//        try {
//            Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
//            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
//            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
//            BitMatrix matrix = new QRCodeWriter().encode(str, BarcodeFormat.QR_CODE, width, height);
//            matrix = deleteWhite(matrix);//删除白边
//            width = matrix.getWidth();
//            height = matrix.getHeight();
//            int[] pixels = new int[width * height];
//            for (int y = 0; y < height; y++) {
//                for (int x = 0; x < width; x++) {
//                    if (matrix.get(x, y)) {
//                        pixels[y * width + x] = Color.BLACK;
//                    } else {
//                        pixels[y * width + x] = Color.WHITE;
//                    }
//                }
//            }
//            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
//            if (logoBm != null) {
//                bitmap = addLogo(bitmap, logoBm);
//            }
//            return bitmap;
//        } catch (Exception e) {
//            return null;
//        }
//    }

    /**
     * 根据指定内容生成自定义宽高的二维码图片
     *
     * @param content 需要生成二维码的内容
     * @param width   二维码宽度
     * @param height  二维码高度
     * @throws WriterException 生成二维码异常
     */
    public static Bitmap createQRImage(String content, int width, int height, Bitmap logoBm)
            throws WriterException {
        // 判断URL合法性
        if (isNoBlankAndNoNull(content)) {
            return null;
        }

        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        // 图像数据转换，使用了矩阵转换
        BitMatrix bitMatrix = new QRCodeWriter().encode(content,
                BarcodeFormat.QR_CODE, width, height, hints);
        bitMatrix = deleteWhite(bitMatrix);//删除白边
        width = bitMatrix.getWidth();
        height = bitMatrix.getHeight();
        int[] pixels = new int[width * height];
        // 按照二维码的算法，逐个生成二维码的图片，两个for循环是图片横列扫描的结果
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (bitMatrix.get(x, y)) {
                    pixels[y * width + x] = 0xff000000;
                } else {
                    pixels[y * width + x] = 0xffffffff;
                }
            }
        }
        // 生成二维码图片的格式，使用ARGB_8888
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        if (logoBm != null) {
            bitmap = addLogo(bitmap, logoBm);
        }
        return bitmap;
    }

    /**
     * 在二维码中间添加Logo图案
     */

    private static Bitmap addLogo(Bitmap src, Bitmap logo) {
        if (src == null) {
            return null;
        }
        if (logo == null) {
            return src;
        }

        //获取图片的宽高
        int srcWidth = src.getWidth();
        int srcHeight = src.getHeight();
        int logoWidth = logo.getWidth();
        int logoHeight = logo.getHeight();

        if (srcWidth == 0 || srcHeight == 0) {
            return null;
        }

        if (logoWidth == 0 || logoHeight == 0) {
            return src;
        }

        //logo大小为二维码整体大小的1/5
        float scaleFactor = srcWidth * 1.0f / 5 / logoWidth;
        Bitmap bitmap = Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888);
        try {
            Canvas canvas = new Canvas(bitmap);
            canvas.drawBitmap(src, 0, 0, null);
            canvas.scale(scaleFactor, scaleFactor, srcWidth / 2, srcHeight / 2);
            canvas.drawBitmap(logo, (srcWidth - logoWidth) / 2, (srcHeight - logoHeight) / 2, null);
        } catch (Exception e) {
            bitmap = null;
            e.getStackTrace();
        }

        return bitmap;
    }

    /**
     * 判断字符串是否非空非null
     *
     * @param strParm 需要判断的字符串
     * @return 真假
     */
    public static boolean isNoBlankAndNoNull(String strParm) {
        return TextUtils.isEmpty(strParm);
    }

    public static void scan_webview_code(WebView webView) {
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                //todo:调用扫描工具

                ScanTools.scanCode(v, new ScanTools.ScanCall() {
                    @Override
                    public void getCode(String code) {
//                        Toast.makeText(MainActivity.this, code, Toast.LENGTH_SHORT);
                    }
                });
                return true;
            }
        });
    }
}
