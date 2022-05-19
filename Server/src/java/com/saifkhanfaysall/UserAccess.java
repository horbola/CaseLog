package com.saifkhanfaysall;


import java.io.IOException;
import java.io.PrintWriter;

import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;



@WebServlet(name = "UserAccess", urlPatterns = {"/UserAccess"})
public class UserAccess extends HttpServlet 
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException 
    {
        try{Class.forName("org.sqlite.JDBC");}
        catch(ClassNotFoundException ex){ex.printStackTrace();}
        
        PrintWriter out = null;
        try(Connection con = DriverManager.getConnection("jdbc:sqlite://C:\\CaseLogServer\\CaseLogDB.db"))
        {
            out = response.getWriter();
            Statement statement = con.createStatement();
            
            String id = request.getParameter("id");
            String selectId = "select id from Client";
            String selectAll = "select * from Client where id = '"+id+"'";

            ResultSet ids = statement.executeQuery(selectId);
            
            while(ids.next())
            {
                if(bothEquals(id, ids.getString("id")))
                {
                    ResultSet profile = statement.executeQuery(selectAll);
                    out.print('T');
                    sendProfileData(profile, out);
                    out.flush();
                    break;
                }
                else continue;
            }
            out.print("");
            out.flush();
        }
        catch(SQLException | IOException ex){ex.printStackTrace(out);}
        finally{out.close();}
    }

    protected void sendProfileData(ResultSet profile, PrintWriter out) throws SQLException
    {
        out.print(profile.getString("id"));
        out.print(",");
        out.print(profile.getString("pass"));
        out.print(",");
        out.print(profile.getString("access"));
        out.print(",");
        out.print(profile.getString("type"));
        out.print(",");
        out.print(profile.getString("name"));
        out.print(",");
        out.print(profile.getString("father"));
        out.print(",");
        out.print(profile.getString("home"));
        out.print(",");
        out.print(profile.getString("village"));
        out.print(",");
        out.print(profile.getString("subDis"));
        out.print(",");
        out.print(profile.getString("district"));
        out.print(",");
        out.print(profile.getString("phone1"));
        out.print(",");
        out.print(profile.getString("phone2"));
        out.print(",");
        out.print(profile.getString("phone3"));
        out.print(",");
        out.print(profile.getString("phone4"));
        out.print(",");
        out.print(profile.getString("phone5"));
        out.print(",");
        
        String s = profile.getString("selectedCases");
        if(bothEquals(s, "") || bothEquals(s, "null") || bothEquals(s, "Empty"))
            s = "Empty";
        out.print(s);
    }
    
    public boolean bothEquals(String s, String ss)
    {
        char[] cs = s.toCharArray();
        char[] css = ss.toCharArray();
        
        if(cs.length != css.length)
            return false;
        
        for(int i = 0; i<cs.length; i++)
        {
            if(cs[i] != css[i])
                return false;
        }
        return true;
    }
    
    
}
