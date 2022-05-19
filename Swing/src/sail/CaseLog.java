package sail;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Set;


/**
 * This class is the main class for this software which is named as CaseLog. 
 * there are three main panels on CaseLog. One is for all cases list of an advocate
 * which he is interested on. Another is for cases list also but that is arranged 
 * date wise. These two panels pop up from the right side of android devices with a 
 * swipe. The third panel pops up from the left side with a swipe. It shows all the 
 * options this application has. For example, personal profile information of an 
 * advocate, payment information, cases list of all courts from which to select.
 * case statistics, a note pad, tutorial, settings, and about. And there is a calender
 * based cases list which will open at the startup of the application.
 * 
 * This software takes it's user choice from AccessDialog.java, ProfileDialog.java and 
 * Cases.java. These inputs are processed by SqlBuilder.java to build SQL statement.
 * these statements are passed as argument to the methods of Client.java. This class
 * contains methods which make connections to necessary ServLets. Those ServLet takes
 * those SQL statements from their request input stream by doGet() method. And
 * these ServLets further make connections to database. The data returned by database
 * is taken by these ServLets and processed if necessary and in turn returned to the 
 * methods of Client.java. The methods of Client.java process these data and store
 * them in different classes like PaymentRecord.java, CaseCount.java, CaseInfo.java.
 * 
 * After this cycle completes, these information is shown by the different GUI staffs.
 * 
 * @author Saif Khan Faysal (January 2019)
 */

public class CaseLog extends JFrame
{
    
    Cases cases = null;
    Client client = null;
    Profile profile = null;
    
    Container c;
    CaseLog()
    {
        super("CaseLog");
        setSize(400,600);
        setLocation(middleIt());
        
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                client.cacheLoginInfo();
                client.updateProfile();
                System.exit(0);
            }
        });
        
        setIconImage(new ImageIcon("D:\\Image\\Icons\\sailBoat.png").getImage());
        
        c = getContentPane();
        setJMenuBar(createMenuBar());
        
        client = new Client();
        profile = new Profile(CaseLog.this);
        cases = new Cases(CaseLog.this);
        
        //This dialog is opened at the startup with textFields for id and password
        //and a button to create profile in case a user is using this application
        //for the first time. That means every user must be registered and licenced
        //to use this service. If a user is validated with this dialog then this dilaog 
        //vanishes and the CaseLog main window comes.
        
        client.getCachedLoginInfo();
        if(!client.loggedin){new AccessDialog(this, "User Access").setVisible(true);}
        else setVisible(true);
    }
    
    
    public void logout()
    {
        client.logout();
        this.setVisible(false);
        main(null);
    }



    
    protected Point middleIt()
    {
        Dimension d1 = getToolkit().getScreenSize();
        Dimension d2 = this.getSize();
        Point p = new Point();
        p.x = (d1.width- d2.width)/2;
        p.y = (d1.height- d2.height)/2;
        return p;
    }
    
    protected JMenuBar createMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();
        JMenuItem menuPanelItem = new JMenuItem("MenuPanel");
            menuBar.add(menuPanelItem);
        JMenuItem caseListItem = new JMenuItem("CaseList");
            menuBar.add(caseListItem);
        JMenuItem dateListItem = new JMenuItem("DateList");
            menuBar.add(dateListItem);
        
        menuPanelItem.addActionListener((e) -> createMenuPanel());
        caseListItem.addActionListener((e) -> createCaseList());
        dateListItem.addActionListener((e) -> createDateList());
        
        return menuBar;
    }
    
    /**
     * This is the panel which comes from left side of devices and contains main
     * options.
     */
    protected void createMenuPanel()
    {
        JPanel menuPanel = new JPanel(new GridLayout(10,1));
        menuPanel.setBackground(Color.WHITE);
        
        ActionListener profileListener = (e) -> profile.showProfile();
        ActionListener paymentListener = (e) -> profile.showPayment();
        ActionListener caseSelectListener = (e) -> cases.showAllCaseNumbersToSelect();
        ActionListener logoutListener = (e) -> logout();
        
        menuPanel.add(new MenuPanelButton("Profile", profileListener));
        menuPanel.add(new MenuPanelButton("Payment", paymentListener));
        menuPanel.add(new MenuPanelButton("CaseSelect", caseSelectListener));
        menuPanel.add(new MenuPanelButton("Your selected Cases", null));
        menuPanel.add(new MenuPanelButton("CaseStatistics", null));
        menuPanel.add(new MenuPanelButton("Note", null));
        menuPanel.add(new MenuPanelButton("Tutorial", null));
        menuPanel.add(new MenuPanelButton("Settings", null));
        menuPanel.add(new MenuPanelButton("About", null));
        menuPanel.add(new MenuPanelButton("Logout", logoutListener));
        
        if(c.getComponentCount() != 0)
        {
            c.removeAll();
        }
        c.add(menuPanel,BorderLayout.WEST);
        c.revalidate();
        c.repaint();
    }
    
    protected class MenuPanelButton  extends JButton
    {
        MenuPanelButton(String caption, ActionListener al)
        {
            super(caption);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setPreferredSize(new Dimension(300,550));
            if(al != null) addActionListener(al);
        }
    }
    
    
    
    protected void createCaseList()
    {
        JList caseList = new JList(new DateListModel(client.getCaseGroup()));
        JScrollPane sp = new JScrollPane(caseList);
        sp.setPreferredSize(new Dimension(300, 550));
        if(c.getComponentCount() != 0)
        {
            c.removeAll();
        }
        c.add(sp, BorderLayout.EAST);
        c.revalidate();
        c.repaint();
    }
    
    protected void createDateList()
    {
        JList caseList = new JList(new DateListModel(client.getDateGroup()));
        JScrollPane sp = new JScrollPane(caseList);
        sp.setPreferredSize(new Dimension(300, 550));
        if(c.getComponentCount() != 0)
        {
            c.removeAll();
        }
        c.add(sp, BorderLayout.EAST);
        c.revalidate();
        c.repaint();
    }
    
    class DateListModel extends DefaultListModel
    {
        Map<Date,ArrayList<CaseRecord>> caseGroup;
        DateListModel(Map<Date,ArrayList<CaseRecord>> caseGroup)
        {
            this.caseGroup = caseGroup;
            Set set = caseGroup.keySet();
            for(Object o : set)
            {
                Date filing = (Date)o;
                ArrayList<CaseRecord> caseRecordList = caseGroup.get(filing);
                SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
                String date = sdf.format(filing);
                addElement(date);
                for(CaseRecord cr : caseRecordList){addElement(cr.getCaseList());}
            }
        }
        
    }
    
    
    
    public static void main(String...s){SwingUtilities.invokeLater(() -> new CaseLog());}
    
}
