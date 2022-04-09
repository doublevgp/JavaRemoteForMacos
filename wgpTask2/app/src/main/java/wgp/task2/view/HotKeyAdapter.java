package wgp.task2.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import wgp.task2.R;
import wgp.task2.data.HotKeyData;

import java.util.ArrayList;

public class HotKeyAdapter extends ArrayAdapter<HotKeyData> {
    Context context;
    ArrayList<HotKeyData> list;
    public HotKeyAdapter(@NonNull Context context, ArrayList<HotKeyData> lst) {
        super(context, android.R.layout.simple_list_item_1, lst);
        this.context = context;
        this.list = lst;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = View.inflate(context, R.layout.dialog_row_view, null);
        TextView tv = v.findViewById(R.id.dialog_tv_hotkeyName);
        tv.setText(list.get(position).getHotKeyName());
        return v;
    }
}
