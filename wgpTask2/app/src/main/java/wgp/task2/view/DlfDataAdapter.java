package wgp.task2.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

import wgp.task2.R;
import wgp.task2.data.CmdServerIpSetting;
import wgp.task2.data.DlfData;
import wgp.task2.socket.FileDownLoadSocketThread;
import wgp.task2.utils.FileIconUtil;

public class DlfDataAdapter extends ArrayAdapter<DlfData> {
    Context context;
    ArrayList<DlfData> dlfDataList;
    public DoneCallback doneCallback;

    public DoneCallback getDoneCallback() {
        return doneCallback;
    }

    public void setDoneCallback(DoneCallback doneCallback) {
        this.doneCallback = doneCallback;
    }

    public interface DoneCallback {
        void onDoneCallback(int pos);
    }
    public DlfDataAdapter(Context context, ArrayList<DlfData> dlfDataList) {
        super(context, android.R.layout.simple_list_item_1, dlfDataList);
        this.context = context;
        this.dlfDataList = dlfDataList;
//        this.doneCallback = (DoneCallback) context;
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

        final ProgressBar bar = view.findViewById(R.id.dlf_row_view_progressbar);

        bar.setMax(100);
        bar.setProgress((int) (dlfData.getFile_down_size() * 100 / dlfData.getFile_size()) );
        System.out.println(dlfData.getFile_down_size() + " / " + dlfData.getFile_size());
        final ImageView play = view.findViewById(R.id.dlf_row_view_bt);
        play.setFocusable(true);
        final long[] Counter = new long[1];
        Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                Bundle data = msg.getData();
                Counter[0] = data.getLong(FileDownLoadSocketThread.KEY_SENT_PRO);
                // 要知道 下载了多少更新进度条
                bar.setProgress((int) (Counter[0] *100/dlfData.getFile_size()) );
                System.out.println("这边收到 "+(int) Counter[0] +" -- 总共 "+dlfData.getFile_size());
                if(msg.what == 1){ //下载好后停两秒 自动关闭窗口
                    play.setImageResource(R.drawable.play);
//                    dlfDataList.remove(position);
                    doneCallback.onDoneCallback(position);
//                    l.onSubmit(fileData.getFile_size()); // 假装下载满了
//                    bar.setProgress(100);
//                    Timer timer=new Timer();
//                    TimerTask task=new TimerTask() {
//                        @Override
//                        public void run() {
//                            dialog.dismiss();
//                        }
//                    };
//                    timer.schedule(task,1000);
                }
                return false;
            }
        });
        AtomicBoolean firstTime = new AtomicBoolean(true);

        final FileDownLoadSocketThread fileDownLoadSocketThread= new FileDownLoadSocketThread(CmdServerIpSetting.ip,
                dlfData.getPort(), handler, new File(dlfData.getFile_path()), dlfData.getFile_size());
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(firstTime.get()){
                    firstTime.set(false);
                    System.out.println("新创建thread文件socket："+dlfData.getPort());
                    fileDownLoadSocketThread.start();
                }
                fileDownLoadSocketThread.ChangeRunningStatus();
                if (fileDownLoadSocketThread.getStatus()) {
                    play.setImageResource(R.drawable.pause);
                } else {
                    play.setImageResource(R.drawable.play);
                }
            }
        });
        return view;
    }
}
