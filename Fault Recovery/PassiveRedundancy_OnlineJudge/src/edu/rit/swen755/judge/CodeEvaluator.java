package edu.rit.swen755.judge;

import edu.rit.swen755.communication.SubmissionMessage;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * This Runnable class is in charge of: reading submission messages from
 * client, performing the judging process (compilation, execution and output
 * verification) and send a response back to the client.
 *
 * @author Joanna
 */
public class CodeEvaluator implements Runnable {

    private static final String RESPONSE_COMPILE_ERROR = "Compile Error";
    private static final String RESPONSE_RUNTIME_ERROR = "Runtime error";
    private static final String RESPONSE_WRONG_ANSWER = "Wrong Answer";
    private static final String RESPONSE_CORRECT = "Correct";

    private final Checkpoint checkPoint;
    // queue of incoming connections
    private final ConcurrentLinkedQueue<SubmissionMessage> sharedQueue;

    // current submission being analyzed
    private SubmissionMessage submission;

    public CodeEvaluator(ConcurrentLinkedQueue<SubmissionMessage> queue, Checkpoint checkpoint) {
        this.sharedQueue = queue;
        this.checkPoint = checkpoint;
    }

    @Override
    public void run() {

        while (true) {
            readSubmission();

            boolean isCompiled = compile();
            if (!isCompiled) {
                checkPoint.update(submission.getId(), RESPONSE_COMPILE_ERROR);
                continue;
            }

            // in this step, a failure may happen!
            boolean isExecuted = execute();
            if (!isExecuted) {
                checkPoint.update(submission.getId(), RESPONSE_RUNTIME_ERROR);
                continue;
            }
            
            boolean isCorrect = verify();
            if (!isCorrect) {
                checkPoint.update(submission.getId(), RESPONSE_WRONG_ANSWER);
            } else {
                checkPoint.update(submission.getId(), RESPONSE_CORRECT);
            }
        }

    }

    /**
     *
     * @throws InterruptedException an error happened when waiting for a new
     * submission in the queue
     */
    private void readSubmission() {
        // wait if queue is empty
        while (sharedQueue.isEmpty()) {
            synchronized (sharedQueue) {
                try {
                    sharedQueue.wait();
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        // retrieves from top
        submission = sharedQueue.poll();
    }

    /**
     * Simulates a compilation of the source code file.
     *
     * @return true if compilation was successful, false otherwise
     */
    private boolean compile() {
        System.out.println(String.format("COMPILING code of team %d for problem %d", submission.getTeamId(), submission.getProblemId()));
        return generateRandomAnswer(false);
    }

    /**
     * Simulates the execution of the source code file.
     *
     * @return true if the execution was successful (i.e. code ran and finished
     * without any crash). False if code had a runtime error.
     */
    private boolean execute() {
        System.out.println(String.format("EXECUTING code of team %d for problem %d", submission.getTeamId(), submission.getProblemId()));
        return generateRandomAnswer(true);
    }

    /**
     * Simulates the verification of the outputs of the execution.
     *
     * @return true if the output is equal to the problem output (code is
     * correct). False if the code's output does not match with the expected
     * output.
     */
    private boolean verify() {
        System.out.println(String.format("VERIFYING OUTPUTS of the code of team %d for problem %d", submission.getTeamId(), submission.getProblemId()));
        return generateRandomAnswer(false);
    }

    /**
     * This method generates true/false randomly. It may throw an
     * ArrayOutOfBoundsException randomly.
     *
     * @param canFail if true, this method may produce an
     * ArrayIndexOutOfBoundsException or a true/false value. Otherwise, it will
     * just return a true/false value (with no failure injected).
     * @return true/false value randomly.
     */
    private boolean generateRandomAnswer(boolean canFail) {
        try {
            //simulates some delay in processing
            Thread.sleep(200);
            Boolean[] choices = {true, false};
            Random random = new Random(System.currentTimeMillis());
            //it may raises ArrayIndexOutOfBoundsException  because of the +1 in the argument
            if (canFail) {
                return choices[random.nextInt(choices.length + 1)];
            }
            return choices[random.nextInt(choices.length)];
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

}
