package edu.rit.swen755.judge;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Joanna
 */
public class SubmissionManager implements Runnable {

    private ServerSocket socket;
    private ConcurrentLinkedQueue<Socket> sharedQueue;

    public SubmissionManager(int SERVER_PORT, ConcurrentLinkedQueue<Socket> queue) throws IOException {
        this.socket = new ServerSocket(SERVER_PORT);
        this.sharedQueue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Socket incomingConnection = socket.accept();
                synchronized (sharedQueue) {
                    sharedQueue.add(incomingConnection);
                    sharedQueue.notifyAll();
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(SubmissionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
