package com.example.therestaurant.Waiter;

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
import com.example.therestaurant.ViewHolder.MenuViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Fragment_table extends Fragment
{
    private View ProductView;
    private RecyclerView myProductsList;
    private DatabaseReference Productref;
    public Fragment_table()
    {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        ProductView = inflater.inflate(R.layout.activity_fragment_table,container,false);
        myProductsList = (RecyclerView) ProductView.findViewById(R.id.waiter_products_list);
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
        FirebaseRecyclerAdapter<Products, MenuViewHolder> adapter =
                new FirebaseRecyclerAdapter<Products, MenuViewHolder>(options)
                {
                    @Override
                    protected void onBindViewHolder(@NonNull MenuViewHolder holder, int position, @NonNull final Products model)
                    {
                        holder.txtproductName.setText("Name = "+model.getPname());
                        holder.txtproductPrice.setText("Price = Rs. " + model.getPrice());
                        holder.txtproductDescription.setText("Description = "+model.getDescription());

                        Picasso.get().load(model.getImage()).into(holder.imageView);

                        holder.itemView.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                Intent intent = new Intent(getActivity(), WaiterProductDetailsActivity.class);
                                intent.putExtra("pid",model.getPid());
                                startActivity(intent);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_list,parent,false);
                        MenuViewHolder holder = new MenuViewHolder(view);
                        return holder;
                    }
                };
        myProductsList.setAdapter(adapter);
        adapter.startListening();
    }
}
