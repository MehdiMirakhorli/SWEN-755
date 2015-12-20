package com.example.viveksweta.udpclientarch;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ACTTcpClient extends Activity {

    EditText serverIp,serverPort;
    Button connectPhones;
    EditText txtSendMessage;
    Activity activity;
    TextView tvActiveStatus;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updclient);
        getHandles();
    }

    private void getHandles(){
        serverIp = (EditText) findViewById(R.id.server_ip);
        txtSendMessage = (EditText) findViewById(R.id.txtSendMessage);
        connectPhones = (Button) findViewById(R.id.connect_phones);
        connectPhones.setOnClickListener(buttonSendOnClickListener);
        serverPort = (EditText) findViewById(R.id.server_port);
        tvActiveStatus = (TextView) findViewById(R.id.tvActiveStatus);
        tempMethod();
        activity = this;
    }

//    public void displayStatus(String ackText){
//        ackMessage = ackText;
//        updateUI(ackText);
//    }

    private void tempMethod(){
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        updateUI();
                        SharedPreferences prefs = getSharedPreferences("mypref", MODE_PRIVATE);
                        String ackMsg = prefs.getString("MSG", null);
                        updateUI(ackMsg);
                        SharedPreferences.Editor prefsEditor = getSharedPreferences("mypref",MODE_PRIVATE).edit();
                        prefsEditor .putString("MSG", "");
                        prefsEditor.commit();

                    }
                });
            }
        },0,5000);
    }

    private void updateUI(String s){
        if (s != "")
            tvActiveStatus.setText(s);
        else
            tvActiveStatus.setText("No Signal");
    }

    OnClickListener buttonSendOnClickListener= new OnClickListener(){

        @Override
        public void onClick(View arg0) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    Socket socket = null;
                    DataOutputStream dataOutputStream = null;
                    DataInputStream dataInputStream = null;
                    Integer portNumber = Integer.parseInt(serverPort.getText().toString());
                    try {
                        socket = new Socket(serverIp.getText().toString(),portNumber);
                        int localPortno = socket.getLocalPort();
                        dataOutputStream = new DataOutputStream(socket.getOutputStream());
                        dataOutputStream.writeBytes(txtSendMessage.getText().toString());
                        Log.v("DATA_SENT", txtSendMessage.getText().toString());
                        startService(new Intent(ACTTcpClient.this, UDPServer.class));

                    } catch (UnknownHostException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    finally{
                        if (socket != null){
                            try {
                                socket.close();
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }

                        if (dataOutputStream != null){
                            try {
                                dataOutputStream.close();
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }

                        if (dataInputStream != null){
                            try {
                                dataInputStream.close();
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }).start();


        }};
}


