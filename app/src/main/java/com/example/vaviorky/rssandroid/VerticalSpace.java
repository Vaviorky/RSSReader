package com.example.vaviorky.rssandroid;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;


class VerticalSpace extends RecyclerView.ItemDecoration {
    private int Space;

    VerticalSpace(int Space) {
        this.Space = Space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = Space;
        outRect.bottom = Space;
        outRect.right = Space;
        if (parent.getChildLayoutPosition(view) == 0) {
            outRect.top = Space;
        }
    }
}
