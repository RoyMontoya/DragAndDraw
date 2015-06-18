package com.example.amado.draganddraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Amado on 16/06/2015.
 */
public class BoxDrawingView extends View {
    public static final String TAG = "BoxDrawingView";
    private  Box mCurrentBox;
    private static final String PARCELEABLE = "parceleable";
    private static final String BOXES = "boxes";
    private static final int VIEW_ID = 10;
    private ArrayList<Box> mBoxes = new ArrayList<Box>();
    private Paint mBoxPaint;
    private Paint mBackgroundPaint;

    public BoxDrawingView(Context context){
        super(context, null);
    }

    public BoxDrawingView(Context context, AttributeSet attrs){
        super(context, attrs);
        this.setId(R.id.view_id);
        mBoxPaint = new Paint();
        mBoxPaint.setColor(0x22ff0000);

        mBackgroundPaint = new Paint();
        mBackgroundPaint.setColor(0xfff8efe0);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        PointF curr = new PointF(event.getX(), event.getY());

        Log.i(TAG, "Recieved event at x="+curr.x+", y="+curr.y+":");
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "ACTION_DOWN");
                mCurrentBox = new Box(curr);
                        mBoxes.add(mCurrentBox);
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "ACTION_MOVE");
                if (mCurrentBox !=null){
                    mCurrentBox.setCurrent(curr);
                    invalidate();
                }

                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "ACTION_UP");
                mCurrentBox = null;
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.i(TAG, "ACTION_CANCEL");
                mCurrentBox = null;
                break;
        }

        return true;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPaint(mBackgroundPaint);
        for(Box box: mBoxes){
            float left = Math.min(box.getOrigin().x, box.getCurrent().x);
            float right = Math.max(box.getOrigin().x, box.getCurrent().x);
            float top = Math.min(box.getOrigin().y, box.getCurrent().y);
            float bottom = Math.max(box.getOrigin().y, box.getCurrent().y);

            canvas.drawRect(left, top, right, bottom, mBoxPaint);
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle onSavedInstanceState = new Bundle();
        onSavedInstanceState.putParcelable(PARCELEABLE, super.onSaveInstanceState());
        onSavedInstanceState.putSerializable(BOXES, mBoxes);

        return onSavedInstanceState;

    }


    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Bundle onSavedInstanceState = (Bundle)state;
        if(onSavedInstanceState!=null){
            Log.d(TAG, "recieved something");
        }
        mBoxes =(ArrayList < Box >)onSavedInstanceState.getSerializable(BOXES);
        super.onRestoreInstanceState(onSavedInstanceState.getParcelable(PARCELEABLE));
        invalidate();

    }
}
