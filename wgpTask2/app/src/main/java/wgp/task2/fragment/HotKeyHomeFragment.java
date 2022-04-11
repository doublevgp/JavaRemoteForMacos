package wgp.task2.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;

import wgp.task2.Dialog.InputDialog;
import wgp.task2.MainActivity;
import wgp.task2.R;
import wgp.task2.data.FileTypeData;
import wgp.task2.data.HotKeyData;
import wgp.task2.utils.HotKeyGenerator;
import wgp.task2.view.HotKeyAdapter;
import wgp.task2.view.HotKeyFileTypeAdapter;

public class HotKeyHomeFragment extends Fragment {
    GridView gridView;
    HotKeyFileTypeAdapter adapter;
    ArrayList<FileTypeData> list;
    Button bt_back, bt_add;
    FileTypeData fileTypeData;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hotkey_home_fragment, null);
        gridView = view.findViewById(R.id.hotkey_home_gridview);
        bt_back = view.findViewById(R.id.hotkey_bt);
        bt_add = view.findViewById(R.id.hotkey_add_bt);
        showAll();
        androidx.appcompat.widget.Toolbar toolbar = view.findViewById(R.id.hotkey_home_toolbar);

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAll();
            }
        });

        return view;
    }
    private void showAll() {
        list = new ArrayList<FileTypeData>();
        list.add(new FileTypeData("default"));
        list.add(new FileTypeData("pdf"));
        list.add(new FileTypeData("doc"));
        list.add(new FileTypeData("mp3"));
        list.add(new FileTypeData("mp4"));
        list.add(new FileTypeData("jpg"));
        list.add(new FileTypeData("xls"));
        list.add(new FileTypeData("ppt"));
        list.add(new FileTypeData("exe"));
        adapter = new HotKeyFileTypeAdapter(getContext(), list);
        gridView.setAdapter(adapter);
//        gridView.setVisibility(View.VISIBLE);
        bt_back.setEnabled(false);
        bt_add.setVisibility(View.INVISIBLE);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showHotKeyMenuByFileType(list.get(position));
//                gridView.setVisibility(View.INVISIBLE);
                bt_back.setEnabled(true);
                bt_add.setVisibility(View.VISIBLE);
            }
        });
    }

    private void showHotKeyMenuByFileType(FileTypeData fileTypeData) {
        ArrayList<HotKeyData> hotkeyList = HotKeyGenerator.getHotkeyList(fileTypeData.getFiletype());
        this.fileTypeData = fileTypeData;
        if (hotkeyList != null) {
            for (HotKeyData d : hotkeyList) {
                System.out.println(d.toString());
            }
            updateHotKeyMenu(hotkeyList);
            bt_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    InputDialog dialog = new InputDialog();
                    dialog.setTitle("Add Hotkey");
                    dialog.setNum(2);
                    dialog.setType("hhh");
                    dialog.setHandler(new Handler(new Handler.Callback() {
                        @Override
                        public boolean handleMessage(@NonNull Message msg) {
                            Bundle bundle = msg.getData();
                            String name = bundle.getString(InputDialog.KEY_NAME);
                            String content = bundle.getString(InputDialog.KEY_CONTENT);
                            ArrayList<HotKeyData> hotKeyDataList = HotKeyGenerator.hotKeyDataHashMap.get(fileTypeData.getFiletype());
                            hotKeyDataList.add(new HotKeyData(name, content));
                            HotKeyGenerator.hotKeyDataHashMap.replace(fileTypeData.getFiletype(), hotKeyDataList);
                            showToast("add ok");
//                            showHotKeyMenuByFileType(fileTypeData);
                            updateHotKeyMenu(hotKeyDataList);
                            return false;
                        }
                    }));
                    dialog.show(getFragmentManager());
                }
            });
        } else {
            showToast("没有对应文件类型的热键菜单");
        }
    }

    public void showToast(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }

    private void updateHotKeyMenu(ArrayList<HotKeyData> hotkeyList) {
        HotKeyAdapter hotKeyAdapter = new HotKeyAdapter(getContext(), hotkeyList);
        gridView.setAdapter(hotKeyAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InputDialog dialog = new InputDialog();
                dialog.setTitle("watch Hotkey");
                dialog.setNum(2);
                dialog.setType("wtc");
                dialog.setKeyname(hotkeyList.get(position).getHotKeyName());
                dialog.setKeycontent(hotkeyList.get(position).getHotKeyContent());
                dialog.setHandler(new Handler(new Handler.Callback() {
                    @Override
                    public boolean handleMessage(@NonNull Message msg) {
                        Bundle bundle = msg.getData();
                        if (bundle.getBoolean("want_delete") == false) {
                            String name = bundle.getString(InputDialog.KEY_NAME);
                            String content = bundle.getString(InputDialog.KEY_CONTENT);
                            ArrayList<HotKeyData> hotKeyDataList = HotKeyGenerator.hotKeyDataHashMap.get(fileTypeData.getFiletype());
                            hotKeyDataList.set(position, new HotKeyData(name, content));
//                        hotKeyDataList.add(new HotKeyData(name, content));
                            HotKeyGenerator.hotKeyDataHashMap.replace(fileTypeData.getFiletype(), hotKeyDataList);
                            showToast("修改成功");
                        } else {
                            ArrayList<HotKeyData> hotKeyDataList = HotKeyGenerator.hotKeyDataHashMap.get(fileTypeData.getFiletype());
                            hotKeyDataList.remove(position);
//                        hotKeyDataList.add(new HotKeyData(name, content));
                            HotKeyGenerator.hotKeyDataHashMap.replace(fileTypeData.getFiletype(), hotKeyDataList);
                            showToast("删除成功");
                            updateHotKeyMenu(hotKeyDataList);
                        }
                        return false;
                    }
                }));
                dialog.show(getFragmentManager());
                showToast("暂不支持修改");
            }
        });
    }
}
