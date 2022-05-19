package test;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

public class OutTest 
{
    OutTest()
    {
        doOut();
    }
    
    
    public void doOut()
    {
        String authority = "http://localhost:8084";
        String path = "/CaseLogServer/OutTestS";
        
        URL url;
        URLConnection con = null;
        try
        {
            url = new URL(authority+path);
            con = url.openConnection();
            con.setDoOutput(true);
        }
        catch(IOException ex){ex.printStackTrace();}
        
        try(OutputStreamWriter outR = new OutputStreamWriter(new BufferedOutputStream(con.getOutputStream())))
        {
            outR.write("write something");
        }
        catch(IOException ex){ex.printStackTrace();}
        
        
        try(InputStream in = con.getInputStream())
        {
            con.setDoOutput(false);
            String monthFee = "";
            int c = 0;
            while((c = in.read()) != -1)
            {
                char ch = (char)c;
                monthFee += ch;
                System.out.println(monthFee);
            }
        }
        catch(IOException ex){ex.printStackTrace();}
    }
    
    
    public static void main(String...s)
    {
        new OutTest();
    }
}
