/**
 *
 *  @author Bartczuk Damian S17763
 *
 */

package zad1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

import javax.swing.JOptionPane;

public class Server {
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private Set setKeys;
    private Iterator iterator;
    private SelectionKey selectionKey;
    private Optional<SocketChannel> socketChannel;


    public Server(){
        try {
            serverSocketChannel= ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress("localhost", 8888));
            selector=Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        connect();
    }


    public void connect(){
        boolean running = true;
        while(running){
            try {
                selector.select();
                setKeys = selector.selectedKeys();
                iterator = setKeys.iterator();
                while(iterator.hasNext()){
                    selectionKey= (SelectionKey) iterator.next();
                    if(selectionKey.isAcceptable()){
                        socketChannel = Optional.ofNullable(serverSocketChannel.accept());
                        if(socketChannel.isPresent()){
                            socketChannel.get().configureBlocking(false);
                            socketChannel.get().register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                        }
                        continue;
                    }
                    if(selectionKey.isReadable()){
                        socketChannel = Optional.of ((SocketChannel) selectionKey.channel());
                        String msg = readDataFromSocketChannel(socketChannel.get());
                        if(msg.length() > 0){
                            this.sendMessToAll(msg);
                        }
                        continue;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }


    private void sendMessToAll(String msg){
        try {
            selector.select();
            setKeys= selector.selectedKeys();
            iterator= setKeys.iterator();
            while(iterator.hasNext()){
                selectionKey= (SelectionKey) iterator.next();
                if(selectionKey.isWritable()){
                    socketChannel = Optional.of((SocketChannel) selectionKey.channel());
                    sendToSocketChannel(msg, socketChannel.get());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readDataFromSocketChannel(SocketChannel socket){
        StringBuffer stringBuffer = new StringBuffer();
        ByteBuffer buffer= ByteBuffer.allocate(2048);
        buffer.clear();
        char sign;
        running:
        while(true){
            try {
                int x = socket.read(buffer);
                if(x > 0){
                    buffer.flip();
                    CharBuffer charBuffer = Charset.forName("UTF-8").decode(buffer);
                    while(charBuffer.hasRemaining()){
                        sign = charBuffer.get();
                        stringBuffer.append(sign);
                    }
                }else {
                    break running;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringBuffer.toString();
    }
    public static void sendToSocketChannel(String message, SocketChannel sc){
        try {
            ByteBuffer bf= Charset.forName("UTF-8").encode(message);
            while(bf.hasRemaining()){
                sc.write(bf);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        new Server();
    }
}
