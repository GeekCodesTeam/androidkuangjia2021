package com.fosung.xuanchuanlan.xuanchuanlan.localprofile.fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.fosung.frameutils.imageloader.ImageLoaderUtils;
import com.fosung.xuanchuanlan.R;

public class ServiceTeamDialog extends Dialog {

    private ServiceTeamDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {

        private View mLayout;

        private ImageView mIcon;
        private TextView mTitle;
        private TextView mPosition;
        private TextView mMessage;
        private TextView mTell;
        private Button mButton;

        private View.OnClickListener mButtonClickListener;
        private Context mContext;
        private ServiceTeamDialog mDialog;

        public Builder(Context context) {
            mContext = context;
            mDialog = new ServiceTeamDialog(context, R.style.Theme_AppCompat_Dialog);
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //加载布局文件
            mLayout = inflater.inflate(R.layout.dialog_service_team_detail, null, false);
            //添加布局文件到 Dialog
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            mDialog.addContentView(mLayout, layoutParams);

            WindowManager manager = (WindowManager) context
                    .getSystemService(Context.WINDOW_SERVICE);
            Display display = manager.getDefaultDisplay();
            Point point = new Point();
            if(Build.VERSION.SDK_INT < 17) {
                display.getSize(point);
            } else {
                display.getRealSize(point);
            }

            int width = point.x;
            int height = point.y;

            mDialog.getWindow().setLayout( width - 40 , height/4*3);


            mIcon = (ImageView) mLayout.findViewById(R.id.id_dialog_header);
            mTitle = (TextView) mLayout.findViewById(R.id.id_dialog_name);
            mMessage = (TextView) mLayout.findViewById(R.id.id_dialog_info);
            mPosition = (TextView) mLayout.findViewById(R.id.id_dialog_position);
            mTell = (TextView) mLayout.findViewById(R.id.id_dialog_tell);

            mButton = (Button) mLayout.findViewById(R.id.id_dialog_close);
        }

        /**
         * 通过 ID 设置 Dialog 图标
         */
        public Builder setIcon(int resId) {
            mIcon.setImageResource(resId);
            return this;
        }

        /**
         * 用 Bitmap 作为 Dialog 图标
         */
        public Builder setIcon(Bitmap bitmap) {
            mIcon.setImageBitmap(bitmap);
            return this;
        }

        public Builder setIcon(String url) {
            ImageLoaderUtils.displayImage(mContext, url, mIcon);
            return this;
        }


        /**
         * 设置 Dialog 标题
         */
        public Builder setTitle(@NonNull String title) {
            mTitle.setText(title);
            mTitle.setVisibility(View.VISIBLE);
            return this;
        }

        /**
         * 设置 Message
         */
        public Builder setMessage(@NonNull String message) {
            mMessage.setText("岗位职责："+message);
            return this;
        }

        /**
         * 设置 positon
         */
        public Builder setPostion(@NonNull String position) {
            mPosition.setText(position);
            return this;
        }

        /**
         * 设置 positon
         */
        public Builder setTell(@NonNull String tell) {
            mTell.setText("联系方式："+tell);
            return this;
        }

        /**
         * 设置按钮文字和监听
         */
        public Builder setButton(View.OnClickListener listener) {
            mButtonClickListener = listener;
            return this;
        }

        public ServiceTeamDialog create() {
            mButton.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               mDialog.dismiss();
                                               mButtonClickListener.onClick(v);
                                           }
                                       });

            mDialog.setContentView(mLayout);
            mDialog.setCancelable(true);                //用户可以点击后退键关闭 Dialog
            mDialog.setCanceledOnTouchOutside(true);   //用户不可以点击外部来关闭 Dialog
            return mDialog;
        }
    }
}

