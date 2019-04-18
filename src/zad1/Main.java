/**
 *
 *  @author Bartczuk Damian S17763
 *
 */

package zad1;


import javax.swing.*;
/**
 * w trudnych chwila zdarzalo mi siê korzystac z  https://github.com/srcmaxim/nio-messenger/blob/master/src/main/java/me/srcmaxim/server/Server.java
 */

public class Main {
    public static void main(String[] args) {
        new Thread(() -> new Server()).start();
        int howManyUsers  = Integer.parseInt(JOptionPane.showInputDialog("How many users do you want in chat "));
            for (int i = 0; i <howManyUsers ; i++) {
                new Client();
            }
    }
}
