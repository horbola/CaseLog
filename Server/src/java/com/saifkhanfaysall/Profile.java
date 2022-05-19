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
import java.sql.SQLException;



@WebServlet(name = "Profile", urlPatterns = {"/Profile"})
public class Profile extends HttpServlet 
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException 
    {
        try{Class.forName("org.sqlite.JDBC");}
        catch(ClassNotFoundException ex){ex.printStackTrace();}
        
        
        PrintWriter out = null;
        try(Connection con = DriverManager.getConnection("jdbc:sqlite://C:\\CaseLogServer\\CaseLogDB.db");)
        {
            out = response.getWriter();
            Statement statement = con.createStatement();
            
            String insertClient = request.getParameter("insertClientSql");
            statement.executeUpdate(insertClient);
            
            String insertPayment = request.getParameter("insertPaymentSql");
            statement.executeUpdate(insertPayment);
        }
        catch(SQLException ex){ex.printStackTrace(out);}
        finally{out.close();}
    }
}
