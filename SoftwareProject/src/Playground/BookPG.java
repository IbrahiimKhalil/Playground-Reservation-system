package Playground;


public class BookPG 
{
    String id;
    String playgroundid;
    String accountid;
    String date;
    String total;
    String description;
    String starthour;
    String duration;
    String paymentid;
    
    public  BookPG (String id,String playgroundid,String accountid,String date,String total,String description,String starthour,String duration,String paymentid)
    {
        this.id=id;
        this.playgroundid=playgroundid;
        this.accountid=accountid;
        this.date=date;
        this.total=total;
        this.description=description;
        this.starthour=starthour;
        this.duration=duration;
        this.paymentid=paymentid;
        System.out.println("Booked successfully. ");
    }
	public BookPG() {
		// TODO Auto-generated constructor stub
	}
    
    
}
