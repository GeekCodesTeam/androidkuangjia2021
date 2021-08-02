package xyz.doikki.dkplayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import xyz.doikki.dkplayer.R;
import xyz.doikki.dkplayer.bean.TiktokBeanDk;
import xyz.doikki.dkplayer.util.cache.PreloadManagerDk;
import xyz.doikki.dkplayer.widget.component.TikTokViewDk;

import java.util.List;

public class Tiktok3AdapterDk extends RecyclerView.Adapter<Tiktok3AdapterDk.ViewHolder> {

    /**
     * 数据源
     */
    private List<TiktokBeanDk> mVideoBeans;

    public Tiktok3AdapterDk(List<TiktokBeanDk> videoBeans) {
        this.mVideoBeans = videoBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tik_tokdk, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        TiktokBeanDk item = mVideoBeans.get(position);
        //开始预加载
        PreloadManagerDk.getInstance(context).addPreloadTask(item.videoDownloadUrl, position);
        Glide.with(context)
                .load(item.coverImgUrl)
                .placeholder(android.R.color.white)
                .into(holder.mThumb);
        holder.mTitle.setText(item.title);
        holder.mPosition = position;
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        TiktokBeanDk item = mVideoBeans.get(holder.mPosition);
        //取消预加载
        PreloadManagerDk.getInstance(holder.itemView.getContext()).removePreloadTask(item.videoDownloadUrl);
    }

    @Override
    public int getItemCount() {
        return mVideoBeans != null ? mVideoBeans.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public int mPosition;
        public TextView mTitle;//标题
        public ImageView mThumb;//封面图
        public TikTokViewDk mTikTokView;
        public FrameLayout mPlayerContainer;

        ViewHolder(View itemView) {
            super(itemView);
            mTikTokView = itemView.findViewById(R.id.tiktok_View);
            mTitle = mTikTokView.findViewById(R.id.tv_title);
            mThumb = mTikTokView.findViewById(R.id.iv_thumb);
            mPlayerContainer = itemView.findViewById(R.id.container);
            itemView.setTag(this);
        }
    }
}
