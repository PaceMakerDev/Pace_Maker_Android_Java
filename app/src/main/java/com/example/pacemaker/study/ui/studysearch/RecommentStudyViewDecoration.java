package com.example.pacemaker.study.ui.studysearch;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class RecommentStudyViewDecoration extends RecyclerView.ItemDecoration {
    private final int height;

    public RecommentStudyViewDecoration(int height) {
        this.height = height;
    }

    @Override
    public void getItemOffsets(@NonNull @NotNull Rect outRect, @NonNull @NotNull View view, @NonNull @NotNull RecyclerView parent, @NonNull @NotNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.top = height;
    }
}
