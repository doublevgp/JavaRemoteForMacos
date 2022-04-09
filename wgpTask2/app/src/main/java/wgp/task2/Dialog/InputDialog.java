package wgp.task2.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
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
    private Callback callback;

    public void show(FragmentManager fragmentManager) {
        show(fragmentManager, "Input String");
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
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
