package com.nely.marketingapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.nely.marketingapp.auth.LoginResponse;
import com.nely.marketingapp.viewmodel.AuthViewModel;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private ProgressBar progressLogin;
    private TextView tvLoginMessage;

    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        progressLogin = findViewById(R.id.progressLogin);
        tvLoginMessage = findViewById(R.id.tvLoginMessage);

        // Inisialisasi ViewModel
        authViewModel = new ViewModelProvider(this)
                .get(AuthViewModel.class);

        // Mengamati hasil login dari ViewModel
        authViewModel.getLoginResult().observe(this, response -> {
            progressLogin.setVisibility(View.GONE);
            btnLogin.setEnabled(true);

            if (response != null
                    && response.isSuccess()
                    && response.getToken() != null
                    && !response.getToken().isEmpty()) {

                saveSession(response);

                Toast.makeText(
                        LoginActivity.this,
                        "Login berhasil.",
                        Toast.LENGTH_SHORT
                ).show();

                Intent intent = new Intent(
                        LoginActivity.this,
                        MainActivity.class
                );

                startActivity(intent);
                finish();

            } else {
                tvLoginMessage.setText(
                        "Login gagal. Periksa email dan password."
                );
            }
        });

        btnLogin.setOnClickListener(v -> login());
    }

    private void login() {

        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            tvLoginMessage.setText(
                    "Email dan password wajib diisi."
            );
            return;
        }

        btnLogin.setEnabled(false);
        progressLogin.setVisibility(View.VISIBLE);
        tvLoginMessage.setText("");

        try {
            JSONObject json = new JSONObject();
            json.put("email", email);
            json.put("password", password);

            RequestBody body = RequestBody.create(
                    MediaType.parse("application/json; charset=utf-8"),
                    json.toString()
            );

            // Memanggil ViewModel, bukan Retrofit secara langsung
            authViewModel.login(body);

        } catch (Exception e) {

            progressLogin.setVisibility(View.GONE);
            btnLogin.setEnabled(true);

            tvLoginMessage.setText(
                    "Terjadi kesalahan saat memproses login."
            );
        }
    }

    private void saveSession(LoginResponse response) {

        SharedPreferences preferences =
                getSharedPreferences(
                        "MarketingAppPrefs",
                        MODE_PRIVATE
                );

        preferences.edit()
                .putString("auth_token", response.getToken())
                .putString(
                        "user_name",
                        response.getUser() != null
                                ? response.getUser().getName()
                                : ""
                )
                .apply();
    }
}
