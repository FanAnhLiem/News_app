package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {
    private EditText emailEditText, usernameEditText, passwordEditText, confirmPasswordEditText;
    private Spinner genderSpinner;
    private Button registerButton;
    CreateDatabase databaseHelper;
    private ImageView backbt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_layout);

        backbt = findViewById(R.id.backbt);
        emailEditText = findViewById(R.id.email);
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        confirmPasswordEditText = findViewById(R.id.confirm_password);
        genderSpinner = findViewById(R.id.genderSpinner);
        registerButton = findViewById(R.id.regiterbt);

        databaseHelper = new CreateDatabase(this);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();
                String gender = genderSpinner.getSelectedItem().toString().trim();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(username) ||
                        TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
                    Toast.makeText(Register.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!isValidEmail(email)) {
                    Toast.makeText(Register.this, "Định dạng email không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    Toast.makeText(Register.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (databaseHelper.checkIfEmailExists(email)) {
                    Toast.makeText(Register.this, "Email đã tồn tại", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean isRegistered = databaseHelper.registerUser(email, gender, username, password);
                if (isRegistered) {
                    Toast.makeText(Register.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                    Intent loginIntent = new Intent(Register.this, Login.class);
                    startActivity(loginIntent);
                    finish();
                } else {
                    Toast.makeText(Register.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        backbt.setOnClickListener(v -> {
            Intent intent = new Intent(Register.this, Login.class);
            startActivity(intent);
        });
    }

    // Phương thức kiểm tra định dạng email
    private boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
