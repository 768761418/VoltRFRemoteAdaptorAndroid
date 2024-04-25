package com.lin.voltrfremoteadaptorandroid.Utils;

import android.content.Intent;
import android.graphics.Color;

public class ColorUtils {

    public static float getColorHue(int colorInt){
        // 获取颜色的红、绿、蓝分量
        int red = Color.red(colorInt);
        int green = Color.green(colorInt);
        int blue = Color.blue(colorInt);

        // 将 RGB 分量转换为 HSV 格式
        float[] hsv = new float[3];
        Color.RGBToHSV(red, green, blue, hsv);

        // 获取 HSV 分量
        float hue = hsv[0]; // 色相
        float saturation = hsv[1]; // 饱和度
        float value = hsv[2]; // 明度

        return  hue;
    }

}
