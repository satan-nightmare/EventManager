package com.example.garvitgupta.eventscheduler;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    EditText ip_address;
    EditText port;
    String ip;
    int port_num;
    Socket s;
    ObjectOutputStream oos;
    ObjectInputStream ois;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ip_address=(EditText)findViewById(R.id.ip_address);
        port=(EditText)findViewById(R.id.port);
        s=null;
    }

    public void establishConnection(View v){
        ip=ip_address.getText().toString();
        port_num=Integer.parseInt(port.getText().toString());
        backgroundTask bgTask=new backgroundTask();
        bgTask.execute();
    }

    public class backgroundTask extends AsyncTask<Void,Integer,Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                s=new Socket(ip,port_num);
            } catch (IOException e) {
                publishProgress(1);
            }
            try {
                oos=new ObjectOutputStream(s.getOutputStream());
                ois=new ObjectInputStream(s.getInputStream());
                IOstream temp=new IOstream(ois,oos,"garvit");
            } catch (IOException e) {
                //return false;
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

            super.onProgressUpdate(values);
            if(values[0]==1)
                Toast.makeText(MainActivity.this,"Unable to connect",Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(s!=null){
                Intent i=new Intent(MainActivity.this,Login.class);
                startActivity(i);
            }
        }
    }
}
