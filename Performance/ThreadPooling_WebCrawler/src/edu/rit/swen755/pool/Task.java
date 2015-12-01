package edu.rit.swen755.pool;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Thread responsible for visiting links and verifying if they are internal or
 * external.
 *
 * @author Joanna
 */
public class Task implements Runnable {

    private final URL currentURL;

    public Task(URL currentURL) {
        this.currentURL = currentURL;
    }

    @Override
    public void run() {
        String responseCode = null;
        String responseMessage = null;
        try {
            HttpURLConnection con = (HttpURLConnection) currentURL.openConnection();
            con.setRequestMethod("HEAD");
            responseCode = String.valueOf(con.getResponseCode());
            responseMessage = con.getResponseMessage();
        } catch (IOException ex) {
            responseCode = ex.getClass().getName();
        } finally {
            System.out.println(Thread.currentThread().getName() + " " + responseCode + " ("+responseMessage+") " + currentURL);
        }

    }
}
