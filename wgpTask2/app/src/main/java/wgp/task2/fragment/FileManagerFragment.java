package wgp.task2.fragment;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import wgp.task2.Dialog.FileProgressDialog;
import wgp.task2.Dialog.HotKeyDialog;
import wgp.task2.Dialog.InputDialog;
import wgp.task2.MainActivity;
import wgp.task2.R;
import wgp.task2.data.CmdServerIpSetting;
import wgp.task2.data.DlfData;
import wgp.task2.data.LinkData;
import wgp.task2.data.NetFileData;
import wgp.task2.db.RemoteDataBase;
import wgp.task2.operator.ShowRemoteFileHandler;
import wgp.task2.socket.CmdClientSocket;
import wgp.task2.socket.FileDownLoadSocketThread;
import wgp.task2.utils.CheckLocalDownloadFolder;
import wgp.task2.utils.HotKeyGenerator;
import wgp.task2.view.MyListView;
import wgp.task2.view.RecyclerAdapter;

public class FileManagerFragment extends Fragment implements InputDialog.Callback {
    MyListView listView;
    ShowRemoteFileHandler showRemoteFileHandler;
    CmdClientSocket clientSocket;
    RemoteDataBase remoteDataBase;
    String link_name;
    public static String link_ip = CmdServerIpSetting.ip;
    public static int link_port = CmdServerIpSetting.port;
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
        View view = getLayoutInflater().inflate(R.layout.connect_fragment, null);
        listView = view.findViewById(R.id.listView);
        et_ip = (EditText) view.findViewById(R.id.et_ip);
        et_port= (EditText) view.findViewById(R.id.et_port);
        et_cmd = (EditText) view.findViewById(R.id.et_cmd);
        et_ip.setText(link_ip);
        et_port.setText(String.valueOf(link_port));
        showRemoteFileHandler = new ShowRemoteFileHandler(getContext(), listView);
        clientSocket = new CmdClientSocket(CmdServerIpSetting.ip, CmdServerIpSetting.port, showRemoteFileHandler);
        MainActivity.cmdClientSocket = clientSocket;
        showRemoteFileHandler.setClientSocket(clientSocket);
        registerForContextMenu(listView);
        recyclerView = view.findViewById(R.id.connect_recyclerview);
        //?????????????????????LayoutManager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//????????????????????????item????????????????????????????????????????????????????????????
        recyclerView.setHasFixedSize(true);
        mAdapter = new RecyclerAdapter(new String[]{"/"});
        recyclerView.setAdapter(mAdapter);
        remoteDataBase = new RemoteDataBase(getActivity());
        remoteDataBase.open();
        view.findViewById(R.id.connect_deal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clientSocket.setIpAndPort(et_ip.getText().toString().trim(), Integer.parseInt(et_port.getText().toString()));
//                clientSocket.work(et_cmd.getText().toString().trim());
                doSocketWork(et_cmd.getText().toString().trim());
            }
        });
        view.findViewById(R.id.connect_add_usual).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                link_ip = et_ip.getText().toString().trim();
                link_port = Integer.parseInt(et_port.getText().toString());
                InputDialog inputDialog = new InputDialog();
                inputDialog.setType("get");
                inputDialog.show(getFragmentManager());
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NetFileData netFileData = (NetFileData) listView.getAdapter().getItem(position);
                System.out.println("fileName == " + netFileData.getFileName());
                String filepath = netFileData.getFilePath();
                String filename = netFileData.getFileName();
                if (filename.equals("...")) {
                    String newCmd = String.format("dir:/");
                    System.out.println("newCMMMMMD is " + newCmd);
                    et_cmd.setText(newCmd);
//                    clientSocket.work(newCmd);
                    doSocketWork(newCmd);
                } else if (filename.equals("..")) {
                    String newCmd = String.format("dir:%s", filepath);
                    System.out.println("newCMMMMMD is " + newCmd);
                    et_cmd.setText(newCmd);
//                    clientSocket.work(newCmd);
                    doSocketWork(newCmd);
                } else {
                    if (netFileData.getFileType() == 1) { // ????????????
                        String newCmd = "";
                        if (filepath == "/") {
                            newCmd = String.format("dir:/%s", filepath, filename);
                        } else {
                            newCmd = String.format("dir:%s/%s", filepath, filename);
                        }
                        System.out.println("newCmd is " + newCmd);
//                        clientSocket.work(newCmd);
                        doSocketWork(newCmd);
                        et_cmd.setText(newCmd);
                    } else { // ????????????
                        String newCmd = "";
                        if (filepath == "/") {
                            newCmd = String.format("opn:/%s", filepath, filename);
                        } else {
                            newCmd = String.format("opn:%s/%s", filepath, filename);
                        }
                        System.out.println("OPN newCmd is " + newCmd);
                        clientSocket.work(newCmd);
                    }
                }
            }
        });
        return view;
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
                    dirord = "dir:/";
                    dirdatas.clear();
                    dirdatas.add("...");
                } else {
                    System.out.println(postion + " " + mAdapter.datas[postion]);
                    dirord = "dir:";
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
                doSocketWork(dirord);
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
        getActivity().getMenuInflater().inflate(R.menu.list_view_ctx_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        NetFileData netFileData = (NetFileData) listView.getAdapter().getItem(menuInfo.position);

        switch (item.getItemId()) {
            case R.id.ctx_hotkey:
                showHotKeyDialog(netFileData);
                break;
            case R.id.ctx_download:
                if (netFileData.getFileType() == 0) {
                    exeDownloadCmd(netFileData);
                } else {
                    Toast.makeText(getContext(), "???????????????????????????????????????", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ctx_add_list:
                if (netFileData.getFileType() == 0) {
                    exeAddDlfListCmd(netFileData);
                } else {
                    Toast.makeText(getContext(), "???????????????????????????????????????", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ctx_finder:
                if (netFileData.getFileType() == 1)
                    clientSocket.work(String.format("opn:" + netFileData.getFilePath() + "/" + netFileData.getFileName()));
                else
                    clientSocket.work(String.format("opn:" + netFileData.getFilePath()));
                break;
            case R.id.ctx_delete:
                exeDelCmd(netFileData);
                break;
            case R.id.ctx_copy:
                exeCmd(netFileData);
                break;
        }
        return super.onContextItemSelected(item);
    }
    private void exeAddDlfListCmd(NetFileData netFileData) {
        /**
         * dlf:remoteFile_path_file_name ????????????
         * dlf:remoteFile_path_file_name?filePosition ????????????
         */
        File file = new File(CheckLocalDownloadFolder.check() + "/" + netFileData.getFileName());
        String cmd = "";
        if (file.exists()) {
            long filepos = file.length();
            cmd = String.format("dlf:" + netFileData.getFilePath() + "/" + netFileData.getFileName() + "?" + filepos);
        } else {
            cmd = String.format("dlf:" + netFileData.getFilePath() + "/" + netFileData.getFileName());
        }
        showRemoteFileHandler.setFileCallback(new ShowRemoteFileHandler.FileCallback() {
            @Override
            public void onDlfCallBack(String fileName, String filePath, long fileSize, long downSize, String ip, int port) {
                DlfData data = new DlfData(fileName, filePath, fileSize, downSize, ip, port);
                if (DownloadListFragment.FileExistListMap.get(fileName) == null) {
                    DownloadListFragment.FileExistListMap.put(fileName, true);
                    DownloadListFragment.dlfDataList.add(data);
                }
            }
        });
        clientSocket.work(cmd);
    }
    private void exeDownloadCmd(NetFileData netFileData) {
        /**
         * dlf:remoteFile_path_file_name ????????????
         * dlf:remoteFile_path_file_name?filePosition ????????????
         */
        File file = new File(CheckLocalDownloadFolder.check() + "/" + netFileData.getFileName());
        String cmd = "";
        if (file.exists()) {
            long filepos = file.length();
            cmd = String.format("dlf:" + netFileData.getFilePath() + "/" + netFileData.getFileName() + "?" + filepos);
        } else {
            cmd = String.format("dlf:" + netFileData.getFilePath() + "/" + netFileData.getFileName());
        }
        showRemoteFileHandler.setFileCallback(new ShowRemoteFileHandler.FileCallback() {
            @Override
            public void onDlfCallBack(String fileName, String filePath, long fileSize, long downSize, String ip, int port) {
                DlfData data = new DlfData(fileName, filePath, fileSize, downSize, ip, port);
//                if (!DownloadListFragment.FileExistListMap.get(fileName)) {
//                    DownloadListFragment.dlfDataList.add(data);
//                }
                BeginDownLoad(data);
            }
        });
        clientSocket.work(cmd);
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

    @Override
    public void onWebClick(String cpsString) {
        String cmd = String.format("cmd:open %s", cpsString);
        System.out.println("DefinedCMD is " + cmd);
        clientSocket.work(cmd);
    }

    @Override
    public void onCpsClick(String cpsString) {
        String cmd = String.format("cps:%s", cpsString);
        System.out.println("DefinedCMD is " + cmd);
        clientSocket.work(cmd);
    }

    @Override
    public void onDefaultClick(String str) {
//        link_name = str;
    }
    void BeginDownLoad(final DlfData downloadFile){
        File file = new File(downloadFile.getFile_path());

        FileProgressDialog progressDialog=new FileProgressDialog(getActivity(),file,downloadFile);
        final DlfData finalDownloadFile = downloadFile;
        progressDialog.showDialog(new FileProgressDialog.OnDialogSubmitListener() {
            @Override
            public void onSubmit(long CounterSize) {
                if(CounterSize==downloadFile.getFile_size()){
                    Toast.makeText(getContext(),"???????????????"+downloadFile.getFile_path(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

//    @Override
//    public void onDlfCallBack(String ip, int port) {
//        DlfData data = new DlfData(ip, port);
//        BeginDownLoad(data);
//    }
}
