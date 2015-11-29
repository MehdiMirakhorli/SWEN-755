package edu.rit.swen755.judge;

/**
 * This is the entry point for the Judge Process.
 *
 * @author Joanna Cecilia
 */
public class JudgeMain {

    public static void main(String[] args) throws Exception {
        Judge judge = new Judge();
        judge.init(args[0]);
    }
}
