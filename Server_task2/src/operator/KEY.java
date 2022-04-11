package operator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static Utils.FilePathUtil.HierarchicalDir;
import static Utils.FilePathUtil.combineHierDir;
import static java.awt.Toolkit.getDefaultToolkit;
import static java.lang.Math.min;
import static java.lang.Math.max;
public class KEY extends BaseOperator {
    Robot r;
    HashMap<String, Integer> KEY_MAP;
    Toolkit toolkit;
    Dimension screenSize;
    public KEY() throws AWTException {
        r = new Robot();
//        KEY_MAP = readSqlKEYMAP();
        KEY_MAP = new HashMap<>();
        toolkit = getDefaultToolkit();
        getKEYMAPbyFunction();
        screenSize = toolkit.getScreenSize();
    }

    private void getKEYMAPbyFunction() {
        KEY_MAP.put("SPACE", KeyEvent.VK_SPACE);
        KEY_MAP.put("ESCAPE", KeyEvent.VK_ESCAPE);
        KEY_MAP.put("META", KeyEvent.VK_META);
        KEY_MAP.put("SHIFT", KeyEvent.VK_SHIFT);
        KEY_MAP.put("CONTROL", KeyEvent.VK_CONTROL);
        KEY_MAP.put("ALT", KeyEvent.VK_ALT);
        KEY_MAP.put("CAPS_LOCK", KeyEvent.VK_CAPS_LOCK);
        KEY_MAP.put("OPTION", KeyEvent.VK_ALT);
        KEY_MAP.put("TAB", KeyEvent.VK_TAB);
        KEY_MAP.put("BACK_SPACE", KeyEvent.VK_BACK_SPACE);
        KEY_MAP.put("ENTER", KeyEvent.VK_ENTER);
        KEY_MAP.put("EQUALS", KeyEvent.VK_EQUALS);
        KEY_MAP.put("MINUS", KeyEvent.VK_MINUS);
        KEY_MAP.put("UP", KeyEvent.VK_UP);
        KEY_MAP.put("DOWN", KeyEvent.VK_DOWN);
        KEY_MAP.put("F1", KeyEvent.VK_F1);
        KEY_MAP.put("F2", KeyEvent.VK_F2);
        KEY_MAP.put("F3", KeyEvent.VK_F3);
        KEY_MAP.put("F4", KeyEvent.VK_F4);
        KEY_MAP.put("F5", KeyEvent.VK_F5);
        KEY_MAP.put("F6", KeyEvent.VK_F6);
        KEY_MAP.put("F7", KeyEvent.VK_F7);
        KEY_MAP.put("F8", KeyEvent.VK_F8);
        KEY_MAP.put("F9", KeyEvent.VK_F9);
        KEY_MAP.put("F10", KeyEvent.VK_F10);
        KEY_MAP.put("F11", KeyEvent.VK_F11);
        KEY_MAP.put("F12", KeyEvent.VK_F12);
        KEY_MAP.put("A", KeyEvent.VK_A);
        KEY_MAP.put("B", KeyEvent.VK_B);
        KEY_MAP.put("C", KeyEvent.VK_C);
        KEY_MAP.put("D", KeyEvent.VK_D);
        KEY_MAP.put("E", KeyEvent.VK_E);
        KEY_MAP.put("F", KeyEvent.VK_F);
        KEY_MAP.put("G", KeyEvent.VK_G);
        KEY_MAP.put("H", KeyEvent.VK_H);
        KEY_MAP.put("I", KeyEvent.VK_I);
        KEY_MAP.put("J", KeyEvent.VK_J);
        KEY_MAP.put("K", KeyEvent.VK_K);
        KEY_MAP.put("L", KeyEvent.VK_L);
        KEY_MAP.put("M", KeyEvent.VK_M);
        KEY_MAP.put("N", KeyEvent.VK_N);
        KEY_MAP.put("O", KeyEvent.VK_O);
        KEY_MAP.put("P", KeyEvent.VK_P);
        KEY_MAP.put("Q", KeyEvent.VK_Q);
        KEY_MAP.put("R", KeyEvent.VK_R);
        KEY_MAP.put("S", KeyEvent.VK_S);
        KEY_MAP.put("T", KeyEvent.VK_T);
        KEY_MAP.put("U", KeyEvent.VK_U);
        KEY_MAP.put("V", KeyEvent.VK_V);
        KEY_MAP.put("W", KeyEvent.VK_W);
        KEY_MAP.put("X", KeyEvent.VK_X);
        KEY_MAP.put("Y", KeyEvent.VK_Y);
        KEY_MAP.put("Z", KeyEvent.VK_Z);
    }
    static int x = 0, y = 0;
    public ArrayList<String> exeClk(String cmdBody) throws Exception {
        ArrayList<String> backList=new ArrayList<String>();
        String[] split = cmdBody.split(",");
        String str = split[0];
//        dealXY(cmdBody);
        Point point = getPointerInfo();
        int x = point.x, y = point.y;
        switch (str) {
            case "left":
                clickLMouse(r, x, y, 100);
                break;
            case "right":
                clickRMouse(r, x, y, 100);
                break;
            case "left_press":
                pressMouse(r, x, y, false);
                break;
            case "left_release":
                pressMouse(r, x, y, true);
                break;
        }
        backList.add("CLK");
        backList.add("1");
        backList.add("ok");
        return backList;
    }
    public void dealXY(String cmdBody) {
        String[] split = cmdBody.split(",");
        x = Integer.parseInt(split[1]);
        y = Integer.parseInt(split[2]);
    }
    public Point getPointerInfo() {
        return java.awt.MouseInfo.getPointerInfo().getLocation();
    }
    public ArrayList<String> exePointerInfo() {
        Point point = java.awt.MouseInfo.getPointerInfo().getLocation();
        ArrayList<String> backList=new ArrayList<String>();
        backList.add("POI");
        backList.add("2");
        backList.add("ok");
        backList.add(String.format("%d,%d",point.x,point.y));
        return backList;
    }
    public ArrayList<String> exeRol(String cmdBody) {
        ArrayList<String> backList=new ArrayList<String>();
        String[] split = cmdBody.split(",");
        int x = Integer.parseInt(split[0]);
        r.mouseWheel(x);
        backList.add("ROL");
        backList.add("1");
        backList.add("ok");
        return backList;
    }
    public ArrayList<String> exeMoa(String cmdBody) {
        ArrayList<String> backList=new ArrayList<String>();
        String[] split = cmdBody.split(",");
        int x = Integer.parseInt(split[0]), y = Integer.parseInt(split[1]);
//        Point point = getPointerInfo();
//        x += point.x;
//        y += point.y;
        x = max(0, x);
        x = min(screenSize.width, x);
        y = max(0, y);
        y = min(screenSize.height, y);
        System.out.println(x + " " + y);
        moveMouse(r, x, y);
        backList.add("MOV");
        backList.add("1");
        backList.add("ok");
        return backList;
    }
    public ArrayList<String> exeMov(String cmdBody) {
        ArrayList<String> backList=new ArrayList<String>();
        String[] split = cmdBody.split(",");
        int x = Integer.parseInt(split[0]), y = Integer.parseInt(split[1]);
        Point point = getPointerInfo();
        x += point.x;
        y += point.y;
        x = max(0, x);
        x = min(screenSize.width, x);
        y = max(0, y);
        y = min(screenSize.height, y);
        System.out.println(x + " " + y);
        moveMouse(r, x, y);
        backList.add("MOV");
        backList.add("1");
        backList.add("ok");
        return backList;
    }
    public ArrayList<String> exeKey(String cmdBody) throws Exception {
        ArrayList<String> backList=new ArrayList<String>();
        int ind = cmdBody.indexOf("?"); // 次数
        int keyNum = 1;
        if (ind != -1) {
            keyNum = Integer.parseInt(cmdBody.substring(ind + 1));
            cmdBody = cmdBody.substring(0, ind);
        }
        String[] split = cmdBody.split(",");
        int[] keys = new int[split.length];
        backList.add("KEY");
        try {
            for (int c = 1; c <= keyNum; c++) {
                for (int i = 0; i < split.length; i++) {
                    keys[i] = (int) KEY_MAP.get(split[i]);
                }
                pressKeys(r, keys, 10);
                backList.add("1");
                backList.add("ok");
            }
        } catch (NullPointerException e) {
            backList.add("2");
            backList.add("没有对应按键");
            backList.add(e.toString());
        }
        return backList;
    }

