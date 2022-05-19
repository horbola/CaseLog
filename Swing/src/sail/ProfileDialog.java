package sail;


import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;



class ProfileDialog extends JDialog
{
    CaseLog caseLog;
    ProfileDialog(CaseLog parent)
    {
        this.caseLog = parent;
        setSize(parent.getSize().width-100, parent.getSize().height-100);
        setLocation(middleIt());
        
        getContentPane().add(new ProfilePanel(ProfileDialog.this));
    }
    
    protected Point middleIt()
    {
        Dimension d1 = caseLog.getSize();
        Dimension d2 = this.getSize();
        Point p = new Point();
        p.x = (d1.width- d2.width)/2;
        p.y = (d1.height- d2.height)/2;
        Point pp = caseLog.getLocation();
        p.x = pp.x +p.x;
        p.y = pp.y +p.y;
        return p;
    }
    
}
        
class ProfilePanel extends JPanel
{
    ProfileDialog profileDialog;
    
    JTextField idText, passText, nameText, fatherText, homeText, 
                villageText, subDisText, districtText, phone1Text, phone2Text;
    JButton submit, cancel;
    boolean fucusLostCounted = false;
    
    protected void initTextField()
    {
        idText = new JTextField();
        passText = new JTextField();
        nameText = new JTextField();
        fatherText = new JTextField();
        homeText = new JTextField();
        villageText = new JTextField();
        subDisText = new JTextField();
        districtText = new JTextField();
        phone1Text = new JTextField();
        phone2Text = new JTextField();
        submit = new JButton("submit");
        cancel = new JButton("cancel");
    }
    
    ProfilePanel(ProfileDialog parent)
    {
        super(new GridLayout(12,2,5,5));
        this.profileDialog = parent;
        initTextField();
        add(new JLabel("User ID"));     add(idText);
        add(new JLabel("Password"));    add(passText);
        add(new JLabel("Name"));        add(nameText);
        add(new JLabel("Father"));      add(fatherText);
        add(new JLabel("Home"));        add(homeText);
        add(new JLabel("Village"));     add(villageText);
        add(new JLabel("Subdistrict")); add(subDisText);
        add(new JLabel("District"));    add(districtText);
        add(new JLabel("Phone1"));      add(phone1Text);
        add(new JLabel("Phone2"));      add(phone2Text);
        add(submit);                    add(cancel);
        
        idText.addFocusListener(new FocusAdapter(){
            public void focusLost(FocusEvent e)
            {
                if(idText.getText().equals("")) return;
                if(fucusLostCounted) return;
                fucusLostCounted = true;
                idText.requestFocusInWindow();
                boolean isDuplicateId = profileDialog.caseLog.client.checkDuplicateId(SqlBuilder.buildDuplicateIdSql(idText.getText()));
                if(isDuplicateId)
                {
                    String message = "This user id is already taken. Please try another one.";
                    JOptionPane.showMessageDialog(profileDialog, message, "Checking Duplicate ID", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        submit.addActionListener(new SubmitListener());
        cancel.addActionListener((e) -> parent.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE));
    }
    
    class SubmitListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            String id = idText.getText();
            String pass = passText.getText();
            String name = nameText.getText();
            String father = fatherText.getText();
            String home = homeText.getText();
            String village = villageText.getText();
            String subDis = subDisText.getText();
            String district = districtText.getText();
            String phone1 = phone1Text.getText();
            String phone2 = phone2Text.getText();
            
            profileDialog.caseLog.client.setProfileWithCache(id, pass, "", "", name, father, home, village, subDis, district, phone1, phone2, "", "", "", "");           
            profileDialog.setVisible(false);
            profileDialog.caseLog.setVisible(true);
        }
            
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