package com.huzaifa.prayerqibla;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ForgotPasswordActivity extends AppCompatActivity {

    private TextInputLayout tilForgotEmail;
    private TextInputEditText etForgotEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        tilForgotEmail = findViewById(R.id.til_forgot_email);
        etForgotEmail = findViewById(R.id.et_forgot_email);

        MaterialButton btnSendRecovery = findViewById(R.id.btn_send_recovery);
        TextView tvBackToLogin = findViewById(R.id.tv_back_to_login);

        btnSendRecovery.setOnClickListener(v -> handlePasswordRecovery());

        tvBackToLogin.setOnClickListener(v -> finish());
    }

    private void handlePasswordRecovery() {
        tilForgotEmail.setError(null);
        String email = etForgotEmail.getText() != null ? etForgotEmail.getText().toString().trim() : "";

        if (email.isEmpty()) {
            tilForgotEmail.setError(getString(R.string.error_email_required));
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilForgotEmail.setError(getString(R.string.error_invalid_email));
            return;
        }

        Toast.makeText(this, R.string.recovery_simulated_msg, Toast.LENGTH_LONG).show();

        Intent intent = new Intent(ForgotPasswordActivity.this, ResetPasswordActivity.class);
        intent.putExtra("EXTRA_EMAIL", email);
        startActivity(intent);
        finish();
    }
}