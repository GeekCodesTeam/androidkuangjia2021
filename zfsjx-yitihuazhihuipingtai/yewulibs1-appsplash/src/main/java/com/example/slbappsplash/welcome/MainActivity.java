//package com.example.slbappsplash.welcome;
//
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//public class MainActivity extends AppCompatActivity {
//
//    private String URL = "http://ww2.sinaimg.cn/large/72f96cbagw1f5mxjtl6htj20g00sg0vn.jpg";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        /** 在 setContentView(R.layout.activity_sample) 后调用 */
//        SplashView.showSplashView(this, 5, R.drawable.default_img, new SplashView.OnSplashViewActionListener() {
//            @Override
//            public void onSplashImageClick(String actionUrl) {
//                Log.d("SplashView", "img clicked. actionUrl: " + actionUrl);
//                Toast.makeText(MainActivity.this, "img clicked.", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onSplashViewDismiss(boolean initiativeDismiss) {
//                Log.d("SplashView", "dismissed, initiativeDismiss: " + initiativeDismiss);
//            }
//        });
//
//        /** 调用此方法可以在任何地方更新闪屏视图数据 */
//        SplashView.updateSplashData(this, URL, "点击图片相应");
//    }
//}