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

public class SerialReceiver extends BroadcastReceiver {
    private final String  TAG = "SerialReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "abandon");
        String action = intent.getAction();
//        监听OTG插入
        if (UsbManager.ACTION_USB_ACCESSORY_ATTACHED.equals(action)){

            // Find all available drivers from attached devices.
            UsbManager manager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
            List<UsbSerialDriver> availableDrivers = UsbSerialProber.getDefaultProber().findAllDrivers(manager);
            if (availableDrivers.isEmpty()) {
                return;
            }

            // Open a connection to the first available driver.
            UsbSerialDriver driver = availableDrivers.get(0);
            UsbDeviceConnection connection = manager.openDevice(driver.getDevice());
            if (connection == null) {
                // add UsbManager.requestPermission(driver.getDevice(), ..) handling here
                return;
            }

            UsbSerialPort port = driver.getPorts().get(0); // Most devices have just one port (port 0)
            try {
                port.open(connection);
                port.setParameters(9600, 8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE);
                Log.d(TAG, "onReceive: +++");
                Toast.makeText(context.getApplicationContext(), "脸上了", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }else if(UsbManager.ACTION_USB_ACCESSORY_DETACHED.equals(action)){
            Log.d(TAG, "onReceive: ---");
            Toast.makeText(context.getApplicationContext(), "端开了", Toast.LENGTH_SHORT).show();
        }

    }



}
