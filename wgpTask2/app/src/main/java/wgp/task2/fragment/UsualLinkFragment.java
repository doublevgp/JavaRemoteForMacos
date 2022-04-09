package wgp.task2.fragment;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import wgp.task2.R;
import wgp.task2.data.LinkData;
import wgp.task2.db.RemoteDataBase;
import wgp.task2.view.LinkDataAdapter;

public class UsualLinkFragment extends Fragment {
    ListView listView;
    LinkDataAdapter adapter;
    RemoteDataBase remoteDataBase;
    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<LinkData> list;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.usuallink_fragment, null);
        listView = view.findViewById(R.id.usuallink_list_view);
        registerForContextMenu(listView);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);

        remoteDataBase = new RemoteDataBase(getActivity());
        remoteDataBase.open();
        list = remoteDataBase.getLinkDataList();
        adapter = new LinkDataAdapter(getContext(), list);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateList();
            }
        });
    }
    public void updateList() {
        changeAdapterDataSet();
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    private void changeAdapterDataSet() {
        list.clear();
        list.addAll(remoteDataBase.getLinkDataList());
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).toString());
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.link_list_ctx_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        LinkData data = list.get(menuInfo.position);
        switch (item.getItemId()) {
            case R.id.link_ctx_modify:
                /**
                 * Dialog弹窗修改连接信息 name address
                 */
                break;
            case R.id.link_ctx_delete:
                if (remoteDataBase.deleteLinkData(data) == 1) {
                    list.remove(menuInfo.position);
                    adapter.notifyDataSetChanged();
                }
                break;
        }
        return super.onContextItemSelected(item);
    }
}
