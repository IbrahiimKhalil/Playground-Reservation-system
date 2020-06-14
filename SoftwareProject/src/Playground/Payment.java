
package Playground;

public class Payment 
{
    public String id;
    String playgroundid;
    String accountid;
    String date;
    String total;
    String description;
    
    public Payment(String id,String playgroundid,String accountid,String date,String total,String description)
    {
        this.id=id;
        this.playgroundid=playgroundid;
        this.accountid=accountid;
        this.date=date;
        this.total=total;
        this.description=description;
    }
}
