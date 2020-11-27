package com.example.therestaurant.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.therestaurant.Interface.ItemClickListner;
import com.example.therestaurant.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtproductName,txtproductPrice;
    public CircleImageView imageView;
    public ItemClickListner listner;

    public ProductViewHolder(@NonNull View itemView)
    {
        super(itemView);

        imageView = (CircleImageView) itemView.findViewById(R.id.product_image);
        txtproductName = (TextView) itemView.findViewById(R.id.product_name);
        txtproductPrice = (TextView) itemView.findViewById(R.id.product_price);

    }

    public void setItemClickListner(ItemClickListner listner)
    {
        this.listner = listner;
    }
    @Override
    public void onClick(View v)
    {
        listner.onClick(v,getAdapterPosition(),false);
    }
}