    /**
     * 鼠标单击（左击）,要双击就连续调用
     *
     * @param r
     * @param x
     *            x坐标位置
     * @param y
     *            y坐标位置
     * @param delay
     *            该操作后的延迟时间
     */
    public static void clickLMouse(Robot r, int x, int y, int delay) {
        r.mouseMove(x, y);
        r.mousePress(InputEvent.BUTTON1_MASK);
        r.delay(10);
        r.mouseRelease(InputEvent.BUTTON1_MASK);
        r.delay(delay);
    }
    public static void moveMouse(Robot r, int x, int y) {
        r.mouseMove(x, y);
    }
    public static void pressMouse(Robot r, int x, int y, boolean isPressed) {
        r.mouseMove(x, y);
        if (isPressed) {
            r.mouseRelease(InputEvent.BUTTON1_MASK);
        } else {
            r.mousePress(InputEvent.BUTTON1_MASK);
        }
    }
    /**
     * 鼠标右击,要双击就连续调用
     *
     * @param r
     * @param x
     *            x坐标位置
     * @param y
     *            y坐标位置
     * @param delay
     *            该操作后的延迟时间
     */
    public static void clickRMouse(Robot r, int x, int y, int delay) {
        r.mouseMove(x, y);
        r.mousePress(InputEvent.BUTTON3_MASK);
        r.delay(10);
        r.mouseRelease(InputEvent.BUTTON3_MASK);
        r.delay(delay);
    }

