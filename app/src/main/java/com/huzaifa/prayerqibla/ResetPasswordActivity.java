package com.huzaifa.prayerqibla;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ResetPasswordActivity extends AppCompatActivity {

    private TextInputLayout tilNewPassword, tilConfirmNewPassword;
    private TextInputEditText etNewPassword, etConfirmNewPassword;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        userEmail = getIntent().getStringExtra("EXTRA_EMAIL");
        if (userEmail == null || userEmail.isEmpty()) {
            userEmail = "admin@example.com";
        }

        tilNewPassword = findViewById(R.id.til_new_password);
        tilConfirmNewPassword = findViewById(R.id.til_confirm_new_password);
        etNewPassword = findViewById(R.id.et_new_password);
        etConfirmNewPassword = findViewById(R.id.et_confirm_new_password);

        MaterialButton btnUpdatePassword = findViewById(R.id.btn_update_password);

        btnUpdatePassword.setOnClickListener(v -> handleResetPassword());
    }

    private void handleResetPassword() {
        tilNewPassword.setError(null);
        tilConfirmNewPassword.setError(null);

        String newPassword = etNewPassword.getText() != null ? etNewPassword.getText().toString().trim() : "";
        String confirmPassword = etConfirmNewPassword.getText() != null ? etConfirmNewPassword.getText().toString().trim() : "";

        boolean isValid = true;

        if (newPassword.isEmpty()) {
            tilNewPassword.setError(getString(R.string.error_password_required));
            isValid = false;
        } else if (newPassword.length() < 6) {
            tilNewPassword.setError(getString(R.string.error_password_length));
            isValid = false;
        }

        if (confirmPassword.isEmpty()) {
            tilConfirmNewPassword.setError(getString(R.string.error_password_required));
            isValid = false;
        } else if (!newPassword.equals(confirmPassword)) {
            tilConfirmNewPassword.setError(getString(R.string.error_password_mismatch));
            isValid = false;
        }

        if (!isValid) return;

        AuthManager authManager = AuthManager.getInstance(this);
        authManager.resetPassword(userEmail, newPassword);

        Toast.makeText(this, R.string.password_reset_success, Toast.LENGTH_LONG).show();

        Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}