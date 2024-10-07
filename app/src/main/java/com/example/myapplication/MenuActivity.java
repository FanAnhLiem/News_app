package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;


public class MenuActivity extends AppCompatActivity {
    private TextView loginText;
    private RelativeLayout loginRegisterLayout;
    private ImageView fbIcon, githubIcon, arrowIcon;
    private LinearLayout navHome, navMenu;
    private SearchView searchView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        searchView = findViewById(R.id.search);
        loginText = findViewById(R.id.loginText);
        loginRegisterLayout = findViewById(R.id.login_register);
        fbIcon = findViewById(R.id.fb);
        githubIcon = findViewById(R.id.github);
        arrowIcon = findViewById(R.id.arrowIcon);
        navHome = findViewById(R.id.nav_home);
        navMenu = findViewById(R.id.menu);

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Intent intent = new Intent(MenuActivity.this, SearchActivity.class);
                    startActivity(intent);
                    searchView.clearFocus();
                }
            }
        });

        handleLogin();
        handleFollowIcons();
        handleNavigation();
    }



    private void handleLogin() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        if (isLoggedIn) {
            String email = sharedPreferences.getString("user_email", "Email chưa đăng nhập");
            String username = sharedPreferences.getString("username", "Tài khoản");
            loginText.setText(email);

            loginRegisterLayout.setOnClickListener(v -> {
                Intent intent = new Intent(MenuActivity.this, AccountActivity.class);
                startActivity(intent);
            });
            arrowIcon.setOnClickListener(v -> {
                Intent intent = new Intent(MenuActivity.this, AccountActivity.class);
                startActivity(intent);
            });
        } else {
            loginText.setText("Đăng nhập / Đăng ký");
            loginRegisterLayout.setOnClickListener(v -> {
                Intent intent = new Intent(MenuActivity.this, Login.class);
                startActivity(intent);
            });
            arrowIcon.setOnClickListener(v -> {
                Intent arrow = new Intent(MenuActivity.this, Login.class);
                startActivity(arrow);
            });
        }
    }



    private void handleFollowIcons() {
        fbIcon.setOnClickListener(v -> {
            String facebookUrl = "https://www.facebook.com/profile.php?id=100028145915338";
            Intent fbIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl));
            startActivity(fbIntent);
        });

        githubIcon.setOnClickListener(v -> {
            String githubUrl = "https://github.com/FanAnhLiem";
            Intent githubIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(githubUrl));
            startActivity(githubIntent);
        });

    }

    private void handleNavigation() {
        navHome.setOnClickListener(v -> {
            Intent homeIntent = new Intent(MenuActivity.this, MainActivity.class);
            startActivity(homeIntent);
        });

        navMenu.setOnClickListener(v -> {
            Intent menuIntent = new Intent(MenuActivity.this, MenuActivity.class);
            startActivity(menuIntent);
        });
    }
}