    /**
     * 键盘输入（一次只能输入一个字符）
     *
     * @param r
     * @param keys
     *            键盘输入的字符数组
     * @param delay
     *            输入一个键后的延迟时间
     */
    public static void pressKeys(Robot r, int[] keys, int delay) {
        for (int i = 0; i < keys.length; i++) {
            r.keyPress(keys[i]);
            r.delay(delay);
        }
        for (int i = keys.length - 1; i >= 0; i--) {
            r.keyRelease(keys[i]);
            r.delay(delay);
        }
    }

    /**
     * 复制
     *
     * @param r
     * @throws InterruptedException
     */
    void doCopy(Robot r) throws InterruptedException {
        Thread.sleep(3000);
        r.setAutoDelay(200);
        r.keyPress(KeyEvent.VK_CONTROL);
        r.keyPress(KeyEvent.VK_C);
        r.keyRelease(KeyEvent.VK_CONTROL);
        r.keyRelease(KeyEvent.VK_C);
    }

    /**
     * 粘贴
     *
     * @param r
     * @throws InterruptedException
     */
    void doParse(Robot r) throws InterruptedException {
        r.setAutoDelay(500);
        Thread.sleep(2000);
        r.mouseMove(300, 300);
        r.mousePress(InputEvent.BUTTON1_MASK);
        r.mouseRelease(InputEvent.BUTTON1_MASK);
        r.keyPress(KeyEvent.VK_CONTROL);
        r.keyPress(KeyEvent.VK_V);
        r.keyRelease(KeyEvent.VK_CONTROL);
        r.keyRelease(KeyEvent.VK_V);
    }

    /**
     * 捕捉全屏慕
     *
     * @param r
     * @return
     */
    public Icon captureFullScreen(Robot r) {
        BufferedImage fullScreenImage = r.createScreenCapture(new Rectangle(
                getDefaultToolkit().getScreenSize()));
        ImageIcon icon = new ImageIcon(fullScreenImage);
        return icon;
    }

    /**
     * 捕捉屏幕的一个矫形区域
     *
     * @param r
     * @param x
     *            x坐标位置
     * @param y
     *            y坐标位置
     * @param width
     *            矩形的宽
     * @param height
     *            矩形的高
     * @return
     */
    public Icon capturePartScreen(Robot r, int x, int y, int width, int height) {
        r.mouseMove(x, y);
        BufferedImage fullScreenImage = r.createScreenCapture(new Rectangle(
                width, height));
        ImageIcon icon = new ImageIcon(fullScreenImage);
        return icon;
    }
}
