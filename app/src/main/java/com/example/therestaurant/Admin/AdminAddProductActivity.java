package com.example.therestaurant.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.example.therestaurant.R;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminAddProductActivity extends AppCompatActivity
{
    private String CategoryName,Description,Price,Pname,saveCurrentDate,saveCurrentTime;
    private Button AddNewProductBtn;
    private ImageView Input_product_image;
    private EditText Input_productname,Input_productDescription,Input_productPrice;
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    private String productRamdomKey, downloadImageurl;
    private StorageReference ProductImagesRef;
    private DatabaseReference ProductsRef;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_product);

        ProductImagesRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");

        AddNewProductBtn =(Button) findViewById (R.id.admin_add_details);
        Input_product_image =(ImageView) findViewById(R.id.admin_Select_product_image);
        Input_productname =(EditText) findViewById(R.id.admin_product_name);
        Input_productDescription =(EditText) findViewById(R.id.admin_product_description);
        Input_productPrice=(EditText) findViewById(R.id.admin_product_price);
        progressBar = findViewById(R.id.admin_progressBaraddproduct);

        Input_product_image.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                OpenGallery();
            }
        });
        AddNewProductBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ValidateProductData();
            }
        });
    }

    private void OpenGallery()
    {
        Intent galleryintent = new Intent();
        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        startActivityForResult(galleryintent,GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GalleryPick && resultCode ==RESULT_OK && data != null)
        {
            ImageUri = data.getData();
            Input_product_image.setImageURI(ImageUri);
        }
    }

    private void  ValidateProductData()
    {
        Description = Input_productDescription.getText().toString();
        Price = Input_productPrice.getText().toString();
        Pname = Input_productname.getText().toString();

        if(ImageUri == null)
        {
            Toast.makeText(this, "Product image is mandatory ...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Description))
        {
            Toast.makeText(this, "Please write product description ...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Price))
        {
            Toast.makeText(this, "Please write product price ...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Pname))
        {
            Toast.makeText(this, "Please write product name ...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            StoreProductInformation();
        }
    }

    private void StoreProductInformation()
    {
        progressBar.setVisibility(View.VISIBLE);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate =currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime =currentTime.format(calendar.getTime());

        productRamdomKey = Pname;

        final StorageReference filePath =ProductImagesRef.child(ImageUri.getLastPathSegment() + productRamdomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                String message = e.toString();
                Toast.makeText(AdminAddProductActivity.this, "Error: "+ message, Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
        {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                Toast.makeText(AdminAddProductActivity.this, "Product Image uploaded Successfully...", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>()
                {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                    {
                        if(!task.isSuccessful())
                        {
                            throw task.getException();
                        }
                        downloadImageurl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task)
                    {
                        if(task.isSuccessful())
                        {
                            downloadImageurl = task.getResult().toString();
                            Toast.makeText(AdminAddProductActivity.this, "Got Product image Url Successfully...", Toast.LENGTH_SHORT).show();

                            SaveProductInfoToDatabase();
                        }
                    }
                });
            }
        });
    }

    private void SaveProductInfoToDatabase()
    {
        HashMap<String,Object> productMap = new HashMap<>();
        progressBar.setVisibility(View.VISIBLE);

        productMap.put("pid",productRamdomKey);
        productMap.put("date",saveCurrentDate);
        productMap.put("time",saveCurrentTime);
        productMap.put("description",Description);
        productMap.put("image",downloadImageurl);
        productMap.put("category",CategoryName);
        productMap.put("price",Price);
        productMap.put("pname",Pname);

        ProductsRef.child(productRamdomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if(task.isSuccessful())
                        {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(AdminAddProductActivity.this, "Product is added Successfully...", Toast.LENGTH_SHORT).show();
                            Intent createintent =new Intent(AdminAddProductActivity.this, AdminNavigationActivity.class);
                            startActivity(createintent);
                            finish();
                        }

                        else
                        {
                            progressBar.setVisibility(View.INVISIBLE);
                            String message =task.getException().toString();
                            Toast.makeText(AdminAddProductActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
