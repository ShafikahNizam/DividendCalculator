package com.example.mydividendcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.*;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    EditText etAmount, etRate, etMonths;
    Button btnCalculate;
    TextView tvResult;

    DrawerLayout drawerLayout;
    NavigationView navView;
    Toolbar toolbar;

    DecimalFormat df = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navView.setNavigationItemSelectedListener(this);

        etAmount = findViewById(R.id.etAmount);
        etRate = findViewById(R.id.etRate);
        etMonths = findViewById(R.id.etMonths);
        btnCalculate = findViewById(R.id.btnCalculate);
        tvResult = findViewById(R.id.tvResult);

        btnCalculate.setOnClickListener(v -> calculateDividend());
    }

    private void calculateDividend() {
        try {
            double amount = Double.parseDouble(etAmount.getText().toString());
            double rate = Double.parseDouble(etRate.getText().toString());
            int months = Integer.parseInt(etMonths.getText().toString());

            if (months < 1 || months > 12) {
                Toast.makeText(this, "Months must be between 1 and 12", Toast.LENGTH_SHORT).show();
                return;
            }

            double monthly = (rate / 100 / 12) * amount;
            double total = monthly * months;

            tvResult.setText("Monthly Dividend = RM " + df.format(monthly)
                    + "\nYear-end total dividend = RM " + df.format(total));
        } catch (Exception e) {
            Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawers();
        if (item.getItemId() == R.id.nav_about) {
            startActivity(new Intent(this, AboutActivity.class));
        }
        return true;
    }
}
