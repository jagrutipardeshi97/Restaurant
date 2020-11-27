package com.example.therestaurant.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.therestaurant.Model.Products;
import com.example.therestaurant.R;
import com.example.therestaurant.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class AdminRemoveProductActivity extends AppCompatActivity
{
    private DatabaseReference Productref;
    private RecyclerView myProductsList;
    RecyclerView.LayoutManager layoutManager;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_remove_product);

        Productref = FirebaseDatabase.getInstance().getReference().child("Products");
        myProductsList = findViewById(R.id.admin_products_remove_list);
        myProductsList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        myProductsList.setLayoutManager(layoutManager);
        toolbar = findViewById(R.id.admin_toolbar_remove_product);
        toolbar.setTitle("Remove Product");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if(item.getItemId() ==android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart()
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
                                CharSequence options[] = new CharSequence[]
                                        {
                                                "NO",
                                                "Delete"
                                        };
                                AlertDialog.Builder builder = new AlertDialog.Builder(AdminRemoveProductActivity.this);
                                builder.setTitle("Want to Remove Product:");

                                builder.setItems(options, new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i)
                                    {
                                        if(i == 0)
                                        {
                                            dialog.dismiss();
                                        }
                                        if(i == 1)
                                        {
                                            Productref.child(model.getPname())
                                                    .removeValue()
                                                    .addOnCompleteListener(new OnCompleteListener<Void>()
                                                    {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task)
                                                        {
                                                            if(task.isSuccessful())
                                                            {
                                                                Toast.makeText(AdminRemoveProductActivity.this, "Product deleted Successfully.", Toast.LENGTH_SHORT).show();
                                                                Intent intent = new Intent(AdminRemoveProductActivity.this,AdminNavigationActivity.class);
                                                                startActivity(intent);
                                                            }
                                                        }
                                                    });
                                        }
                                    }
                                });
                                builder.show();
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
