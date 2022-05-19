package sail;


import java.io.Serializable;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.Vector;
import java.util.Iterator;



public class CaseCount implements Serializable
{
    boolean selected = false;
    String courtTable = null;
    int count[] = new int[0];
    Vector<String> years = null;
    int minYear = 2010;
    ArrayList<String> selectedNumbers = new ArrayList<>();
    
    
    
    CaseCount(String courtTable, int count[])
    {
        this.courtTable = courtTable;
        this.count = count;
    }
    
    
    String getTableName(){return courtTable;}
    @Override
    public String toString()
    {
        StringTokenizer courtTableTokenizer = new StringTokenizer(courtTable, "_");
        String courtName = "";
        while(courtTableTokenizer.hasMoreElements())
        {
            courtName += courtTableTokenizer.nextToken() +" ";
        }
        courtName += "Judge Court";
        return courtName;
    }


    
    
    public boolean isSelected(){return selected;}
    public void setSelected(boolean selected){this.selected = selected;}

    
    public int[] getCounter(){return count;}
    
    
    public Vector<String> getYears(int[] count) 
    {
        if(years == null)
            createYears(count);
        return years;
    }
    
    private void createYears(int[] count)
    {
        years = new Vector<>();
        for(int i = 0; i<count.length; i++)
        {
            if(count[i] != 0)
            {
                String year = "" +(i+2010);
                years.addElement(year);
                
                String print = courtTable +"  " +(i+2010)+"  " +count[i];
            }
        }
    }
    
    
    public int getMinYear(){return minYear;}
    int getMaxYear(int[] count) 
    {
        int max = 2010;
        Vector<String> years = getYears(count);
        for(String y : years)
        {
            int yInt = Integer.parseInt(y);
            max = Math.max(max, yInt);
        }
        return max;
    }
    
    
    public Vector<String> getNumbers(int year)
    {
        Vector<String> numbers = new Vector<>();
        int maxNum = count[year-2010];
        for(int i = 1; i <= maxNum; i++)
        {
            String number = i +"/" +year;
            numbers.addElement(number);
        }
        return numbers;
    }
    
    
    
    ArrayList<String> getSelectedNumbers(){return selectedNumbers;}
    
    void setSelectedNumbers(ArrayList<String> selectedNumbers){this.selectedNumbers = selectedNumbers;}
    
    void addSelectedNumber(String selectedNumber) {selectedNumbers.add(selectedNumber);}
    
    
    
    public String buildSelectedCasesString()
    {
        String numbers = getTableName()+"?";
        Iterator ite = getSelectedNumbers().iterator();
        while(ite.hasNext())
        {
            numbers += ite.next();
            numbers += ".";
        }
        return numbers;
    }
    
}
