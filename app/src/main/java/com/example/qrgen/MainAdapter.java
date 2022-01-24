package com.example.qrgen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder>  {

    private List<String> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    MainAdapter(Context context, List<String> data){
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_main, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {
        String animal = mData.get(position);
        mData.get(holder.getAdapterPosition());
        holder.myTextView.setText(animal);
        //        holder.myTextView.setText(mData.get(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final TextView myTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.tv_row_elem);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) mClickListener.onItemClick(v, this.getAdapterPosition());
        }
    }

    String getItem(int id) {
        return mData.get(id);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
