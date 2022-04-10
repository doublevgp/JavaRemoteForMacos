package operator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DealCmd {
    ArrayList<String> g;
    public static File upLoadFile(String paramString, int paramInt) {
        File file = new File(paramString);
        File f;
        String s;
        if ((paramInt == 0) && (file.exists())) {
            paramInt = 1;
            do {
                int i = paramInt++;
                s = paramString;
                int j;
                s = s + "(" + i + ")";
            } while ((f = new File(s)).exists());
        }
        try {
            System.out.println("uploading file:" + file.getCanonicalPath());
        } catch (IOException localIOException) {
            localIOException.printStackTrace();
        }
        return file;
    }

    public static String[] checkCmd(String paramString) throws Exception // 判断cmd合法
    {
        int i = paramString.indexOf(":");
        System.out.println("Cmd from client: " + paramString);
        if (i <= 0)
            throw new Exception("Invalid Cmd from client: " + paramString);
        String[] arrayOfString = new String[2];
        String str = paramString.substring(0, i);
        paramString = paramString.substring(i + 1);
        arrayOfString[0] = str.toLowerCase();
        arrayOfString[1] = paramString;
        return arrayOfString;
    }
    public static String[] dealQuery(String paramString) // 带?的cmd
    {
        String[] arrayOfString = null;
        int i = paramString.indexOf("?");
        System.out.println("Cmd from client: " + paramString);
        if (i > 0)
        {
            arrayOfString = new String[2];
            String str = paramString.substring(0, i);
            paramString = paramString.substring(i + 1);
            arrayOfString[0] = str;
            arrayOfString[1] = paramString;
        }
        return arrayOfString;
    }

    public static ArrayList<String> exe(ArrayList<String> cmdList) throws Exception {
        if (cmdList.size() == 0)
            throw new Exception("Cmd size is 0. ");
        ArrayList<String> backMsg = new ArrayList<>();
        for (String cmd : cmdList) {
            String[] split = cmd.split(":");
            String cmdHead = split[0];
            String cmdBody = cmd.substring(4);
            System.out.println("cmdHead == " + cmdHead);
            System.out.println("cmdBody == " + cmdBody);
            switch (cmdHead) {
                case "dir":
                    backMsg = new DIR().exe(cmdBody);
                    break;
                case "opn":
                    backMsg = new OPN().exe(cmdBody);
                    break;
                case "key":
                    backMsg = new KEY().exeKey(cmdBody);
                    break;
                case "clk":
                    backMsg = new KEY().exeClk(cmdBody);
                    break;
                case "mov":
                    backMsg = new KEY().exeMov(cmdBody);
                    break;
                case "rol":
                    backMsg = new KEY().exeRol(cmdBody);
                    break;
                case "poi":
                    backMsg = new KEY().exePointerInfo();
                    break;
                case "del":
                    backMsg = new DEL().exe(cmdBody);
                    break;
                case "cmd":
                    backMsg = new CMD().exe(cmdBody);
                    break;
                case "cps":
                    backMsg = new CPS().exe(cmdBody);
                    break;
                case "dlf": // 下载文件
                    backMsg = new DLF().exe(cmdBody);
                    break;
                case "ulf":
                    backMsg = new ULF().exe(cmdBody);
                case "clo": // 关闭port
                    backMsg = new CLO().exe(cmdBody);
                    break;
            }
        }
        return backMsg;
        // region 组合键
//        Object localObject2;
//        String[] s1;
//        String str = (s1 = b((String)paramArrayList.get(0)))[0];
//        paramArrayList.set(0, s1[1]);
//        g ob1;
//        if (str.equals("for"))
//        {
//            localObject2 = paramArrayList;
//            (ob1 = new g()).a = ((ArrayList)localObject2);
//            localObject2 = new ArrayList();
//            new Thread(new h((g)localObject1)).start();
//            ((ArrayList)localObject2).add(localObject1.getClass().getSimpleName() + ":post");
//            localObject1 = localObject2;
//            System.out.println("Combo command--" + (String)paramArrayList.get(0));
//        }
        // endregion
    }
    public static String[] getStringArray(String paramString)
    {
        String[] arrayOfString = null;
        int i = paramString.indexOf("?");
        System.out.println("Cmd from client: " + paramString);
        if (i > 0)
        {
            arrayOfString = new String[2];
            String str = paramString.substring(0, i);
            paramString = paramString.substring(i + 1);
            arrayOfString[0] = str;
            arrayOfString[1] = paramString;
        }
        return arrayOfString;
    }
}
