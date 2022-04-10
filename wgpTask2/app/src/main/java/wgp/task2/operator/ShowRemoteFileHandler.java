package wgp.task2.operator;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import androidx.annotation.NonNull;


import java.io.File;
import java.util.ArrayList;

import wgp.task2.SocketClient;
import wgp.task2.data.CmdServerIpSetting;
import wgp.task2.data.NetFileData;
import wgp.task2.socket.CmdClientSocket;
import wgp.task2.socket.FileDownLoadSocketThread;
import wgp.task2.socket.FileUpLoadSocketThread;
import wgp.task2.utils.CheckLocalDownloadFolder;
import wgp.task2.view.MyListView;
import wgp.task2.view.NetFileListAdapter;

public class ShowRemoteFileHandler extends Handler {
    private Context context;
    private MyListView listView;
    ArrayList<NetFileData> netFileList;
    CmdClientSocket clientSocket;

    public FileCallback getFileCallback() {
        return fileCallback;
    }

    public void setFileCallback(FileCallback fileCallback) {
        this.fileCallback = fileCallback;
    }

    public FileCallback fileCallback;
    public interface FileCallback {
        void onDlfCallBack(String fileName, String filePath, long fileSize, long downSize, String ip, int port);
    }
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
                case "DLF":
                    if (ackMsgList.get(2).equals("ok")) {
                        int port = Integer.parseInt(ackMsgList.get(3));
                        long fileSize = Long.parseLong(ackMsgList.get(4));
                        String cmd = ackMsgList.get(5);
                        int id = cmd.indexOf(":");
                        NetFileData netFileData = new NetFileData(ackMsgList.get(6), ackMsgList.get(6));
                        File file = new File(CheckLocalDownloadFolder.check() + "/" + netFileData.getFileName());
                        if (fileCallback != null) {
                            fileCallback.onDlfCallBack(netFileData.getFileName(), file.getPath(), fileSize, file.length(), CmdServerIpSetting.ip, port);
                        }
//                        FileTransferBeginHandler fileTransferBeginHandler = new FileTransferBeginHandler(context, netFileData);
//                        new FileDownLoadSocketThread(CmdServerIpSetting.ip, port, fileTransferBeginHandler, file, fileSize).start();
                    }
//                    new DLF().exe(ackMsgList);
                    break;
                case "ULF":
                    if (ackMsgList.get(2).equals("ok")) {
//                        String ip = ackMsgList;
                        int port = Integer.parseInt(ackMsgList.get(3));
                        long fileSize = Long.parseLong(ackMsgList.get(4));
                        String cmd = ackMsgList.get(5);
                        int id = cmd.indexOf(":");
                        NetFileData netFileData = new NetFileData(ackMsgList.get(6), ackMsgList.get(6));
                        File file = new File(CheckLocalDownloadFolder.check() + "/" + netFileData.getFileName());
                        FileTransferBeginHandler fileTransferBeginHandler = new FileTransferBeginHandler(context, netFileData);
                        new FileUpLoadSocketThread(CmdServerIpSetting.ip, port, fileTransferBeginHandler, file, fileSize).start();
                    }
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
