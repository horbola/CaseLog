package sail;


import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;



public class paymentTableModel extends AbstractTableModel 
{
    ArrayList<PaymentRecord> paymentArrayList;
    public paymentTableModel(ArrayList<PaymentRecord> paymentArrayList) 
    {
        this.paymentArrayList = paymentArrayList;
    }
    
    String colName[] = {"Month", "Paid", "Due"};
    @Override
    public String getColumnName(int col)
    {
        return colName[col];
    }
    
    @Override
    public int getColumnCount()
    {
        return colName.length;
    }
    
    @Override
    public int getRowCount()
    {
        return paymentArrayList.size();
    }
    
    @Override
    public Object getValueAt(int row, int col)
    {
        if(row < 0 || row >= paymentArrayList.size())
            return "";
        
        PaymentRecord pRecord = paymentArrayList.get(row);
        switch(col)
        {
            case 0: return pRecord.month;
            case 1: return pRecord.paid;
            case 2: return pRecord.due;
        }
        return "";
    }
    
    @Override
    public boolean isCellEditable(int row, int col)
    {
        return false;
    }
}
