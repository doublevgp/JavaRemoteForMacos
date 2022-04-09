package wgp.task2.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import wgp.task2.Dialog.HotKeyDialog;
import wgp.task2.Dialog.InputDialog;
import wgp.task2.R;
import wgp.task2.data.CmdServerIpSetting;
import wgp.task2.data.NetFileData;
import wgp.task2.operator.ShowRemoteFileHandler;
import wgp.task2.socket.CmdClientSocket;
import wgp.task2.utils.HotKeyGenerator;
import wgp.task2.view.MyListView;

public class HomeFragment extends Fragment  {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, null);
        return view;
    }
}
