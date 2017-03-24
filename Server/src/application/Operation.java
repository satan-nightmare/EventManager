package application;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by anubhav on 3/23/2017.
 */
public class Operation implements Runnable{
    ObjectOutputStream serverOutputStream=null;
    ObjectInputStream serverInputStream=null;
    Packet p=null;
    Methods method=new Methods();
    Operation(ObjectInputStream serverInputStream,ObjectOutputStream serverOutputStream,Packet p)
    {
        this.serverOutputStream=serverOutputStream;
        this.serverInputStream=serverInputStream;
        this.p=p;
    }

    @Override
    public void run() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        // System.out.println("object received.");
        String operation=p.operation;
        System.out.println(operation);
        if(operation.equals("signup")) {
            try {
                if (method.check(p.s1)) {
                    p.response = method.signUp(p.s1, p.s2, p.s3, p.s4, p.s5, p.s6, p.s7);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(operation.equals("check")) {
            try {
                    p.response = method.check(p.s1);
                } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(operation.equals("login"))
        {
            System.out.println(p.s1+" "+p.s2);
            try {
                p.response=method.login(p.s1,p.s2);
            } catch (SQLException ex) {
                Logger.getLogger(iostream.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(operation.equals("userinfo"))
        {
            List<String> l=new ArrayList<String>();
            try {
               l= method.userInfo(p.s1);
                p.s1=l.get(0);
                p.s2=l.get(1);
                p.s4=l.get(2);
                p.s5=l.get(3);
                p.s6=l.get(4);
                p.s7=l.get(5);
            } catch (SQLException ex) {
                Logger.getLogger(iostream.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        /*if(operation.equals("venueList"))
        {
            try {
                p.list=method.venueList();
            } catch (SQLException ex) {
                Logger.getLogger(iostream.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        else if(operation.equals("getSlotInfo"))
        {
            try {
                p.list2=method.getSlotInfo(p.s1,p.date);
            } catch (SQLException ex) {
                Logger.getLogger(iostream.class.getName()).log(Level.SEVERE, null, ex);
            }


        }
        else if(operation.equals("bookEvent"))
        {
            try {
                p.response=method.bookEvent(p.i,p.s1,p.s2,p.s3,p.date);
            } catch (SQLException ex) {
                Logger.getLogger(iostream.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if(operation.equals("check"))
        {
            try {
                p.response=method.check(p.s1);
            } catch (SQLException ex) {
                Logger.getLogger(iostream.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if(operation.equals("userInfo"))
        {
            try {
                p=method.userInfo(p.s1);
            } catch (SQLException ex) {
                Logger.getLogger(Operation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if(operation.equals("userEvents"))
        {
            try {
                p.list2=method.userEvents(p.s1);
            } catch (SQLException ex) {
                Logger.getLogger(Operation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }*/
        try {
            serverOutputStream.writeObject(p);
        } catch (IOException ex) {
            Logger.getLogger(iostream.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
