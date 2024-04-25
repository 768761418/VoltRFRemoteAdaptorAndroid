package com.lin.voltrfremoteadaptorandroid.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


public class ColorPickerView extends View {
    //色盘画笔
    private Paint colorWheelPaint;
    //边框画笔
    private Paint borderPaint;
    //选中画笔
    private Paint selectedPaint;
    //当前选中颜色
    private int selectedColor;

    int touchX, touchY;
    int centerX,centerY,radius;

    public ColorPickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
//        色盘画笔
        colorWheelPaint = new Paint();
        selectedColor = Color.WHITE; // 初始颜色，可根据需要更改
//        边框画笔
        borderPaint = new Paint();
//        选中画笔
        selectedPaint = new Paint();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        int padding = 15;
        int selectCircle = 20;
        int drawWidth = getWidth();
        int drawHeight = getHeight() - 2 * padding;
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;

//        radius = Math.min(centerX, centerY);
        radius = Math.min(drawWidth, drawHeight) / 2;
        int[] colors = generateColorWheel(); // 生成颜色环
        // 创建渐变色填充
        SweepGradient sweepGradient = new SweepGradient(centerX, centerY, colors, null);
        RadialGradient radialGradient = new RadialGradient(centerX, centerY, radius, Color.WHITE, Color.TRANSPARENT, Shader.TileMode.CLAMP);
        ComposeShader composeShader = new ComposeShader(sweepGradient, radialGradient, PorterDuff.Mode.SRC_OVER);
        colorWheelPaint.setShader(composeShader);
//        绘制色盘
        canvas.drawCircle(centerX, centerY, radius, colorWheelPaint);

        // 绘制选中颜色的圆圈
        selectedPaint.setAntiAlias(true);
        selectedPaint.setColor(selectedColor);
        // 根据触摸点坐标绘制圆圈
        canvas.drawCircle(touchX, touchY, selectCircle, selectedPaint);


        // 设置外边框画笔
        borderPaint.setColor(Color.WHITE);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(3); // 设置外边框宽度
        borderPaint.setAntiAlias(true); // 设置抗锯齿
        // 绘制手指触摸点外边框
        canvas.drawCircle(touchX, touchY, selectCircle, borderPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                // 计算触摸点与色盘中心的距离
                int distance = (int) Math.sqrt(Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2));
                // 如果距离大于色盘半径，则将触摸点移动到离色盘中心最近的点上
                if (distance > radius) {
                    float touchAngle = (float) Math.toDegrees(Math.atan2(y - centerY, x - centerX));
                    float newX = (float) (centerX + radius * Math.cos(Math.toRadians(touchAngle)));
                    float newY = (float) (centerY + radius * Math.sin(Math.toRadians(touchAngle)));
                    touchX = (int) newX;
                    touchY = (int) newY;
                } else {
                    touchX = x;
                    touchY = y;
                }
                selectedColor = getPixelColor(x, y);
                break;
            case MotionEvent.ACTION_UP:
                // 处理抬起事件，如果有需要的话
                break;
        }

        if (selectListener != null) {
            selectListener.selectedColor(selectedColor);
        }

        invalidate(); // 刷新视图
        return true;
    }

    public int getSelectedColor() {
        return selectedColor;
    }

    // 辅助方法：获取触摸点的颜色
    private int getPixelColor(int x, int y) {
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int radius = Math.min(centerX, centerY);
        int angle = (int) Math.toDegrees(Math.atan2(y - centerY, x - centerX));
        if (angle < 0) {
            angle += 360;
        }
        return generateColorWheel()[angle];
    }

    private SelectListener selectListener;
    public void setColorListener(SelectListener selectListener){
        this.selectListener = selectListener;
    }

    public void setColorListener2(SelectListener selectListener){
        this.selectListener = selectListener;
    }

    // 辅助方法：生成颜色环
    private int[] generateColorWheel() {
        int numColors = 360;
        int[] colors = new int[numColors];
        int startHue = 0; // 色相值的起始位置为绿色（0）
        for (int i = 0; i < 360; i++) {
            int hue = (startHue + i) % 360; // 计算当前色相值
            colors[numColors - i - 1] = Color.HSVToColor(new float[]{hue, 0.9f, 1f}); // 生成当前色相对应的颜色
        }
        return colors;
    }


    public interface SelectListener{
        void selectedColor(int color);
    }
}