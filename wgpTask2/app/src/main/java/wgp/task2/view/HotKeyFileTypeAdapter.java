package wgp.task2.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Locale;

import wgp.task2.R;
import wgp.task2.data.FileTypeData;
import wgp.task2.utils.FileIconUtil;

public class HotKeyFileTypeAdapter extends ArrayAdapter<FileTypeData> {
    Context context;
    ArrayList<FileTypeData> list;
    public HotKeyFileTypeAdapter(@NonNull Context context, ArrayList<FileTypeData> list) {
        super(context, android.R.layout.simple_list_item_1, list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = View.inflate(getContext(), R.layout.hotkey_home_grid_row_view, null);
        FileTypeData data = list.get(position);
        ImageView iv = view.findViewById(R.id.hotkey_home_grid_row_view_iv);
        TextView tv = view.findViewById(R.id.hotkey_home_grid_row_view_tv);
        iv.setImageResource(FileIconUtil.ChoseFileIconByFileSuffixName(data.getFiletype()));
        tv.setText(data.getFiletype().toUpperCase(Locale.ROOT));
        return view;
    }
}
