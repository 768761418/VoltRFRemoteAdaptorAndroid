package com.lin.voltrfremoteadaptorandroid.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class TemperatureSeekbarView extends View {

    private Paint seekbarPaint;
    private Paint currentPaint;
    private RectF seekbarRect;
    private Rect currentRect;
    private int touchX,touchY;
    private int seekbarX,seekbarY;


    public TemperatureSeekbarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        seekbarRect = new RectF();
        seekbarRect.top = 20;
        seekbarRect.bottom = getHeight();
        seekbarRect.left = 0;
        seekbarRect.right = getWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        seekbarX = 0;
        seekbarY = 0;
        canvas.drawRoundRect(seekbarRect,seekbarX,seekbarY,seekbarPaint);
    }
}
