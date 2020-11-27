package com.example.therestaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{

    private static int SPASH_SCREEN =4000;
    Animation topAnim,bottomAnim;
    TextView restaurant;
    ImageView  restaurantlogo;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Animation
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        //Hooks
        restaurantlogo = findViewById(R.id.home_restaurant_logo);
        restaurant = findViewById(R.id.home_title);

        restaurantlogo.setAnimation(topAnim);
        restaurant.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                Intent intent = new Intent(MainActivity.this, SubMainActivity.class);
                startActivity(intent);
                finish();
            }
        },SPASH_SCREEN);
    }
}
