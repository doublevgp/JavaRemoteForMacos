package wgp.task2.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import wgp.task2.R;
import wgp.task2.data.LinkData;

public class LinkDataAdapter extends ArrayAdapter<LinkData> {
    Context context;
    ArrayList<LinkData> list;
    ClickCallBack clickCallBackListener;
    public LinkDataAdapter(@NonNull Context context, ArrayList<LinkData> list) {
        super(context, android.R.layout.simple_list_item_1, list);
        this.context = context;
        this.list = list;
        clickCallBackListener = (ClickCallBack) context;
    }
    public interface ClickCallBack {
        void onUsualLinkClick(LinkData linkData);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = View.inflate(context, R.layout.usuallink_row_view, null);
        TextView tv_name = view.findViewById(R.id.usuallink_tv_name);
        TextView tv_address = view.findViewById(R.id.usuallink_tv_address);
        LinkData linkData = list.get(position);
        tv_name.setText(linkData.getLinkName());
        tv_address.setText(String.format("%s:%s", linkData.getLinkIp(), linkData.getLinkPort()));
        Button button = view.findViewById(R.id.usuallink_bt);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickCallBackListener != null) {
                    clickCallBackListener.onUsualLinkClick(linkData);
                }
            }
        });
        return view;
    }

}
