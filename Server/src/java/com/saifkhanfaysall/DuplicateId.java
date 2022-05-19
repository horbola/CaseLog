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
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;



@WebServlet(name = "DuplicateId", urlPatterns = {"/DuplicateId"})
public class DuplicateId extends HttpServlet 
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
            
            String id = request.getParameter("isDuplicateId");
            ResultSet ids = statement.executeQuery("select id from Client");
            while(ids.next())
            {
                String existingId = ids.getString("id");
                if(bothEquals(id, existingId)) {out.print('T'); out.flush(); return;}
            }
            out.print('F');
            out.flush();
        }
        catch(SQLException ex){ex.printStackTrace(out);}
        finally{out.close();}
    }
    
    
    public boolean bothEquals(String s, String ss) 
    {
        char[] cs = s.toCharArray();
        char[] css = ss.toCharArray();

        if (cs.length != css.length){return false;}

        for (int i = 0; i < cs.length; i++) {if (cs[i] != css[i]){return false;}}
        
        return true;
    }
}
