package com.example.viveksweta.udpclientarch;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPServer extends Service{

    @Override
    public void onCreate() {
        super.onCreate();
        new udpAckReceiver().execute("");
    }

    class udpAckReceiver extends AsyncTask<String,String,String>{

        String receiveMessage(){

            DatagramSocket clientsocket = null;
            String rec_str = "";
            try {
                int port = 5000;
                if (clientsocket == null) clientsocket=new DatagramSocket(port);

                byte[] receivedata = new byte[30];

                DatagramPacket recv_packet = new DatagramPacket(receivedata, receivedata.length);
                //Log.d("UDP", "S: Receiving...");
                clientsocket.receive(recv_packet);
                rec_str = new String(recv_packet.getData()); //stringa con mesasggio ricevuto
//                rec_str= rec_str.replace(Character.toString((char) 0), "");

                return rec_str;

            } catch (Exception e) {
                Log.e("UDP", "S: Error", e);
            }
            return rec_str;
        }

        @Override
        protected String doInBackground(String... params) {
            while (true) {
                try {
                    publishProgress(receiveMessage());
                    if(isCancelled()) break;
                } catch (Exception e) {
                }
            }


//            String actText = "";
//            byte[] lMsg = new byte[1000];
//
//
//            try {
//                ds = new DatagramSocket(5000);
//                ds.receive(dp);
//                actText = new String(lMsg, 0, dp.getLength());
//                Log.d("DATA RECEIVED", actText);
//            } catch (SocketException e) {
//                e.printStackTrace();
//                actText = e.toString();
//            } catch (IOException e) {
//                e.printStackTrace();
//                actText = e.toString();
//            }
            return "";
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
//            DataTransfer dataTransfer = new DataTransfer();
//            dataTransfer.ackReceived(values.toString());

            SharedPreferences.Editor prefsEditor = getSharedPreferences("mypref",MODE_PRIVATE).edit();
            prefsEditor .putString("MSG", values.toString());
            prefsEditor.commit();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }
    }



    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
