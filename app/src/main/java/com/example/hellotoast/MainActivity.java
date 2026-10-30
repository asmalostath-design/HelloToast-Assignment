package com.example.hellotoast;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hellotoast.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final String STATE_COUNT = "saved_count";

    private int count = 0;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (view, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(systemBars.left, systemBars.top,
                    systemBars.right, systemBars.bottom);
            return insets;
        });

        if (savedInstanceState != null) {
            count = savedInstanceState.getInt(STATE_COUNT, 0);
        }

        updateCountDisplay();
        updateButtonColors();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_COUNT, count);
        super.onSaveInstanceState(outState);
    }

    // يظهر رسالة Hello Toast عند ضغط زر Toast.
    public void show_toast(View view) {
        Toast.makeText(this, "Hello Toast", Toast.LENGTH_SHORT).show();
    }

    // يزيد العدد واحدًا، ثم يلوّن Count حسب فردية/زوجية القيمة الجديدة.
    public void increase_count(View view) {
        count++;
        updateCountDisplay();
        updateButtonColors();
    }

    // يصفر العدد ويعيد زر Zero إلى اللون الرمادي.
    public void show_zero(View view) {
        count = 0;
        updateCountDisplay();
        updateButtonColors();
    }

    private void updateCountDisplay() {
        binding.textDisplayCount.setText(String.valueOf(count));
    }

    private void updateButtonColors() {
        if (count == 0) {
            binding.buttonZero.setBackgroundColor(
                    ContextCompat.getColor(this, R.color.zero_gray));
            return;
        }

        // عندما يكون العدد أكبر من صفر، يكون زر Zero نشطًا ولونه برتقاليًا.
        binding.buttonZero.setBackgroundColor(
                ContextCompat.getColor(this, R.color.zero_orange));

        // لون Count أخضر للعدد الفردي وأزرق للعدد الزوجي.
        int countColor = (count % 2 == 1)
                ? R.color.count_green
                : R.color.count_blue;
        binding.buttonCount.setBackgroundColor(
                ContextCompat.getColor(this, countColor));
    }
}
