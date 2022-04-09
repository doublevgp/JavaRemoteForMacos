package wgp.task2.operator;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import androidx.annotation.NonNull;


import java.util.ArrayList;

import wgp.task2.SocketClient;
import wgp.task2.data.NetFileData;
import wgp.task2.socket.CmdClientSocket;
import wgp.task2.view.MyListView;
import wgp.task2.view.NetFileListAdapter;

public class ShowRemoteFileHandler extends Handler {
    private Context context;
    private MyListView listView;
    ArrayList<NetFileData> netFileList;
    CmdClientSocket clientSocket;
    public ShowRemoteFileHandler(Context context, MyListView listView) {
        super();
        this.context = context;
        this.listView = listView;
    }

    public CmdClientSocket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(CmdClientSocket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        ArrayList<String> ackMsgList = msg.getData().getStringArrayList(SocketClient.KEY_SERVER_ACK_MSG);
        if (ackMsgList.size() >= 1) {
            String MsgType = ackMsgList.get(0);
            switch (MsgType) {
                case "DIR":
                    dealDir(ackMsgList);
                    break;
                case "OPN":
                    break;
                case "KEY":
                    break;
                case "DEL":
                    dealDel(ackMsgList);
                    break;
            }
        }
    }
    private void dealDir(ArrayList<String> ackMsgList) {
        netFileList = new DIR().Handle(ackMsgList.subList(1, ackMsgList.size()));
        if (netFileList.size() == 0) {
            dealError(ackMsgList.toString());
        } else {
//            System.out.println("??");
            NetFileListAdapter netFileListAdapter = new NetFileListAdapter(context, netFileList);
            listView.setAdapter(netFileListAdapter);
            netFileListAdapter.notifyDataSetChanged();
        }
    }
    private void dealDel(ArrayList<String> ackMsgList) {
        String s = ackMsgList.get(2);
        if (s.equals("ok")) {
            clientSocket.work(String.format("dir:%s", ackMsgList.get(3)));
        } else {
            dealError(s);
        }
    }
    private void dealError(String s) {
        Toast.makeText(context, s,Toast.LENGTH_SHORT).show();
    }
}
