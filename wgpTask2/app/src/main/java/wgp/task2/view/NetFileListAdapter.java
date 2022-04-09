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
import wgp.task2.data.NetFileData;
import wgp.task2.utils.FileIconUtil;

public class NetFileListAdapter extends ArrayAdapter<NetFileData> {
    Context context;
    ArrayList<NetFileData> netFileList;
    public NetFileListAdapter(Context context, ArrayList<NetFileData> netFileList) {
        super(context, android.R.layout.simple_list_item_1, netFileList);
        this.context = context;
        this.netFileList = netFileList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = View.inflate(context, R.layout.list_row_view, null);
        NetFileData data = netFileList.get(position);
        TextView tv_filename = v.findViewById(R.id.tv_filename);
        TextView tv_modifydate = v.findViewById(R.id.tv_modifydate);
        TextView tv_memory = v.findViewById(R.id.tv_memory);
        ImageView iv_file = v.findViewById(R.id.iv_file);
        String file_name = data.getFileName();
        if (data.getFileType() == 1) {
            iv_file.setImageResource(R.drawable.directory);
            tv_memory.setText("");
        } else {
            String[] split = file_name.split("\\.");//要使用\\转义符
            String suffixName = split[split.length - 1];
            iv_file.setImageResource(FileIconUtil.ChoseFileIconByFileSuffixName(suffixName));
            tv_memory.setText(data.getFileSizeStr());
        }
        tv_filename.setText(file_name);
        tv_modifydate.setText(data.getFileModifiedDate());
        return v;
    }
}
