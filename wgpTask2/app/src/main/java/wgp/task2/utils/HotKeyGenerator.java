package wgp.task2.utils;

import java.util.ArrayList;
import java.util.Arrays;

import wgp.task2.data.HotKeyData;
import wgp.task2.data.NetFileData;

public class HotKeyGenerator {
    public static ArrayList<HotKeyData> getHotkeyList(NetFileData netFileData) {
        ArrayList<HotKeyData> hotKeyDataList = new ArrayList<>();
        String file_name = netFileData.getFileName();
        String[] split = file_name.split("\\.");//要使用\\转义符
        String suffixName = split[split.length - 1];
        switch (suffixName) {
            case "pptx":
            case "ppt":
                hotKeyDataList.add(new HotKeyData("退出程序", new ArrayList<String>(Arrays.asList("META", "Q"))));
                hotKeyDataList.add(new HotKeyData("切换程序", new ArrayList<String>(Arrays.asList("META", "TAB"))));
                hotKeyDataList.add(new HotKeyData("最大化", new ArrayList<String>(Arrays.asList("CONTROL", "OPTION", "ENTER"))));
                hotKeyDataList.add(new HotKeyData("最小化", new ArrayList<String>(Arrays.asList("META", "M"))));
                hotKeyDataList.add(new HotKeyData("从头放映", new ArrayList<String>(Arrays.asList("SHIFT","META","ENTER"))));
                hotKeyDataList.add(new HotKeyData("当前放映", new ArrayList<String>(Arrays.asList("META","ENTER"))));
                hotKeyDataList.add(new HotKeyData("退出放映", new ArrayList<String>(Arrays.asList("ESCAPE"))));
                hotKeyDataList.add(new HotKeyData("上一页", new ArrayList<String>(Arrays.asList("UP"))));
                hotKeyDataList.add(new HotKeyData("下一页", new ArrayList<String>(Arrays.asList("DOWN"))));
                break;
            case "mp3":
                hotKeyDataList.add(new HotKeyData("退出程序", new ArrayList<String>(Arrays.asList("META", "Q"))));
                hotKeyDataList.add(new HotKeyData("切换程序", new ArrayList<String>(Arrays.asList("META", "TAB"))));
                hotKeyDataList.add(new HotKeyData("最大化", new ArrayList<String>(Arrays.asList("CONTROL", "OPTION", "ENTER"))));
                hotKeyDataList.add(new HotKeyData("还原", new ArrayList<String>(Arrays.asList("CONTROL", "OPTION", "BACK_SPACE"))));
                hotKeyDataList.add(new HotKeyData("居中显示", new ArrayList<String>(Arrays.asList("CONTROL", "OPTION", "C"))));
                hotKeyDataList.add(new HotKeyData("最小化", new ArrayList<String>(Arrays.asList("META", "M"))));
                hotKeyDataList.add(new HotKeyData("播放", new ArrayList<String>(Arrays.asList("META", "TAB"))));
                hotKeyDataList.add(new HotKeyData("暂停", new ArrayList<String>(Arrays.asList("META", "TAB"))));
                hotKeyDataList.add(new HotKeyData("音量+", new ArrayList<String>(Arrays.asList("META", "TAB"))));
                hotKeyDataList.add(new HotKeyData("音量-", new ArrayList<String>(Arrays.asList("META", "TAB"))));
                break;
            case "png":
            case "jpg":
                hotKeyDataList.add(new HotKeyData("退出程序", new ArrayList<String>(Arrays.asList("META", "Q"))));
                hotKeyDataList.add(new HotKeyData("切换程序", new ArrayList<String>(Arrays.asList("META", "TAB"))));
                hotKeyDataList.add(new HotKeyData("最大化", new ArrayList<String>(Arrays.asList("CONTROL", "OPTION", "ENTER"))));
                hotKeyDataList.add(new HotKeyData("还原", new ArrayList<String>(Arrays.asList("CONTROL", "OPTION", "BACK_SPACE"))));
                hotKeyDataList.add(new HotKeyData("居中显示", new ArrayList<String>(Arrays.asList("CONTROL", "OPTION", "C"))));
                hotKeyDataList.add(new HotKeyData("最小化", new ArrayList<String>(Arrays.asList("META", "M"))));
                hotKeyDataList.add(new HotKeyData("放大", new ArrayList<String>(Arrays.asList("META", "EQUALS"))));
                hotKeyDataList.add(new HotKeyData("缩小", new ArrayList<String>(Arrays.asList("META", "MINUS"))));
                break;
            default:
                hotKeyDataList.add(new HotKeyData("退出程序", new ArrayList<String>(Arrays.asList("META", "Q"))));
                hotKeyDataList.add(new HotKeyData("切换程序", new ArrayList<String>(Arrays.asList("META", "TAB"))));
                hotKeyDataList.add(new HotKeyData("最大化", new ArrayList<String>(Arrays.asList("CONTROL", "OPTION", "ENTER"))));
                hotKeyDataList.add(new HotKeyData("最小化", new ArrayList<String>(Arrays.asList("META", "M"))));
                hotKeyDataList.add(new HotKeyData("关闭此页", new ArrayList<String>(Arrays.asList("META", "W"))));
                hotKeyDataList.add(new HotKeyData("打开网页", new ArrayList<String>()));
                hotKeyDataList.add(new HotKeyData("下一个焦点", new ArrayList<String>(Arrays.asList("TAB"))));
                hotKeyDataList.add(new HotKeyData("回车", new ArrayList<String>(Arrays.asList("ENTER"))));
                hotKeyDataList.add(new HotKeyData("粘贴输入内容", new ArrayList<String>()));
                break;
        }
        return hotKeyDataList;
    }
}
