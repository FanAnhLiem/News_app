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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EditActivity extends AppCompatActivity {
    private TextView emailA, passwordA, usernameA, genderA;
    private ImageView editpass, editusername, editgender, closepass, closename,closegender, backbt;
    private LinearLayout passf, namef, genderf;
    private Spinner gender_update;
    private EditText pass_update, name_update;
    private Button updatebt;
    CreateDatabase databaseHelper;
    private String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.editaccount_activity);

        backbt = findViewById(R.id.backbt);
        updatebt = findViewById(R.id.updatebt);
        closepass = findViewById(R.id.closepass);
        closename = findViewById(R.id.closename);
        closegender = findViewById(R.id.closegender);
        emailA = findViewById(R.id.emailAccount);
        passwordA = findViewById(R.id.passwordAccount);
        usernameA = findViewById(R.id.usernameAccount);
        genderA = findViewById(R.id.genderAccount);
        editpass = findViewById(R.id.editpassword);
        editusername = findViewById(R.id.editusername);
        editgender = findViewById(R.id.editgender);
        passf = findViewById(R.id.passf);
        namef = findViewById(R.id.namef);
        genderf = findViewById(R.id.genderf);
        gender_update = findViewById(R.id.gender_update);
        pass_update = findViewById(R.id.pass_update);
        name_update = findViewById(R.id.name_update);

        databaseHelper = new CreateDatabase(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender_update.setAdapter(adapter);

        email = getIntent().getStringExtra("email");

        Cursor cursor = databaseHelper.getUserByEmail(email);
        if (cursor.moveToFirst()) {
            emailA.setText(cursor.getString(cursor.getColumnIndexOrThrow("email")));
            usernameA.setText(cursor.getString(cursor.getColumnIndexOrThrow("username")));
            name_update.setText(cursor.getString(cursor.getColumnIndexOrThrow("username")));
            passwordA.setText(cursor.getString(cursor.getColumnIndexOrThrow("password")));
            pass_update.setText(cursor.getString(cursor.getColumnIndexOrThrow("password")));
            genderA.setText(cursor.getString(cursor.getColumnIndexOrThrow("gender")));
            String gender = cursor.getString(cursor.getColumnIndexOrThrow("gender"));
            if (gender.equals("Nam")) {
                gender_update.setSelection(0);
            } else {
                gender_update.setSelection(1);
            }
        }
        cursor.close();

        updatebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newGender = gender_update.getSelectedItem().toString().trim();
                Log.d("Gender Selected", newGender);
                String newUsername = name_update.getText().toString();
                String newPassword = pass_update.getText().toString();

                if (databaseHelper.userUpdate(email, newGender, newUsername, newPassword)) {
                    Toast.makeText(EditActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    passf.setVisibility(View.GONE);
                    namef.setVisibility(View.GONE);
                    genderf.setVisibility(View.GONE);
                    pass_update.setText("");
                    name_update.setText("");
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                } else {
                    Toast.makeText(EditActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });


        backbt.setOnClickListener(v -> {
            Intent intent = new Intent(EditActivity.this, AccountActivity.class);
            startActivity(intent);
        });

        editpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passf.setVisibility(View.VISIBLE);
            }
        });

        editusername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                namef.setVisibility(View.VISIBLE);
            }
        });

        editgender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genderf.setVisibility(View.VISIBLE);
            }
        });

        closepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passf.setVisibility(View.GONE);
            }
        });

        closename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                namef.setVisibility(View.GONE);
            }
        });

        closegender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genderf.setVisibility(View.GONE);
            }
        });

    }

}