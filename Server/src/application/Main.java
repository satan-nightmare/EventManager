/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author anubhav
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ServerSocket socketConnection = null;
        try {
            // TODO add your handling code here:
            socketConnection = new ServerSocket(12345);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        //jButton1.setForeground(Color.blue);
        Socket pipe = null;
        while (true) {
            try {
                System.out.println("Server is waiting");
                // JOptionPane.showMessageDialog(this,"Server started successfully");
                pipe = socketConnection.accept();
                iostream r = new iostream(pipe);
                Thread t = new Thread(r);
                t.start();
                System.out.println("Server has received connection");
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
