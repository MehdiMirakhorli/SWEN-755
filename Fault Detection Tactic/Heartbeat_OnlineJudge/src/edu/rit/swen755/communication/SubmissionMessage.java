package edu.rit.swen755.communication;

import java.io.Serializable;

/**
 * Message sent from the Client application to the Server (the Judge process)
 * when a team submit a source code.
 *
 * @author Joanna
 */
public class SubmissionMessage implements Serializable {

    private String fileName;
    private int teamId;
    private int problemId;

    public SubmissionMessage(String fileName, int teamId, int problemId) {
        this.fileName = fileName;
        this.teamId = teamId;
        this.problemId = problemId;
    }

    public String getFileName() {
        return fileName;
    }

    public int getTeamId() {
        return teamId;
    }

    public int getProblemId() {
        return problemId;
    }

}
