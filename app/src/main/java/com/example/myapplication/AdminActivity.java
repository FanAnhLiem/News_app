package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {
    ListView userListView;
    CreateDatabase databaseHelper;
    Button logoutButton;
    List<User> userList;
    UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_layout); // Sử dụng giao diện admin_layout

        // Ánh xạ các thành phần từ layout
        userListView = findViewById(R.id.userListView);
        logoutButton = findViewById(R.id.logoutButton);
        databaseHelper = new CreateDatabase(this);

        loadUserList();

        // Xử lý sự kiện khi Admin nhấn nút "Đăng xuất"
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xóa thông tin đăng nhập khỏi SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isLoggedIn", false);  // Đặt trạng thái đăng nhập thành false
                editor.putString("role", ""); // Xóa vai trò của người dùng (admin hoặc user)
                editor.apply();

                Intent logoutIntent = new Intent(AdminActivity.this, Login.class);
                logoutIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(logoutIntent);

                finish();
            }
        });

    }

    // Hàm load danh sách người dùng từ database
    private void loadUserList() {
        Cursor cursor = databaseHelper.getAllUsers();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Không có người dùng nào!", Toast.LENGTH_SHORT).show();
            return;
        }

        userList = new ArrayList<>();
        while (cursor.moveToNext()) {
            int ID = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
            String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
            String role = cursor.getString(cursor.getColumnIndexOrThrow("role"));
            String gender = cursor.getString(cursor.getColumnIndexOrThrow("gender"));
            String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));

            User user = new User(ID, email, gender, username, password, role);
            userList.add(user);
        }

        // Cài đặt adapter cho ListView
        userAdapter = new UserAdapter(this, userList);
        userListView.setAdapter(userAdapter);
    }

}
