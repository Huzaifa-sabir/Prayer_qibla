package com.huzaifa.prayerqibla;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class SplashActivity extends AppCompatActivity {

    private final Handler handler = new Handler(Looper.getMainLooper());
    private Runnable navigateRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        MaterialButton btnGetStarted = findViewById(R.id.btn_get_started);

        navigateRunnable = this::proceedToNextScreen;

        // Auto navigate after 2.5 seconds
        handler.postDelayed(navigateRunnable, 2500);

        btnGetStarted.setOnClickListener(v -> {
            handler.removeCallbacks(navigateRunnable);
            proceedToNextScreen();
        });
    }

    private void proceedToNextScreen() {
        AuthManager authManager = AuthManager.getInstance(this);
        Intent intent;
        if (authManager.isLoggedIn()) {
            intent = new Intent(SplashActivity.this, DashboardActivity.class);
        } else {
            intent = new Intent(SplashActivity.this, LoginActivity.class);
        }
        startActivity(intent);
        finish();
    }
}