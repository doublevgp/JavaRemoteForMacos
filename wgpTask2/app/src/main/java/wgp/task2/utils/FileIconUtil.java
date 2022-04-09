package wgp.task2.utils;

import wgp.task2.R;

public class FileIconUtil {
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
