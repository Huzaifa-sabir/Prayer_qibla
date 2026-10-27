package com.huzaifa.prayerqibla;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        AuthManager authManager = AuthManager.getInstance(this);

        TextView tvProfileName = findViewById(R.id.tv_profile_name);
        TextView tvProfileEmail = findViewById(R.id.tv_profile_email);
        TextView tvDetailName = findViewById(R.id.tv_detail_name);
        TextView tvDetailEmail = findViewById(R.id.tv_detail_email);

        MaterialButton btnLogout = findViewById(R.id.btn_logout);

        String email = authManager.getLoggedInEmail();
        String name = authManager.getLoggedInName();

        tvProfileName.setText(name);
        tvProfileEmail.setText(email);
        tvDetailName.setText(name);
        tvDetailEmail.setText(email);

        btnLogout.setOnClickListener(v -> confirmLogout());
    }

    private void confirmLogout() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.logout_button)
                .setMessage(R.string.logout_confirmation)
                .setPositiveButton("Yes", (dialog, which) -> handleLogout())
                .setNegativeButton("No", null)
                .show();
    }

    private void handleLogout() {
        AuthManager authManager = AuthManager.getInstance(this);
        authManager.logout();

        Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}