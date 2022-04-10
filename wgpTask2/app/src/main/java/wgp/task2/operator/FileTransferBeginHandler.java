package wgp.task2.operator;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import wgp.task2.SocketClient;
import wgp.task2.data.NetFileData;
import wgp.task2.utils.CheckLocalDownloadFolder;

public class FileTransferBeginHandler extends Handler {
    private NetFileData fileData;
    private Context context;
    private String downloadFolderPath;

    public FileTransferBeginHandler(Context context, NetFileData fileData) {
        super();
        this.fileData = fileData;
        this.context = context;
        this.downloadFolderPath = CheckLocalDownloadFolder.check();
    }

    @Override
    public void handleMessage(@NonNull Message msg) { // handle 处理消息
        super.handleMessage(msg);
        System.out.println("handler");
        ArrayList<String> ackMsgList = msg.getData().getStringArrayList(SocketClient.KEY_SERVER_ACK_MSG);
        if (ackMsgList != null) {
            for (int i = 0; i < ackMsgList.size(); i++) {
                System.out.println(String.format("DLF:%d msg == %s", i, ackMsgList.get(i)));
            }
        }
    }
}
