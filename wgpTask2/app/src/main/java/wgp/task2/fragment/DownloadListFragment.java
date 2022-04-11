package wgp.task2.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;

import wgp.task2.R;
import wgp.task2.data.DlfData;
import wgp.task2.view.DlfDataAdapter;
import wgp.task2.view.MyListView;

public class DownloadListFragment extends Fragment {
    public static ArrayList<DlfData> dlfDataList = new ArrayList<>();
    public static HashMap<String, Boolean> FileExistListMap = new HashMap<>();
    DlfDataAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.downloadlist_fragment, null);
        MyListView listView = view.findViewById(R.id.download_list_view);
//        ArrayList<DlfData> dlfDataList = new ArrayList<>();
        adapter = new DlfDataAdapter(getContext(), dlfDataList);
        DlfDataAdapter.DoneCallback doneCallback = new DlfDataAdapter.DoneCallback() {
            @Override
            public void onDoneCallback(int pos) {
                dlfDataList.remove(pos);
                adapter.notifyDataSetChanged();
            }
        };
        adapter.setDoneCallback(doneCallback);
        listView.setAdapter(adapter);
        return view;
    }


}
