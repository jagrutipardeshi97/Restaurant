package com.example.therestaurant.Admin;

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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

public class AdminRegistrationActivity extends AppCompatActivity {

    private Button RegisterButton;
    private TextView mLoginBtn;
    private EditText InputNam, InputPhoneNo, InputPwd ,InputConfirmPassword ;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_registration);

        RegisterButton = (Button) findViewById(R.id.admin_create_acc_btn);
        mLoginBtn =findViewById(R.id.admin_Login_here);
        InputNam = (EditText) findViewById(R.id.admin_register_username);
        InputPhoneNo = (EditText) findViewById(R.id.admin_register_phone_number);
        InputPwd = (EditText) findViewById(R.id.admin_register_password);
        InputConfirmPassword = (EditText) findViewById(R.id.admin_register_confirm_password);
        progressBar = (ProgressBar) findViewById(R.id.admin_progressBar_registration);

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
                        if (!(dataSnapshot.child("Admin").child(phone_no).exists()))
                        {
                            HashMap<String, Object> userdataMap = new HashMap<>();
                            userdataMap.put("password",password);
                            userdataMap.put("phone_no",phone_no);
                            userdataMap.put("name",name);

                            RootRef.child("Admin").child(phone_no).updateChildren(userdataMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>()
                                    {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task)
                                        {
                                            if(task.isSuccessful())
                                            {
                                                Toast.makeText(AdminRegistrationActivity.this, "Congratulations farmers, your account has been created.", Toast.LENGTH_SHORT).show();
                                                Intent intent =new Intent(AdminRegistrationActivity.this, AdminLoginActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                            else
                                            {
                                                progressBar.setVisibility(View.INVISIBLE);
                                                Toast.makeText(AdminRegistrationActivity.this, "Network error...Please try again", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                        else
                        {
                            Toast.makeText(AdminRegistrationActivity.this, "This " + phone_no + "already exists", Toast.LENGTH_SHORT).show();
                            Toast.makeText(AdminRegistrationActivity.this, "Please try again using another phone number", Toast.LENGTH_SHORT).show();
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
                Intent loginintent =new Intent(AdminRegistrationActivity.this,AdminLoginActivity.class);
                startActivity(loginintent);
                finish();
            }
        });
    }
}
