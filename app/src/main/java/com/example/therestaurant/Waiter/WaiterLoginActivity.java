package com.example.therestaurant.Waiter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.therestaurant.Model.Waiter;
import com.example.therestaurant.Prevalent.PrevalentWaiter;
import com.example.therestaurant.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WaiterLoginActivity extends AppCompatActivity
{
    EditText mphone,mPassword;
    Button mLoginBtn;
    TextView mCreateBtn;
    TextView forgotPassword;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiter_login);

        mphone = findViewById(R.id.login_phone_waiter);
        mPassword = findViewById(R.id.login_password_waiter);
        progressBar = findViewById(R.id.progressBar_login_waiter);
        mLoginBtn = findViewById(R.id.login_waiter);
        forgotPassword = findViewById(R.id.forgot_password_waiter);
        mCreateBtn = findViewById(R.id.Register_here_waiter);

        mLoginBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final String phone = mphone.getText().toString().trim();
                final String password = mPassword.getText().toString().trim();
                if (TextUtils.isEmpty(phone))
                {
                    mphone.setError("Phone id is required");
                    return;
                }
                if (TextUtils.isEmpty(password))
                {
                    mPassword.setError("Password is required");
                    return;
                }
                if (password.length() < 8)
                {
                    mPassword.setError("Password must be  greater than or equal to 8 characters.");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);


                final DatabaseReference RootRef;
                RootRef = FirebaseDatabase.getInstance().getReference();
                RootRef.addListenerForSingleValueEvent(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        if(dataSnapshot.child("Waiter").child(phone).exists())
                        {
                            Waiter userData =dataSnapshot.child("Waiter").child(phone).getValue(Waiter.class);

                            if(userData.getPhone_no().equals(phone))
                            {
                                if(userData.getPassword().equals(password))
                                {
                                    Toast.makeText(WaiterLoginActivity.this, "Welcome, Logged in successfully" , Toast.LENGTH_SHORT).show();
                                    Intent createintent =new Intent(WaiterLoginActivity.this, WaiterNavigationActivity.class);
                                    PrevalentWaiter.CurrentOnlineUser = userData;
                                    startActivity(createintent);
                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(WaiterLoginActivity.this, "Password is incorrect.", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.INVISIBLE);
                                }
                            }
                        }
                        else
                        {
                            Toast.makeText(WaiterLoginActivity.this, "Account with this  " + phone + "number do not exist", Toast.LENGTH_SHORT).show();
                            Toast.makeText(WaiterLoginActivity.this, "You need to create a new account.", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError)
                    {

                    }
                });
            }
        });
        mCreateBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent createintent =new Intent(WaiterLoginActivity.this, WaiterRegistrationActivity.class);
                startActivity(createintent);
                finish();
            }
        });
    }
}
