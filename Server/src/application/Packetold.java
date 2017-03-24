package application;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.Date;
import java.util.List;

public class Packetold implements Serializable{
    public String operation=null;
    public String s1=null;
    public String s2=null;
    public String s3=null;
    public String s4=null;
    public String s5=null;
    public String s6=null;
    public String s7=null;
    public ResultSet rs=null;
    public int i;
    public boolean response;
    public Date date;
    public List<String> list;
    public List<List<String> > list2;
}
