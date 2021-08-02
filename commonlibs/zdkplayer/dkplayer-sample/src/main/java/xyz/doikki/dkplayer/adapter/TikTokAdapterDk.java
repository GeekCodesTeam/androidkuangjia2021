package xyz.doikki.dkplayer.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import xyz.doikki.dkplayer.R;
import xyz.doikki.dkplayer.bean.TiktokBeanDk;
import xyz.doikki.dkplayer.util.cache.PreloadManagerDk;
import xyz.doikki.dkplayer.widget.component.TikTokViewDk;

import java.util.List;

@Deprecated
public class TikTokAdapterDk extends RecyclerView.Adapter<TikTokAdapterDk.VideoHolder> {

    private List<TiktokBeanDk> videos;

    public TikTokAdapterDk(List<TiktokBeanDk> videos) {
        this.videos = videos;
    }

    private static final String TAG = "TikTokAdapter";

    @Override
    public VideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tik_tokdk, parent, false);
        return new VideoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final VideoHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: " + position);
        TiktokBeanDk item = videos.get(position);
        Glide.with(holder.thumb.getContext())
                .load(item.coverImgUrl)
                .placeholder(android.R.color.white)
                .into(holder.thumb);
        holder.mPosition = position;
        PreloadManagerDk.getInstance(holder.itemView.getContext()).addPreloadTask(item.videoDownloadUrl, position);
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull VideoHolder holder) {
        super.onViewDetachedFromWindow(holder);
        TiktokBeanDk item = videos.get(holder.mPosition);
        PreloadManagerDk.getInstance(holder.itemView.getContext()).removePreloadTask(item.videoDownloadUrl);
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public static class VideoHolder extends RecyclerView.ViewHolder {

        private ImageView thumb;
        public TikTokViewDk mTikTokView;
        public int mPosition;
        public FrameLayout mPlayerContainer;

        VideoHolder(View itemView) {
            super(itemView);
            mTikTokView = itemView.findViewById(R.id.tiktok_View);
            thumb = mTikTokView.findViewById(R.id.iv_thumb);
            mPlayerContainer = itemView.findViewById(R.id.container);
            itemView.setTag(this);
        }
    }
}