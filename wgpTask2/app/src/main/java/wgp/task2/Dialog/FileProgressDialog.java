package wgp.task2.Dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

import wgp.task2.R;
import wgp.task2.data.CmdServerIpSetting;
import wgp.task2.data.DlfData;
import wgp.task2.socket.FileDownLoadSocketThread;
import wgp.task2.utils.FileIconUtil;

public class FileProgressDialog {
    AlertDialog dialog;
    public static Handler handler;
    Context context;
    private String title="下载进度";
    File file;
    DlfData fileData;

    long Counter;

    public interface OnDialogSubmitListener{
        public void onSubmit(long CounterSize);
    }

    public FileProgressDialog(Context context,File file, DlfData fileData){
        this.context=context;
        this.file=file;
        this.fileData=fileData;
//        this.Counter=fileData.getFile_down_size();
    }

    public void showDialog(final OnDialogSubmitListener l){
        final AlertDialog.Builder bl = new AlertDialog.Builder(context);
        bl.setTitle(title);
        final AtomicBoolean firstTime=new AtomicBoolean(true);

        View v= LayoutInflater.from(context).inflate(R.layout.dlf_row_view,null,false);
        final TextView fileinfo=v.findViewById(R.id.dlf_row_view_file_name);
        final TextView fileSize=v.findViewById(R.id.dlf_row_view_file_size);
        final TextView filePath=v.findViewById(R.id.dlf_row_view_file_path);
        long sz = fileData.getFile_size();
        String szstr = "";
        if (sz > 800) {
            szstr = sz / 1024 + "KB";
            if ((sz / 1024) > 800) {
                Double fileMB = Double.parseDouble(String.valueOf(sz / 1024.0 / 1024.0));
                if (fileMB > 800) {
                    Double fileGB = Double.parseDouble(String.valueOf(fileMB / 1024.0));
                    szstr = String.format("%.2f GB", fileGB);
                } else {
                    szstr = String.format("%.2f MB", fileMB);
                }
            }
        } else {
            szstr = sz + " B";
        }
        fileSize.setText(szstr);
        filePath.setText(fileData.getFile_path() + "/" + fileData.getFile_name());
        fileinfo.setText(fileData.getFile_name());
        ImageView icon = v.findViewById(R.id.dlf_row_view_iv);
        icon.setImageResource(FileIconUtil.ChoseFileIconByFileSuffixName(fileData.getSuffixName()));
        final ProgressBar bar=v.findViewById(R.id.dlf_row_view_progressbar);
        bar.setMax(100);
        bar.setProgress((int) (fileData.getFile_down_size() * 100 / fileData.getFile_size()) );
        System.out.println(fileData.getFile_down_size() + " / " + fileData.getFile_size());
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                Bundle data = msg.getData();
                Counter = data.getLong(FileDownLoadSocketThread.KEY_SENT_PRO);
                // 要知道 下载了多少更新进度条
                bar.setProgress((int) (Counter*100/fileData.getFile_size()) );
                System.out.println("这边收到 "+(int) Counter+" -- 总共 "+fileData.getFile_size());
                if(msg.what == 1){ //下载好后停两秒 自动关闭窗口
                    l.onSubmit(fileData.getFile_size()); // 假装下载满了
                    bar.setProgress(100);
                    Timer timer=new Timer();
                    TimerTask task=new TimerTask() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                        }
                    };
                    timer.schedule(task,1000);
                }
                return false;
            }
        });

        final ImageView iv = v.findViewById(R.id.dlf_row_view_bt);
        final FileDownLoadSocketThread fileDownLoadSocketThread= new FileDownLoadSocketThread(CmdServerIpSetting.ip,
                fileData.getPort(), handler, file, fileData.getFile_size());
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(firstTime.get()){
                    firstTime.set(false);
                    System.out.println("新创建thread文件socket："+fileData.getPort());
                    fileDownLoadSocketThread.start();
                }
                fileDownLoadSocketThread.ChangeRunningStatus();
                if (fileDownLoadSocketThread.getStatus()) {
                    iv.setImageResource(R.drawable.pause);
                } else {
                    iv.setImageResource(R.drawable.play);
                }
            }
        });
        bl.setView(v);
        bl.setPositiveButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                File file=new File(fileData.getFile_path());
                long countersize=file.length();
                System.out.println("文件真实下载量: "+countersize);
                fileDownLoadSocketThread.close(); // 回收
                // 这两句好像没什么用
                handler.removeCallbacks(fileDownLoadSocketThread);
                handler.removeCallbacksAndMessages(fileDownLoadSocketThread);
                l.onSubmit(countersize); //传给Main 修改下数据库中的下载量
            }
        });
        dialog = bl.show();
    }
}