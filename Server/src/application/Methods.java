package application;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by anubhav on 3/23/2017.
 */
public class Methods {
    PreparedStatement pstmt;
    Connection con=null;
    String sql;
    ResultSet rs;

    Methods(){
        try {
            Class.forName("java.sql.DriverManager");
            con=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3308/Event_Scheduler","root",
                    "root");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        pstmt=null;
        sql=null;
        rs=null;
    }

    //method to check whether the username previously exists or not
    public boolean check(String username) throws SQLException{
        sql="SELECT * FROM user WHERE username=?";
        pstmt=con.prepareStatement(sql);
        pstmt.setString(1,username);
        rs=pstmt.executeQuery();
        if(!rs.next())
            return true;
        else
            return false;
    }

    //method for login of user
    public boolean login(String username,String password) throws SQLException
    {
        sql="SELECT * FROM user WHERE username=? AND password=?";
        pstmt=con.prepareStatement(sql);
        pstmt.setString(1,username);
        pstmt.setString(2,password);
        rs=(ResultSet)pstmt.executeQuery();
        if(rs.next())
            return true;
        else
            return false;
    }

    //method for signup of user
    public boolean signUp(String username,String name,String password,String gender,String email,String branch,String mobile) throws SQLException
    {
        sql="INSERT INTO user VALUES"+"(?,?,?,?,?,?,?)";
        pstmt=con.prepareStatement(sql);
        pstmt.setString(1,username);
        pstmt.setString(2,name);
        pstmt.setString(3,password);
        pstmt.setString(4,gender);
        pstmt.setString(5,email);
        pstmt.setString(6,branch);
        pstmt.setString(7,mobile);

        int temp=pstmt.executeUpdate();
        if(!(temp==0))
            return true;
        else
            return false;
    }

    //method to generate a list of available venues and their properties
    public List venueList() throws SQLException{
        List<List<String> > l=new ArrayList<List<String>>();
        sql="SELECT venuename,capacity FROM venues";
        pstmt=con.prepareStatement(sql);
        rs=pstmt.executeQuery();
        int i=0;
        while (rs.next()){
            l.add(new ArrayList<String>());
            l.get(i).add(rs.getString("venuename"));
            l.get(i).add(Integer.toString(rs.getInt("capacity")));//capacity is sent as string
            i++;
        }
        return l;
    }
    
    public List userInfo(String username) throws SQLException{
        List<String> l=new ArrayList<String>();
        sql="select * from user where username=?";
        pstmt=con.prepareStatement(sql);
        pstmt.setString(1,username);
        rs=pstmt.executeQuery();
        if(rs.next()){
        l.add(rs.getString("username"));
        l.add(rs.getString("name"));
        l.add(rs.getString("gender"));
        l.add(rs.getString("email"));
        l.add(rs.getString("branch"));
        l.add(rs.getString("mobile"));
    }
         return l;
    }

    //method to generate the schedule of a given venue
    List getSlotInfo(String venuename,Date date) throws SQLException{
        List<List<String> > l=new ArrayList<List<String> >();
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH);
        int day=cal.get(Calendar.DAY_OF_MONTH);
        int venueID=0;
        sql="SELECT venueID FROM venues WHERE venuename=?";
        pstmt=con.prepareStatement(sql);
        pstmt.setString(1,venuename);
        rs=pstmt.executeQuery();
        while (rs.next())
            venueID=rs.getInt("venueID");
        sql="select * from V? WHERE day=? && month=? && year=? ORDER BY start_time";
        pstmt=con.prepareStatement(sql);
        pstmt.setInt(1,venueID);
        pstmt.setInt(2,day);
        pstmt.setInt(3,month);
        pstmt.setInt(4,year);
        rs=pstmt.executeQuery();
        int i=0;
        while (rs.next()){
            l.add(new ArrayList<String>());
            l.get(i).add(rs.getString("start_time"));
            l.get(i).add(rs.getString("end_time"));
            l.get(i).add(rs.getString("eventname"));
            //not showing hostid
            i++;
        }
        return l;
    }

    //method to get created events of a user
 /*   List getCreatedEvents(String username) throws SQLException{
        List<List<String>> l=new ArrayList<List<String>>();
        sql="SELECT eventID,eventname,venueID FROM events WHERE hostname=?";
        pstmt=con.prepareStatement(sql);
        pstmt.setString(1,username);
        rs=pstmt.executeQuery();
        int i=0;
        while (rs.next()){
            l.add(new ArrayList<String>());
            sql="SELECT * FROM V? WHERE hostname=?";
            pstmt=con.prepareStatement(sql);
            pstmt.setInt(1,rs.getInt("venueID"));
            pstmt.setString(2,rs.getString("hostname"));
            ResultSet rs2=pstmt.executeQuery();
            while(rs2.next()){
            l.get(i).add(rs.getString("eventname"));
            l.get(i).add(rs.getString("eventname"));
            l.get(i).add(rs.getString("eventname"));

        }
    }

}*/
}

