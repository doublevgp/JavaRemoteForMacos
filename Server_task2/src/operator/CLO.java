package operator;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class CLO extends BaseOperator {
    public ArrayList<String> exe(String cmdBody) throws IOException {
        ArrayList<String> backList = new ArrayList<>();
        int port = Integer.parseInt(cmdBody);
        try {
            ServerSocket serverSocket = SocketMap.socketMap.get(port);
            serverSocket.close();
            backList.add("2");
            backList.add("ok");
            backList.add("socket " + port + " 已经关闭!");
            backList.add(0, "CLO");
        } catch (IOException e) {
            backList.add("2");
            backList.add("error");
            backList.add(e.getMessage());
            backList.add(0, "CLO");
        }
        return backList;
    }
}
