package Playground;

import java.util.ArrayList;
import java.util.List;

public class PG 
{
    
    String id;
    String name;
    String description;
    String address;
    int pricehour;
    int bookingNo;
    Boolean approved;
    String [][]Avilablehours;
    String state;
    String ownerid;
    static int ComplaintsN=0;
    static List<String> Complaints = new ArrayList<String>();
    
    
    public void updateavaliability(int fromhour,int tohour)
    {
        for(int i=fromhour;i<tohour;i++)
        {
               
        }
    }
    public  PG (String id,String name,String description,String address,int pricehour,int bookingNo,Boolean approved,String [][]Avilablehours,String state,String ownerid)
    {
        this.id=id;
        this.name=name;
        this.description=description;
        this.address=address;
        this.pricehour=pricehour;
        this.bookingNo=bookingNo;
        this.approved=approved;
        this.Avilablehours=Avilablehours;
        this.state=state;
        this.ownerid=ownerid;
        System.out.println("Play ground added successfully.");
    }
    
}
