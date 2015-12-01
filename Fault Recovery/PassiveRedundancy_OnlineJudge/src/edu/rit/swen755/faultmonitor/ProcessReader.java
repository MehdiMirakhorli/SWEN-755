package edu.rit.swen755.faultmonitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

public class ProcessReader implements Runnable {

    private BufferedReader br;
    private volatile JTextArea textArea;

    public ProcessReader(InputStream is, JTextArea textArea) {
        this.br = new BufferedReader(new InputStreamReader(is));
        this.textArea = textArea;
    }

    public void updateTextArea(JTextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void run() {

        String line;
        try {
            while ((line = br.readLine()) != null) {
                textArea.append(line.trim() + "\n");
            }
        } catch (IOException ex) {
            Logger.getLogger(MonitorMain.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
