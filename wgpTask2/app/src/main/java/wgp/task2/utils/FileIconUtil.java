package wgp.task2.utils;

import wgp.task2.R;

public class FileIconUtil {
    public static String getFileSuffixName(String file_name) {
        String[] split = file_name.split("\\.");//要使用\\转义符
        if (split.length > 0) {
            String suffixName = split[split.length - 1];
            return suffixName;
        } else {
            return "";
        }
    }
    public static int ChoseFileIconByFileSuffixName(String suffixName) {
        int ret = 0;
        switch (suffixName) {
            case "pdf":
                ret = R.drawable.pdf;
                break;
            case "ppt":
            case "pptm":
            case "pptx":
                ret = R.drawable.ppt;
                break;
            case "mp3":
            case "wav":
                ret = R.drawable.mp3;
                break;
            case "jpg":
            case "png":
                ret = R.drawable.image;
                break;
            case "zip":
            case "rar":
                ret = R.drawable.zip;
                break;
            case "mp4":
            case "mov":
                ret = R.drawable.video;
                break;
            case "doc":
            case "docx":
                ret = R.drawable.word;
                break;
            case "xls":
            case "xlsx":
            case "csv":
            case "xltx":
                ret = R.drawable.excel;
                break;
            default:
                ret = R.drawable.unknown;
                break;
        }
        return ret;
    }
}
