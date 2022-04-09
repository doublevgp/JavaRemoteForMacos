package Utils;

import java.util.ArrayList;

public class FilePathUtil {
    public static ArrayList<String> HierarchicalDir(String allPath) {
        ArrayList<String> HierDir = new ArrayList<>();
        String[] split = allPath.split("/");
        for (int i = 0; i < split.length; i++) {
//            System.out.println(String.format("%d's path is %s", i, split[i]));
            HierDir.add(split[i]);
        }
        return HierDir;
    }
    public static String combineHierDir(ArrayList<String> strList, int ind, int tail) {
        String ret = "/";
        for (int i = ind; i < strList.size() - tail; i++) {
            System.out.println("now ret == " + ret);
            ret = ret + strList.get(i) + ((i == strList.size() - tail - 1) ? "" : "/");
        }
        return ret;
    }
}
