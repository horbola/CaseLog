package sail;



public class PaymentRecord 
{
    String month = "";
    //these two fields can not be zero simultaneously. if so there must be some issues.
    int paid = 0;
    int due = 0;
    
    public PaymentRecord(String month, String fee) 
    {
        this.month = month;
        this.paid = getIntFromFeeString(fee);
        this.due = 500- paid;
    }

    private int getIntFromFeeString(String fee) 
    {
        int paid = 0;
        try{paid = Integer.parseInt(fee);}
        catch(NumberFormatException ex)
        {
            System.out.println(""); 
            //here we've to show an optionpane also.
            //and some other necessary task.
        }
        return paid;
    }
        
}