//package com.example.slbappcomm.viewpager2.adapter;
//
//import android.content.Context;
//import android.graphics.Color;
//import android.util.SparseArray;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.slbappcomm.R;
//import com.example.slbappcomm.viewpager2.bean.DataBean;
//import com.example.slbappcomm.viewpager2.viewholder.ImageHolder;
//import com.example.slbappcomm.viewpager2.viewholder.TitleHolder;
//import com.example.slbappcomm.viewpager2.viewholder.VideoHolder;
//import com.youth.banner.adapter.BannerAdapter;
//import com.youth.banner.util.BannerUtils;
//
//import java.util.List;
//
///**
// * 自定义布局,多个不同UI切换
// */
//public class MultipleTypesAdapterbeifen extends BannerAdapter<DataBean, RecyclerView.ViewHolder> {
//    private Context context;
//    private SparseArray<RecyclerView.ViewHolder> mVHMap = new SparseArray<>();
//
//    public MultipleTypesAdapterbeifen(Context context, List<DataBean> mDatas) {
//        super(mDatas);
//        this.context = context;
//    }
//
//    @Override
//    public RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
//        switch (viewType) {
//            case 1:
//                return new ImageHolder(BannerUtils.getView(parent, R.layout.lunbo_banner_image));
//            case 2:
//                return new VideoHolder(BannerUtils.getView(parent, R.layout.lunbo_banner_video));
//            case 3:
//                return new TitleHolder(BannerUtils.getView(parent, R.layout.lunbo_banner_title));
//        }
//        return new ImageHolder(BannerUtils.getView(parent, R.layout.lunbo_banner_image));
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        return getData(getRealPosition(position)).viewType;
//    }
//
//    @Override
//    public void onBindView(RecyclerView.ViewHolder holder, DataBean data, int position, int size) {
//        int viewType = holder.getItemViewType();
//        switch (viewType) {
//            case 1:
//                ImageHolder imageHolder = (ImageHolder) holder;
//                mVHMap.append(position,imageHolder);
//                imageHolder.imageView.setImageResource(data.imageRes);
//                break;
//            case 2:
//                VideoHolder videoHolder = (VideoHolder) holder;
//                mVHMap.append(position,videoHolder);
//                videoHolder.player.setUp(data.imageUrl, true, null);
//                videoHolder.player.getBackButton().setVisibility(View.GONE);
//                //增加封面
//                ImageView imageView = new ImageView(context);
//                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                imageView.setImageResource(R.drawable.image8);
//                videoHolder.player.setThumbImageView(imageView);
////                videoHolder.player.startPlayLogic();
//                break;
//            case 3:
//                TitleHolder titleHolder = (TitleHolder) holder;
//                mVHMap.append(position,titleHolder);
//                titleHolder.title.setText(data.title);
//                titleHolder.title.setBackgroundColor(Color.parseColor(DataBean.getRandColor()));
//                break;
//        }
//    }
//
//    public SparseArray<RecyclerView.ViewHolder> getVHMap() {
//        return mVHMap;
//    }
//
//
//}
