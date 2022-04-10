package operator;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static Utils.FilePathUtil.HierarchicalDir;
import static Utils.FilePathUtil.combineHierDir;

public class OPN extends BaseOperator {
    public ArrayList<String> exe(String cmdBody) throws IOException {
        ArrayList<String> backList=new ArrayList<String>();
        backList.add("OPN");
        File file = new File(cmdBody);
        try {
            if (file.isDirectory()) {
                new CMD().exe("open " + cmdBody);
                backList.add("1");
                backList.add("ok");
            } else if (file.exists() && !(file.isDirectory())) {
                Desktop.getDesktop().open(file);
                backList.add("1");
                backList.add("ok");
            }
        } catch (IOException e) {
            backList.add("2");
            e.printStackTrace();
            backList.add("error");
            backList.add(e.toString());
            return backList;
        }
        return backList;
    }
}
