package com.example.baitapcuoiky;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baitapcuoiky.model.Task;
import com.example.baitapcuoiky.model.User;

public class DetailActivity extends AppCompatActivity {
    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "1";
    private TextView txtTitle, txtDes, txtDate, txtStatus;
    private Button btnFlag, btnToEdit, btnDelete;
    private Task task;
    private User user;
    private DBOpenHelper db;
    private SharedPreferences sp;
    private NotificationManagerCompat notificationManagerCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();

        //get data
        Intent intent = getIntent();
        task = (Task) intent.getSerializableExtra("task");
        txtTitle.setText("Tiêu đề: " + task.getTitle());
        txtDes.setText("Mô tả: " + task.getDes());
        txtDate.setText("Ngày: " + task.getDate());
        String status = task.getStatus() == 0 ? "Chưa hoàn thành" : "Đã hoàn thành";
        txtStatus.setText("Trạng thái: " + status);
        db = new DBOpenHelper(this);
        sp = getSharedPreferences("dataUser", MODE_PRIVATE);
        int userId = sp.getInt("userId", 0);
        user = db.findUserById(userId);

        createNotificationChannels();
        notificationManagerCompat = NotificationManagerCompat.from(this);

        btnToEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailActivity.this, EditActivity.class);
                i.putExtra("task", task);
                i.putExtra("user", user);
                startActivity(i);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (db.deleteTask(task.getId()) != 0){
                    Toast.makeText(DetailActivity.this, "Xóa thành công", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                } else {
                    Toast.makeText(DetailActivity.this, "Có lỗi xảy ra, vui lòng thử lại",
                            Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });

        btnFlag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(db.updateStatus(task.getId()) != 0){
                    Toast.makeText(DetailActivity.this, "Thành công", Toast.LENGTH_LONG).show();
                    sendNotification();
                    Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                } else {
                    Toast.makeText(DetailActivity.this, "Có lỗi xảy ra, vui lòng thử lại",
                            Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });

    }

    private void createNotificationChannels() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_ID,
                    "Channel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("This is channel 1");
            NotificationManager manager =
                    this.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
        }
    }

    private void sendNotification() {
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Thành công!")
                .setContentText("Đã thêm vào danh sách công việc đã hoàn thành")
                .setSmallIcon(R.drawable.done)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManagerCompat.notify(NOTIFICATION_ID, notification);
    }

    private void initView() {
        txtTitle = findViewById(R.id.txtTitle);
        txtDes = findViewById(R.id.txtDes);
        txtDate = findViewById(R.id.txtDate);
        txtStatus = findViewById(R.id.txtStatus);
        btnFlag = findViewById(R.id.btnFlag);
        btnToEdit = findViewById(R.id.btnToEdit);
        btnDelete = findViewById(R.id.btnDelete);
    }
}