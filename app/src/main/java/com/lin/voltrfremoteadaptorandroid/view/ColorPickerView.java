package com.lin.voltrfremoteadaptorandroid.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.lin.voltrfremoteadaptorandroid.R;
import com.lin.voltrfremoteadaptorandroid.setting.ColorSetting;

import java.util.TimerTask;


public class ColorPickerView extends View {
    //色盘画笔
    private Paint colorWheelPaint;
    //边框画笔
    private Paint borderPaint;
    //选中画笔
    private Paint selectedPaint;
    private Paint blackPaint;
    private Paint colorBorderPaint;
    //当前选中颜色
    private int selectedColor;
//    选择器位置
    private int touchX, touchY;
//    色盘位置与半径
    private int centerX,centerY,radius,innerCircleRadius;
//    颜色环数组
    private int[] colors;
    private static String TAG = "ColorPickerView";
    private int numColors ;
    private  boolean isDrawn = false;
    private Runnable onDrawCompletedCallback;
//    移动动画效果


    public ColorPickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
//        色盘画笔
        colorWheelPaint = new Paint();
        selectedColor = Color.WHITE; // 初始颜色，可根据需要更改
//        边框画笔
        borderPaint = new Paint();
//        选中画笔
        selectedPaint = new Paint();
//        中心圆画笔
        blackPaint = new Paint();
        colorBorderPaint = new Paint();
        numColors = 360;
    }



    @Override
    protected void onDraw(Canvas canvas) {
        //        设置圆心位置
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
        //设置选择器半径
        int selectCircle = (int)( Math.min(centerX, centerY)*0.1);
        int selectBorder = selectCircle + 3;
        //设置色盘半径
        int drawWidth = getWidth();
        int drawHeight = getHeight() - 2*selectBorder;
        radius = Math.min(drawWidth, drawHeight) / 2;

        // 设置中心圆的半径
        innerCircleRadius = radius / 3;
        int colorWheelTopY = centerY - radius;
        int colorWheelBottomY = centerY + radius;
        int innerCircleTopY = centerY - innerCircleRadius;
        int innerCircleBottomY = centerY + innerCircleRadius;

        // 绘制色盘
        colors = generateColorWheel(); // 生成颜色环
        SweepGradient sweepGradient = new SweepGradient(centerX, centerY, colors, null);
        RadialGradient radialGradient = new RadialGradient(centerX, centerY, radius, Color.WHITE, Color.TRANSPARENT, Shader.TileMode.CLAMP);
        ComposeShader composeShader = new ComposeShader(sweepGradient, radialGradient, PorterDuff.Mode.SRC_OVER);
        colorWheelPaint.setShader(composeShader);
        canvas.drawCircle(centerX, centerY, radius, colorWheelPaint);

        //绘制色盘边框
        int colorWheelBorderStartColor = getContext().getColor(R.color.color_wheel_border_start);
        int colorWheelBorderEndColor = getContext().getColor(R.color.color_wheel_border_end);
        LinearGradient colorWheelBorderGradient = new LinearGradient(centerX,colorWheelTopY,centerX,colorWheelBottomY,colorWheelBorderStartColor,colorWheelBorderEndColor, Shader.TileMode.CLAMP);
        colorBorderPaint.setShader(colorWheelBorderGradient);
        colorBorderPaint.setStyle(Paint.Style.STROKE);
        colorBorderPaint.setStrokeWidth(5); // 设置外边框宽度
        colorBorderPaint.setAntiAlias(true); // 设置抗锯齿
        canvas.drawCircle(centerX, centerY, radius, colorBorderPaint);


        //绘制中心圆
        int innerStartColor = getContext().getColor(R.color.inner_circle_start);
        int innerEndColor = getContext().getColor(R.color.inner_circle_end);
        // 创建垂直渐变，从上到下
        LinearGradient gradient = new LinearGradient(centerX, innerCircleTopY, centerX,innerCircleBottomY, innerStartColor, innerEndColor, Shader.TileMode.CLAMP);
        // 设置画笔的渐变着色器
        blackPaint.setShader(gradient);
        blackPaint.setStyle(Paint.Style.FILL_AND_STROKE); // 设置绘制风格为填充并具有平滑边缘
        canvas.drawCircle(centerX, centerY, innerCircleRadius, blackPaint);



        // 绘制选择器
        selectedPaint.setAntiAlias(true);
        selectedPaint.setColor(selectedColor);
        // 根据触摸点坐标绘制圆圈
        canvas.drawCircle(touchX, touchY, selectCircle, selectedPaint);


        // 设置选择器边框
        borderPaint.setColor(Color.WHITE);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(5); // 设置外边框宽度
        borderPaint.setAntiAlias(true); // 设置抗锯齿
        // 绘制手指触摸点外边框
        canvas.drawCircle(touchX, touchY, selectCircle, borderPaint);
        if (!isDrawn){
            // 如果第一次有回调，执行回调
            if (onDrawCompletedCallback != null) {
                new Handler().post(onDrawCompletedCallback);
            }
            isDrawn = true;
        }

    }

    public boolean getIsDraw(){
        return this.isDrawn;
    }
    public void setIsDraw(boolean isDraw){
        this.isDrawn = isDraw;
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
                }else if (distance< innerCircleRadius ){
                    float touchAngle = (float) Math.toDegrees(Math.atan2(y - centerY, x - centerX));
                    float newX = (float) (centerX + innerCircleRadius * Math.cos(Math.toRadians(touchAngle)));
                    float newY = (float) (centerY + innerCircleRadius * Math.sin(Math.toRadians(touchAngle)));
                    touchX = (int) newX;
                    touchY = (int) newY;
                }

                else {
                    touchX = x;
                    touchY = y;
                }
                selectedColor = getPixelColor(x, y);
                break;
            case MotionEvent.ACTION_UP:
                // 处理抬起事件，如果有需要的话
                if (keyUpListener != null){
                    keyUpListener.saveColor(selectedColor,touchX,touchY);
                }
                break;
        }

        if (selectListener != null) {
            selectListener.selectedColor(selectedColor);
        }

        invalidate(); // 刷新视图
        return true;
    }



