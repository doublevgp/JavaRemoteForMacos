package wgp.task2.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import wgp.task2.data.HotKeyData;
import wgp.task2.data.NetFileData;

public class HotKeyGenerator {
    public static HashMap<String, ArrayList<HotKeyData>> hotKeyDataHashMap = new HashMap();
    public static ArrayList<HotKeyData> getHotkeyList(NetFileData netFileData) {
        generatorHashMap();
        String file_name = netFileData.getFileName();
        String[] split = file_name.split("\\.");//要使用\\转义符
        String suffixName = split[split.length - 1];
        System.out.println("file suffixName");
        switch (suffixName) {
            case "pptx":
            case "ppt":
                suffixName = "ppt";
                break;
            case "mp3":
                break;
            case "png":
            case "jpg":
                suffixName = "jpg";
                break;
            default:
                suffixName = "default";
                break;
        }
        return hotKeyDataHashMap.get(suffixName);
//        ArrayList<HotKeyData> hotKeyDataList = new ArrayList<>();
//        String file_name = netFileData.getFileName();
//        String[] split = file_name.split("\\.");//要使用\\转义符
//        String suffixName = split[split.length - 1];
//        switch (suffixName) {
//            case "pptx":
//            case "ppt":
//                hotKeyDataList.clear();
//                hotKeyDataList.add(new HotKeyData("退出程序", new ArrayList<String>(Arrays.asList("META", "Q"))));
//                hotKeyDataList.add(new HotKeyData("切换程序", new ArrayList<String>(Arrays.asList("META", "TAB"))));
//                hotKeyDataList.add(new HotKeyData("最大化", new ArrayList<String>(Arrays.asList("CONTROL", "OPTION", "ENTER"))));
//                hotKeyDataList.add(new HotKeyData("最小化", new ArrayList<String>(Arrays.asList("META", "M"))));
//                hotKeyDataList.add(new HotKeyData("从头放映", new ArrayList<String>(Arrays.asList("SHIFT","META","ENTER"))));
//                hotKeyDataList.add(new HotKeyData("当前放映", new ArrayList<String>(Arrays.asList("META","ENTER"))));
//                hotKeyDataList.add(new HotKeyData("退出放映", new ArrayList<String>(Arrays.asList("ESCAPE"))));
//                hotKeyDataList.add(new HotKeyData("上一页", new ArrayList<String>(Arrays.asList("UP"))));
//                hotKeyDataList.add(new HotKeyData("下一页", new ArrayList<String>(Arrays.asList("DOWN"))));
//                hotKeyDataHashMap.put("ppt", hotKeyDataList);
//                break;
//            case "mp3":
//                hotKeyDataList.clear();
//                hotKeyDataList.add(new HotKeyData("退出程序", new ArrayList<String>(Arrays.asList("META", "Q"))));
//                hotKeyDataList.add(new HotKeyData("切换程序", new ArrayList<String>(Arrays.asList("META", "TAB"))));
//                hotKeyDataList.add(new HotKeyData("最大化", new ArrayList<String>(Arrays.asList("CONTROL", "OPTION", "ENTER"))));
//                hotKeyDataList.add(new HotKeyData("还原", new ArrayList<String>(Arrays.asList("CONTROL", "OPTION", "BACK_SPACE"))));
//                hotKeyDataList.add(new HotKeyData("居中显示", new ArrayList<String>(Arrays.asList("CONTROL", "OPTION", "C"))));
//                hotKeyDataList.add(new HotKeyData("最小化", new ArrayList<String>(Arrays.asList("META", "M"))));
//                hotKeyDataList.add(new HotKeyData("播放", new ArrayList<String>(Arrays.asList("META", "TAB"))));
//                hotKeyDataList.add(new HotKeyData("暂停", new ArrayList<String>(Arrays.asList("META", "TAB"))));
//                hotKeyDataList.add(new HotKeyData("音量+", new ArrayList<String>(Arrays.asList("META", "TAB"))));
//                hotKeyDataList.add(new HotKeyData("音量-", new ArrayList<String>(Arrays.asList("META", "TAB"))));
//                hotKeyDataHashMap.put("mp3", hotKeyDataList);
//                break;
//            case "png":
//            case "jpg":
//                hotKeyDataList.add(new HotKeyData("退出程序", new ArrayList<String>(Arrays.asList("META", "Q"))));
//                hotKeyDataList.add(new HotKeyData("切换程序", new ArrayList<String>(Arrays.asList("META", "TAB"))));
//                hotKeyDataList.add(new HotKeyData("最大化", new ArrayList<String>(Arrays.asList("CONTROL", "OPTION", "ENTER"))));
//                hotKeyDataList.add(new HotKeyData("还原", new ArrayList<String>(Arrays.asList("CONTROL", "OPTION", "BACK_SPACE"))));
//                hotKeyDataList.add(new HotKeyData("居中显示", new ArrayList<String>(Arrays.asList("CONTROL", "OPTION", "C"))));
//                hotKeyDataList.add(new HotKeyData("最小化", new ArrayList<String>(Arrays.asList("META", "M"))));
//                hotKeyDataList.add(new HotKeyData("放大", new ArrayList<String>(Arrays.asList("META", "EQUALS"))));
//                hotKeyDataList.add(new HotKeyData("缩小", new ArrayList<String>(Arrays.asList("META", "MINUS"))));
//                hotKeyDataHashMap.put("jpg", hotKeyDataList);
//                break;
//            default:
//                hotKeyDataList.add(new HotKeyData("退出程序", new ArrayList<String>(Arrays.asList("META", "Q"))));
//                hotKeyDataList.add(new HotKeyData("切换程序", new ArrayList<String>(Arrays.asList("META", "TAB"))));
//                hotKeyDataList.add(new HotKeyData("最大化", new ArrayList<String>(Arrays.asList("CONTROL", "OPTION", "ENTER"))));
//                hotKeyDataList.add(new HotKeyData("最小化", new ArrayList<String>(Arrays.asList("META", "M"))));
//                hotKeyDataList.add(new HotKeyData("关闭此页", new ArrayList<String>(Arrays.asList("META", "W"))));
//                hotKeyDataList.add(new HotKeyData("打开网页", new ArrayList<String>()));
//                hotKeyDataList.add(new HotKeyData("下一个焦点", new ArrayList<String>(Arrays.asList("TAB"))));
//                hotKeyDataList.add(new HotKeyData("回车", new ArrayList<String>(Arrays.asList("ENTER"))));
//                hotKeyDataList.add(new HotKeyData("粘贴输入内容", new ArrayList<String>()));
//                hotKeyDataHashMap.put("default", hotKeyDataList);
//                break;
//        }
//        return hotKeyDataList;
    }
    public static ArrayList<HotKeyData> getHotkeyList(String suffixName) {
        generatorHashMap();
        return hotKeyDataHashMap.get(suffixName);
    }
    public static void generatorHashMap() {
        if (hotKeyDataHashMap.isEmpty()) {
            ArrayList<HotKeyData> hotKeyDataList = new ArrayList<>();
            hotKeyDataList.clear();
            hotKeyDataList.add(new HotKeyData("退出程序", new ArrayList<String>(Arrays.asList("META", "Q"))));
            hotKeyDataList.add(new HotKeyData("切换程序", new ArrayList<String>(Arrays.asList("META", "TAB"))));
            hotKeyDataList.add(new HotKeyData("最大化", new ArrayList<String>(Arrays.asList("CONTROL", "OPTION", "ENTER"))));
            hotKeyDataList.add(new HotKeyData("最小化", new ArrayList<String>(Arrays.asList("META", "M"))));
            hotKeyDataList.add(new HotKeyData("从头放映", new ArrayList<String>(Arrays.asList("SHIFT","META","ENTER"))));
            hotKeyDataList.add(new HotKeyData("当前放映", new ArrayList<String>(Arrays.asList("META","ENTER"))));
            hotKeyDataList.add(new HotKeyData("退出放映", new ArrayList<String>(Arrays.asList("ESCAPE"))));
            hotKeyDataList.add(new HotKeyData("上一页", new ArrayList<String>(Arrays.asList("UP"))));
            hotKeyDataList.add(new HotKeyData("下一页", new ArrayList<String>(Arrays.asList("DOWN"))));
            hotKeyDataHashMap.put("ppt", hotKeyDataList);
            hotKeyDataList.clear();
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
            hotKeyDataHashMap.put("mp3", hotKeyDataList);
            hotKeyDataList.clear();
            hotKeyDataList.add(new HotKeyData("退出程序", new ArrayList<String>(Arrays.asList("META", "Q"))));
            hotKeyDataList.add(new HotKeyData("切换程序", new ArrayList<String>(Arrays.asList("META", "TAB"))));
            hotKeyDataList.add(new HotKeyData("最大化", new ArrayList<String>(Arrays.asList("CONTROL", "OPTION", "ENTER"))));
            hotKeyDataList.add(new HotKeyData("还原", new ArrayList<String>(Arrays.asList("CONTROL", "OPTION", "BACK_SPACE"))));
            hotKeyDataList.add(new HotKeyData("居中显示", new ArrayList<String>(Arrays.asList("CONTROL", "OPTION", "C"))));
            hotKeyDataList.add(new HotKeyData("最小化", new ArrayList<String>(Arrays.asList("META", "M"))));
            hotKeyDataList.add(new HotKeyData("放大", new ArrayList<String>(Arrays.asList("META", "EQUALS"))));
            hotKeyDataList.add(new HotKeyData("缩小", new ArrayList<String>(Arrays.asList("META", "MINUS"))));
            hotKeyDataHashMap.put("jpg", hotKeyDataList);
            hotKeyDataList.clear();
            hotKeyDataList.add(new HotKeyData("退出程序", new ArrayList<String>(Arrays.asList("META", "Q"))));
            hotKeyDataList.add(new HotKeyData("切换程序", new ArrayList<String>(Arrays.asList("META", "TAB"))));
            hotKeyDataList.add(new HotKeyData("最大化", new ArrayList<String>(Arrays.asList("CONTROL", "OPTION", "ENTER"))));
            hotKeyDataList.add(new HotKeyData("最小化", new ArrayList<String>(Arrays.asList("META", "M"))));
            hotKeyDataList.add(new HotKeyData("关闭此页", new ArrayList<String>(Arrays.asList("META", "W"))));
            hotKeyDataList.add(new HotKeyData("爱奇艺搜索叶问4", new ArrayList<String>()));
            hotKeyDataList.add(new HotKeyData("下一个焦点", new ArrayList<String>(Arrays.asList("TAB"))));
            hotKeyDataList.add(new HotKeyData("回车", new ArrayList<String>(Arrays.asList("ENTER"))));
            hotKeyDataList.add(new HotKeyData("粘贴输入内容", new ArrayList<String>()));
            hotKeyDataHashMap.put("default", hotKeyDataList);
        }
    }
}
