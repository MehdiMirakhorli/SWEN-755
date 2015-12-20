package com.tcpserver.viveksweta.serverarch.server;

import android.app.Activity;
import android.util.Log;

import com.tcpserver.viveksweta.serverarch.activity.ACTMainDisplay;
import com.tcpserver.viveksweta.serverarch.controller.BusinessLogic;
import com.tcpserver.viveksweta.serverarch.global.EnvironmentVariable;
import com.tcpserver.viveksweta.serverarch.heartbeat.HealthMonitor;

import java.util.Calendar;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by VIVEK on 10/09/2015.
 */

public class TCPServerBackup extends TCPServer{

    static Timer timer;

    public TCPServerBackup(ACTMainDisplay actMainDisplay) {
        super(actMainDisplay);
        sendHeartBeat();
    }

    @Override
    public String[] sendHeartBeat() {

        final String []dataToMonitor = new String[3];

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Random rn = new Random();
                int result = rn.nextInt(3 - 0 + 1) + 0;


                Calendar calendar = Calendar.getInstance();
                dataToMonitor[0] = "" + result;
                dataToMonitor[1] = displayMsg();
                dataToMonitor[2] = "" + calendar.getTime();

                HealthMonitor.tempImCalled(dataToMonitor);

                Log.i("BackUp", dataToMonitor[0]+dataToMonitor[1]);
            }
        }, 1000, 3000);

        return  dataToMonitor;

    }

    //Stop listening to messages
    public static void stopBackupTimer() {
        if (timer !=null) {
            timer.cancel();
            timer = null;
        }
    }


    @Override
    public String displayMsg() {
        EnvironmentVariable.status = EnvironmentVariable.backupServer;
        return EnvironmentVariable.backupServer;
    }
}
