package wgp.task2.socket;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

import wgp.task2.MainActivity;
import wgp.task2.data.CmdServerIpSetting;
import wgp.task2.db.RemoteDataBase;

public class FileDownLoadSocketThread extends FileSocket {
    public static String KEY_SENT_PRO = "sent_pro";
    public interface DoneCallback {
        void onDoneCallback(int port);
    }
    DoneCallback doneCallback;
    Handler handler;
    String ip;
    int port;
    File file;
    long fileSize;
    long downLoadFileSize;
    AtomicBoolean isRunning;
    AtomicBoolean isValid;
    public boolean getStatus() {
        return isRunning.get();
    }

    public FileDownLoadSocketThread(String ip, int port, Handler handler, File file, long fileSize) {
        super(file, fileSize);
        this.ip = ip;
        this.port = port;
        this.handler = handler;
        this.file = file;
        this.fileSize = fileSize;
        this.downLoadFileSize = new File(file.getPath()).length();
        isValid=new AtomicBoolean(true);
        isRunning=new AtomicBoolean(false);
    }

    public DoneCallback getDoneCallback() {
        return doneCallback;
    }

    public void setDoneCallback(DoneCallback doneCallback) {
        this.doneCallback = doneCallback;
    }

    @Override
    public void task() {
        try {
            Timer timer1 = new Timer();
            TimerTask task1 = new TimerTask() {  // 这个task每0.2秒通过handler 向dialog发送下载量
                @Override
                public void run() {
                    if (isRunning.get()) {
                        sendCounter(0);
                    }
                }
            };
            timer1.schedule(task1, 0, 200);  //  延迟0秒进行，每0.2秒执行 task1任务

            Socket socket = new Socket(ip, port);
            System.out.println("本次下载连接的port:" + port);
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            RandomAccessFile randomAccessFile = new RandomAccessFile(this.file.getPath(), "rw");
            randomAccessFile.seek(downLoadFileSize); //跳过已经存在的量

            DataInputStream dis = new DataInputStream(
                    new BufferedInputStream(in));

            System.out.println("开始下载 " + this.fileSize);
            byte[] buffer = new byte[8192];
            while (isValid.get()) {
                if (isRunning.get()) {
                    if (downLoadFileSize >= this.fileSize) {
                        isValid.set(false);
                        break;
                    }
                    int read = 0;
                    if (dis != null) {
                        try {
                            read = dis.read(buffer); // 程序会在这不执行到cancel 不知道什么原因
                        } catch (IOException e) {
                            timer1.cancel();
                            System.out.println(e.getMessage());
                        }
                    }
                    System.out.println("read == ?? " + read);
                    System.out.println("filesize == " + fileSize);
                    System.out.println("downLoadFileSize == " + downLoadFileSize);
                    if (read == -1 || downLoadFileSize >= this.fileSize) {

                        isValid.set(false);
                        System.out.println(isValid.toString());
                        break;
                    }
                    randomAccessFile.write(buffer, 0, read);
                    downLoadFileSize += read;
                    // System.out.println("累计下载了 " + downLoadFileSize);
                }
            }
            timer1.cancel();
            System.out.println("文件下载完成: " + port);
            System.out.println("send222");
            sendCounter(1);
            dis.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendCounter(int type) { // 0：下载中  1 done
        Message mes = handler.obtainMessage();
        mes.what=type;
        Bundle bundle=new Bundle();
//        System.out.println(getClass().getCanonicalName() + " getpath === " + this.file.getPath());
        downLoadFileSize = new File(this.file.getPath()).length();
        bundle.putLong(KEY_SENT_PRO, downLoadFileSize);
        mes.setData(bundle);
        handler.sendMessage(mes);
    }

    public void ChangeRunningStatus() {
        if (isRunning.get()) isRunning.set(false);
        else isRunning.set(true);
    }
}
