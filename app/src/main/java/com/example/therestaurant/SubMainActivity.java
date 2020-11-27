package com.example.therestaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.therestaurant.Admin.AdminLoginActivity;
import com.example.therestaurant.Waiter.WaiterLoginActivity;


public class SubMainActivity extends AppCompatActivity
{
    TextView kitchen,waiter,admin;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_main);

        kitchen = findViewById(R.id.home_cheff);
        waiter = findViewById(R.id.home_waiter);
        admin = findViewById(R.id.home_admin);

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubMainActivity.this, AdminLoginActivity.class);
                startActivity(intent);
            }
        });
        waiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubMainActivity.this, WaiterLoginActivity.class);
                startActivity(intent);
            }
        });
        kitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubMainActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
