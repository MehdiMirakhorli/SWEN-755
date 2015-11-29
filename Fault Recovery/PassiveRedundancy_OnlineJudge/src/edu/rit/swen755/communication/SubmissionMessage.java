package edu.rit.swen755.communication;

import java.io.Serializable;

/**
 * Message sent from the Client application to the Server (the Judge process)
 * when a team submit a source code.
 *
 * @author Joanna
 */
public class SubmissionMessage implements Serializable {

    private Integer id;
    private String fileName;
    private Integer teamId;
    private Integer problemId;

    public SubmissionMessage(String fileName, Integer teamId, Integer problemId) {
        this.fileName = fileName;
        this.teamId = teamId;
        this.problemId = problemId;
    }

    public SubmissionMessage(Integer id) {
        this.id = id;
    }

    public SubmissionMessage(Integer id, String fileName, Integer teamId, Integer problemId) {
        this.id = id;
        this.fileName = fileName;
        this.teamId = teamId;
        this.problemId = problemId;
    }

    public String getFileName() {
        return fileName;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public Integer getProblemId() {
        return problemId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "{id=" + id + ", fileName=" + fileName + ", teamId=" + teamId + ", problemId=" + problemId + '}';
    }
}
