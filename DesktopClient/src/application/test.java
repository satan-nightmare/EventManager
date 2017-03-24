package application;

import java.io.IOException;
import java.net.ServerSocket;

import static java.lang.Thread.sleep;

/**
 * Created by Sumit on 24-03-2017.
 */
public class test {
    public static void main(String[] args) throws IOException {
        ServerSocket s = new ServerSocket(12345);
        System.out.println("Waiting");
        s.accept();
        System.out.println("Connected");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
