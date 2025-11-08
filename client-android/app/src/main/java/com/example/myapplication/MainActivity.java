package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.api.AuthService;
import com.example.myapplication.api.RetrofitClient;
import com.example.myapplication.models.LoginRequest;
import com.example.myapplication.models.LoginResponse;
import com.example.myapplication.utils.TokenManager;
import com.example.myapplication.TaskActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnRegister;
    private AuthService authService;
    private TokenManager tokenManager;
    private static final String TAG = "AUTH_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        RetrofitClient.initialize(this);
        authService = RetrofitClient.getAuthService();
        tokenManager = new TokenManager(this);


        if (tokenManager.getToken() != null) {


            Log.d(TAG, "Token existent. Navigare la Task Screen.");

        }


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegister();
            }
        });
    }


    private void attemptLogin() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Introduceți Username și Parolă.", Toast.LENGTH_SHORT).show();
            return;
        }

        LoginRequest loginRequest = new LoginRequest(username, password);


        authService.login(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {

                    String token = response.body().getToken();
                    tokenManager.saveToken(token);
                    Toast.makeText(MainActivity.this, "Login Reușit!", Toast.LENGTH_LONG).show();
                    Log.i(TAG, "Token salvat: " + token);

                    Intent intent = new Intent(MainActivity.this, TaskActivity.class);
                    startActivity(intent);
                    finish();

                } else {

                    Toast.makeText(MainActivity.this, "Login Eșuat. Verifică credențialele.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

                Toast.makeText(MainActivity.this, "Eroare de Rețea: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e(TAG, "Eroare: ", t);
            }
        });
    }


    private void attemptRegister() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Introduceți Username și Parolă.", Toast.LENGTH_SHORT).show();
            return;
        }

        LoginRequest registerRequest = new LoginRequest(username, password);


        authService.register(registerRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {

                    String token = response.body().getToken();


                    tokenManager.saveToken(token);

                    Toast.makeText(MainActivity.this, "Login Reusit!", Toast.LENGTH_LONG).show();


                    Intent intent = new Intent(MainActivity.this, TaskActivity.class);
                    startActivity(intent);


                    finish();
                } else {

                    Toast.makeText(MainActivity.this, "Login Esuat. Verifica credentialele.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Eroare de Rețea: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}