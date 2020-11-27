package com.example.therestaurant.Admin;

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
import android.widget.EditText;

import com.example.therestaurant.R;
import com.example.therestaurant.SubMainActivity;
import com.google.android.material.navigation.NavigationView;

public class AdminNavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_navigation);

        toolbar =findViewById(R.id.admin_toolbar);
        toolbar.setTitle("Restaurant");
        setSupportActionBar(toolbar);

        drawerLayout =findViewById(R.id.admin_drawer_layout);
        navigationView =findViewById(R.id.admin_nav_view);

        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle =new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        View headerView =navigationView.getHeaderView(0);


        if(savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.admin_fragment_container,new Fragment_menu()).commit();
            navigationView.setCheckedItem(R.id.admin_products_list);
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
            case R.id.admin_menu:
                getSupportFragmentManager().beginTransaction().replace(R.id.admin_fragment_container,new Fragment_menu()).commit();
                break;
            case R.id.admin_add_product:
                Intent addintent =new Intent(AdminNavigationActivity.this, AdminAddProductActivity.class);
                startActivity(addintent);
                break;
            case R.id.admin_remove_product:
                Intent removeintent =new Intent(AdminNavigationActivity.this, AdminRemoveProductActivity.class);
                startActivity(removeintent);
                break;
            case R.id.admin_received_order:
                Intent orderintent =new Intent(AdminNavigationActivity.this, AdminReceivedOrderActivity.class);
                startActivity(orderintent);
                break;
            case R.id.admin_datewise_bill:
                Intent billintent =new Intent(AdminNavigationActivity.this, AdminDateWiseBillActivity.class);
                startActivity(billintent);
                break;
            case R.id.admin_logout:
                Intent logoutintent =new Intent(AdminNavigationActivity.this, SubMainActivity.class);
                logoutintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(logoutintent);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
