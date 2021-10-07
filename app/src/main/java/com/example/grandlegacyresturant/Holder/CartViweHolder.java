package com.example.grandlegacyresturant.Holder;


import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.grandlegacyresturant.Interfaces.ItemClickListner;
import com.example.grandlegacyresturant.R;


public class CartViweHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtFoodName, txtPrice,TxtQuantity;

    private ItemClickListner itemClickListner;

    public CartViweHolder(View itemView) {
        super(itemView);

        txtFoodName = itemView.findViewById(R.id.order_id);
        txtPrice = itemView.findViewById(R.id.order_status);
        TxtQuantity = itemView.findViewById(R.id.total_price);

    }

    @Override
    public void onClick(View v) {
        itemClickListner.onClick(v,getAdapterPosition(),false);
    }

    public void setItemClickListner(ItemClickListner itemClickListner) {
        this.itemClickListner = itemClickListner;
    }
}
