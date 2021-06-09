package com.example.baitapcuoiky;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.baitapcuoiky.model.User;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin, btnRegister;
    private EditText txtUsername, txtPassword;
    private DBOpenHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        db = new DBOpenHelper(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();

                if(username.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Username không được để trống", Toast.LENGTH_LONG).show();
                    return;
                }

                if(password.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Password không được để trống", Toast.LENGTH_LONG).show();
                    return;
                }

                User user = new User();
                user.setUsername(username);
                user.setPassword(password);

                if(db.checkLogin(user)){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("user", user);

                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Sai tên tài khoản hoặc mật khẩu, vui lòng thử lại",
                            Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
    }
}