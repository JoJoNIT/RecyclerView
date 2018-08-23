package com.wzlab.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class LinearAdapter extends RecyclerView.Adapter<LinearAdapter.LinearViewHolder> {
    private Context mContext;
   private  OnItemClickListener mlistener;
   String[] label ;
    public LinearAdapter(Context context,String[] label, OnItemClickListener listener){
        this.mContext = context;
        this.mlistener = listener;
        this.label = label;
    }

    @NonNull
    @Override
    public LinearAdapter.LinearViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new LinearViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_linear_item,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(LinearViewHolder viewHolder, final int i) {
        viewHolder.textView.setText(label[i]);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               mlistener.onClick(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return 3;
    }
    class LinearViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_title);
        }
    }
    public interface OnItemClickListener{
        void onClick(int pos);
    }
}
