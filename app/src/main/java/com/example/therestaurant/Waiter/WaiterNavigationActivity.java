package com.example.therestaurant.Waiter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.therestaurant.Admin.AdminAddProductActivity;
import com.example.therestaurant.Admin.AdminDateWiseBillActivity;
import com.example.therestaurant.Admin.AdminNavigationActivity;
import com.example.therestaurant.Admin.AdminReceivedOrderActivity;
import com.example.therestaurant.Admin.AdminRemoveProductActivity;
import com.example.therestaurant.Admin.Fragment_menu;
import com.example.therestaurant.R;
import com.example.therestaurant.SubMainActivity;
import com.google.android.material.navigation.NavigationView;

public class WaiterNavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiter_navigation);

        toolbar =findViewById(R.id.waiter_toolbar);
        toolbar.setTitle("Restaurant");
        setSupportActionBar(toolbar);

        drawerLayout =findViewById(R.id.waiter_drawer_layout);
        navigationView =findViewById(R.id.waiter_nav_view);

        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle =new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        View headerView =navigationView.getHeaderView(0);

        if(savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.waiter_fragment_container,new Fragment_table()).commit();
            navigationView.setCheckedItem(R.id.waiter_products_list);
        }
    }

    @Override
    public void onBackPressed()
    {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawers();
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
    {
        switch (menuItem.getItemId())
        {
            case R.id.waiter_tables:
                getSupportFragmentManager().beginTransaction().replace(R.id.waiter_fragment_container,new Fragment_table()).commit();
                break;
            case R.id.waiter_cart:
                Intent addintent =new Intent(WaiterNavigationActivity.this, WaiterCartActivity.class);
                startActivity(addintent);
                break;
            case R.id.waiter_logout:
                Intent logoutintent =new Intent(WaiterNavigationActivity.this, SubMainActivity.class);
                logoutintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(logoutintent);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
