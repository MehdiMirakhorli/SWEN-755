package edu.rit.swen755.client;

import edu.rit.swen755.communication.SubmissionMessage;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author Joanna
 */
public class TCPConnection  {

    private final Socket connection;

    public TCPConnection(String hostname, int port) throws IOException {
        connection = new Socket(hostname, port);
        connection.setKeepAlive(true);
    }

    public String send(String fileName, int problemId, int teamId) throws IOException {
        SubmissionMessage message = new SubmissionMessage(fileName, teamId, problemId);
        ObjectOutputStream outStream = new ObjectOutputStream(connection.getOutputStream());
        outStream.writeObject(message);
        DataInputStream  inStream = new DataInputStream(connection.getInputStream());
        String feedback = inStream.readUTF();
        connection.close();
        return feedback;
    }
}
