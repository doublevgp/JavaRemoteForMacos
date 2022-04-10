package operator;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

public class g
{
    ArrayList a; //





//    public static ArrayList getAndDelCmd(String paramString) // dealCmd(String cmd);
//    {
//        String str = (paramString = b(paramString))[0].toLowerCase();
//        paramString = paramString[1];
//        new ArrayList();
//        if (str.equals("dir"))
//        {
//            new e();
//            return paramString = e.a(paramString);
//        }
//        if (str.equals("opn"))
//            return paramString = new l().a(paramString);
//        if (str.equals("key"))
//            return paramString = new i().a(paramString);
//        if (str.equals("mov"))
//            return paramString = new j().a(paramString);
//        if (str.equals("mva"))
//            return paramString = new k().a(paramString);
//        if (str.equals("clk"))
//            return paramString = new b().a(paramString);
//        if (str.equals("rol"))
//            return paramString = new m().a(paramString);
//        if (str.equals("cmd"))
//            return paramString = new c().a(paramString);
//        if (str.equals("slp"))
//            return paramString = new n().a(paramString);
//        if (str.equals("cps"))
//            return paramString = new d().a(paramString);
//        if (str.equals("dlf"))
//            return paramString = new f().a(paramString);
//        if (str.equals("ulf"))
//            return paramString = new o().a(paramString);
//        throw new Exception("invalid cmd!");
//    }

    public static String[] checkCmd(String paramString) throws Exception {
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

    public static String[] c(String paramString)
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

/* Location:           C:\Users\叶琼\Desktop\Word & PPT\大三下\移动应用开发课程设计\TestServer13_encode.jar
 * Qualified Name:     xxx.server.a.g
 * JD-Core Version:    0.6.2
 */