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



@WebServlet(name = "UpdateProfile", urlPatterns = {"/UpdateProfile"})
public class UpdateProfile extends HttpServlet 
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
            
            String sql = request.getParameter("updateProfileSql");
            statement.executeUpdate(sql);
        }
        catch(SQLException ex){ex.printStackTrace(out);}
        finally{out.close();}
    }

}
