package wgp.task2.operator;

import wgp.task2.data.NetFileData;

import java.util.ArrayList;
import java.util.List;

public class DEL {
    public ArrayList<NetFileData> Handle(List<String> ackMsgList) {
        ArrayList<NetFileData> netFileList = new ArrayList<>();
        int MsgNum = Integer.parseInt(ackMsgList.get(0));
        String isOk = ackMsgList.get(1);
        if (isOk.equals("ok")) {
            for (int i = 2; i < MsgNum; i++){
                System.out.println(ackMsgList.get(i));
                NetFileData data = new NetFileData(ackMsgList.get(i), ackMsgList.get(i));
                netFileList.add(data);
            }
        }
        return netFileList;
    }
}
