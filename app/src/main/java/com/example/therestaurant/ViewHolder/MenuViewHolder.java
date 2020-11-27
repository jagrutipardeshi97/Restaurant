package com.example.therestaurant.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.therestaurant.Interface.ItemClickListner;
import com.example.therestaurant.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtproductName,txtproductPrice,txtproductDescription;
    public CircleImageView imageView;
    public ItemClickListner listner;

    public MenuViewHolder(@NonNull View itemView)
    {
        super(itemView);

        imageView = (CircleImageView) itemView.findViewById(R.id.waiter_product_image_menu);
        txtproductName = (TextView) itemView.findViewById(R.id.waiter_product_name_menu);
        txtproductPrice = (TextView) itemView.findViewById(R.id.waiter_product_price_menu);
        txtproductDescription = (TextView) itemView.findViewById(R.id.waiter_product_description_menu);
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
