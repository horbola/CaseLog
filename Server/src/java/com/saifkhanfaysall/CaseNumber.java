package com.saifkhanfaysall;


import java.io.IOException;
import java.io.PrintWriter;

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



@WebServlet(name = "CaseNumber", urlPatterns = {"/CaseNumber"})
public class CaseNumber extends HttpServlet 
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
            
            String courtTable = "District";
            String sql = "select number from District";
            ResultSet rs = statement.executeQuery(sql);
            sendData(courtTable, rs, out);
            
            courtTable = "Joint_District";
            sql = "select number from Joint_District";
            rs = statement.executeQuery(sql);
            sendData(courtTable, rs, out);
            
            courtTable = "Additional_District";
            sql = "select number from Additional_District";
            rs = statement.executeQuery(sql);
            sendData(courtTable, rs, out);
        }
        catch(SQLException | IOException ex){ex.printStackTrace(out);}
        finally{out.close();}
    }

    protected void sendData(String courtTable, ResultSet rs, PrintWriter out) throws SQLException
    {
        out.print(courtTable +"-");
        while(rs.next())
        {
            String caseNumber = rs.getString("number");
            out.print(caseNumber +",");
        }
        out.print(")");
        out.flush();
    }
    
}
