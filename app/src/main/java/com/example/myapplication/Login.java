package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Login extends AppCompatActivity {
    ImageView backbt;
    EditText emailEditText, passwordEditText;
    Button loginButton;
    CheckBox rememberMeCheckbox;
    TextView registerTextView;
    CreateDatabase databaseHelper;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        // Ánh xạ view từ layout
        backbt = findViewById(R.id.backbt1);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginbt);
        rememberMeCheckbox = findViewById(R.id.Rmbme);
        registerTextView = findViewById(R.id.signupNow);

        // Khởi tạo SharedPreferences
        sharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // Khởi tạo database
        databaseHelper = new CreateDatabase(this);

        // Kiểm tra nếu có thông tin lưu trong SharedPreferences
        checkSharedPreferences();

        backbt.setOnClickListener(view -> {
            Intent intent = new Intent(Login.this, MenuActivity.class);
            startActivity(intent);
        });

        // Sự kiện nhấn nút đăng nhập
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Kiểm tra nếu người dùng đã nhập email và mật khẩu chưa
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Login.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                User user = databaseHelper.checkLogin(email, password);
                if (user != null) {
                    Log.d("LoginActivity", "Username: " + user.getUsername());
                    editor.putBoolean("isLoggedIn", true);
                    editor.putString("user_email", email);
                    editor.putString("username", user.getUsername());
                    editor.apply();

                    // Lưu email và mật khẩu nếu checkbox được chọn
                    if (rememberMeCheckbox.isChecked()) {
                        editor.putString("email", email);
                        editor.putString("password", password);
                        editor.putBoolean("remember", true);
                        editor.apply();
                    }

                    // Điều hướng dựa trên vai trò của người dùng
                    if (user.getRole().equals("admin")) {
                        Intent adminIntent = new Intent(Login.this, AdminActivity.class);
                        startActivity(adminIntent);
                        finish();
                    } else {
                        Intent userIntent = new Intent(Login.this, MenuActivity.class);
                        startActivity(userIntent);
                        finish();
                    }
                } else {
                    Toast.makeText(Login.this, "Sai thông tin đăng nhập", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // Sự kiện nhấn vào "Đăng ký" để chuyển sang RegisterActivity
        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(Login.this, Register.class);
                startActivity(registerIntent);
            }
        });
    }

    private void checkSharedPreferences() {
        boolean remember = sharedPreferences.getBoolean("remember", false);
        if (remember) {
            String savedEmail = sharedPreferences.getString("email", "");
            String savedPassword = sharedPreferences.getString("password", "");
            emailEditText.setText(savedEmail);
            passwordEditText.setText(savedPassword);
            rememberMeCheckbox.setChecked(true);
        }
    }
}

