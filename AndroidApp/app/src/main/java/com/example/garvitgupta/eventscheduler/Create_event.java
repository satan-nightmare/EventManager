package com.example.garvitgupta.eventscheduler;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import application.Packet;

public class Create_event extends AppCompatActivity implements AdapterView.OnItemClickListener{
    Button b1,b2,b3;
    Packet p,p2;
    ListView l;
    String notifications[];
    List<String> data,data2;
    TextView txt_venue,txt_date;
    int capacity;
    Date dt1;
    int leng,tt;
    String selectedvenue;
    ArrayAdapter<String> adapter1;
    boolean ac;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        notifications=new String[10];
        for(int i=0;i<10;i++)
            notifications[i]="";
        //notifications[0]="garvit";
        l = (ListView) findViewById(R.id.lv);
        adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,notifications);
        l.setAdapter(adapter1);
        l.setOnItemClickListener(this);

        b1 = (Button) findViewById(R.id.btn_selectvenue);
        b2 = (Button) findViewById(R.id.btn_selectdate);
        b3 = (Button) findViewById(R.id.btn_slotinfo);
        txt_venue=(TextView) findViewById(R.id.txt_venue);
        txt_date=(TextView) findViewById(R.id.txt_date);
        p=null;
        p2=null;
        capacity=0;
        ac=false;
        data2=null;
        data=null;
        selectedvenue="";
        tt=0;
        leng=100;

    }
    public void click(View v)
    {
        if(v.getId()== R.id.btn_selectvenue){
            p=new Packet();
            p.operation="venuelist";
            Thread t=new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        IOstream.oos.writeObject(p);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        p=(Packet)IOstream.ois.readObject();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    data=p.list;
                }
            });
            t.start();
            while(true){
                if(data!=null)
                    break;
            }
            int len=data.size();
            final String l[]=new String[len];
            for (int i=0;i<len;i++){
                l[i]=data.get(i);
            }
            AlertDialog.Builder builder=new AlertDialog.Builder(Create_event.this);
            builder.setTitle("Choose venue");
            builder.setItems(l, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    selectedvenue=l[which];
                    txt_venue.setText(selectedvenue);
                    txt_venue.setTextColor(Color.BLUE);
                    p2=new Packet();
                    p2.operation="venuedetail";
                    p2.s1=l[which];
                    //Log.d("hehe","venue="+l[which]);
                    Thread t2=new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                IOstream.oos.writeObject(p2);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            try {
                                p2=(Packet)IOstream.ois.readObject();
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Log.d("hehe","capacity="+p2.i);
                            capacity=p2.i;
                            ac=p2.response;
                        }
                    });
                    t2.start();
                    while (true){
                        if(capacity>0)
                            break;
                    }
                    String air="no";
                    if(ac==true)
                        air="yes";
                    else
                        air="no";
//                    String specs[]=new String[10];
//                    specs[0]="Capacity : "+capacity;
//                    specs[1]="AC : "+air;
                    AlertDialog.Builder builder2=new AlertDialog.Builder(Create_event.this);
                    builder2.setTitle("capacity : "+capacity+"   AC : "+air+"\n\n");


                    /*builder2.setItems(specs,new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Log.d("hehe","hehe");
                        }
                    });*/
                    AlertDialog alertDialog2=builder2.create();
                    alertDialog2.show();
                    //Message.message(getApplicationContext(),l[which]);
                }
            });
            AlertDialog alertDialog=builder.create();
            alertDialog.show();
//            while (true){
//                if(!(selectedvenue.isEmpty()))
//                    break;
//            }
//            txt_venue.setText(selectedvenue);
//            txt_venue.setTextColor(Color.GREEN);
        }
        if(v.getId()== R.id.btn_selectdate)
        {
            showDialog(1);
        }
        if(v.getId()== R.id.btn_slotinfo) {
            p=new Packet();
            p.operation="venueslotinfo";
            p.s1=txt_venue.getText().toString();
            p.date=dt1;

            Thread t3=new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        IOstream.oos.writeObject(p);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        p=(Packet)IOstream.ois.readObject();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    List<List<String> > ltemp=null;
                    ltemp=p.list2;
                    if(ltemp==null){
                        notifications[0]="No events on this day";
                        tt=100;
                        leng=100;
                    }
                    else {

                        leng = ltemp.size();
                        Log.d("hehe", "leng=" + leng);
                        for (tt = 0; tt < leng; tt++) {
                            notifications[tt] = ltemp.get(tt).get(0) + ":00 to " + ltemp.get(tt).get(1) + ":00   Event -> " + ltemp.get(tt).get(2);
                            Log.d("hehe", notifications[tt]);
                        }
                    }
                    //tt=0;

                }
            });
            t3.start();
            while(true){
                if(tt>=leng) {
                    tt=0;
                    leng=100;
                    break;
                }
            }
            Log.d("hehe","here");
            adapter1.notifyDataSetChanged();
        }
    }


    @Override
    protected Dialog onCreateDialog(int id) {

        Date date=new Date();
        int dat=date.getDate();
        int mon=date.getMonth();
        int year=date.getYear();
        if(id==1)
            return new DatePickerDialog(this,dpl,year+1900,mon,dat);
        else
            return null;
    }

    private DatePickerDialog.OnDateSetListener dpl=new DatePickerDialog.OnDateSetListener(){

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String input_date=""+dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
            SimpleDateFormat format1=new SimpleDateFormat("dd/MM/yyyy");
            dt1= null;
            try {
                dt1 = format1.parse(input_date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            DateFormat format2=new SimpleDateFormat("EEEE");
            String finalDay=format2.format(dt1);
            txt_date.setText(input_date);
            txt_date.setTextColor(Color.BLUE);
            //Message.message(FeedTimetable.this,finalDay);
            //Message.message(FeedTimetable.this,""+dayOfMonth+"/"+year);
//            Intent intent=new Intent(FeedTimetable.this,GoToDate.class);
//            Bundle bundle=new Bundle();
//            bundle.putInt("date",dayOfMonth);
//            bundle.putString("month",new DateFormatSymbols().getMonths()[monthOfYear]);
//            bundle.putString("day",finalDay);
//            bundle.putInt("year",year);
//            intent.putExtras(bundle);
//            startActivity(intent);
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView v = (TextView) view;
        Toast.makeText(this,v.getText()+""+position,Toast.LENGTH_LONG).show();
    }
}
