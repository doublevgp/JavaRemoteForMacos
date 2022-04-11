package wgp.task2.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import wgp.task2.MainActivity;
import wgp.task2.R;
import wgp.task2.data.HotKeyData;
import wgp.task2.socket.CmdClientSocket;
import wgp.task2.view.HotKeyAdapter;

import java.util.ArrayList;

public class HotKeyDialog extends DialogFragment {
    Context context;
    ArrayList<HotKeyData> hotKeyDataList;
    CmdClientSocket clientSocket; //用于HotKeyGridAdapter的视图点击触发cmdClientSocket向远程端发送命令
    String title;
    GridView gridView = null;

    public HotKeyDialog(Context context, ArrayList<HotKeyData> hotKeyDataList, String title, CmdClientSocket clientSocket) {
        this.context = context;
        this.hotKeyDataList = hotKeyDataList;
        this.clientSocket = clientSocket;
        this.title = title;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_view, null);
        builder.setView(view);
        gridView = view.findViewById(R.id.dialog_gridview);
        HotKeyAdapter adapter = new HotKeyAdapter(context, hotKeyDataList);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HotKeyData hotKeyData = adapter.getItem(position);
                if (hotKeyData.getHotKeys().size() != 0) {
                    exeHotKeyCmd(hotKeyData);
                } else {
                    FragmentManager fragmentManager = getFragmentManager();
                    InputDialog inputDialog = new InputDialog();
                    switch (hotKeyData.getHotKeyName()) {
                        case "粘贴输入内容":
                            inputDialog.show(fragmentManager);
                            inputDialog.setType("cps");
                            break;
                        case "爱奇艺搜索叶问4":
                            try {
//                                String cmd = "for:1>cmd:open https://iqiyi.com>clk:left>key:TAB?6>cps:叶问4>key:ENTER";
                                String cmd = "for:1>cmd:open https://iqiyi.com>moa:757,137>clk:left>cps:叶问4>key:ENTER";
                                clientSocket.work(cmd);
//                                cmd = String.format("cmd:open %s", "https://iqiyi.com");
    //                            System.out.println("DefinedCMD is " + cmd);
//                                clientSocket.work(cmd);
//                                Thread.sleep(5000);
//                                cmd = String.format("clk:left");
//                                clientSocket.work(cmd);
                                Thread.sleep(1000);
////                                for (int i = 1; i <= 6; i++) {
//                                cmd = String.format("key:TAB?6");
//                                clientSocket.work(cmd);
//                                Thread.sleep(100);
////                                }
//                                cmd = String.format("cps:叶问4");
//                                clientSocket.work(cmd);
//                                Thread.sleep(100);
//                                cmd = String.format("key:ENTER");
//                                clientSocket.work(cmd);
//                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
//                            clientSocket.work("opn");
//                            inputDialog.show(fragmentManager);
//                            inputDialog.setType("opn");
                            break;
                    }
                }
            }
        });
        return builder.create();
    }

    private void exeHotKeyCmd(HotKeyData hotKeyData) {
        String keys = "";
        ArrayList<String> hotKeys = hotKeyData.getHotKeys();
        for (int i = 0; i < hotKeys.size(); i++) {
            if (i == 0) {
                keys = keys + hotKeys.get(i);
            } else {
                keys = keys + "," + hotKeys.get(i);
            }
        }
        String cmd = String.format("key:%s", keys);
        System.out.println("keys cmd is " + cmd);
        clientSocket.work(cmd);
    }
    public void exeDefineCmd(String cmdBody) {
        String cmd = String.format("cps:%s", cmdBody);
        System.out.println("DefinedCMD is " + cmd);
        clientSocket.work(cmd);
    }

    public void show(FragmentManager fragmentManager) {
        show(fragmentManager, this.title);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
