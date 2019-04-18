package zad1;

import javax.swing.*;
import java.awt.*;

public class UserWindow extends JPanel {

    public JTextField textField;
    public JTextArea jTextArea;
    public Client client;
    private static int which = 0;
    public UserWindow(Client client) {
        this.client = client;
        JFrame frame = new JFrame("You're loged as : " + client.getClientNick());
        JPanel panel = new JPanel();
        textField = new JTextField(20);
        panel.add(textField);
        textField.addActionListener(actionEvent -> {
            if(textField.getText().length() > 0)
               this.client.send(textField.getText());

        });
        frame.add(panel, BorderLayout.PAGE_START);

        jTextArea = new JTextArea();
        jTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(jTextArea);
        configFrame(frame, scrollPane);
    }

    private void configFrame(JFrame frame, JScrollPane scrollPane) {
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setLocation(new Point(300*which, 0));
        which++;
        frame.setSize(300,500);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void addText(String msg){
        jTextArea.append(msg + "\n");
        jTextArea.setCaretPosition(jTextArea.getDocument().getLength());
        jTextArea.update(jTextArea.getGraphics());
    }

}
