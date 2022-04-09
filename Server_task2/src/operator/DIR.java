package operator;

import Utils.FilePathUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static Utils.FilePathUtil.HierarchicalDir;
import static Utils.FilePathUtil.combineHierDir;

public class DIR {
    public ArrayList<String> exeDir(String cmdBody) throws Exception {
        // TODO Auto-generated method stub
        ArrayList<String> backList=new ArrayList<String>();
        backList.add("DIR");
        String parentPath = combineHierDir(HierarchicalDir(cmdBody), 1, 1);

        File file = new File(cmdBody);
        if (file.exists()) {
            File[] listFiles = file.listFiles();
//            backList.add(String.valueOf(listFiles.length + 1));
            backList.add("ok");
            if (!(cmdBody.equals("/"))) {
                backList.add("...>0>0>1>/");
                backList.add("..>0>0>1>"+parentPath);
            }
            for(File mfile : listFiles){
                String fileName = mfile.getName();
                long lastModified = mfile.lastModified();//获取文件修改时间
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//给时间格式，例如：2018-03-16 09:50:23
                String fileDate = dateFormat.format(new Date(lastModified));//取得文件最后修改时间，并按格式转为字符串
                String fileSize="0";
                String isDir="1";
                String filePath = file.getAbsolutePath();
                if(!mfile.isDirectory()){//判断是否为目录
                    isDir="0";
                    fileSize=""+mfile.length();
                }
                backList.add(fileName+">"+fileDate+">"+fileSize+">"+isDir+">"+filePath);
            }
            backList.add(1, String.valueOf(backList.size()));
        } else {
            backList.add("1");
            backList.add("error");
        }
        return backList;
    }
}
