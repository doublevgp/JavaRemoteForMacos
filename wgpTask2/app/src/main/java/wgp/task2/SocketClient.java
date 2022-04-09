package wgp.task2;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import wgp.task2.operator.ShowNonUiUpdateCmdHandler;
import wgp.task2.operator.ShowRemoteFileHandler;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

public class SocketClient {
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    private String ip;
    private int port;
    private ArrayList<String> cmd;
    private int time_out=10000;
    private Handler handler;
    private Socket socket;
    public static final String KEY_SERVER_ACK_MSG = "KEY_SERVER_ACK_MSG";
    public static int SERVER_MSG_OK = 0;
    public static int SERVER_MSG_ERROR = 1;
    public static int msgType = 0;
    private OutputStreamWriter writer;
    private BufferedReader bufferedReader;
    public SocketClient(String ip, int port, ShowRemoteFileHandler handler) {
        this.port = port;
        this.ip = ip;
        this.handler = handler;
    }
    public SocketClient(String ip, int port, ShowNonUiUpdateCmdHandler handler) {
        this.port = port;
        this.ip = ip;
        this.handler = handler;
    }
    private void connect() throws IOException {
        InetSocketAddress address=new InetSocketAddress(ip,port);
        socket=new Socket();
        socket.connect(address,time_out);
    }
    private void writeCmd(String cmd) throws IOException {
        BufferedOutputStream os=new BufferedOutputStream(socket.getOutputStream());
        writer=new OutputStreamWriter(os,"UTF-8");
        writer.write("1\n");
        writer.write(cmd+"\n");
        writer.flush();
    }
    private ArrayList<String> readSocketMsg() throws IOException {
        ArrayList<String> msgList=new ArrayList<>();
        InputStreamReader isr=new InputStreamReader(socket.getInputStream(),"UTF-8");
        bufferedReader=new BufferedReader(isr);
        String numStr = bufferedReader.readLine();
        int linNum = Integer.parseInt(numStr);
        for (int i = 0; i <linNum ; i++) {
            String s = bufferedReader.readLine();
            msgList.add(s);
        }
        return msgList;
    }
    private void close() throws IOException {
        bufferedReader.close();
        writer.close();
        socket.close();
    }
    private void doCmdTask(String cmd){
        ArrayList<String> msgList=new ArrayList<>();
        try {
            connect();
            writeCmd(cmd);
            msgList = readSocketMsg();
            System.out.println("readMsg" + msgList.toString());
            close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Message message = handler.obtainMessage();
        Bundle bundle = new Bundle();
        message.arg2 = msgType;
        bundle.putStringArrayList(KEY_SERVER_ACK_MSG, msgList);
        message.setData(bundle);
        handler.sendMessage(message);
    }
    public void work(final String cmd){
        new Thread(new Runnable() {
            @Override
            public void run() {
                doCmdTask(cmd);
            }
        }).start();
    }
}
