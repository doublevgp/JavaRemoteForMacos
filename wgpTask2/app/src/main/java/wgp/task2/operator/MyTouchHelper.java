package wgp.task2.operator;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;

import wgp.task2.Callbacks.TouchCallback;

public class MyTouchHelper extends ItemTouchHelper {
    private TouchCallback touchCallback;
    public MyTouchHelper(TouchCallback callback) {
        super(callback);
        this.touchCallback = callback;
    }
    public void setEnableDrag(boolean enableDrag) {
        touchCallback.setEnableDrag(enableDrag);
    }

    public void setEnableSwipe(boolean enableSwipe) {
        touchCallback.setEnableSwipe(enableSwipe);
    }
}
