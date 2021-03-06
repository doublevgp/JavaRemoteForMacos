package wgp.task2.Callbacks;

import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TouchCallback extends ItemTouchHelper.Callback {
    private boolean isEnableSwipe;
    private boolean isEnableDrag;
    private OnItemTouchCallbackListener itemTouchCallbackListener;

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            int dragFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            int swipeFlags = 0;
            return makeMovementFlags(dragFlags, swipeFlags);
        } else if (layoutManager instanceof LinearLayoutManager) { // LinearLayout
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            int orientation = linearLayoutManager.getOrientation(); // get Orientation
            int dragFlags = 0;
            int swipeFlags = 0;

            if (orientation == LinearLayoutManager.HORIZONTAL) { // Horizontal
                dragFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                swipeFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            } else if (orientation == LinearLayoutManager.VERTICAL) { // Vertical
                dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            }
            return makeMovementFlags(dragFlags, swipeFlags);
        }
        return 0;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        if (this.itemTouchCallbackListener != null) {
            this.itemTouchCallbackListener.onMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        }
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        if (this.itemTouchCallbackListener != null) {
            this.itemTouchCallbackListener.onSwiped(viewHolder.getAdapterPosition());
        }
    }

    /**
     * @return
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return isEnableDrag;
    }

    public void setEnableDrag(boolean enableDrag) {
        this.isEnableDrag = enableDrag;
    }

    public void setEnableSwipe(boolean enableSwipe) {
        this.isEnableSwipe = enableSwipe;
    }

    public interface OnItemTouchCallbackListener {
        /**
         * ?????????Item????????????????????????
         */
        void onSwiped(int adapterPosition);

        /**
         * ?????????Item??????????????????????????????(??????)
         * @param srcPosition    ?????????item???position
         * @param targetPosition ????????????Item???position
         * @return ????????????????????????????????????true?????????????????????????????????false
         */
        boolean onMove(int srcPosition, int targetPosition);
    }
}
