package thread;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class FileDownLoadSocketThread extends FileSocket {
    public FileDownLoadSocketThread(File file,long filePos) {
        super(file, filePos);
    }
    public final void exe() throws IOException {
        byte[] arrayOfByte = new byte[8192];
        Socket localSocket = this.serverSocket.accept();
        FileInputStream localFileInputStream = new FileInputStream(this.file);
        if (this.fileSize < this.file.length()) {
            localFileInputStream.skip(this.fileSize);
            BufferedInputStream localBufferedInputStream = new BufferedInputStream(localFileInputStream);
            OutputStream localOutputStream = localSocket.getOutputStream();
            BufferedOutputStream localBufferedOutputStream = new BufferedOutputStream(localOutputStream);
            while (true) {
                int i;
                if (((i = localBufferedInputStream.read(arrayOfByte)) != -1) && (localSocket != null) && (!localSocket.isClosed()))
                    try {
                        localBufferedOutputStream.write(arrayOfByte, 0, i);
                        localBufferedOutputStream.flush();
                    }
                    catch (SocketException localSocketException) {
                        System.out.println(localSocketException.getMessage());
                        break;
                    }
            }
            localBufferedInputStream.close();
            localFileInputStream.close();
            localBufferedOutputStream.close();
            localOutputStream.close();
            close();
            System.out.println(this.file.getName() + " download finished!");
            return;
        }
        localFileInputStream.close();
        localSocket.close();
        close();
        System.out.println("pos>file length!");
    }

}