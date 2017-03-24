package application;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by anubhav on 3/23/2017.
 */
public class iostream implements Runnable {
    Socket pipe;
    Packet p = null;
    //String user,pass;
    Methods method = new Methods();

    iostream(Socket pipe) {
        this.pipe = pipe;
        p=new Packet();
    }

    @Override
    public void run() {
        ObjectInputStream serverInputStream = null;
        try {
            serverInputStream = new ObjectInputStream(pipe.getInputStream());
            //System.out.println("hehe");
        } catch (IOException ex) {
            Logger.getLogger(iostream.class.getName()).log(Level.SEVERE, null, ex);
        }
        ObjectOutputStream serverOutputStream = null;
        try {
            serverOutputStream = new ObjectOutputStream(pipe.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(iostream.class.getName()).log(Level.SEVERE, null, ex);
        }
        //input and output  stream created
        int i = 1;
        while (true) {
            try {
                //System.out.println("object received.");
                if(serverInputStream==null)
                    System.out.println("fuck");
                p = (Packet) serverInputStream.readObject();
                System.out.println("Operation of the object received is:" + p.operation + " " + i);
                i++;
                System.out.println("Waiting to receive packet.");
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(iostream.class.getName()).log(Level.SEVERE, null, ex);
            }
            Operation r = new Operation(serverInputStream, serverOutputStream, p);
            Thread t = new Thread(r);
            t.start();
            System.out.println("A new thread for receiving packets has been started..");
            if (p.operation.equals("logout"))
                break;
        }
    }
}