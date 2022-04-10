package wgp.task2.socket;

import android.os.Handler;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.Socket;
import java.net.SocketException;

public class FileUpLoadSocketThread extends FileSocket {
    public interface DoneCallback {
        void onDoneCallback(int port);
    }
    DoneCallback doneCallback;
    Handler handler;
    String ip;
    int port;
    File file;
    long fileSize;
    long uploadFileSize = 0;
    public FileUpLoadSocketThread(String ip, int port, Handler handler, File file, long fileSize) {
        super(file, fileSize);
        this.ip = ip;
        this.port = port;
//        this.handler = handler;
        this.file = file;
        this.fileSize = fileSize;
    }

    public DoneCallback getDoneCallback() {
        return doneCallback;
    }

    public void setDoneCallback(DoneCallback doneCallback) {
        this.doneCallback = doneCallback;
    }

    @Override
    public void task() throws IOException {
        System.out.println("本次上传连接的port:" + port);
        byte[] arrayOfByte = new byte[8192];
        Socket localSocket = this.serverSocket.accept();
        FileInputStream localFileInputStream = new FileInputStream(this.file);
        if (this.fileSize < this.file.length())
        {
            localFileInputStream.skip(this.fileSize);
            BufferedInputStream localBufferedInputStream = new BufferedInputStream(localFileInputStream);
            OutputStream localOutputStream = localSocket.getOutputStream();
            BufferedOutputStream localBufferedOutputStream = new BufferedOutputStream(localOutputStream);
            while (true)
            {
                int i;
                if (((i = localBufferedInputStream.read(arrayOfByte)) != -1) && (localSocket != null) && (!localSocket.isClosed()))
                    try {
                        localBufferedOutputStream.write(arrayOfByte, 0, i);
                        localBufferedOutputStream.flush();
                    }
                    catch (SocketException localSocketException) {
                        break;
                    }
            }
            localBufferedInputStream.close();
            localFileInputStream.close();
            localBufferedOutputStream.close();
            localOutputStream.close();
            close();
            System.out.println(this.file.getName() + " upload finished!");
            return;
        }
        localFileInputStream.close();
        localSocket.close();
        close();
        System.out.println("pos>file length!");
    }

}
