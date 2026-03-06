package com.example.singin_shashin.presentations;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.singin_shashin.datas.apis.UserLogin;
import com.example.singin_shashin.domains.callbacks.MyResponseCallback;
import com.example.singin_shashin.domains.models.User;

import androidx.appcompat.app.AppCompatActivity;

import com.example.singin_shashin.R;
import com.example.singin_shashin.datas.common.CheckInternet;

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        TextView bthOpenSingIn = findViewById(R.id.btn_open_sing_in);
        bthOpenSingIn.setOnClickListener(v -> {
            Intent SingIn = new Intent(this, RegInActivity.class);
            startActivity(SingIn);
        });

        Button bthLogIn = findViewById(R.id.btn_log_in);
        bthLogIn.setOnClickListener(v -> {
            TextView etEmail = findViewById(R.id.et_email);
            TextView etPassword = findViewById(R.id.et_password);

            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();

            if(email.isEmpty()) {
                Toast.makeText(this, "Не указана почта пользователя", Toast.LENGTH_SHORT).show();
                return;
            }

            if(password.isEmpty()) {
                Toast.makeText(this, "Не указан пароль пользователя", Toast.LENGTH_SHORT).show();
                return;
            }
            RequestUserLogin(email, password);
        });
    }

    public void RequestUserLogin(String email, String password) {
        Context context = this;
        CheckInternet checkInternet = new CheckInternet(this);
        User User = new User();
        User.email = email;
        User.password = password;

        UserLogin RequestUserLogin = new UserLogin(
                User,
                checkInternet,
                new MyResponseCallback() {
                    @Override
                    public void onCompile(String result) {
                        Log.d("USER LOGIN", result);
                        Toast.makeText(context, "Успешная авторизация пользователя", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("USER LOGIN", error);
                        Toast.makeText(context, "Авторизация не увенчалась успехом", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        RequestUserLogin.execute();
    }
}