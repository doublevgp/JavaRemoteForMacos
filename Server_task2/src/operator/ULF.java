package operator;

import thread.FileUpLoadSocketThread;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ULF extends BaseOperator {
    public final ArrayList<String> exe(String paramString) throws IOException {
        long l1 = -1L;
        ArrayList localArrayList = new ArrayList();
        String[] arrayOfString;
        if (((arrayOfString = g.c(paramString)) != null) && (arrayOfString.length == 2)) {
            paramString = arrayOfString[0];
            l1 = Long.parseLong(arrayOfString[1]);
        } else {
            paramString = paramString;
        }
        long l2 = 0L;
        Object localObject;
        if (l1 == -1L) {
            localObject = DealCmd.upLoadFile(paramString, 0);
        } else {
            localObject = DealCmd.upLoadFile(paramString, 1);
            l2 = ((File) localObject).length();
        }
        localObject = new FileUpLoadSocketThread((File)localObject, l2);
        int port = ((FileUpLoadSocketThread) localObject).getLocalPort();
        localArrayList.add("ok");
        localArrayList.add(String.valueOf(port));
        localArrayList.add(String.valueOf(l2));
        paramString = getClass().getSimpleName() + ":" + paramString;
        localArrayList.add(paramString);
//        File o =
//        String fileName = o.getName();
//        long lastModified = o.lastModified();//获取文件修改时间
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//给时间格式，例如：2018-03-16 09:50:23
//        String fileDate = dateFormat.format(new Date(lastModified));//取得文件最后修改时间，并按格式转为字符串
//        String fileSize="0";
//        String isDir="1";
//        String filePath = o.getAbsolutePath();
//        localArrayList.add(fileName+">"+fileDate+">"+fileSize+">"+isDir+">"+filePath);
//        System.out.println("file size : " + l2 + " , on port: " + port);
//        localArrayList.add(0, "DLF");
//        localArrayList.add(1, "4");
//        ((FileUpLoadSocketThread)localObject).start();
//        localArrayList.add(getClass().getSimpleName());
//        localArrayList.add(i);
//        localArrayList.add(l2);
//        paramString = getClass().getSimpleName() + ":" + paramString;
//        localArrayList.add(paramString);
//        System.out.println("remoteFileSize size:" + l2 + " , port=" + i);
        return localArrayList;
    }
}
