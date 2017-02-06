package com.xiangying.fighting.common.layoutmanager;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2015/11/11.
 */
public class ExStaggeredGridLayoutManager extends StaggeredGridLayoutManager {

    public ExStaggeredGridLayoutManager(int spanCount, int orientation) {
        super(spanCount, orientation);
    }

    private int[] measuredDimension = new int[2];

    private int[] dimension;


    @Override

    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
        final int widthMode = View.MeasureSpec.getMode(widthSpec);
        final int widthSize = View.MeasureSpec.getSize(widthSpec);
        final int heightMode = View.MeasureSpec.getMode(heightSpec);
        final int heightSize = View.MeasureSpec.getSize(heightSpec);

        int width = 0;
        int height = 0;
        int count = getItemCount();
        int span = getSpanCount();
        dimension = new int[span];

        for (int i = 0; i < count; i++) {
            measureScrapChild(recycler, i,
                    View.MeasureSpec.makeMeasureSpec(i, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(i, View.MeasureSpec.UNSPECIFIED), measuredDimension);

            //Log.d("LISTENER", "position " + i + " height = " + measuredDimension[1]);
            if (getOrientation() == VERTICAL) {
                dimension[findMinIndex(dimension)] += measuredDimension[1];
            } else {
                dimension[findMinIndex(dimension)] += measuredDimension[0];
            }
        }
        if (getOrientation() == VERTICAL) {
            height = findMax(dimension);
        } else {
            width = findMax(dimension);
        }


        switch (widthMode) {
            case View.MeasureSpec.EXACTLY:
                width = widthSize;
                break;
            case View.MeasureSpec.AT_MOST:
                break;
            case View.MeasureSpec.UNSPECIFIED:
                break;
        }
        switch (heightMode) {
            case View.MeasureSpec.EXACTLY:
                height = heightSize;
                break;
            case View.MeasureSpec.AT_MOST:
                break;
            case View.MeasureSpec.UNSPECIFIED:
                break;
        }
        setMeasuredDimension(width, height);
    }

    private void measureScrapChild(RecyclerView.Recycler recycler, int position, int widthSpec,
                                   int heightSpec, int[] measuredDimension) {

        if (position < getItemCount()) {
            try {
                View view = recycler.getViewForPosition(position);

                if (view != null) {
                    RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) view.getLayoutParams();
                    int childWidthSpec = ViewGroup.getChildMeasureSpec(widthSpec, getPaddingLeft() + getPaddingRight(), lp.width);
                    int childHeightSpec = ViewGroup.getChildMeasureSpec(heightSpec, getPaddingTop() + getPaddingBottom(), lp.height);
                    view.measure(childWidthSpec, childHeightSpec);
                    measuredDimension[0] = view.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
                    measuredDimension[1] = view.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
                    recycler.recycleView(view);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private int findMax(int[] array) {
        int max = array[0];
        for (int value : array) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    /**
     *
     * @param array
     * @return
     */
    private int findMinIndex(int[] array) {
        int index = 0;
        int min = array[0];
        for (int i = 0; i < array.length; i++) {
            if (array[i] < min) {
                min = array[i];
                index = i;
            }
        }
        return index;
    }

}

