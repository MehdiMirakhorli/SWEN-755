package com.tcpserver.viveksweta.serverarch.global;

import com.tcpserver.viveksweta.serverarch.model.MODELServerData;

import java.util.ArrayList;

/**
 * Created by VIVEK on 10/09/2015.
 */
public class EnvironmentVariable {

    public static int selfPort = 12345;

    public static String primaryServer = "Server_1";

    public static String backupServer = "Server_2";

    public static String status = "";

    public static ArrayList<MODELServerData> clientList = new ArrayList<MODELServerData>();
}
