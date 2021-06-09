package com.example.baitapcuoiky;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.baitapcuoiky.model.Task;
import com.example.baitapcuoiky.model.User;

import java.util.ArrayList;
import java.util.List;

public class DBOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "TaskDB.db";
    private static final int DATABASE_VERSION = 1;

    public DBOpenHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create user table
        String sql1 = "CREATE TABLE users(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT," +
                "password TEXT," +
                "name TEXT)";
        db.execSQL(sql1);

        // Create task table
        String sql2 = "CREATE TABLE tasks(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT," +
                "des TEXT," +
                "date TEXT," +
                "status INTEGER," +
                "user_id INTEGER ," +
                "FOREIGN KEY (user_id) REFERENCES users(id)" +
        ")";
        db.execSQL(sql2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // register user
    public long registerUser(User user){
        ContentValues values = new ContentValues();
        values.put("username", user.getUsername());
        values.put("password", user.getPassword());
        values.put("name", user.getName());

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert("users", null, values);
    }

    // check login
    public boolean checkLogin(User user){
        boolean result = false;

        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        String[] args = {user.getUsername(), user.getPassword()};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.rawQuery(sql, args);
        if(rs.moveToNext()){
            int id = rs.getInt(0);
            String name = rs.getString(3);
            user.setId(id);
            user.setName(name);

            result = true;
        }

        return result;
    }

    public User findUserById(int userId){
        User user = new User();
        String sql = "SELECT * FROM users WHERE id = ?";
        String[] args = {Integer.toString(userId)};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.rawQuery(sql, args);
        if(rs.moveToNext()){
            int id = rs.getInt(0);
            String username = rs.getString(1);
            String password = rs.getString(2);
            String name = rs.getString(3);

            user.setId(id);
            user.setUsername(username);
            user.setPassword(password);
            user.setName(name);
        }
        return user;
    }

    // add task
    public long addTask(Task task){
        ContentValues values = new ContentValues();
        values.put("title", task.getTitle());
        values.put("des", task.getDes());
        values.put("date", task.getDate());
        values.put("status", task.getStatus());
        values.put("user_id", task.getUser().getId());

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert("tasks", null, values);
    }

    // get list task not done
    public List<Task> getListTaskNotDone(int userId){
        List<Task> list = new ArrayList<>();

        String sql = "SELECT * FROM tasks WHERE user_id = ? AND status = ?";
        String[] args = {Integer.toString(userId), Integer.toString(0)};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.rawQuery(sql, args);
        while(rs.moveToNext()){
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String des = rs.getString(2);
            String date = rs.getString(3);
            int status = rs.getInt(4);

            User user = new User();
            user.setId(userId);
            Task task = new Task(title, des, date, status, user);
            task.setId(id);

            list.add(task);
        }
        return list;
    }

    // get list task done
    public List<Task> getListTaskDone(int userId){
        List<Task> list = new ArrayList<>();

        String sql = "SELECT * FROM tasks WHERE user_id = ? AND status = ?";
        String[] args = {Integer.toString(userId), Integer.toString(1)};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.rawQuery(sql, args);
        while(rs.moveToNext()){
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String des = rs.getString(2);
            String date = rs.getString(3);
            int status = rs.getInt(4);

            User user = new User();
            user.setId(userId);
            Task task = new Task(title, des, date, status, user);
            task.setId(id);

            list.add(task);
        }
        return list;
    }

    // edit task
    public int editTask(Task task){
        ContentValues values = new ContentValues();
        values.put("title", task.getTitle());
        values.put("des", task.getDes());
        values.put("date", task.getDate());
        values.put("status", task.getStatus());

        String whereClause = "id = ?";
        String[] args = {Integer.toString(task.getId())};
        SQLiteDatabase st = getWritableDatabase();
        return st.update("tasks", values, whereClause, args);
    }

    // delete task
    public int deleteTask(int id){
        String whereClause = "id=?";
        String[] args = {Integer.toString(id)};
        SQLiteDatabase st = getWritableDatabase();
        return st.delete("tasks",  whereClause, args);
    }

    public Task findTaskById(int taskId){
        Task task = new Task();
        String sql = "SELECT * FROM tasks WHERE id = ?";
        String[] args = {Integer.toString(taskId)};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.rawQuery(sql, args);
        if(rs.moveToNext()){
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String des = rs.getString(2);
            String date = rs.getString(3);
            int status = rs.getInt(4);
            int userId = rs.getInt(5);
            User user = new User();
            user.setId(userId);

            task.setTitle(title);
            task.setDes(des);
            task.setDate(date);
            task.setStatus(status);
            task.setUser(user);
        }
        return task;
    }

    // update status
    public int updateStatus(int id){
        Task task = findTaskById(id);
        task.setStatus(1);

        ContentValues values = new ContentValues();
        values.put("status", task.getStatus());
        String whereClause = "id = ?";
        String[] args = {Integer.toString(id)};
        SQLiteDatabase st = getWritableDatabase();
        return st.update("tasks", values, whereClause, args);
    }

}
