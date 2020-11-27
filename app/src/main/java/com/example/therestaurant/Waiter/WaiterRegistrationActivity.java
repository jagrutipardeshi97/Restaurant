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

import com.example.therestaurant.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class WaiterRegistrationActivity extends AppCompatActivity
{

    private Button RegisterButton;
    private TextView mLoginBtn;
    private EditText InputNam, InputPhoneNo, InputPwd ,InputConfirmPassword ;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiter_registration);

        RegisterButton = (Button) findViewById(R.id.create_acc_btn_waiter);
        mLoginBtn =findViewById(R.id.Login_here_waiter);
        InputNam = (EditText) findViewById(R.id.register_username_waiter);
        InputPhoneNo = (EditText) findViewById(R.id.register_phone_number_waiter);
        InputPwd = (EditText) findViewById(R.id.register_password_waiter);
        InputConfirmPassword = (EditText) findViewById(R.id.register_confirm_password_waiter);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_registration_waiter);

        RegisterButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                final String name = InputNam.getText().toString();
                final String phone_no = InputPhoneNo.getText().toString();
                final String password = InputPwd.getText().toString();
                final String confirm_password = InputConfirmPassword.getText().toString();
                char[] array = phone_no.toCharArray();

                if (TextUtils.isEmpty(name))
                {
                    InputNam.setError("Name is required");
                    return;
                }
                if (TextUtils.isEmpty(phone_no))
                {
                    InputPhoneNo.setError("Phone number is required");
                    return;
                }
                if (TextUtils.isEmpty(password))
                {
                    InputPwd.setError("Password is required");
                    return;
                }
                if (TextUtils.isEmpty(confirm_password))
                {
                    InputConfirmPassword.setError("Password is required");
                    return;
                }
                if (array[0] != '9')
                {
                    if(array[0] != '8')
                    {
                        if(array[0] != '7')
                        {
                            InputPhoneNo.setError("Please Check entered Phone number .Enter valid Phone number");
                            return;
                        }
                    }
                }
                if (phone_no.length() < 10 || phone_no.length() > 10)
                {
                    InputPhoneNo.setError("Enter Valid Phone Number");
                    return;
                }

                if (password.length() < 8)
                {
                    InputPwd.setError("Password must be  greater than or equal to 8 characters.");
                    return;
                }
                if(!confirm_password.equals(password))
                {
                    InputConfirmPassword.setError("Password do not match.");
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
                        if (!(dataSnapshot.child("Waiter").child(phone_no).exists()))
                        {
                            HashMap<String, Object> userdataMap = new HashMap<>();
                            userdataMap.put("password",password);
                            userdataMap.put("phone_no",phone_no);
                            userdataMap.put("name",name);

                            RootRef.child("Waiter").child(phone_no).updateChildren(userdataMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>()
                                    {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task)
                                        {
                                            if(task.isSuccessful())
                                            {
                                                Toast.makeText(WaiterRegistrationActivity.this, "Congratulations, your account has been created.", Toast.LENGTH_SHORT).show();
                                                Intent intent =new Intent(WaiterRegistrationActivity.this, WaiterLoginActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                            else
                                            {
                                                progressBar.setVisibility(View.INVISIBLE);
                                                Toast.makeText(WaiterRegistrationActivity.this, "Network error...Please try again", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                        else
                        {
                            Toast.makeText(WaiterRegistrationActivity.this, "This " + phone_no + "already exists", Toast.LENGTH_SHORT).show();
                            Toast.makeText(WaiterRegistrationActivity.this, "Please try again using another phone number", Toast.LENGTH_SHORT).show();
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

        mLoginBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent loginintent =new Intent(WaiterRegistrationActivity.this,WaiterLoginActivity.class);
                startActivity(loginintent);
                finish();
            }
        });
    }
}
