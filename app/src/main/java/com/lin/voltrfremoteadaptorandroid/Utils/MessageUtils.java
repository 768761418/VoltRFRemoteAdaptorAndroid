package com.lin.voltrfremoteadaptorandroid.Utils;

import android.util.Log;

public class MessageUtils {
    private static String TAG = "MessageUtils";
//    zone按钮发消息的函数
    public static void sendMessageForZone(int zone){
        Log.d(TAG, "zone" + zone);
        //TODO
        //发送zone的信息
    }

//    设置亮度
    public static void sendMessageForSetLuminance(int luminance){
        Log.d(TAG, "Luminance: " + luminance);
        //TODO
        //发送设置亮度的信息
    }

    public static void sendMessageForSetColor(int hue){

    }

    public static void sendMessageForOnOrOff(boolean isOn){
        if(isOn){

        }else {

        }
    }


}
