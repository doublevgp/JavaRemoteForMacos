package operator;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import static Utils.FilePathUtil.*;
public class DEL extends BaseOperator {
    public ArrayList<String> exe(String cmdBody) throws Exception {
        ArrayList<String> backList=new ArrayList<String>();
        backList.add("DEL");
        File file = new File(cmdBody);
        String parentPath = combineHierDir(HierarchicalDir(cmdBody), 1, 1);
        System.out.println("del ParentPath ==== " + parentPath);
        if (file.exists() && !(file.isDirectory())) {
            backList.add("2");
            if (file.delete()) {
                backList.add("ok");
                backList.add(parentPath);
            } else {
                backList.add("error");
            }
        } else {
            backList.add("1");
            backList.add("error");
            return backList;
        }
        return backList;
    }
}
