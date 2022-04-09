package wgp.task2.operator;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import wgp.task2.SocketClient;
import wgp.task2.fragment.TouchPadFragment;

public class ShowNonUiUpdateCmdHandler extends Handler {
    private Context context;

    public ShowNonUiUpdateCmdHandler(Context context) {
        this.context = context;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        Bundle bundle = msg.getData();
        ArrayList<String> ackMsgList = bundle.getStringArrayList(SocketClient.KEY_SERVER_ACK_MSG);
        if (ackMsgList.size() > 0) {
            switch (ackMsgList.get(0)) {
                case "POI":
                    setPointerInfo(ackMsgList);
                    break;
            }
        }
    }
    private void setPointerInfo(ArrayList<String> ackMsgList) {
        String str = ackMsgList.get(3);
        String[] split = str.split(",");
        int x = Integer.parseInt(split[0]);
        int y = Integer.parseInt(split[1]);
        TouchPadFragment.lastXpos = x;
        TouchPadFragment.lastYpos = y;
    }
}
