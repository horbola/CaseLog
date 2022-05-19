package com.saifkhanfaysall;


import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@WebServlet(name = "Payment", urlPatterns = {"/Payment"})
public class Payment extends HttpServlet {

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
            
            String id = request.getParameter("id");
            String paymentSql = request.getParameter("paymentSql");
            ResultSet rs = statement.executeQuery(paymentSql);
            sendData(id, rs, out);
        }
        catch(IOException | SQLException ex){ex.printStackTrace(out);}
        finally{out.close();}
    }
    
    protected void sendData(String id, ResultSet rs, PrintWriter out) throws SQLException
    {
        while(rs.next())
        {
            String month = rs.getString("month");
            String fee = rs.getString(id);
            out.print(month +"," +fee +"/");
        }
        out.flush();
    }
    
}
