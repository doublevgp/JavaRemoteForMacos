package wgp.task2.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;

import wgp.task2.MainActivity;
import wgp.task2.R;
import wgp.task2.data.FileTypeData;
import wgp.task2.view.HotKeyFileTypeAdapter;

public class HotKeyHomeFragment extends Fragment {
    GridView gridView;
    HotKeyFileTypeAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hotkey_home_fragment, null);
        gridView = view.findViewById(R.id.hotkey_home_gridview);
        ArrayList<FileTypeData> list = new ArrayList<FileTypeData>();
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
        androidx.appcompat.widget.Toolbar toolbar = view.findViewById(R.id.hotkey_home_toolbar);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showHotKeyMenuByFileType(list.get(position));
            }
        });
        return view;
    }

    private void showHotKeyMenuByFileType(FileTypeData fileTypeData) {
        Fragment singleHotKeyFragment = new SingleHotKeyFragment();

    }
}
