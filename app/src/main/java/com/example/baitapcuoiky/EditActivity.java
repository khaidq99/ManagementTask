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

import com.example.baitapcuoiky.model.Task;
import com.example.baitapcuoiky.model.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EditActivity extends AppCompatActivity {
    private EditText edtTitle, edtDes, edtDate;
    private Button btnEdit;
    private DBOpenHelper db;
    private Task task;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        initView();
        Intent intent = getIntent();
        task = (Task) intent.getSerializableExtra("task");
        user = (User) intent.getSerializableExtra("user");
        edtTitle.setText(task.getTitle());
        edtDes.setText(task.getDes());
        edtDate.setText(task.getDate());

        db = new DBOpenHelper(this);

        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditActivity.this,
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

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int id = task.getId();
                //int status = task.getStatus();
                String title = edtTitle.getText().toString();
                String des = edtDes.getText().toString();
                String date = edtDate.getText().toString();

                task.setTitle(title);
                task.setDes(des);
                task.setDate(date);
                if(db.editTask(task) != 0){
                    Toast.makeText(EditActivity.this, "Sửa thành công", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(EditActivity.this, MainActivity.class);
                    i.putExtra("user", user);
                    startActivity(i);
                } else {
                    Toast.makeText(EditActivity.this, "Có lỗi xảy ra, vui lòng thử lại",
                            Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });

    }

    private void initView() {
        edtTitle = findViewById(R.id.edtTitle);
        edtDes = findViewById(R.id.edtDes);
        edtDate = findViewById(R.id.edtDate);
        btnEdit = findViewById(R.id.btnEdit);
    }
}