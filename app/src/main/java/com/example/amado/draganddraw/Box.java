package com.example.amado.draganddraw;

import android.graphics.PointF;

/**
 * Created by Amado on 16/06/2015.
 */
public class Box {
    private PointF mOrigin;

    private PointF mCurrent;

    public Box(PointF origin){
        mOrigin = mCurrent = origin;
    }

    public PointF getOrigin() {
        return mOrigin;
    }

    public PointF getCurrent() {
        return mCurrent;
    }

    public void setCurrent(PointF current) {
        mCurrent = current;
    }
}
