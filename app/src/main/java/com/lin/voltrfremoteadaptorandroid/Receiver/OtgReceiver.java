package com.lin.voltrfremoteadaptorandroid.Receiver;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.util.Log;
import android.widget.Toast;
import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialPort;
import com.hoho.android.usbserial.driver.UsbSerialProber;
import java.io.IOException;
import java.util.List;

public class OtgReceiver extends BroadcastReceiver {
    private final String  TAG = "SerialReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "8484848");
        String action = intent.getAction();
//        监听OTG插入
        if (UsbManager.ACTION_USB_ACCESSORY_ATTACHED.equals(action)){
            boolean openOTG = findOTG(context);
            if (openOTG){
                Log.d(TAG, "OTG connect success");
            }else{
                Log.d(TAG, "OTG connect fail");
            }
        }else if(UsbManager.ACTION_USB_ACCESSORY_DETACHED.equals(action)){
            Log.d(TAG, "onReceive: ---");
            Toast.makeText(context.getApplicationContext(), "端开了", Toast.LENGTH_SHORT).show();
        }

    }



    private Boolean findOTG(Context context){
        // 查找所有的设备
        UsbManager manager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
        List<UsbSerialDriver> availableDrivers = UsbSerialProber.getDefaultProber().findAllDrivers(manager);
        if (availableDrivers.isEmpty()) {
            return false;
        }

        // 获取设备，如果设备为空直接返回
        UsbSerialDriver driver = availableDrivers.get(0);
        UsbDeviceConnection connection = manager.openDevice(driver.getDevice());
        if (connection == null) {
            // add UsbManager.requestPermission(driver.getDevice(), ..) handling here
            return false;
        }
//        手机只有一个usb口，所以打开USB0
        UsbSerialPort port = driver.getPorts().get(0); // Most devices have just one port (port 0)
        try {
            port.open(connection);
            port.setParameters(9600, 8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE);
            Log.d(TAG, "onReceive: +++");
            Toast.makeText(context.getApplicationContext(), "脸上了", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }




}
