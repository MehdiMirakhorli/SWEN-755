/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.rit.swen755.faultmonitor;

/**
 *
 * @author Joanna
 */
public class Teste {

    public static void main(String[] args) {
        
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("Exiting app");

            }
        });
        while(true);
    }
}
