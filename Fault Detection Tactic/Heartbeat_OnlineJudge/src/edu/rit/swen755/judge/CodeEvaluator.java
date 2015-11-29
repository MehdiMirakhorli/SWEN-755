package edu.rit.swen755.judge;

import edu.rit.swen755.communication.SubmissionMessage;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This Runnable class is in charging of: reading submission messages from
 * client, performing the judging process (compilation, execution and output
 * verification) and send a response back to the client.
 *
 * @author Joanna
 */
public class CodeEvaluator implements Runnable {

    private ConcurrentLinkedQueue<Socket> sharedQueue;

    // openned connection to the client
    private Socket socket;
    // current submission being analyzed
    private SubmissionMessage submission;

    public CodeEvaluator(ConcurrentLinkedQueue<Socket> queue) {
        this.sharedQueue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                readSubmission();

                boolean isCompiled = compile();
                if (!isCompiled) {
                    sendResponse("Compile error");
                    // exits, since we cannot continue to execute an uncompiled code
                    return;
                }

                boolean isExecuted = execute();
                if (!isExecuted) {
                    sendResponse("Runtime error");
                    // exits, since we cannot check the outputs since it crashed during its execution
                    return;
                }

                boolean isCorrect = verify();
                if (!isCorrect) {
                    sendResponse("Wrong Answer");
                } else {
                    sendResponse("Correct");
                }
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(CodeEvaluator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @throws IOException an error happened when getting the stream
     * @throws ClassNotFoundException an error happened when parsing the
     * incoming message (SubmissionMessage object).
     */
    private void readSubmission() throws IOException, ClassNotFoundException, InterruptedException {
        //wait if queue is empty
        while (sharedQueue.isEmpty()) {
            synchronized (sharedQueue) {
                System.out.println("[JUDGE-ACTIVE] Queue is empty " + Thread.currentThread().getName()
                        + " is waiting , size: " + sharedQueue.size());
                sharedQueue.wait();
            }
        }

        socket = sharedQueue.poll();

        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
        submission = (SubmissionMessage) inputStream.readObject();
    }

    /**
     * Simulates a compilation of the source code file.
     *
     * @return true if compilation was successful, false otherwise
     */
    private boolean compile() throws InterruptedException {
        System.out.println(String.format("[JUDGE-ACTIVE] COMPILING code of team %d for problem %d", submission.getTeamId(), submission.getProblemId()));
        return generateRandomAnswer();
    }

    /**
     * Simulates the execution of the source code file.
     *
     * @return true if the execution was successful (i.e. code ran and finished
     * without any crash). False if code had a runtime error.
     */
    private boolean execute() throws InterruptedException {
        System.out.println(String.format("[JUDGE-ACTIVE] EXECUTING code of team %d for problem %d", submission.getTeamId(), submission.getProblemId()));
        return generateRandomAnswer();
    }

    /**
     * Simulates the verification of the outputs of the execution.
     *
     * @return true if the output is equal to the problem output (code is
     * correct). False if the code's output does not match with the expected
     * output.
     */
    private boolean verify() throws InterruptedException {
        System.out.println(String.format("[JUDGE-ACTIVE] VERIFYING OUTPUTS of the code of team %d for problem %d", submission.getTeamId(), submission.getProblemId()));
        return generateRandomAnswer();
    }

    /**
     * Send a response back to the client through the TCP connection.
     *
     * @param feedback a String containing the submission feedback (it can be:
     * compile error, runtime error, wrong answer or correct).
     * @throws IOException if there was a problem to send the response back
     */
    private void sendResponse(String feedback) throws IOException {
        System.out.println(String.format("[JUDGE-ACTIVE] SENDING RESPONSE (%s) to team %d", feedback, submission.getTeamId()));
        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
        outputStream.writeUTF(feedback);
        socket.close();
    }

    /**
     * This method generates true/false randomly. It may throw an
     * ArrayOutOfBoundsException randomly.
     *
     * @return true/false value randomly.
     */
    private boolean generateRandomAnswer() throws InterruptedException {
        Thread.sleep(200); //simulates some delay in processing
        Boolean[] choices = {true, false};
        Random random = new Random(System.currentTimeMillis());
        //it may raises ArrayIndexOutOfBoundsException  because of the +1 in the argument
        return choices[random.nextInt(choices.length + 1)];
    }

    /**
     * Not implemented in this assignment. Placeholder for next assignment:
     * Recovering from failure.
     */
    private void saveStatus() {
        //TODO in next assingment for recovery
        throw new UnsupportedOperationException("Not Implemented Yet");
    }

}
