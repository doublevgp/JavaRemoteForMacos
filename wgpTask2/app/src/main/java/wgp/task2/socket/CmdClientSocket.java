package wgp.task2.socket;

import wgp.task2.SocketClient;
import wgp.task2.operator.ShowNonUiUpdateCmdHandler;
import wgp.task2.operator.ShowRemoteFileHandler;

public class CmdClientSocket {
    SocketClient client;
    public CmdClientSocket(String ip, int port, ShowRemoteFileHandler handler) {
        client = new SocketClient(ip, port, handler);
    }
    public CmdClientSocket(String ip, int port, ShowNonUiUpdateCmdHandler handler) {
        client = new SocketClient(ip, port, handler);
    }
    public void setIpAndPort(String ip, int port) {
        client.setIp(ip);
        client.setPort(port);
    }

    public void work(final String cmd) {
        client.work(cmd);
    }
}
