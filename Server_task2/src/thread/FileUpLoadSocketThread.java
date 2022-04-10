package thread;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public final class FileUpLoadSocketThread extends FileSocket
{
    public FileUpLoadSocketThread(File paramFile, long paramLong)
    {
        super(paramFile, paramLong);
    }

    public final void exe() throws IOException {
        byte[] arrayOfByte = new byte[8192];
        Socket localSocket;
        InputStream localInputStream = (localSocket = this.serverSocket.accept()).getInputStream();
        BufferedInputStream localBufferedInputStream = new BufferedInputStream(localInputStream);
        RandomAccessFile localRandomAccessFile;
        (localRandomAccessFile = new RandomAccessFile(this.file, "rw")).seek(this.fileSize);
        int i;
        while ((i = localBufferedInputStream.read(arrayOfByte)) != -1)
            localRandomAccessFile.write(arrayOfByte, 0, i);
        localRandomAccessFile.close();
        localBufferedInputStream.close();
        localInputStream.close();
        localSocket.close();
        close();
        System.out.println(this.file.getPath() + " upload finished!");
    }
}