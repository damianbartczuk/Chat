/**
 *
 *  @author Bartczuk Damian S17763
 *
 */

package zad1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class Client {
    private SocketChannel channel;
    private String nick;
    private String message;

    public Client(){
        new LoginWindow();
    }

    public Client(String clientNick){
        this.setClient(clientNick);
    }

    public String getClientNick(){
        return this.nick;
    }

    public void send(String message){
        Server.sendToSocketChannel((getClientNick() + ": " + message), this.channel);
    }

    public void setClient(String clientNick){
        this.nick = clientNick;
        UserWindow userWindow = new UserWindow(this);
        try {
            channel = SocketChannel.open();
            channel.connect(new InetSocketAddress("localhost", 8888));
            while (true){
                message = Server.readDataFromSocketChannel(channel);
                if(message.length() > 0);
                userWindow.addText(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        new Client();
    }
}
