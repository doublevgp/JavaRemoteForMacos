package thread;

import operator.SocketMap;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

public abstract class FileSocket extends Thread
{
    ServerSocket serverSocket;
    long fileSize = 0L;
    File file;

    public abstract void exe() throws IOException;

    public FileSocket(File file, long fileSize)
    {
        try
        {
            this.serverSocket = new ServerSocket(0);
            int port = getLocalPort();
//            SocketMap.socketMap.put(port, serverSocket);
        }
        catch (IOException localIOException1)
        {
            IOException localIOException2;
            (localIOException2 = localIOException1).printStackTrace();
        }
        this.file = file;
        this.fileSize = fileSize;
    }

    public final int getLocalPort()
    {
        return this.serverSocket.getLocalPort();
    }

    public void run()
    {
        try
        {
            exe();
            return;
        }
        catch (Exception localException2)
        {
            Exception localException1;
            (localException1 = localException2).printStackTrace();
            close();
        }
    }

    public final void close()
    {
        if (this.serverSocket != null)
            try
            {
                this.serverSocket.close();
                System.out.println("The " + getClass().getCanonicalName() + " is closed!");
                return;
            }
            catch (IOException localIOException2)
            {
                IOException localIOException1;
                (localIOException1 = localIOException2).printStackTrace();
            }
    }
}