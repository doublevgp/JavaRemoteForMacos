package operator;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static Utils.FilePathUtil.HierarchicalDir;
import static Utils.FilePathUtil.combineHierDir;

public class OPN {
    public ArrayList<String> exeOpn(String cmdBody) throws Exception {
        ArrayList<String> backList=new ArrayList<String>();
        backList.add("OPN");
        File file = new File(cmdBody);

        if (file.exists() && !(file.isDirectory())) {
            try {
                Desktop.getDesktop().open(file);
                backList.add("1");
                backList.add("ok");
            } catch (IOException e) {
                backList.add("2");
                e.printStackTrace();
                backList.add("error");
                backList.add(e.toString());
            }


        } else {
            backList.add("1");
            backList.add("error");
            return backList;
        }
        return backList;
    }
}
