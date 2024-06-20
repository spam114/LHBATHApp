package com.example.basecommon.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class SimpleDrawingView extends View {
    private final int paintColor = Color.BLACK;
    private Paint drawPaint;
    public Path path = new Path();
    public boolean panSwitch;

    public SimpleDrawingView(Context context, AttributeSet attrs){
        super(context,attrs);
        panSwitch = true;
        setFocusable(true);
        setFocusableInTouchMode(true);
        setupPaint();
    }

    public void onClear(){
        path = new Path();
        postInvalidate();
    }

    public void setupPaint(){
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(5);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    public void onDraw(Canvas canvas){
        canvas.drawPath(path, drawPaint);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        float pointX = event.getX();
        float pointY = event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                path.moveTo(pointX,pointY);
                return true;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(pointX,pointY);
                break;
            default:
                return false;
        }

        postInvalidate();
        return true;
    }
}
