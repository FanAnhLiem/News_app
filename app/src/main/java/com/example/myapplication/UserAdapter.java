package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class UserAdapter extends BaseAdapter {
    private Context context;
    private List<User> userList;

    // Constructor
    public UserAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return userList.get(position).getID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Sử dụng LayoutInflater để ánh xạ view từ file XML
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false);
        }

        // Lấy thông tin người dùng hiện tại
        User user = (User) getItem(position);

        // Ánh xạ các TextView trong user_item.xml
        TextView emailTextView = convertView.findViewById(R.id.detail_email);
        TextView usernameTextView = convertView.findViewById(R.id.detail_username);
        TextView roleTextView = convertView.findViewById(R.id.detail_role);
        TextView genderTextView = convertView.findViewById(R.id.detail_gender);
        Button editButton = convertView.findViewById(R.id.edit_button);

        // Set dữ liệu cho các TextView
        emailTextView.setText(user.getEmail());
        usernameTextView.setText(user.getUsername());
        roleTextView.setText(user.getRole());
        genderTextView.setText(user.getGender());

        // Xử lý sự kiện khi nhấn nút "Chỉnh sửa"
        editButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, UserDetail.class);
            intent.putExtra("email", user.getEmail());
            context.startActivity(intent);
        });

        return convertView;
    }
}

