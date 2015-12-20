package com.tcpserver.viveksweta.serverarch.heartbeat;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.tcpserver.viveksweta.serverarch.controller.BusinessLogic;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by VIVEK on 10/09/2015.
 */
public class HealthMonitor extends Service{

    static String[] receivedData = new String[3];

    @Override
    public void onCreate() {
        super.onCreate();
        countDownTimer();
    }

    private void countDownTimer(){

        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (receivedData != null) {
                    if (receivedData[0] != null) {
                        Log.i("Monitor", receivedData[0] + receivedData[1]);
                        BusinessLogic.switchServer(receivedData);
                    }
                }
                else{

                }

            }
        },1000,1000);

    }

    public static void tempImCalled(String[] dataFromServer){
        receivedData[0] = dataFromServer[0];
        receivedData[1] = dataFromServer[1];
        receivedData[2] = dataFromServer[2];
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
