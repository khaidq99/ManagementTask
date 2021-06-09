package com.example.baitapcuoiky;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.baitapcuoiky.model.User;

public class RegisterActivity extends AppCompatActivity {
    private Button btnRegister, btnCancel;
    private EditText txtUsername, txtPassword, txtName;
    private DBOpenHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        db = new DBOpenHelper(this);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();
                String name = txtName.getText().toString();

                if(username.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Username không được để trống",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                if(password.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Password không được để trống",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                if(name.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Tên không được để trống",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                User user = new User();
                user.setUsername(username);
                user.setPassword(password);
                user.setName(name);

                if(db.registerUser(user) == -1){
                    Toast.makeText(RegisterActivity.this, "Có lỗi xảy ra, vui lòng thử lại!",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(RegisterActivity.this, "Đăng kí tài khoản thành công!",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initView() {
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        txtName = findViewById(R.id.txtName);
        btnCancel = findViewById(R.id.btnCancel);
        btnRegister = findViewById(R.id.btnRegister);
    }
}