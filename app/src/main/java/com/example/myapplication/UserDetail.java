package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UserDetail extends AppCompatActivity {
    private EditText emailEditText, usernameEditText, passwordEditText, roleEditText;
    private Spinner genderSpinner;
    private Button updateButton, deleteButton;
    CreateDatabase databaseHelper;
    private String email;
    private ImageView backbt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userdetail_layout);

        backbt = findViewById(R.id.backbt);
        emailEditText = findViewById(R.id.emailEditText);
        genderSpinner = findViewById(R.id.genderSpinner);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        roleEditText = findViewById(R.id.roleEditText);
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);

        databaseHelper = new CreateDatabase(this);

        // Thiết lập adapter cho Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter);

        // Lấy email từ intent
        email = getIntent().getStringExtra("email");

        // Hiển thị thông tin người dùng
        Cursor cursor = databaseHelper.getUserByEmail(email);
        if (cursor.moveToFirst()) {
            emailEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow("email")));
            usernameEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow("username")));
            passwordEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow("password")));
            roleEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow("role")));

            String gender = cursor.getString(cursor.getColumnIndexOrThrow("gender"));
            if (gender.equals("Nam")) {
                genderSpinner.setSelection(0);
            } else {
                genderSpinner.setSelection(1);
            }
        }
        cursor.close();

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newGender = genderSpinner.getSelectedItem().toString().trim();
                Log.d("Gender Selected", newGender);
                String newUsername = usernameEditText.getText().toString();
                String newPassword = passwordEditText.getText().toString();
                String newRole = roleEditText.getText().toString();

                if (databaseHelper.updateUser(email,newGender, newUsername, newPassword, newRole)) {
                    Toast.makeText(UserDetail.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                } else {
                    Toast.makeText(UserDetail.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Xóa người dùng
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.deleteUser(email);
                Toast.makeText(UserDetail.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                finish();
                Intent intent = new Intent(UserDetail.this,AdminActivity.class);
                startActivity(intent);
            }
        });
        backbt.setOnClickListener(v -> {
            Intent intent = new Intent(UserDetail.this,AdminActivity.class);
            startActivity(intent);
        });
    }
}
