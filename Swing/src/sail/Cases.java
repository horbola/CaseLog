package sail;


import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;



public class Cases 
{
    Vector<CaseCount> caseCountsCache = null;
    
    CaseLog caseLog;
    Cases(CaseLog caseLog){this.caseLog = caseLog;}
    
    public void showAllCaseNumbersToSelect()
    {
        Vector<CaseCount> caseCounts = caseLog.client.getSyncronizedCaseNumbers();
        buildCourtList(caseCounts);
        setCaseCountsCache(caseCounts);
    }
    
    private void buildCourtList(Vector<CaseCount> caseCounts)
    {
        JList<CaseCount> courtList = new JList<>(new CourtListModel(caseCounts));
        courtList.addListSelectionListener(new CourtListSelectionListener(courtList));
        
        JScrollPane sp = new JScrollPane(courtList);
        sp.setPreferredSize(new Dimension(300, sp.getPreferredSize().getSize().height));
        
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener((e) -> listenToCancel());
        
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.add(sp);
        p.add(cancel);
        
        Container c = caseLog.c;
        
        if(c.getComponentCount() != 0)
        {
            c.removeAll();
        }
        c.add(p, BorderLayout.WEST);
        c.revalidate();
        c.repaint();
    }
    
    
    private void listenToCancel()
    {
        caseLog.createMenuPanel();
        caseLog.client.onClose();
    }
    
    
    
    private class CourtListModel extends DefaultListModel
    {
        Vector<CaseCount> caseNumberVector;
        CourtListModel(Vector<CaseCount> caseCounts)
        {
            this.caseNumberVector = caseCounts;
            for(int i = 0; i<caseCounts.size(); i++)
            {
                addElement(caseCounts.get(i));
            }
        }
        
    }

    private class CourtListSelectionListener implements ListSelectionListener 
    {
        JList<CaseCount> list;
        ListSelectionModel selM = null;
        public CourtListSelectionListener(JList<CaseCount> list) 
        {
            this.list = list;
            selM = list.getSelectionModel();
        }

        @Override
        public void valueChanged(ListSelectionEvent e) 
        {
            if(selM.getValueIsAdjusting())
            {
                CaseCount cc = list.getSelectedValue();
                cc.setSelected(true);
                buildYear_NumberList(cc);
            }
        }
    }
    
    
    
    
    private Vector<CaseCount> getCaseCountsCache(){return caseCountsCache;}
    
    private void setCaseCountsCache(Vector<CaseCount> caseCountsCache){this.caseCountsCache = caseCountsCache;}
    
    
    public void buildYear_NumberList(CaseCount caseCount)
    {
        Container c = caseLog.c;
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
        
        Vector<String> years = caseCount.getYears(caseCount.getCounter());
        
        int maxYear = caseCount.getMaxYear(caseCount.getCounter());
        Vector<String> numbers = caseCount.getNumbers(maxYear);
        
        JList<String> yearList = new JList<>(years);
        yearList.setSelectedValue(""+maxYear, true);
        JScrollPane yearScroll = new JScrollPane(yearList);
        yearScroll.setPreferredSize(new Dimension(100, yearScroll.getPreferredSize().getSize().height));
            p.add(yearScroll);
        
        JList<String> numberList = new JList<>(numbers);
        numberList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        JScrollPane numberScroll = new JScrollPane(numberList);
        yearScroll.setPreferredSize(new Dimension(200, yearScroll.getPreferredSize().getSize().height));
            p.add(numberScroll);

        JPanel pp = new JPanel();
        pp.setLayout(new BoxLayout(pp, BoxLayout.Y_AXIS));
            pp.add(p); 
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener((e) -> buildCourtList(getCaseCountsCache()));
            pp.add(cancel);
        
        if(c.getComponentCount() != 0)
        {
            c.removeAll();
        }
        c.add(pp, BorderLayout.WEST);
        c.revalidate();
        c.repaint();
        
        yearList.addListSelectionListener(new YearL(caseCount, yearList, numberList));
        numberList.addListSelectionListener(new NumberL(caseCount, numberList));
    }
    
    private class YearL implements ListSelectionListener
    {
        CaseCount caseCount;
        JList<String> yearList, numberList;
        ListSelectionModel yearListSelModel = null;
        public YearL(CaseCount caseCount, JList<String> yearList, JList<String> numberList) 
        {
            this.caseCount = caseCount;
            this.yearList = yearList;
            this.numberList = numberList;
            yearListSelModel = yearList.getSelectionModel();
        }

        @Override
        public void valueChanged(ListSelectionEvent e) 
        {
            if(yearListSelModel.getValueIsAdjusting())
            {
                String selectedYear = yearList.getSelectedValue();
                int selectedYearInt = Integer.parseInt(selectedYear);
                Vector<String> numbers = caseCount.getNumbers(selectedYearInt);
                numberList.setListData(numbers);
                numberList.repaint();
            }
        }
    }
    
    private class NumberL implements ListSelectionListener
    {
        CaseCount caseCount;
        JList<String> numberList;
        ListSelectionModel numberListSelModel = null;
        public NumberL(CaseCount caseCount, JList<String> numberList) 
        {
            this.caseCount = caseCount;
            this.numberList = numberList;
            numberListSelModel = numberList.getSelectionModel();
        }

        @Override
        public void valueChanged(ListSelectionEvent e) 
        {
            if(numberListSelModel.getValueIsAdjusting())
            {
                String selectedNumber = numberList.getSelectedValue();
                caseCount.addSelectedNumber(selectedNumber);
            }
        }
    }
    
}
