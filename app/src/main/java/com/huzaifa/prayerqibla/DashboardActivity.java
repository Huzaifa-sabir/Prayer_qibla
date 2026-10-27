package com.huzaifa.prayerqibla;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        AuthManager authManager = AuthManager.getInstance(this);

        TextView tvUserNameHeader = findViewById(R.id.tv_user_name_header);
        ImageView ivProfileShortcut = findViewById(R.id.iv_profile_shortcut);

        MaterialCardView cardPrayerTimes = findViewById(R.id.card_prayer_times);
        MaterialCardView cardQibla = findViewById(R.id.card_qibla);
        MaterialCardView cardQuran = findViewById(R.id.card_quran);
        MaterialCardView cardCalendar = findViewById(R.id.card_calendar);
        MaterialCardView cardDuas = findViewById(R.id.card_duas);
        MaterialCardView cardProfile = findViewById(R.id.card_profile);

        String userName = authManager.getLoggedInName();
        tvUserNameHeader.setText(userName);

        ivProfileShortcut.setOnClickListener(v -> openProfile());
        cardProfile.setOnClickListener(v -> openProfile());

        cardPrayerTimes.setOnClickListener(v -> showModuleDialog(
                getString(R.string.module_prayer_times),
                "Fajr: 05:15 AM\nDhuhr: 12:30 PM\nAsr: 03:45 PM\nMaghrib: 06:15 PM\nIsha: 07:45 PM"
        ));

        cardQibla.setOnClickListener(v -> showModuleDialog(
                getString(R.string.module_qibla),
                "Qibla direction: 254.5° WSW from your location (Makkah al-Mukarramah)."
        ));

        cardQuran.setOnClickListener(v -> showModuleDialog(
                getString(R.string.module_quran),
                "Surah Al-Fatiha completed.\nSurah Al-Baqarah (Verses 1-100 read)."
        ));

        cardCalendar.setOnClickListener(v -> showModuleDialog(
                getString(R.string.module_calendar),
                "Today: 14 Ramadan 1446 AH\nUpcoming Event: Laylat al-Qadr"
        ));

        cardDuas.setOnClickListener(v -> showModuleDialog(
                getString(R.string.module_duas),
                "Daily Dua: 'Rabbi zidni 'ilma' (O my Lord, increase me in knowledge)."
        ));
    }

    private void openProfile() {
        Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    private void showModuleDialog(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }
}