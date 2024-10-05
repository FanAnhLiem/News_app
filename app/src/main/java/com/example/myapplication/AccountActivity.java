package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AccountActivity extends AppCompatActivity {
    private ImageView backbt, nextbt, logoutbt;
    private TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.account_activity);

        name = findViewById(R.id.name);
        backbt = findViewById(R.id.backbt);
        nextbt = findViewById(R.id.nextbt);
        logoutbt = findViewById(R.id.logoutbt);

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "Tài khoản");
        String email = sharedPreferences.getString("user_email", "Email chưa đăng nhập");
        name.setText(email);

        backbt.setOnClickListener(v -> {
            Intent intent = new Intent(AccountActivity.this, MenuActivity.class);
            startActivity(intent);
        });

        nextbt.setOnClickListener(v -> {
            Intent intent = new Intent(AccountActivity.this, EditActivity.class);
            intent.putExtra("email", email);
            startActivity(intent);
        });

        logoutbt.setOnClickListener(v -> {
            logout();
        });

    }

    public void logout() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", false);
        editor.apply();
        Intent intent = new Intent(AccountActivity.this, MenuActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}