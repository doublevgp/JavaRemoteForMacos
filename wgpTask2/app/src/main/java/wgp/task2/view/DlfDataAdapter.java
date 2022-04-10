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

import wgp.task2.R;
import wgp.task2.data.DlfData;
import wgp.task2.utils.FileIconUtil;

public class DlfDataAdapter extends ArrayAdapter<DlfData> {
    Context context;
    ArrayList<DlfData> dlfDataList;
    public DlfDataAdapter(Context context, ArrayList<DlfData> dlfDataList) {
        super(context, android.R.layout.simple_list_item_1, dlfDataList);
        this.context = context;
        this.dlfDataList = dlfDataList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = View.inflate(context, R.layout.dlf_row_view, null);
        ImageView iv = view.findViewById(R.id.dlf_row_view_iv);
        TextView file_name = view.findViewById(R.id.dlf_row_view_file_name);
        TextView file_size = view.findViewById(R.id.dlf_row_view_file_size);
        TextView file_path = view.findViewById(R.id.dlf_row_view_file_path);
        DlfData dlfData = dlfDataList.get(position);
        iv.setImageResource(FileIconUtil.ChoseFileIconByFileSuffixName(dlfData.getSuffixName()));
        file_name.setText(dlfData.getFile_name());
        file_size.setText(String.valueOf(dlfData.getFileSize()));
        file_path.setText(dlfData.getFile_path());

        return view;
    }
}
