package com.example.garvitgupta.eventscheduler;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Garvit Gupta on 3/24/2017.
 */
public class IOstream {
    static ObjectOutputStream oos;
    static ObjectInputStream ois;
    static String temp;
    IOstream(ObjectInputStream ois,ObjectOutputStream oos,String temp){
        this.oos=oos;
        this.ois=ois;
        this.temp=temp;
    }
}
