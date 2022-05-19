package test;

import java.util.*;


public class SqlBuildingTest 
{
    public static void main(String...s)
    {
        ArrayList al = new ArrayList();
        al.add("001 2011");
        al.add("002 2011");
        al.add("003 2011");
        al.add("004 2011");
        al.add("005 2011");
        
        String sqlSelect = "?sql=select * from DisJCaseInfo where ";
        String sql = "";
        for(int i = 0; i<al.size(); i++)
        {
            String ss = (String)al.get(i);
            sql += "number = '"+ss+"' or ";
        }
        String sqls = sqlSelect+sql;
        StringTokenizer st = new StringTokenizer(sqls, " ");
        String sqlsp = "";
        while(st.hasMoreTokens())
        {
            sqlsp += st.nextToken()+"+";
        }
        sqlsp = sqlsp.substring(0, sqls.length()-4);
        
        System.out.println(sqlsp);
    }
    
}
