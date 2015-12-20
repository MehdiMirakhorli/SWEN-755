package com.tcpserver.viveksweta.serverarch.controller;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.tcpserver.viveksweta.serverarch.activity.ACTMainDisplay;
import com.tcpserver.viveksweta.serverarch.global.EnvironmentVariable;
import com.tcpserver.viveksweta.serverarch.model.MODELServerData;
import com.tcpserver.viveksweta.serverarch.server.TCPServer;
import com.tcpserver.viveksweta.serverarch.server.TCPServerBackup;
import com.tcpserver.viveksweta.serverarch.server.TCPServerPrimary;

/**
 * Created by VIVEK on 10/09/2015.
 */
public class BusinessLogic {

    static ACTMainDisplay actMainDisplay;

    public BusinessLogic(ACTMainDisplay actMainDisplay){
        this.actMainDisplay = actMainDisplay;
    }

    public BusinessLogic(){
        super();
    }

    public boolean saveClientIP(String ip, int port) {

        boolean saveStatus = false;

        EnvironmentVariable.clientList.add(new MODELServerData(ip, port));

        return saveStatus;
    }

    public String getOwnIP(Activity activity){
        WifiManager wifiMan = (WifiManager) activity.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInf = wifiMan.getConnectionInfo();
        int ipAddress = wifiInf.getIpAddress();
        return String.format("%d.%d.%d.%d", (ipAddress & 0xff), (ipAddress >> 8 & 0xff), (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff));
    }


    public void initializePrimaryServer(ACTMainDisplay actMainDisplay){
        TCPServer serverPrimary = new TCPServerPrimary(actMainDisplay);
        TCPServer serverBackup = new TCPServerBackup(actMainDisplay);
        serverBackup.stopListening();
        serverPrimary.startService(EnvironmentVariable.selfPort);
    }

    public static void switchServer(String[] receivedMsg){

        if (receivedMsg[0].equals("0")){
            Log.i("Monitor",receivedMsg[0]+receivedMsg[1]);
            String server = receivedMsg[1];
            if (server.equals(EnvironmentVariable.primaryServer)){
                TCPServerPrimary.stopPrimaryTimer();
                TCPServerBackup backup = new TCPServerBackup(actMainDisplay);
                backup.sendHeartBeat();
                backup.displayMsg();

            }else if (server.equals(EnvironmentVariable.backupServer)){
                Log.i("Monitor",receivedMsg[0]+receivedMsg[1]);
                TCPServerBackup.stopBackupTimer();
                TCPServerPrimary primary = new TCPServerPrimary(actMainDisplay);
                primary.sendHeartBeat();
                primary.displayMsg();
            }
        }else {

        }
    }

    public void updateUI(String clientMsg){
        actMainDisplay.updateMsg(clientMsg);
    }
}
