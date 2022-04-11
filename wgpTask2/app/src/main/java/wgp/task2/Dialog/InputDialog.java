package wgp.task2.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import wgp.task2.R;

public class InputDialog extends DialogFragment {
    String type;
    String title;
    int num;
    Handler handler;
    String keyname;
    String keycontent;

    public void setKeyname(String keyname) {
        this.keyname = keyname;
    }

    public void setKeycontent(String keycontent) {
        this.keycontent = keycontent;
    }

    public static String KEY_NAME = "name";
    public static String KEY_CONTENT = "content";

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public interface Callback {
        void onCpsClick(String cpsString);
        void onWebClick(String cpsString);
        void onDefaultClick(String str);
    }
    public Callback callback;

    public void show(FragmentManager fragmentManager) {
        show(fragmentManager, title);
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        if (this.num == 1) { // 一个输入框
            final View view = inflater.inflate(R.layout.inputdialog_view, null);
            builder.setView(view).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (callback != null) {
                        EditText et = view.findViewById(R.id.inputDialog_et);
                        switch (type) {
                            case "cps":
                                callback.onCpsClick(et.getText().toString());
                                break;
                            case "opn":
                                callback.onWebClick(et.getText().toString());
                                break;
                            default:
                                callback.onDefaultClick(et.getText().toString());
                                break;
                        }
                    }
                }
            });
        }
        if (this.num == 2) {
            final View view = inflater.inflate(R.layout.inputdialog_two_view, null);
            EditText et_name = view.findViewById(R.id.inputDialog_et_name);
            EditText et_content = view.findViewById(R.id.inputDialog_et_content);
            if (type.equals("wtc")) {
                System.out.println("hi");
                et_name.setText(keyname);
                et_content.setText(keycontent);
//                et_name.setFocusableInTouchMode(false);
//                et_content.setFocusableInTouchMode(false);
                builder.setView(view).setNegativeButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Message msg = handler.obtainMessage();
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("want_delete", true);
                        msg.setData(bundle);
//                    msg.what = 1;
                        handler.sendMessage(msg);
                    }
                });
            } else {
                builder.setView(view).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
            }
            builder.setView(view).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
//                    if (type.equals("wtc")) {
//
//                    } else {
                        String name = et_name.getText().toString();
                        String content = et_content.getText().toString();
                        Message msg = handler.obtainMessage();
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("want_delete", false);
                        bundle.putString(KEY_NAME, name);
                        bundle.putString(KEY_CONTENT, content);
                        msg.setData(bundle);
                        handler.sendMessage(msg);
                    }
//                }
            });

        }

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Callback) {
            callback = (Callback) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement Callback!");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        callback = null;
    }
}
