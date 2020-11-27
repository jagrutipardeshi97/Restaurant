package com.example.therestaurant.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.therestaurant.R;

public class AdminReceivedOrderActivity extends AppCompatActivity
{

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_received_order);

        toolbar = findViewById(R.id.admin_toolbar_received_order);
        toolbar.setTitle("Received Orders");
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
}
