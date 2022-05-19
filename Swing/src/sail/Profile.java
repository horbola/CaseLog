package sail;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

    

public class Profile
{
    CaseLog caseLog;
    
    Profile(CaseLog caseLog){this.caseLog = caseLog;}
    
    public void showProfile()
    {
        JLabel name, father, home, village, subDis, district, phone1, phone2;
        JButton cancel;
        
        name = new JLabel();
        name.setPreferredSize(new Dimension(300,name.getSize().height));
        father = new JLabel();
        home = new JLabel();
        village = new JLabel();
        subDis = new JLabel();
        district = new JLabel();
        phone1 = new JLabel();
        phone2 = new JLabel();
        cancel = new JButton();
        
        JPanel p = new JPanel(new GridLayout(9,1,0,5));
        p.setBackground(Color.WHITE);
        p.add(name);
        p.add(father);
        p.add(home);
        p.add(village);
        p.add(subDis);
        p.add(district);
        p.add(phone1);
        p.add(phone2);
        p.add(cancel);
        cancel.addActionListener((e) -> caseLog.createMenuPanel());
        
        HashMap<String,String> profileCache = caseLog.client.getProfileCache();
        if(profileCache != null && !profileCache.isEmpty())
        {
            name.setText(profileCache.get("name"));
            father.setText(profileCache.get("father"));
            home.setText(profileCache.get("home"));
            village.setText(profileCache.get("village"));
            subDis.setText(profileCache.get("subDis"));
            district.setText(profileCache.get("district"));
            phone1.setText(profileCache.get("phone1"));
            phone2.setText(profileCache.get("phone2"));
        }
        else
        {
            name.setText("Enter Your Name");
            father.setText("Enter Your Father's Name");
            home.setText("Enter Your Home");
            village.setText("Enter Your Village");
            subDis.setText("Enter Your Subdistrict");
            district.setText("Enter Your District");
            phone1.setText("Enter Your Phone Number 1");
            phone2.setText("Enter Your Phone Number 2");
        }
        
        Container c = caseLog.c;
        if(c.getComponentCount() != 0)
        {
            c.removeAll();
        }
        c.add(p, BorderLayout.WEST);
        c.revalidate();
        c.repaint();
    }
    
    
    
    
    
    public void showPayment()
    {
        ArrayList<PaymentRecord> payment = caseLog.client.getPayment();
        buildPaymentTable(payment);
    }
    

    private void buildPaymentTable(ArrayList<PaymentRecord> paymentArrayList) 
    {
        Container c = caseLog.c;
        JTable paymentTable = new JTable(new paymentTableModel(paymentArrayList));
        JScrollPane sp = new JScrollPane();
        sp.getViewport().add(paymentTable);
        sp.setPreferredSize(new Dimension(300, sp.getPreferredSize().getSize().height));
        
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener((e) -> caseLog.createMenuPanel());
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
            p.add(paymentTable);
            p.add(cancel);
        
        if(c.getComponentCount() != 0)
        {
            c.removeAll();
        }
        c.add(p, BorderLayout.WEST);
        c.revalidate();
        c.repaint();
    }
    
}
