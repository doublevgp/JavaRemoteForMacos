package wgp.task2.data;

import java.util.ArrayList;

public class HotKeyData {
    String HotKeyName;
    ArrayList<String> HotKeys;
    String HotKeyContent;

    public String getHotKeyContent() {
        return HotKeyContent;
    }

    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public HotKeyData(String hotKeyName, ArrayList<String> hotKeys) {
        HotKeyName = hotKeyName;
        HotKeys = hotKeys;
        this.HotKeyContent = generatorContent();
    }
    public HotKeyData(String hotKeyName, String content) {
        HotKeyName = hotKeyName;
        HotKeys = dealContent(content);
        this.HotKeyContent = content;
    }
    public String generatorContent() {
        if (HotKeys.size() > 0) {
            String ret = HotKeys.get(0);
            for (int i = 1; i < HotKeys.size(); i++) {
                ret = ret + "," + HotKeys.get(i);
            }
            return ret;
        }
        return "空";
    }
    @Override
    public String toString() {
        return "HotKeyData{" +
                "HotKeyName='" + HotKeyName + '\'' +
                ", HotKeys=" + HotKeys +
                '}';
    }

    public ArrayList<String> dealContent(String content) {
        ArrayList<String> back = new ArrayList<>();
        content.split(",");
        for (String s : content.split(",")) {
            back.add(s);
        }
        return back;
    }
    public String getHotKeyName() {
        return HotKeyName;
    }

    public void setHotKeyName(String hotKeyName) {
        HotKeyName = hotKeyName;
    }

    public ArrayList<String> getHotKeys() {
        return HotKeys;
    }

    public void setHotKeys(ArrayList<String> hotKeys) {
        HotKeys = hotKeys;
    }
}
