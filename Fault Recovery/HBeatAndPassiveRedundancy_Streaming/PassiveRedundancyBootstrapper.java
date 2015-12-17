import java.net.*;
import java.io.*;

public class PassiveRedundancyBootstrapper {

    public static Thread run(final String command) {
        return new Thread(){
            public void run() {
               int s = -1;
                try {
                    Process p = Runtime.getRuntime().exec(command);
                    BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
                    // read the output from the command
                    while ((s = stdInput.read()) != -1) {
                        System.out.print((char)s);
                    }
                    // read any errors from the attempted command
                    while ((s = stdError.read()) != -1) {
                        System.out.print((char)s);
                    }
                    // System.exit(0);
                }
                catch (IOException e) {
                    e.printStackTrace();
                    // System.exit(-1);
                }
            }
        };
    }

    public static void main(String args[]) {
        run("java StreamServer active").start();
        run("java StreamServer passive").start();
    }
}
