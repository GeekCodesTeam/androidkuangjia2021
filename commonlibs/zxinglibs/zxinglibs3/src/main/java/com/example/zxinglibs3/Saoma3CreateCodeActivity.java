package com.example.zxinglibs3;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.zxinglibs3.saoma3zxing.encode.EncodingHandler;
import com.google.zxing.WriterException;

import java.io.UnsupportedEncodingException;

/**
 * Created by 刘红亮 on 2015/9/24 14:37.
 */
public class Saoma3CreateCodeActivity extends Activity implements View.OnClickListener {
    EditText etCodeKey;
    Button btnCreateCode;
    Button btn_create_code_and_img;
    ImageView iv2Code;
    ImageView ivBarCode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saoma3_activity_create_code);
        etCodeKey = (EditText) findViewById(R.id.et_code_key);
        btnCreateCode = (Button) findViewById(R.id.btn_create_code);
        btn_create_code_and_img = (Button) findViewById(R.id.btn_create_code_and_img);
        iv2Code = (ImageView) findViewById(R.id.iv_2_code);
        ivBarCode = (ImageView) findViewById(R.id.iv_bar_code);
        btnCreateCode.setOnClickListener(this);
        btn_create_code_and_img.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String key=etCodeKey.getText().toString();
        int id = view.getId();
        if (id == R.id.btn_create_code) { //生成码
            if (TextUtils.isEmpty(key)) {
                Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
            } else {
                create2Code(key);
                createBarCode(key);
            }
        } else if (id == R.id.btn_create_code_and_img) { //生成码
            Bitmap bitmap = create2Code(key);
            Bitmap headBitmap = getHeadBitmap(60);
            if (bitmap != null && headBitmap != null) {
                createQRCodeBitmapWithPortrait(bitmap, headBitmap);
            }
        }
    }

    private Bitmap createBarCode(String key) {
        Bitmap qrCode = null;
        try {
            qrCode = EncodingHandler.createBarCode(key, 600, 300);
            ivBarCode.setImageBitmap(qrCode);
        } catch (Exception e) {
            Toast.makeText(this,"输入的内容条形码不支持！", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return qrCode;
    }

    /**
     * 生成二维码
     * @param key
     */
    private Bitmap create2Code(String key) {
        Bitmap qrCode=null;
        try {
            qrCode= EncodingHandler.create2Code(key, 400);
            iv2Code.setImageBitmap(qrCode);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return qrCode;
    }
    /**
     * 初始化头像图片
     */
    private Bitmap getHeadBitmap(int size) {
        try {
            // 这里采用从asset中加载图片abc.jpg
            Bitmap portrait = BitmapFactory.decodeResource(getResources(),R.drawable.head);
            // 对原有图片压缩显示大小
            Matrix mMatrix = new Matrix();
            float width = portrait.getWidth();
            float height = portrait.getHeight();
            mMatrix.setScale(size / width, size / height);
            return Bitmap.createBitmap(portrait, 0, 0, (int) width,
                    (int) height, mMatrix, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 在二维码上绘制头像
     */
    private void createQRCodeBitmapWithPortrait(Bitmap qr, Bitmap portrait) {
        // 头像图片的大小
        int portrait_W = portrait.getWidth();
        int portrait_H = portrait.getHeight();

        // 设置头像要显示的位置，即居中显示
        int left = (qr.getWidth() - portrait_W) / 2;
        int top = (qr.getHeight() - portrait_H) / 2;
        int right = left + portrait_W;
        int bottom = top + portrait_H;
        Rect rect1 = new Rect(left, top, right, bottom);

        // 取得qr二维码图片上的画笔，即要在二维码图片上绘制我们的头像
        Canvas canvas = new Canvas(qr);

        // 设置我们要绘制的范围大小，也就是头像的大小范围
        Rect rect2 = new Rect(0, 0, portrait_W, portrait_H);
        // 开始绘制
        canvas.drawBitmap(portrait, rect2, rect1, null);
    }


}
