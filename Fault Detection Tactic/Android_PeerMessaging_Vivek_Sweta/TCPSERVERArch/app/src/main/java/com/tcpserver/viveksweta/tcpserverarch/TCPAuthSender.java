package com.tcpserver.viveksweta.tcpserverarch;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

/**
 * Created by VIVEK on 10/06/2015.
 */
public class TCPAuthSender extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
//        Toast.makeText(this,"OnDestroy",Toast.LENGTH_SHORT).show();
    }
}
