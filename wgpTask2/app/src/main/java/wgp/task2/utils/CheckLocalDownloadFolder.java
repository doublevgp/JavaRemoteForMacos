package wgp.task2.utils;

import android.os.Environment;

import java.io.File;

public class CheckLocalDownloadFolder {
    public static String check() {
        String s = Environment.getExternalStorageDirectory().getAbsolutePath();

        File sd = Environment.getExternalStorageDirectory();
        String path = sd.getPath()+"/Download";
        System.out.println(s);
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        return path;
    }
}
