package com.example.slbappcomm.widgets.recyclerviewnice;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
 
public class XRecyclerView extends RecyclerView {
    public XRecyclerView(Context context) {
        this(context, null); 
     } 
 
    public XRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0); 
     } 
 
    public XRecyclerView(Context context, AttributeSet attrs, int defStyle) { 
        super(context, attrs, defStyle); 
         init(); 
     } 
 
    private void init() { 
        addOnScrollListener(new ImageAutoLoadScrollListener()); 
     } 
 
    //监听滚动来对图片加载进行判断处理 
     public class ImageAutoLoadScrollListener extends OnScrollListener{ 
 
        @Override 
         public void onScrolled(RecyclerView recyclerView, int dx, int dy) { 
            super.onScrolled(recyclerView, dx, dy); 
         } 
 
        @Override 
         public void onScrollStateChanged(RecyclerView recyclerView, int newState) { 
            super.onScrollStateChanged(recyclerView, newState); 
             switch (newState){ 
                case SCROLL_STATE_IDLE: // The RecyclerView is not currently scrolling. 
                     //当屏幕停止滚动，加载图片 
                     try { 
                        if(getContext() != null) Glide.with(getContext()).resumeRequests();
                    } 
                    catch (Exception e) { 
                        e.printStackTrace(); 
                     } 
                    break; 
                 case SCROLL_STATE_DRAGGING: // The RecyclerView is currently being dragged by outside input such as user touch input. 
                     //当屏幕滚动且用户使用的触碰或手指还在屏幕上，停止加载图片 
                     try { 
                        if(getContext() != null) Glide.with(getContext()).pauseRequests(); 
                     } 
                    catch (Exception e) { 
                        e.printStackTrace(); 
                     } 
                    break; 
                 case SCROLL_STATE_SETTLING: // The RecyclerView is currently animating to a final position while not under outside control. 
                     //由于用户的操作，屏幕产生惯性滑动，停止加载图片 
                     try { 
                        if(getContext() != null) Glide.with(getContext()).pauseRequests(); 
                     } 
                    catch (Exception e) { 
                        e.printStackTrace(); 
                     } 
                    break; 
             } 
        } 
    } 
}