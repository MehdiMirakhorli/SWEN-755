package com.tcpserver.viveksweta.tcpserverarch;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer extends Activity implements OnClickListener{

    // default ip
    public static String SERVERIP = "";
    // designate a port
    public int _serverPort = 12345;
    private Thread thread;
    private TextView serverStatus,txtMessage;
    private Handler handler = new Handler();
    private ServerSocket serverSocket;
    Button buttonStart, buttonStop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcpserver);
        getHandles();
        SERVERIP = getLocalIpAddress();

    }


    private void getHandles() {
        serverStatus = (TextView) findViewById(R.id.server_status);
        txtMessage = (TextView) findViewById(R.id.txtRecieved);
        buttonStart = (Button) findViewById(R.id.btnStartServer);
        buttonStart.setOnClickListener(this);
        buttonStop = (Button) findViewById(R.id.btnstopserver);
        buttonStop.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnStartServer:
                startListening();
                break;

            case R.id.btnstopserver:
                stopListening();

                break;
        }
    }


    private void startListening() {
        thread=new Thread(new ServerThread(_serverPort));
        thread.start();
        buttonStart.setEnabled(false);
        buttonStop.setEnabled(true);
        startService(new Intent(TCPServer.this,TCPAuthSender.class));
    }


    private void stopListening() {
        if(thread.isAlive()){
            try {
                serverSocket.close();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            buttonStart.setEnabled(true);
            buttonStop.setEnabled(false);
            txtMessage.setText("");
            stopService(new Intent(this, TCPAuthSender.class));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Update Port Number");
        return super.onCreateOptionsMenu(menu);
    }


    public boolean onOptionsItemSelected(MenuItem item)

    {
        AlertDialog.Builder alertbox=new Builder(TCPServer.this);
        alertbox.setMessage("Enter the new PORT NUMBER");
        final EditText PortNumber=new EditText(getBaseContext());
        PortNumber.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        PortNumber.setHint("port number");
        alertbox.setView(PortNumber);
        alertbox.setTitle("New Port");
        alertbox.setPositiveButton("Submit", new
                DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        if(PortNumber.getText().length()>0){
                            stopListening();
                            _serverPort = Integer.parseInt(PortNumber.getText().toString());
                            startListening();

                        }
                    }
                });

        alertbox.setNegativeButton("Cancel", new
                DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {}
                });

        alertbox.show();


        return super.onOptionsItemSelected(item);
    }

    public class ServerThread implements Runnable {

        int SERVERPORT;

        public ServerThread(int port) {
            this.SERVERPORT = port;
        }

        public void run() {
            try {
                if (SERVERIP != null) {
                    handler.post(new ServerThread(SERVERPORT) {
                        @Override
                        public void run() {
                            serverStatus.setText("IP: " + SERVERIP+" : "+" PORT: "+SERVERPORT);
                        }
                    });

                    serverSocket = new ServerSocket(SERVERPORT);
                    while (true) {
                        // listen for incoming clients

                        final Socket client = serverSocket.accept();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                serverStatus.setText("CONNECTED");
                            }
                        });

                        try {
                            BufferedReader in = new BufferedReader(new
                                    InputStreamReader(client.getInputStream()));

                            final String line = in.readLine();
                            if (line!=null) {
                                //Log.d("ServerActivity", line);
                                serverSocket.close();
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        txtMessage.setText(line);
                                        thread=null;
                                        thread=new Thread(new ServerThread(SERVERPORT));
                                        thread.start();
                                    }

                                });
                                break;
                            }else{
                                Log.e("line null", "line null");

                            }
                            break;
                        } catch (Exception e) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    serverStatus.setText("Oops. Connection interrupted. Please reconnect your phones.");

                                }
                            });
                            e.printStackTrace();
                        }

                    }
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            serverStatus.setText("Couldn't detect internet connection");
                        }
                    });
                }
            } catch (Exception e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        serverStatus.setText("Stopped");
                    }
                });
                e.printStackTrace();
            }
        }
    }


    // // gets the ip address of your phone's network
    private String getLocalIpAddress() {
        WifiManager wifiMan = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInf = wifiMan.getConnectionInfo();
        int ipAddress = wifiInf.getIpAddress();
        String ip = String.format("%d.%d.%d.%d", (ipAddress & 0xff),(ipAddress >> 8 & 0xff),(ipAddress >> 16 & 0xff),(ipAddress >> 24 & 0xff));
        return ip;
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            // make sure you close the socket upon exiting
            if(serverSocket==null) return;
            serverSocket.close();
            buttonStart.setEnabled(true);
            buttonStop.setEnabled(false);
            stopService(new Intent(this,TCPAuthSender.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            // make sure you close the socket upon exiting
            if(serverSocket==null) return;
            serverSocket.close();
            buttonStart.setEnabled(true);
            buttonStop.setEnabled(false);
            stopService(new Intent(this, TCPAuthSender.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
