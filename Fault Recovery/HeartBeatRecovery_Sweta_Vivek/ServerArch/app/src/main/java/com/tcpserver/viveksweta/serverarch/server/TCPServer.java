package com.tcpserver.viveksweta.serverarch.server;

import android.app.Activity;

import com.tcpserver.viveksweta.serverarch.activity.ACTMainDisplay;
import com.tcpserver.viveksweta.serverarch.controller.BusinessLogic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by VIVEK on 10/09/2015.
 */
public abstract class TCPServer {

    ACTMainDisplay actMainDisplay;
    private Thread thread;
    ServerSocket serverSocket;

    public TCPServer(ACTMainDisplay actMainDisplay){
        this.actMainDisplay = actMainDisplay;
    }

    //Start the service to listen to messages
    public void startService(int port) {
        thread = new Thread(new ServerThread(actMainDisplay,port));
        thread.start();
    }

    //Stop listening to messages
    public void stopListening() {
        if (serverSocket != null) {
            if (thread.isAlive()) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public abstract String[] sendHeartBeat();

    public abstract String displayMsg();

    public class ServerThread implements Runnable {

        Activity activity;
        int port;

        ServerThread(Activity activity,int port){
            this.activity = activity;
            this.port = port;
        }

        public void run() {
            try {
                serverSocket = new ServerSocket(port);

                while (true) {

                    // listen for incoming clients
                    final Socket client = serverSocket.accept();

                    try {
                        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                        new BusinessLogic().saveClientIP(client.getInetAddress().toString(),port);

//                        SharedPreferences.Editor prefsEditor = getSharedPreferences("mypref",MODE_PRIVATE).edit();
//                        prefsEditor .putString("CLIENTIP", client.getInetAddress().toString());
//                        prefsEditor.commit();

//                            startService(new Intent(TCPServer.this, TCPAuthSender.class));

                        final String line = in.readLine();

                        // Restart the thread to keep the listener alive
                        if (line != null) {
                            new BusinessLogic(actMainDisplay).updateUI(line);
                            serverSocket.close();
                            thread = null;
                            thread = new Thread(new ServerThread(activity,port));
                            thread.start();
                            break;
                        }
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }
    }
}
