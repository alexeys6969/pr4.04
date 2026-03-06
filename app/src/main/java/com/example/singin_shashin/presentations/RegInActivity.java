package com.example.singin_shashin.presentations;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.Toast;

import com.example.singin_shashin.R;
import com.example.singin_shashin.datas.apis.UserCreate;
import com.example.singin_shashin.datas.apis.UserLogin;
import com.example.singin_shashin.datas.common.CheckInternet;
import com.example.singin_shashin.domains.callbacks.MyResponseCallback;
import com.example.singin_shashin.domains.models.User;

public class RegInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reg_in);
        TextView bthOpenLogIn = findViewById(R.id.btn_open_log_in);
        bthOpenLogIn.setOnClickListener(v -> {
            finish();
        });
        Button registerButton = findViewById(R.id.appCompatButton);
        registerButton.setOnClickListener(v -> {
            onReg();
        });
    }

    private void onReg() {
        EditText emailET = findViewById(R.id.email);
        String Email = emailET.getText().toString();
        EditText firstnameET = findViewById(R.id.FirstName);
        String Firstname = firstnameET.getText().toString();
        EditText nameET = findViewById(R.id.Name);
        String Name = nameET.getText().toString();
        EditText LastnameET = findViewById(R.id.LastName);
        String Lastname = LastnameET.getText().toString();
        Spinner genders = findViewById(R.id.gender);
        String Gender = genders.getSelectedItem().toString();
        EditText passwordET = findViewById(R.id.password);
        String Password = passwordET.getText().toString();

        if(Email.isEmpty()) {
            Toast.makeText(this, "Введите почту", Toast.LENGTH_SHORT).show();
            return;
        }
        if(Firstname.isEmpty()) {
            Toast.makeText(this, "Введите фамилию", Toast.LENGTH_SHORT).show();
            return;
        }
        if(Name.isEmpty()) {
            Toast.makeText(this, "Введите имя", Toast.LENGTH_SHORT).show();
            return;
        }
        if(Lastname.isEmpty()) {
            Toast.makeText(this, "Введите отчество", Toast.LENGTH_SHORT).show();
            return;
        }
        if(Gender.isEmpty()) {
            Toast.makeText(this, "Выберите пол", Toast.LENGTH_SHORT).show();
            return;
        }
        if(Password.isEmpty()) {
            Toast.makeText(this, "Введите пароль", Toast.LENGTH_SHORT).show();
            return;
        }
        RequestUserRegister(Email, Firstname, Name, Lastname, genders.getSelectedItemPosition(), Password);
    }

    public void RequestUserRegister(String Email, String Firstname, String Lastname, String Surname, Integer Gender, String Password) {
        Context context = this;
        CheckInternet checkInternet = new CheckInternet(this);
        User User = new User();
        User.email = Email;
        User.firstname = Firstname;
        User.lastname = Lastname;
        User.surname = Surname;
        User.gender = Gender;
        User.password = Password;

        UserCreate RequestUserRegister = new UserCreate(
                User,
                checkInternet,
                new MyResponseCallback() {
                    @Override
                    public void onCompile(String result) {
                        Log.d("USER CREATE", result);
                        Toast.makeText(context, "Успешная регистрация пользователя", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("USER CREATE", error);
                        Toast.makeText(context, "Регистрация не увенчалась успехом", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        RequestUserRegister.execute();
    }
}