package com.saifkhanfaysall;


import java.io.IOException;
import java.io.PrintWriter;

import java.util.Enumeration;
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



@WebServlet(name = "CaseInfo", urlPatterns = {"/CaseInfo"})
public class CaseInfo extends HttpServlet 
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
            
            Enumeration tableNames = request.getParameterNames();
            while(tableNames.hasMoreElements())
            {
                String tableName = (String)tableNames.nextElement();
                String sql = request.getParameter(tableName);
                ResultSet rs = statement.executeQuery(sql);
                String courtName = buildCourtName(tableName);
                sendData(courtName, rs, out);
            }
        }
        catch(SQLException | IOException ex){ex.printStackTrace(out);}
        finally
        {
            if(out != null) out.close();
        }
    }
    
    protected void sendData(String courtName, ResultSet rs, PrintWriter out) throws SQLException
    {
        out.print(courtName +"?");
        while(rs.next())
        {
            for(int i = 1; i <= 50; i++)
            {
                out.print(1);
                out.print(rs.getString(i));
                out.print(".");
            }
            out.print("'");
        }
        out.print(")");
        out.flush();
    }
    
    private String buildCourtName(String tableName)
    {
        String tableNameLocal = tableName;
        StringTokenizer tableNameTokenizer = new StringTokenizer(tableNameLocal, "_");
        String courtName = "";
        while(tableNameTokenizer.hasMoreElements())
        {
            courtName += tableNameTokenizer.nextToken() +" ";
        }
        courtName += "Judge Court";
        return courtName;
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
