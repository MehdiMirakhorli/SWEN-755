package com.tcpserver.viveksweta.serverarch.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.tcpserver.viveksweta.serverarch.R;
import com.tcpserver.viveksweta.serverarch.controller.BusinessLogic;
import com.tcpserver.viveksweta.serverarch.global.EnvironmentVariable;
import com.tcpserver.viveksweta.serverarch.heartbeat.HealthMonitor;

/**
 * Created by VIVEK on 10/09/2015.
 */
public class ACTMainDisplay extends Activity{

    TextView tvIP,tvPort,tvStatus;
    EditText etMessage;

    @Override
    protected void onStart() {
        super.onStart();
        System.out.print(";lkasjdf");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actdisplay_message);
        getHandles();
    }

    //Provide handles for all the views used
    private void getHandles(){
        tvIP = (TextView)findViewById(R.id.tvIP);
        tvPort = (TextView)findViewById(R.id.tvPort);
        tvStatus = (TextView)findViewById(R.id.tvStatus);
        etMessage = (EditText)findViewById(R.id.etMessage);

        tvIP.setText(new BusinessLogic().getOwnIP(ACTMainDisplay.this));
        tvPort.setText("" + EnvironmentVariable.selfPort);

        startServersAndMonitor();
        updateStatus();
    }

    private void startServersAndMonitor(){
        new BusinessLogic(ACTMainDisplay.this).initializePrimaryServer(ACTMainDisplay.this);
        startHealthMonitor();
    }

    public void startHealthMonitor(){
        startService(new Intent(ACTMainDisplay.this, HealthMonitor.class));
    }

    public void updateMsg(final String clientMsg){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                etMessage.setText(clientMsg);
            }
        });

    }



    public void updateStatus(){
        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvStatus.setText(EnvironmentVariable.status);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        t.start();
    }
}
