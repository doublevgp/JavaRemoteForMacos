package wgp.task2.operator;

import java.io.File;
import java.util.ArrayList;

import wgp.task2.data.CmdServerIpSetting;
import wgp.task2.data.NetFileData;
import wgp.task2.socket.FileDownLoadSocketThread;

public class DLF {
    public void exe(ArrayList<String> ackMsgList) {
//        int msgNum = Integer.valueOf(ackMsgList.get(1));
//        for (int i = 2; i < msgNum + 2; i++) {
//
//        }
        if (ackMsgList.get(2).equals("ok")) {
            int port = Integer.parseInt(ackMsgList.get(3));
            long fileSize = Long.parseLong(ackMsgList.get(4));
            String cmd = ackMsgList.get(5);
            int id = cmd.indexOf(":");
            File file = new File(cmd.substring(id + 1));
            NetFileData netFileData = new NetFileData(ackMsgList.get(6), ackMsgList.get(6));
//            FileTransferBeginHandler fileTransferBeginHandler = new FileTransferBeginHandler(, netFileData);
//            new FileDownLoadSocketThread(CmdServerIpSetting.ip, port, fileTransferBeginHandler, file, fileSize);
        }
    }
}
