package com.example.qrgen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder>  {

    private List<Article> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    MainAdapter(Context context, List<Article> data){
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

        Article name = mData.get(position);
        Article id = mData.get(position);
        Article amount = mData.get(position);
        Article price = mData.get(position);
        mData.get(holder.getAdapterPosition());
        holder.myTextViewPrice.setText(price.getPrice());
        holder.myTextViewName.setText(name.getName());
        holder.myTextViewColor.setText(id.getColor());
        holder.myTextViewAmount.setText(amount.getAmount());

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final TextView myTextViewAmount;
        private final TextView myTextViewName;
        private final TextView myTextViewColor;
        private final TextView myTextViewPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            myTextViewPrice = itemView.findViewById(R.id.tv_price);
            myTextViewName = itemView.findViewById(R.id.tv_name);
            myTextViewColor = itemView.findViewById(R.id.tv_color);
            myTextViewAmount = itemView.findViewById(R.id.tv_row_elem);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) mClickListener.onItemClick(v, this.getAdapterPosition());
        }
    }

    Article getItem(int id) {
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
