package sail;


import java.util.ArrayList;
import java.util.Vector;
import java.util.HashMap;
import java.util.StringTokenizer;



public class SqlBuilder 
{
    
    
    public static String buildIdSql(String id)
    {
        String query = "?id="+id;
        return query;
    }
    
    
    public static String buildDuplicateIdSql(String isDuplicateId)
    {
        String query = "?isDuplicateId="+isDuplicateId;
        return query;
    }
    
    
    public static String buildProfileSql(HashMap<String, String> profileCache) 
    {
        String insertClientSql = "insertClientSql=insert+into+Client(id,pass,access,type,name,father,home,village,subDis,district,phone1,phone2,phone3,phone4,phone5,selectedCases)"
                +"+values("
                + "'"+profileCache.get("id")+"',"
                + "'"+profileCache.get("pass")+"',"
                + "'"+profileCache.get("access")+"',"
                + "'"+profileCache.get("type")+"',"
                + "'"+profileCache.get("name")+"',"
                + "'"+profileCache.get("father")+"',"
                + "'"+profileCache.get("home")+"',"
                + "'"+profileCache.get("village")+"',"
                + "'"+profileCache.get("subDis")+"',"
                + "'"+profileCache.get("district")+"',"
                + "'"+profileCache.get("phone1")+"',"
                + "'"+profileCache.get("phone2")+"',"
                + "'"+profileCache.get("phone3")+"',"
                + "'"+profileCache.get("phone4")+"',"
                + "'"+profileCache.get("phone5")+"',"
                + "'"+profileCache.get("selectedCases")+"')";
        
        String insertPaymentSql = "insertPaymentSql=alter+table+Payment+add+"+profileCache.get("id")+"+INTEGER";
        
        String query = "?"+insertClientSql+"&"+insertPaymentSql;
        query = spaceRemover(query);
        return query;
    }
    
    public static String buildUpdateProfileSql(HashMap<String,String> profileCache) 
    {
        String updateProfileSql = "?updateProfileSql=update+Client+set+"
                + "pass='"+profileCache.get("pass")+"',"
                + "access='"+profileCache.get("access")+"',"
                + "type='"+profileCache.get("type")+"',"
                + "name='"+profileCache.get("name")+"',"
                + "father='"+profileCache.get("father")+"',"
                + "home='"+profileCache.get("home")+"',"
                + "village='"+profileCache.get("village")+"',"
                + "subDis='"+profileCache.get("subDis")+"',"
                + "district='"+profileCache.get("district")+"',"
                + "phone1='"+profileCache.get("phone1")+"',"
                + "phone2='"+profileCache.get("phone2")+"',"
                + "phone3='"+profileCache.get("phone3")+"',"
                + "phone4='"+profileCache.get("phone4")+"',"
                + "phone5='"+profileCache.get("phone5")+"',"
                + "selectedCases='"+profileCache.get("selectedCases")+"'"
                + "+where+id='"+profileCache.get("id")+"'";
        updateProfileSql = spaceRemover(updateProfileSql);
        return updateProfileSql;
    }
    
    
    
    private static String spaceRemover(String query)
    {
        String queryWithPlus = "";
        StringTokenizer spaceRemover = new StringTokenizer(query, " ");
        while(spaceRemover.hasMoreTokens()){queryWithPlus += spaceRemover.nextToken()+"+";}
        return queryWithPlus;
    }
    
    

    public static String buildPaymentSql(String id)
    {
        String paymentSql = "?id="+id+"&paymentSql=select+month,"+id+"+from+Payment";
        return paymentSql;
    }

    public static String buildCaseInfoSql(Vector<CaseCount> caseCounts)
    {
        String query = "?";
        for(CaseCount cc : caseCounts)
        {
            if(cc.isSelected())
            {
                String courtTable = cc.getTableName();
                ArrayList<String> selectedNumbers = cc.getSelectedNumbers();
                query += buildCaseInfoSql(courtTable, selectedNumbers) +"&";
            }
        }
        query = query.substring(0, query.length()-1);
        return query;
    }

    public static String buildCaseInfoSql(String tableName, ArrayList<String> selectedNumbers) 
    {
        String query = tableName+"=";
        String select = "select+*+from+"+tableName+"+";
        String where = "where+";
        String number = "";
        for(String sNum : selectedNumbers )
        {
            number += "number='"+sNum+"'+or+";
        }
        number = number.substring(0,number.length()-4);
        query += select+where+number;
        return query;
    }

    

}
