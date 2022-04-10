package operator;

import org.apache.commons.io.IOUtils;

import java.util.ArrayList;

public class CMD extends BaseOperator {
//    public static void main(String[] args) {
//        // 方法2：
//        try {
//            Process process = new ProcessBuilder("/bin/sh", "-c", "rm -f /Users/doublevgp/Downloads/1222.png").start();
//            String[] split = IOUtils.toString(process.getInputStream(), "utf-8").split("\n");
//            for (int i = 0; i < split.length; i++) {
//                System.out.println(String.format("%d split string is %s", i, split[i]));
//            }
//            String s = IOUtils.toString(process.getInputStream(), "utf-8");
//            System.out.println("方法2：");
//            System.out.println(s);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    public ArrayList<String> exe(String cmdStr) {
        ArrayList<String> backList = new ArrayList<>();
        try {
//            Process process = new ProcessBuilder("/bin/sh", "-c", String.format("rm -f /Users/doublevgp/Downloads/1222.png").start();
            Process process = new ProcessBuilder("/bin/sh", "-c", String.format("%s", cmdStr)).start();
            String[] split = IOUtils.toString(process.getInputStream(), "utf-8").split("\n");
            for (int i = 0; i < split.length; i++) {
                System.out.println(String.format("%d split string is %s", i, split[i]));
                backList.add(split[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return backList;
    }
}
