package com.example.zxinglibs3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.zxinglibs3.utils.Saoma3Constant;


public class SaomaAct3 extends Activity implements View.OnClickListener {

    private Button create_code;
    private Button scan_2code;
    private Button scan_bar_code;
    private Button scan_code;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saoma3_activity_main);
        create_code = (Button) findViewById(R.id.create_code);
        scan_2code = (Button) findViewById(R.id.scan_2code);
        scan_bar_code = (Button) findViewById(R.id.scan_bar_code);
        scan_code = (Button) findViewById(R.id.scan_code);
        create_code.setOnClickListener(this);
        scan_2code.setOnClickListener(this);
        scan_bar_code.setOnClickListener(this);
        scan_code.setOnClickListener(this);
        int mode = getIntent().getIntExtra(Saoma3Constant.REQUEST_SCAN_MODE, Saoma3Constant.REQUEST_SCAN_MODE_ALL_MODE);


    }


    @Override
    public void onClick(View view) {
        Intent intent;
        int id = view.getId();
        if (id == R.id.create_code) { //生成码
            intent = new Intent(this, Saoma3CreateCodeActivity.class);
            startActivity(intent);
        } else if (id == R.id.scan_2code) { //扫描二维码
            intent = new Intent(this, Saoma3CommonScanActivity.class);
            intent.putExtra(Saoma3Constant.REQUEST_SCAN_MODE, Saoma3Constant.REQUEST_SCAN_MODE_QRCODE_MODE);
            startActivity(intent);
        } else if (id == R.id.scan_bar_code) {//扫描条形码
            intent = new Intent(this, Saoma3CommonScanActivity.class);
            intent.putExtra(Saoma3Constant.REQUEST_SCAN_MODE, Saoma3Constant.REQUEST_SCAN_MODE_BARCODE_MODE);
            startActivity(intent);
        } else if (id == R.id.scan_code) {//扫描条形码或者二维码
            intent = new Intent(this, Saoma3CommonScanActivity.class);
            intent.putExtra(Saoma3Constant.REQUEST_SCAN_MODE, Saoma3Constant.REQUEST_SCAN_MODE_ALL_MODE);
            startActivity(intent);
        }
    }

}
