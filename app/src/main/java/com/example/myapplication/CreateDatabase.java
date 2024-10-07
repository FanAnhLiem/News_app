package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CreateDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Userdata.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_USER = "users";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_GENDER = "gender";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_ROLE = "role";

    public CreateDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_USER + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_EMAIL + " TEXT NOT NULL UNIQUE, "
                + COLUMN_GENDER + " TEXT CHECK (" + COLUMN_GENDER + " IN ('Nam', 'Nữ')), "
                + COLUMN_USERNAME + " TEXT, "
                + COLUMN_PASSWORD + " TEXT NOT NULL, "
                + COLUMN_ROLE + " TEXT NOT NULL" + ")";
        db.execSQL(createTable);

        addAdminUser(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    public boolean registerUser(String email, String gender, String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_GENDER, gender);
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_ROLE, "user");  

        long result = db.insert(TABLE_USER, null, values);
        return result != -1;
    }

    private void addAdminUser(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, "admin@gmail.com");
        values.put(COLUMN_GENDER, "Nam");
        values.put(COLUMN_USERNAME, "Cristiano Quan");
        values.put(COLUMN_PASSWORD, "123456");
        values.put(COLUMN_ROLE, "admin");

        db.insert(TABLE_USER, null, values);
    }

    public User checkLogin(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_EMAIL + "=? AND " + COLUMN_PASSWORD + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{email, password});

        if (cursor.moveToFirst()) {
            User user = new User(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENDER)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ROLE))
            );
            cursor.close();
            return user;
        } else {
            cursor.close();
            return null;
        }
    }

    // Kiểm tra nếu email đã tồn tại
    public boolean checkIfEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_EMAIL + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{email});
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    public Cursor getUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_EMAIL + "=?";
        return db.rawQuery(query, new String[]{email});
    }

    // Lấy danh sách người dùng (dành cho Admin)
    public Cursor getAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USER;
        return db.rawQuery(query, null);
    }

    public boolean deleteUser(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_USER, COLUMN_EMAIL + "=?", new String[]{email}) > 0;
    }

    public boolean updateUser(String email, String gender, String username, String password, String role) {
        if (!gender.equals("Nam") && !gender.equals("Nữ")) {
            Log.e("Update User", "Giá trị gender không hợp lệ. Chỉ chấp nhận 'Nam' hoặc 'Nữ'.");
            return false;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_GENDER, gender);
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_ROLE, role);

        return db.update(TABLE_USER, values, COLUMN_EMAIL + "=?", new String[]{email}) > 0;
    }

    public boolean userUpdate(String email, String gender, String username, String password) {
        if (!gender.equals("Nam") && !gender.equals("Nữ")) {
            Log.e("Update User", "Giá trị gender không hợp lệ. Chỉ chấp nhận 'Nam' hoặc 'Nữ'.");
            return false;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_GENDER, gender);
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);

        return db.update(TABLE_USER, values, COLUMN_EMAIL + "=?", new String[]{email}) > 0;
    }

}
