package wgp.task2.fragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.Timer;
import java.util.regex.Pattern;

import wgp.task2.R;
import wgp.task2.data.CmdServerIpSetting;
import wgp.task2.operator.ShowNonUiUpdateCmdHandler;
import wgp.task2.socket.CmdClientSocket;

/**
 * todo 实现鼠标移动
 */
public class TouchPadFragment extends Fragment {
    public static int lastXpos = 0;
    public static int lastYpos = 0;
    boolean keyboard = false;
    Thread checking;
    String ip;
    int port;
    String cmd;
    int flag = 0;
    ShowNonUiUpdateCmdHandler showNonUiUpdateCmdHandler;
    CmdClientSocket cmdClientSocket;
    Timer timer;
    long firstTime;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        mainViewModel =
//                ViewModelProviders.of(this).get(MainViewModel.class);
        View v = inflater.inflate(R.layout.touchpad_fragment, container, false);
        ip =  CmdServerIpSetting.ip;
        port = CmdServerIpSetting.port;
        showNonUiUpdateCmdHandler = new ShowNonUiUpdateCmdHandler(getContext());
        getPointerInfo();
        Button left = v.findViewById(R.id.LeftClickButton);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cmd = String.format("clk:left");
                CmdClientSocket cmdClientSocket = new CmdClientSocket(ip, port, showNonUiUpdateCmdHandler);
                cmdClientSocket.work(cmd);
            }
        });
//        am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstTime, 5*1000, sender);
        Button right = v.findViewById(R.id.RightClickButton);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cmd = String.format("clk:right");
                CmdClientSocket cmdClientSocket = new CmdClientSocket(ip, port, showNonUiUpdateCmdHandler);
                cmdClientSocket.work(cmd);
            }
        });
        Button press = v.findViewById(R.id.LeftPressButton);
        press.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag == 0) {
//                    cmd = String.format("clk:left_press,%d,%d", lastXpos, lastYpos);
                    cmd = String.format("clk:left_press");
                    CmdClientSocket cmdClientSocket = new CmdClientSocket(ip, port, showNonUiUpdateCmdHandler);
                    cmdClientSocket.work(cmd);
                    flag = 1;
                } else {
//                    cmd = String.format("clk:left_release,%d,%d", lastXpos, lastYpos);
                    cmd = String.format("clk:left_release");
                    CmdClientSocket cmdClientSocket = new CmdClientSocket(ip, port, showNonUiUpdateCmdHandler);
                    cmdClientSocket.work(cmd);
                    flag = 0;
                }

            }
        });


        View touchView = v.findViewById(R.id.TouchPad);
        //TextView mousePad=(TextView) findViewById(R.id.TouchPad);
        final GestureDetector mGestureDetector = new GestureDetector(getContext(), new MousePadOnGestureListener());
        touchView.setOnTouchListener(new TextView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                mGestureDetector.onTouchEvent(event);
                return true;
            }
        });

        View scrollView = v.findViewById(R.id.ScrollPad);
        //TextView mousePad=(TextView) findViewById(R.id.TouchPad);
        final GestureDetector mGestureDetector1 = new GestureDetector(getContext(), new ScrollPadOnGestureListener());
        scrollView.setOnTouchListener(new TextView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                mGestureDetector1.onTouchEvent(event);
                return true;
            }
        });
        return v;
    }

    private void getPointerInfo() {
        String cmd = String.format("poi:");
        CmdClientSocket cmdClientSocket = new CmdClientSocket(ip, port, showNonUiUpdateCmdHandler);
//        if (firstTime == 0) {
//            firstTime = getActivity().getSystemService();
//        }
        cmdClientSocket.work(cmd);
    }

    class MousePadOnGestureListener extends GestureDetector.SimpleOnGestureListener {
        //手势处理接口，通过集成SimpleOnGestureListener改写对应手势方法

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            cmd=String.format("clk:left");
            CmdClientSocket cmdClientSocket = new CmdClientSocket(ip, port, showNonUiUpdateCmdHandler);
            cmdClientSocket.work(cmd);
            return super.onSingleTapConfirmed(e);
        }
        @Override
        public void onShowPress(MotionEvent e) {
//            cmd=String.format("clk:left");
//            CmdClientSocket cmdClientSocket = new CmdClientSocket(ip, port, showNonUiUpdateCmdHandler);
//            cmdClientSocket.work(cmd);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {//双击
            // TODO Auto-generated method stub
            cmd=String.format("clk:left");
            CmdClientSocket cmdClientSocket = new CmdClientSocket(ip, port, showNonUiUpdateCmdHandler);
            cmdClientSocket.work(cmd);
            return true;
        }
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {//滚动
            // TODO Auto-generated method stub
//            lastXpos -= distanceX;
//            lastYpos -= distanceY;
            cmd="mov:"+(int)-distanceX+","+(int)-distanceY;//手势方向与鼠标控制方向相反，对值取反
//            cmd="mov:"+lastXpos+","+(int)lastYpos;//手势方向与鼠标控制方向相反，对值取反
            CmdClientSocket cmdClientSocket = new CmdClientSocket(ip, port, showNonUiUpdateCmdHandler);
            cmdClientSocket.work(cmd);
            return true;
        }
    }
    class ScrollPadOnGestureListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            cmd="rol:"+(int)-distanceY;//手势方向与鼠标控制方向相反，对值取反
            CmdClientSocket cmdClientSocket = new CmdClientSocket(ip, port, showNonUiUpdateCmdHandler);
            cmdClientSocket.work(cmd);
            return true;
        }
    }
}
