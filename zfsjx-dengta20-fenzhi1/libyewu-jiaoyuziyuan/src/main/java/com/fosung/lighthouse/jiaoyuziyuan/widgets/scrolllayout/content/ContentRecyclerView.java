package com.fosung.lighthouse.jiaoyuziyuan.widgets.scrolllayout.content;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.fosung.lighthouse.jiaoyuziyuan.widgets.scrolllayout.ScrollLayout;
import com.zcolin.gui.zrecyclerview.ZRecyclerView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ContentRecyclerView extends ZRecyclerView {

    private final CompositeScrollListener compositeScrollListener =
            new CompositeScrollListener();

    public ContentRecyclerView(Context context) {
        super(context);
    }

    public ContentRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ContentRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    {
        getRecyclerView().addOnScrollListener(compositeScrollListener);

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ViewGroup.LayoutParams layoutParams = getLayoutParams();
                ViewParent parent = getParent();
                while (parent != null) {
                    if (parent instanceof ScrollLayout) {
                        int height = ((ScrollLayout) parent).getMeasuredHeight() - ((ScrollLayout) parent).minOffset;
                        if (layoutParams.height == height) {
                            return;
                        } else {
                            layoutParams.height = height;
                            break;
                        }
                    }
                    parent = parent.getParent();
                }
                setLayoutParams(layoutParams);
            }
        });
    }

    private class CompositeScrollListener extends RecyclerView.OnScrollListener {
        private final List<RecyclerView.OnScrollListener> scrollListenerList = new
                ArrayList<RecyclerView.OnScrollListener>();

        public void addOnScrollListener(RecyclerView.OnScrollListener listener) {
            if (listener == null) {
                return;
            }
            for (RecyclerView.OnScrollListener scrollListener : scrollListenerList) {
                if (listener == scrollListener) {
                    return;
                }
            }
            scrollListenerList.add(listener);
        }

        public void removeOnScrollListener(AbsListView.OnScrollListener listener) {
            if (listener == null) {
                return;
            }
            Iterator<RecyclerView.OnScrollListener> iterator = scrollListenerList.iterator();
            while (iterator.hasNext()) {
                RecyclerView.OnScrollListener scrollListener = iterator.next();
                if (listener == scrollListener) {
                    iterator.remove();
                    return;
                }
            }
        }

        @Override
        public void onScrollStateChanged(RecyclerView view, int scrollState) {
            List<RecyclerView.OnScrollListener> listeners = new ArrayList<RecyclerView.OnScrollListener>(scrollListenerList);
            for (RecyclerView.OnScrollListener listener : listeners) {
                listener.onScrollStateChanged(view, scrollState);
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            List<RecyclerView.OnScrollListener> listeners = new ArrayList<RecyclerView.OnScrollListener>(scrollListenerList);
            for (RecyclerView.OnScrollListener listener : listeners) {
                listener.onScrolled(recyclerView, dx, dy);
            }
        }
    }
}
