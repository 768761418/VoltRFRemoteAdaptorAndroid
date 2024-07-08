package com.lin.voltrfremoteadaptorandroid.Utils;

import android.util.Log;

public class MessageUtils {
    private static String TAG = "MessageUtils";

//    开关按钮
    public static void sendMessageForSwitch(boolean isOpen){

    }
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
        Log.d(TAG, "sendMessageForSetColor: " + hue);

    }

    public static void sendMessageForOnOrOff(boolean isOn){
        Log.d(TAG, "sendMessageForOnOrOff: " + isOn);
        if(isOn){

        }else {

        }
    }

//    色温
    public static void sendMessageForTemperature(int temperature){
        Log.d(TAG, "sendMessageForTemperature: " +temperature);

    }

//    旧遥控的其他按钮
    public static void sendMessageForRemoteOther(String other){
        switch (other){
            case "flash":
                Log.d(TAG, "other:flash");
                break;
            case "strobe":
                Log.d(TAG, "other:strobe");
                break;
            case "fade":
                Log.d(TAG, "other:fade");
                break;
            case "smooth":
                Log.d(TAG, "other:smooth");
                break;

        }
    }

}
