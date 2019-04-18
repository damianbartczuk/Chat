package zad1;



import javax.swing.*;
import java.awt.*;

public class LoginWindow extends JPanel {
    public JLabel nickLabel;
    public JTextField textField;
    public JButton logInBtn;
    public JFrame frame;

    private void configFrame(JPanel panelLogin, JPanel jPanelNewUser) {
        frame.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width/3)+150, Toolkit.getDefaultToolkit().getScreenSize().height/3);
        textField.setFont(new Font("TimesRoman", Font.PLAIN, 18));
        frame.add(panelLogin, BorderLayout.PAGE_START);
        frame.add(jPanelNewUser, BorderLayout.PAGE_END);
        frame.add(textField, BorderLayout.CENTER);
        frame.setSize(600,150);
        frame.setVisible (true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
    }

    public LoginWindow() {
        frame = new JFrame ();
        JPanel panelLogin= new JPanel();
        nickLabel = new JLabel ("Put your nick");
        panelLogin.add(nickLabel);
        textField = new JTextField ();
        JPanel jPanelNewUser= new JPanel();
        logInBtn = new JButton ("Log in");
        logInBtn.addActionListener(actionEvent -> {
            if(textField.getText().length() > 0){
                new Thread(() -> new Client(textField.getText())).start();
                frame.dispose();
            }
        });
        jPanelNewUser.add(logInBtn);
        configFrame(panelLogin, jPanelNewUser);
    }


}
