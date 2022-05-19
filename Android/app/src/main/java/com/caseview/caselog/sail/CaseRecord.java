package com.caseview.caselog.sail;


import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;



public class CaseRecord implements Serializable
{
    static SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
    
    String courtName = "Empty";
    HashMap<Integer, String> fields = new HashMap<>();
    
    CaseRecord(String courtName){this.courtName = courtName;}
    
    public Date getField30AsDate()
    {
        String field30 = (String)fields.get(30);
        Date filing = null;
        try {filing = sdf.parse(field30);}
        catch (ParseException ex){ex.printStackTrace();}
        return filing;
    }
    
    public Date getFieldsAsDate(int dateField)
    {
        String dateString = fields.get(dateField);
        if(bothEquals(dateString, "null")) return null;
        Date date = null;
        try {date = sdf.parse(dateString);}
        catch (ParseException ex){ex.printStackTrace(); return null;}
        return date;
        
    }
    
    public String getCaseList()
    {
        return getCourtName() +"  " +getCaseNumber() +"  " +getRunning();
    }
    
    String getCourtName(){return courtName;}
    String getCaseNumber(){String caseNumber = fields.get(1); return caseNumber;}
    String getRunning(){String running = fields.get(13); return running;}
    String getClosed(){String closed = fields.get(14); return closed;}
    String getSoloved(){String solved = fields.get(15); return solved;}
    
    
    public boolean bothEquals(String s, String ss) 
    {
        char[] cs = s.toCharArray();
        char[] css = ss.toCharArray();

        if (cs.length != css.length) {return false;}

        for (int i = 0; i < cs.length; i++) 
        {
            if (cs[i] != css[i]) {return false;}
        }
        return true;
    }

    public String getTempCourtName() {
        return "Court Name";
    }

    public String getTempCasetNumber() {
        return "Case Number";
    }


    @Override
    public String toString(){
        return courtName;
    }
}


