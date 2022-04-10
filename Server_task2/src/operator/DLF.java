package operator;

import thread.FileDownLoadSocketThread;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static operator.DealCmd.getStringArray;

public class DLF extends BaseOperator {
    public ArrayList<String> exeDir(String cmdBody) {
        ArrayList<String> backList = new ArrayList<>();
        int ind=cmdBody.indexOf('?');
        String filepath = cmdBody.substring(0,ind);
        String Sdownsize = cmdBody.substring(ind+1);
        long downsize=Long.parseLong(Sdownsize);

        File file=new File(filepath); //找文件
        long size=file.length();//文件大小

        FileDownLoadSocketThread thread = null;
        if(size>0){
            backList.add("ok");
            backList.add("dlf");
            thread=new FileDownLoadSocketThread(file,downsize);
            System.out.println("Downloadthread 的 port: "+thread.getLocalPort());
            backList.add(String.valueOf(thread.getLocalPort()));
            backList.add(String.valueOf(size));
        }
        thread.start();
        return backList; // 传给客户端的信息包括  port + 文件大小
    }
    public final ArrayList<String> exe(String cmdBody) throws AWTException, IOException {
        long l1 = 0L;
        ArrayList<String> localArrayList = new ArrayList<String>();
        String[] s = getStringArray(cmdBody);
        if ((s != null) && (s.length == 2)) {
            cmdBody = s[0];
            l1 = Long.parseLong(s[1]);
            int i = Integer.parseInt(String.valueOf(l1));
        }
        File o = new File(cmdBody);
        long l2 = o.length();
        FileDownLoadSocketThread fileDownLoadSocketThread = new FileDownLoadSocketThread(o, l1); // l1 filepos
        int port = fileDownLoadSocketThread.getLocalPort();
        localArrayList.add("ok");
        localArrayList.add(String.valueOf(port));
        localArrayList.add(String.valueOf(l2));
        cmdBody = getClass().getSimpleName() + ":" + cmdBody;
        localArrayList.add(cmdBody);
        String fileName = o.getName();
        long lastModified = o.lastModified();//获取文件修改时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//给时间格式，例如：2018-03-16 09:50:23
        String fileDate = dateFormat.format(new Date(lastModified));//取得文件最后修改时间，并按格式转为字符串
        String fileSize="0";
        String isDir="1";
        String filePath = o.getAbsolutePath();
        localArrayList.add(fileName+">"+fileDate+">"+fileSize+">"+isDir+">"+filePath);
        System.out.println("file size : " + l2 + " , on port: " + port);
        localArrayList.add(0, "DLF");
        localArrayList.add(1, "4");
        fileDownLoadSocketThread.start();
        return localArrayList;
    }
}
