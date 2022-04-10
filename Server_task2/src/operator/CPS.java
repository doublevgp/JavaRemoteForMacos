package operator;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.util.ArrayList;

public class CPS extends BaseOperator {
    public ArrayList<String> exe(String cmdBody) throws Exception {
        ArrayList<String> backList = new ArrayList<>();
        backList.add("CPS");
        backList.add("1");
        try {
            Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();//获取剪切板
            Transferable tText = new StringSelection(cmdBody);//cmdBody为String字符串，需要拷贝进剪贴板的内容
            clip.setContents(tText, null); //设置剪切板内容
            Thread.sleep(10L);
            new KEY().exeKey("META,V");
            backList.add("ok");
        } catch (Exception e) {
            e.printStackTrace();
            backList.add(e.toString());
        }
        return backList;
    }
}
