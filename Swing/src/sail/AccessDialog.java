package sail;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class AccessDialog extends JDialog
{
    boolean idExists = false;
    boolean passExists = false;
    CaseLog caseLog;
    
    public AccessDialog(CaseLog parent, String title) 
    {
        this.caseLog = parent;
        setSize(parent.getSize().width-100, parent.getSize().height-100);
        setLocation(middleIt());
        getContentPane().add(createAccessPanel());
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

    private JPanel panel()
    {
        JPanel p = new JPanel();
            p.add(new JButton("button"));
        return p;
    }
    
    JTextField idText, passText;
    JButton login, account;
    private JPanel createAccessPanel()
    {
        JPanel accessPanel = new JPanel(new BorderLayout());
        JPanel p = new JPanel();
            p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
            idText = new JTextField();
                p.add(idText);
                
                
            passText = new JTextField();
            passText.addFocusListener(new FocusAdapter()
            {
                public void focusGained(FocusEvent e)
                {
                    if(idText.getText().equals(""))
                    {
                        idText.requestFocusInWindow();
                        String message = "You nust enter an userID before you check for a password of it.";
                        JOptionPane.showMessageDialog(AccessDialog.this, message, "ID Field Left Empty", JOptionPane.INFORMATION_MESSAGE);
                        
                    }
                }
            });
                p.add(passText);
                
                
            login = new JButton("login");
                p.add(login);
                
            account = new JButton("Create Account");
                p.add(account);
                
        accessPanel.add(p, BorderLayout.CENTER);
        
        idText.addFocusListener(new IdFocusL());
        login.addActionListener((e) -> checkAndLogin());
        account.addActionListener((e) -> createProfile());
        return accessPanel;
    }

    
    private class IdFocusL implements FocusListener 
    {
        public void focusGained(FocusEvent e){}
        
        public void focusLost(FocusEvent e)
        {
                String id = idText.getText();
                if(id.equals("")) return; 
                idExists = caseLog.client.checkID(id);
                String message = "This userid doesn't exist. Please enter an unserid with what you are registered";
                if(!idExists) 
                {
                    JOptionPane.showMessageDialog(AccessDialog.this, message, "User ID Checking", JOptionPane.ERROR_MESSAGE);
                    idText.requestFocusInWindow();
                }
        }
    }
    
    
    
    private void checkAndLogin()
    {
        String id = idText.getText();
        if(id.equals("")) 
        {
            String message = "You nust enter an userID before you login.";
            JOptionPane.showMessageDialog(AccessDialog.this, message, "ID Field Left Empty", JOptionPane.INFORMATION_MESSAGE);
            idText.requestFocusInWindow();
            return;
        }
        
        String pass = passText.getText();
        if(pass.equals(""))
        {
            String message = "You nust verify your access with the password you've set for this userId.";
            JOptionPane.showMessageDialog(AccessDialog.this, message, "Password Field Left Empty", JOptionPane.INFORMATION_MESSAGE);
            idText.requestFocusInWindow();
            return;
        }
        
        if(idExists) passExists = caseLog.client.checkPass(pass);
        
        if(passExists)
        {
            this.setVisible(false);
            caseLog.setVisible(true);
            caseLog.client.login();
        }
        else 
        {
            String message = "The password you entered didn't match. If you forget your password and want to set a new one then click on 'Set'.";
            String options[] = {"Set", "Not now. Let me try again"};
            int selectedOption = JOptionPane.showOptionDialog(AccessDialog.this, message, "Password Checking", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[1]);
            if(selectedOption == 0) setPassword();
            else if(selectedOption == 1) passText.requestFocusInWindow();
            
        }
    }
    
    private void setPassword()
    {
        
    }
    
    
    
    
    
    public void createProfile() 
    {
        
        this.setVisible(false);
        new ProfileDialog(caseLog).setVisible(true);
    }
    
}
