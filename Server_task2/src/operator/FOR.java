package operator;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Arrays;

public class FOR extends BaseOperator {
    public ArrayList<String> exe(String cmdBody) throws Exception {
        ArrayList<String> backList = new ArrayList<>();
        String[] split = cmdBody.split(">");
        int num = Integer.parseInt(split[0]);
        for (int i = 1; i <= num; i++) {
            for (int j = 1; j < split.length; j++) {
                String cmd = split[j];
                System.out.println("FOR cmd " + cmd);
                backList.addAll(DealCmd.exe(new ArrayList<String>(Arrays.asList(cmd))));
            }
        }
        return backList;
    }
}
