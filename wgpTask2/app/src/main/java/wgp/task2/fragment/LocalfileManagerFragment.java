package wgp.task2.fragment;

import android.os.Bundle;
import android.os.Environment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import wgp.task2.Dialog.HotKeyDialog;
import wgp.task2.Dialog.InputDialog;
import wgp.task2.MainActivity;
import wgp.task2.R;
import wgp.task2.data.CmdServerIpSetting;
import wgp.task2.data.LinkData;
import wgp.task2.data.NetFileData;
import wgp.task2.db.RemoteDataBase;
import wgp.task2.operator.FileTransferBeginHandler;
import wgp.task2.operator.ShowRemoteFileHandler;
import wgp.task2.socket.CmdClientSocket;
import wgp.task2.socket.FileDownLoadSocketThread;
import wgp.task2.socket.FileUpLoadSocketThread;
import wgp.task2.utils.HotKeyGenerator;
import wgp.task2.view.MyListView;
import wgp.task2.view.NetFileListAdapter;
import wgp.task2.view.RecyclerAdapter;

public class LocalfileManagerFragment extends Fragment {


    MyListView listView;
    ShowRemoteFileHandler showRemoteFileHandler;
    CmdClientSocket clientSocket;
    RemoteDataBase remoteDataBase;
    String link_name;
    EditText et_ip;
    EditText et_port;
    EditText et_cmd;
    ArrayList<String> dirdatas = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerAdapter mAdapter;
    String nowPath;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.local_file_fragment, null);
        listView = view.findViewById(R.id.listView);
        registerForContextMenu(listView);
        recyclerView = view.findViewById(R.id.local_recyclerview);
        work(Environment.getExternalStorageDirectory().getAbsolutePath());
        //?????????????????????LayoutManager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        //????????????????????????item????????????????????????????????????????????????????????????
        recyclerView.setHasFixedSize(true);
        mAdapter = new RecyclerAdapter(new String[]{"/storage/emulated/0"});
        recyclerView.setAdapter(mAdapter);
        remoteDataBase = new RemoteDataBase(getActivity());
        remoteDataBase.open();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NetFileData netFileData = (NetFileData) listView.getAdapter().getItem(position);
                System.out.println("fileName == " + netFileData.getFileName());
                String filepath = netFileData.getFilePath();
                String filename = netFileData.getFileName();
                if (filename.equals("...")) {
                    String newCmd = Environment.getExternalStorageDirectory().getAbsolutePath();
                    System.out.println("newCMMMMMD is " + newCmd);
                    work(newCmd);
                } else if (filename.equals("..")) {
                    String newCmd = String.format("%s", filepath);
                    System.out.println("newCMMMMMD is " + newCmd);
                    work(newCmd);
                } else {
                    if (netFileData.getFileType() == 1) { // ????????????
                        String newCmd = "";
                        if (filepath == "/") {
                            newCmd = String.format("/%s", filepath, filename);
                        } else {
                            newCmd = String.format("%s/%s", filepath, filename);
                        }
                        System.out.println("newCmd is " + newCmd);
//                        clientSocket.work(newCmd);
                        work(newCmd);
                    } else { // ????????????
//                        String newCmd = "";
//                        if (filepath == "/") {
//                            newCmd = String.format("/%s", filepath, filename);
//                        } else {
//                            newCmd = String.format("%s/%s", filepath, filename);
//                        }
//                        System.out.println("OPN newCmd is " + newCmd);
//                        clientSocket.work(newCmd);
                    }
                }
            }
        });
        return view;
    }
    private void work(String cmd) {
        System.out.println("cmd == " + cmd);
        ArrayList<NetFileData> lst = delBackList(getLocalFileList(cmd));
        if (lst.size() > 0) {
            nowPath = cmd;
            NetFileListAdapter adapter = new NetFileListAdapter(getContext(), lst);
            listView.setAdapter(adapter);
            updateView();
        }
    }
    private void updateView() {
        System.out.println("UPDATEVIEW FUN");
        String[] split = nowPath.split("/");
        System.out.println("nowpath == " + nowPath);
        dirdatas.clear();
        dirdatas.add("/");
        for (int i = 0; i < split.length; i++) {
            dirdatas.add(split[i]);
        }
        System.out.println("Show dirdatas:");
        for (int i = 0; i < dirdatas.size(); i++) {
            System.out.println(dirdatas.get(i));
        }
        String[] str = new String[split.length];
        if (str.length > 0) {
            str[0] = "...";
        }
        for (int i = 1; i < str.length; i++) {
            str[i] = "/" + split[i];
        }
        mAdapter = new RecyclerAdapter(str);
        mAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final int postion) {
                String dirord = null;
                if (postion == 0) {
                    dirord = "/storage/emulated/0";
                    dirdatas.clear();
                    dirdatas.add("...");
                } else {
                    System.out.println(postion + " " + mAdapter.datas[postion]);
                    dirord = "";
                    for(int i = 2; i <= postion + 1; i++){
                        System.out.println(i + " " + dirdatas.get(i));
                        dirord = dirord + "/" + dirdatas.get(i);
                        System.out.println("now dirord == " + dirord);
                    }
                    int nowsize = dirdatas.size();
                    for(int i = nowsize - 1; i > postion + 1; i--){
                        dirdatas.remove(i);
                    }
                }
                work(dirord);
//                doSocketWork(dirord);
//                ConnectWithOrder(dirord, new OrderDone() {
//                    @Override
//                    public void onFinished() {
//                        Toast.makeText(getActivity(),"pos "+postion,Toast.LENGTH_SHORT).show();
//                    }
//                });

            }
        });
        recyclerView.setAdapter(mAdapter);
    }
    private void doSocketWork(String cmd) {
        clientSocket.work(cmd);
        et_cmd.setText(cmd);
        String[] split = cmd.split(":");
        System.out.println("cmd ?? " + split[0]);
        if (split[0].equals("dir")) {
            nowPath = cmd.substring(4);
            System.out.println("nowPath === " + nowPath);
            updateView();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.local_ctx_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        NetFileData netFileData = (NetFileData) listView.getAdapter().getItem(menuInfo.position);

        switch (item.getItemId()) {
            case R.id.ctx_upload:
                System.out.println("??????????????????");
                clientSocket.work("ulf:" + netFileData.getFileName());
                File o = new File(netFileData.getFilePath() + "/" + netFileData.getFileName());
                FileTransferBeginHandler handler = new FileTransferBeginHandler(getContext(), netFileData);
//                File o = new File("/Users/doublevgp/Downloads/" + netFileData.getFileName());
                new FileUpLoadSocketThread(CmdServerIpSetting.ip, CmdServerIpSetting.up_port, handler, o, o.length()).run();
                break;
        }
        return super.onContextItemSelected(item);
    }
    public ArrayList<NetFileData> delBackList(ArrayList<String> backList) {
        System.out.println("readMsg" + backList.toString());
        ArrayList<NetFileData> lst = new ArrayList<>();
        if (backList.size() > 0) {
            int num = Integer.parseInt(backList.get(0));

            for (int i = 1; i < backList.size(); i++) {
//                System.out.println(backList.get(i));
                NetFileData data = new NetFileData(backList.get(i), backList.get(i));
                lst.add(data);
            }
        }
        return lst;
    }
    public ArrayList<String> getLocalFileList(String cmd) {
        ArrayList<String> backList = new ArrayList<>();
        File file = new File(cmd);
//        System.out.println(file.getAbsolutePath());
//        System.out.println(file.getParentFile().getAbsolutePath());
//        System.out.println(file.getAbsolutePath());
        if (file.exists()) {
            try {

                File[] listFiles = file.listFiles();
                for(File mfile : listFiles){
                    String fileName = mfile.getName();
                    long lastModified = mfile.lastModified();//????????????????????????
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//???????????????????????????2018-03-16 09:50:23
                    String fileDate = dateFormat.format(new Date(lastModified));//????????????????????????????????????????????????????????????
                    String fileSize="0";
                    String isDir="1";
                    String filePath = file.getAbsolutePath();
                    if(!mfile.isDirectory()){//?????????????????????
                        isDir="0";
                        fileSize=""+mfile.length();
                    }
                    backList.add(fileName+">"+fileDate+">"+fileSize+">"+isDir+">"+filePath);
                }
                if (!cmd.equals("/storage/emulated/0")) {
                    backList.add(0, ("...>0>0>1>"+Environment.getExternalStorageDirectory().getAbsolutePath()));
                    backList.add(1,("..>0>0>1>"+file.getParentFile().getAbsolutePath()));
                }
                backList.add(0, String.valueOf(listFiles.length + 2));
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            backList.add("0");
            backList.add("error");
        }
        return backList;
    }
    private void exeDownloadCmd(NetFileData netFileData) {
        /**
         * dlf:remoteFile_path_file_name ????????????
         * dlf:remoteFile_path_file_name?filePosition ????????????
         */
        String cmd = String.format("dlf:" + netFileData.getFilePath() + "/" + netFileData.getFileName());
//        String cmd = String.format("dlf:" + netFileData.getFilePath() + "/" + netFileData.getFileName() + "?" + gotFileSize);

        clientSocket.work(cmd);
//        FileDownLoadSocketThread downloadSocket = new FileDownLoadSocketThread();
    }

    public void showHotKeyDialog(NetFileData netFileData){
        CmdClientSocket cmdClientSocket = new CmdClientSocket(CmdServerIpSetting.ip, CmdServerIpSetting.port, showRemoteFileHandler);
        //showNonUiUpdateCmdHandler???????????????socket?????????????????????????????????????????????????????????????????????UI????????????????????????????????????Toast??????????????????
        new HotKeyDialog(getContext(), HotKeyGenerator.getHotkeyList(netFileData), "???????????????", cmdClientSocket).show(getParentFragmentManager());
        //HotKeyDialog?????????????????????public HotKeyDialog(Context context,ArrayList<HotKeyData> hotKeyList,String title,CmdClientSocket cmdClientSocket)
        //??????Context context????????????
        //ArrayList<HotKeyData> hotKeyList,????????????????????????????????????HotKeyData??????????????????????????????????????????????????????????????????
        //??????HotKeyData("????????????", "key:vk_alt+vk_f4")???????????????????????????"????????????"???????????????"key:vk_alt+vk_f4"??????alt+f4???????????????
        //HotKeyGenerator.getHotkeyList(netFileData)???????????????????????????netFileData??????????????????????????????????????????
        //public static ArrayList<HotKeyData> getHotkeyList(NetFileData fileData),??????fileData??????????????????????????????????????????
    }

    public ArrayList<String> HierarchicalDir(String allPath) {
        ArrayList<String> HierDir = new ArrayList<>();
        String[] split = allPath.split("/");
        for (int i = 0; i < split.length; i++) {
            System.out.println(String.format("%d's path is %s", i, split[i]));
            HierDir.add(split[i]);
        }
        return HierDir;
    }

    public String combineHierDir(ArrayList<String> strList, int ind, int tail) {
        String ret = "/";
        for (int i = ind; i < strList.size() - tail; i++) {
            System.out.println(String.format("Strlist %d str is %s", i, strList.get(i)));
            ret = ret + strList.get(i) + ((i == strList.size() - tail - 1) ? "" : "/");
        }
        return ret;
    }
    public String backDir(String _pwd) {
        String ans = combineHierDir(HierarchicalDir(_pwd), 1, 1);
        return ans;
    }

    public void exeDelCmd(NetFileData netFileData) { // cmd?????????mac???????????????
        String ret = "";
        if (netFileData.getFileType() == 1) { // ????????????
//            ret = String.format("cmd:rm -rf %s", (netFileData.getFilePath() + "/" + netFileData.getFileName()));
            ret = String.format("del:%s", (netFileData.getFilePath() + "/" + netFileData.getFileName()));
        } else { // ????????????
//            ret = String.format("cmd:rm -f %s", (netFileData.getFilePath() + "/" + netFileData.getFileName()));
            ret = String.format("del:%s", (netFileData.getFilePath() + "/" + netFileData.getFileName()));
        }
        clientSocket.work(ret);
    }
    public void exeCmd(NetFileData netFileData) {
        String cmd = "";
        if (netFileData.getFileType() == 0) {
            String oldFile = netFileData.getFilePath() + "/" + netFileData.getFileName();
            System.out.println("oldFile=== " + oldFile);
            String[] split = oldFile.split("\\.");
            String newFile = split[0];
            newFile = newFile + "(new)." + split[1];
            cmd = String.format("cmd:cp %s %s", oldFile, newFile);
            System.out.println("cmd === " + cmd);
            clientSocket.work(cmd);
        }
    }

}