//    Activity调用预设改变色盘选择的函数
    @SuppressLint("ObjectAnimatorBinding")
    public int[] externalClickPresuppose(int color){
        int[] resultXY = useGetNewXY(color,0);

        int newX = resultXY[0];
        int newY = resultXY[1];
//                    修改当前颜色
        selectedColor = color;
        // 创建属性动画
        ObjectAnimator xAnimator = ObjectAnimator.ofInt(this, "touchX", touchX, newX);
        ObjectAnimator yAnimator = ObjectAnimator.ofInt(this, "touchY", touchY, newY);

        // 设置动画持续时间
        xAnimator.setDuration(1000);
        yAnimator.setDuration(1000);

                    // 开始动画
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(xAnimator, yAnimator);
        animatorSet.start();
        touchX = newX;
        touchY = newY;
        invalidate();

        return resultXY;
    }

    public int[] setStartColor(int color ){
        int[] resultXY = useGetNewXY(color,0);
        touchX = resultXY[0];
        touchY = resultXY[1];
        selectedColor = color;
        invalidate();
        return resultXY;
    }


//    setter给动画调用的
    public void setTouchX(int x) {
        this.touchX = x;
        invalidate(); // 更新视图以反映属性更改
    }
    public void setTouchY(int y) {
        this.touchY = y;
        invalidate(); // 更新视图以反映属性更改
    }
    public void setSelectedColor(int selectedColor){
        this.selectedColor = selectedColor;
        invalidate();
    }




    public int[] useGetNewXY(int color,int hue){
        int[] result = new int[2];
        float[] colorHsv = getHsv(color);
        if (hue ==0){
            hue = (int)(colorHsv[0] + 0.5f);
        }
        int angle = numColors - hue - 1;
        int colorRadius = (int)(colorHsv[1]*radius + 0.5f);

        // 计算坐标
        int newX = (int) (centerX +colorRadius  * Math.cos(Math.toRadians(angle)));
        int newY = (int) (centerY +colorRadius  * Math.sin(Math.toRadians(angle)));
        Log.d(TAG, "useGetNewXY: " + newX + "xxxx" + newY);
        result[0] = newX;
        result[1] = newY;
        return result;
    }

    // 辅助方法：获取触摸点的颜色
    private int getPixelColor(int x, int y) {
//        int radius = Math.min(centerX, centerY);
        int angle = (int) Math.toDegrees(Math.atan2(y - centerY, x - centerX));
        if (angle < 0) {
            angle += 360;
        }
        float[] hsv = getHsv(generateColorWheel()[angle]);
        // 计算 (x, y) 和 (centerX, centerY) 之间的距离
        float distance = (float) Math.sqrt(Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2));
        float saturation = distance/radius;
        int currentColor =  Color.HSVToColor(new float[]{hsv[0], saturation, ColorSetting.colorV});
        return currentColor;
    }


//    获取hsv的值
//    hsv[0] 是色相值，hsv[1] 是饱和度值，hsv[2] 是明度值
    private float[] getHsv(int color){
        float[] hsv = new float[3];
        Color.colorToHSV(color,hsv);
        return  hsv;
    }

//    选择接口和保存接口
    private SelectListener selectListener;
    private KeyUpListener keyUpListener;
    public void setColorListener(SelectListener selectListener){
        this.selectListener = selectListener;
    }
    public void setKeyUpListener(KeyUpListener keyUpListener){
        this.keyUpListener = keyUpListener;
    }


    // 辅助方法：生成颜色环
    private int[] generateColorWheel() {

        int[] colors = new int[numColors];
        int startHue = 0; // 色相值的起始位置为绿色（0）
        for (int i = 0; i < 360; i++) {
            int hue = (startHue + i) % 360; // 计算当前色相值
            colors[numColors - i - 1] = Color.HSVToColor(new float[]{hue, ColorSetting.colorS, ColorSetting.colorV}); // 生成当前色相对应的颜色
        }
        return colors;
    }

    public void setOnDrawCompletedCallback(Runnable callback) {
        this.onDrawCompletedCallback = callback;
    }


    public interface SelectListener{
        void selectedColor(int color);
    }

    public interface KeyUpListener{
        void saveColor(int color,int touchX, int touchY);
    }




}