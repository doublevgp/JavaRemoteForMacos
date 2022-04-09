package wgp.task2.data;

import java.util.ArrayList;

public class HotKeyData {
    String HotKeyName;
    ArrayList<String> HotKeys;

    public HotKeyData(String hotKeyName, ArrayList<String> hotKeys) {
        HotKeyName = hotKeyName;
        HotKeys = hotKeys;
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
