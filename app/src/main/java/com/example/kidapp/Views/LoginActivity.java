package com.example.kidapp.Views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kidapp.Models.Kid;
import com.example.kidapp.R;
import com.example.kidapp.Repositories.DataManager;
import com.google.android.material.button.MaterialButton;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextPhoneNumber;
    private MaterialButton buttonLogin;
    private ProgressDialog progressDialog;
    private DataManager dataManager= DataManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        buttonLogin = findViewById(R.id.buttonLogin);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Please Wait...");

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = editTextPhoneNumber.getText().toString()+"@gmail.com";
                    progressDialog.show();
                    login(phoneNumber);
            }
        });
    }
    private void login(String phoneNumber) {
            dataManager.loginUser(phoneNumber, new DataManager.OnLoginListener() {
                @Override
                public void onSuccess(Kid kid) {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.putExtra("phoneNumber", kid.getPhone());
                    startActivity(new Intent(LoginActivity.this, KidProfileActivity.class));
                    finish();
                }
                @Override
                public void onFailure(Exception exception) {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Phone Number does not Exist", Toast.LENGTH_SHORT).show();
                    Toast.makeText(LoginActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("Login Error", exception.getMessage());
                }
            });
    }

}