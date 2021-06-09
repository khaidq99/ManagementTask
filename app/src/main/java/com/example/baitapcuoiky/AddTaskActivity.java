package com.example.baitapcuoiky;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.baitapcuoiky.model.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity {
    private EditText edtTitle, edtDes, edtDate;
    private Button btnAdd;
    private DBOpenHelper db;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        initView();
        user = (User) getIntent().getSerializableExtra("user");
        db = new DBOpenHelper(this);

        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddTaskActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        edtDate.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                }, calendar.get(calendar.YEAR), calendar.get(calendar.MONTH), calendar.get(calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = edtTitle.getText().toString();
                String des = edtDes.getText().toString();
                String date = edtDate.getText().toString();
                // set status has not done yet
                int status = 0;

                if(title.isEmpty()){
                    Toast.makeText(AddTaskActivity.this, "Tiêu đề không được để trống", Toast.LENGTH_LONG).show();
                    return;
                }
                if(date.isEmpty()){
                    Toast.makeText(AddTaskActivity.this, "Ngày không được để trống", Toast.LENGTH_LONG).show();
                    return;
                }

                Task task = new Task(title, des, date, status, user);
                if(db.addTask(task) == -1){
                    Toast.makeText(AddTaskActivity.this, "Thêm mới thất bại, vui lòng thử lại", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    Toast.makeText(AddTaskActivity.this, "Thêm thành công!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(AddTaskActivity.this, MainActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
            }
        });

    }

    private void initView() {
        edtTitle = findViewById(R.id.edtTitle);
        edtDes = findViewById(R.id.edtDes);
        edtDate = findViewById(R.id.edtDate);
        btnAdd = findViewById(R.id.btnAdd);
    }
}