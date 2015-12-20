package com.tcpserver.viveksweta.serverarch.model;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.tcpserver.viveksweta.serverarch.R;

public class MODELServerData{

    String ip;
    int port;

    public MODELServerData(String ip, int port){
        super();
        this.ip = ip;
        this.port = port;
    }

    public MODELServerData(){
        super();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}