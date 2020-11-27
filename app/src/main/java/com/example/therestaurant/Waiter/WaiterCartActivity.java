package com.example.therestaurant.Waiter;

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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.therestaurant.Model.Cart;
import com.example.therestaurant.Prevalent.PrevalentWaiter;
import com.example.therestaurant.R;
import com.example.therestaurant.ViewHolder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.tasks.OnCompleteListener;

public class WaiterCartActivity extends AppCompatActivity
{
    Toolbar toolbar;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button NextProcessBtn;
    private TextView txtTotalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiter_cart);

        toolbar = findViewById(R.id.waiter_toolbar_cart);
        toolbar.setTitle("Cart");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.waiter_cart_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        NextProcessBtn = (Button) findViewById(R.id.waiter_next_process_btn);
        txtTotalAmount =(TextView) findViewById(R.id.waiter_total_price);
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

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");

        FirebaseRecyclerOptions<Cart> options =
                new FirebaseRecyclerOptions.Builder<Cart>()
                        .setQuery(cartListRef.child("Waiter View")
                                .child(PrevalentWaiter.CurrentOnlineUser.getPhone_no()).child("Products"),Cart.class)
                        .build();
        FirebaseRecyclerAdapter<Cart , CartViewHolder> adapter
                = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options)
        {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull final Cart model)
            {
                holder.txtProductQuantity.setText("Quantity = "+ model.getQuantity());
                holder.txtProductPrice.setText("Price = Rs."+ model.getPrice());
                holder.txtProductName.setText(model.getPname());

                holder.itemView.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        CharSequence options[] = new CharSequence[]
                                {
                                        "Edit",
                                        "Remove"
                                };
                        AlertDialog.Builder builder = new AlertDialog.Builder(WaiterCartActivity.this);
                        builder.setTitle("Cart Options:");

                        builder.setItems(options, new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int i)
                            {
                                if ( i == 0 )
                                {
                                    Intent intent = new Intent(WaiterCartActivity.this, WaiterProductDetailsActivity.class);
                                    intent.putExtra("pid",model.getPid());
                                    startActivity(intent);

                                }
                                if ( i == 1 )
                                {
                                    cartListRef.child("Waiter View")
                                            .child(PrevalentWaiter.CurrentOnlineUser.getPhone_no())
                                            .child("Products")
                                            .child(model.getPid())
                                            .removeValue()
                                            .addOnCompleteListener(new OnCompleteListener<Void>()
                                            {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task)
                                                {
                                                    if(task.isSuccessful())
                                                    {
                                                        Toast.makeText(WaiterCartActivity.this, "Items removed successfully.", Toast.LENGTH_SHORT).show();

                                                        Intent intent = new Intent(WaiterCartActivity.this, WaiterNavigationActivity.class);
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
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout,parent,false);
                CartViewHolder holder = new CartViewHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
