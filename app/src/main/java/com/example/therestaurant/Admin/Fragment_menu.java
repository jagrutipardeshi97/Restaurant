package com.example.therestaurant.Admin;

import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.therestaurant.Model.Products;
import com.example.therestaurant.R;
import com.example.therestaurant.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Fragment_menu extends Fragment
{
    private View ProductView;
    private RecyclerView myProductsList;
    private DatabaseReference Productref;
    public Fragment_menu ()
    {
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        ProductView = inflater.inflate(R.layout.activity_fragment_menu,container,false);
        myProductsList = (RecyclerView) ProductView.findViewById(R.id.admin_products_list);
        myProductsList.setLayoutManager(new LinearLayoutManager(getContext()));
        Productref = FirebaseDatabase.getInstance().getReference().child("Products");
        return ProductView;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        FirebaseRecyclerOptions<Products> options =
                new FirebaseRecyclerOptions.Builder<Products>()
                        .setQuery(Productref, Products.class)
                        .build();
        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter =
                new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options)
                {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Products model)
                    {
                        holder.txtproductName.setText(model.getPname());
                        holder.txtproductPrice.setText("Price = Rs. " + model.getPrice());
                        Picasso.get().load(model.getImage()).into(holder.imageView);

                        holder.itemView.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list,parent,false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;
                    }
                };
        myProductsList.setAdapter(adapter);
        adapter.startListening();
    }
}
