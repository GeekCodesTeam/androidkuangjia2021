package com.haier.cellarette.baselibrary.recycleviewmultitype.viewholders.demo6;

import android.graphics.Rect;
//import android.support.annotation.NonNull;
//import androidx.appcompat.widget.GridLayoutManager.SpanSizeLookup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

public class ItemDemo6ItemDecoration extends RecyclerView.ItemDecoration {

  private int space;
  private @NonNull
  SpanSizeLookup spanSizeLookup;


  public ItemDemo6ItemDecoration(int space, @NonNull SpanSizeLookup spanSizeLookup) {
    this.space = space;
    this.spanSizeLookup = spanSizeLookup;
  }


  @Override
  public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
    int position = parent.getChildLayoutPosition(view);
    if (spanSizeLookup.getSpanSize(position) == 1) {
      outRect.left = space;
      if (position % 2 == 0) {
        outRect.right = space;
      }
    }
  }
}
