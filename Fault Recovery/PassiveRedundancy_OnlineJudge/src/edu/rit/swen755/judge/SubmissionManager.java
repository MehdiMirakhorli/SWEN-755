package edu.rit.swen755.judge;

import edu.rit.swen755.communication.SubmissionMessage;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author Joanna
 */
public class SubmissionManager implements Runnable {

    private Checkpoint checkpoint;
    private ServerSocket socket;
    private ConcurrentLinkedQueue<SubmissionMessage> sharedQueue;

    public SubmissionManager(int SERVER_PORT, ConcurrentLinkedQueue<SubmissionMessage> queue, Checkpoint checkpoint) throws IOException {
        this.socket = new ServerSocket(SERVER_PORT);
        this.sharedQueue = queue;
        this.checkpoint = checkpoint;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Socket incomingConnection = socket.accept();

                ObjectInputStream inputStream = new ObjectInputStream(incomingConnection.getInputStream());
                SubmissionMessage submissionMessage = (SubmissionMessage) inputStream.readObject();

                // if the id is not provided, then it is a request to execute code
                if (submissionMessage.getId() == null) {
                    // checkpoints the new incoming submission received
                    int sid = checkpoint.save(submissionMessage);
                    submissionMessage.setId(sid);

                    // sends Submission id back to client
                    DataOutputStream outputStream = new DataOutputStream(incomingConnection.getOutputStream());
                    outputStream.writeUTF(String.valueOf(sid));
                    incomingConnection.close();

                    synchronized (sharedQueue) {
                        // add to the queue so it becomes availble to the CodeEvaluator
                        sharedQueue.add(submissionMessage);
                        sharedQueue.notifyAll();
                    }
                } else {
                    // tries to retrieve info from database
                    Integer sid = submissionMessage.getId();
                    String feedback = checkpoint.getFeedback(sid);

                    //Send a response back to the client through the TCP connection.
                    DataOutputStream outputStream = new DataOutputStream(incomingConnection.getOutputStream());
                    outputStream.writeUTF(feedback);
                    incomingConnection.close();

                    if (!feedback.isEmpty()) {
                        System.out.println(String.format("SENDING RESPONSE (%s) for submission %d", feedback, sid));
                    }
                }

            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
