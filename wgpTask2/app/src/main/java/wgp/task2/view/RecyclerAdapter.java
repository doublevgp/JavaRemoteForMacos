package wgp.task2.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import wgp.task2.R;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private OnItemClickListener toMain;
    public interface OnItemClickListener {
        public void onItemClick(int postion);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.toMain = listener;
    }

    public String[] datas = null;

    public RecyclerAdapter(String[] datas) {
        this.datas = datas;
    }


    //创建新View，被LayoutManager所调用
    @Override

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycleritem, viewGroup, false);
        ViewHolder vh = new ViewHolder(view, new OnItemClickListener() {
            @Override
            public void onItemClick(int postion) {
                toMain.onItemClick(postion);
            }
        });

        return vh;
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.mTextView.setText(datas[position]);
    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return datas.length;
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTextView;
        private OnItemClickListener mListener;// 声明自定义的接

        public ViewHolder(View view, OnItemClickListener listener) {
            super(view);
            mListener = listener;
            // 为ItemView添加点击事件
            view.setOnClickListener(this);

            mTextView = (TextView) view.findViewById(R.id.text);
        }

        @Override
        public void onClick(View v) {
            // getpostion()为Viewholder自带的一个方法，用来获取RecyclerView当前的位置，将此作为参数，传出去
            mListener.onItemClick(getPosition());
        }
    }
}

