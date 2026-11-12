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

        // استعادة قيمة العداد عند دوران الشاشة (دعم التعديل المتجاوب Responsive Design)
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

    // [تعديل 1]: تم تغيير اسم الدالة إلى showToast
    public void showToast(View view) {
        Toast.makeText(this, "Hello Toast", Toast.LENGTH_SHORT).show();
    }

    // [تعديل 2]: تم تغيير اسم الدالة إلى showCount
    public void showCount(View view) {
        count++;
        updateCountDisplay();
        updateButtonColors();
    }

    // [تعديل 3]:  اعتماد دالة showZero لتقوم بتصفير العداد وإعادة زر Zero إلى لونه الرمادي الافتراضي
    public void showZero(View view) {
        count = 0;
        updateCountDisplay();
        updateButtonColors();
    }

    private void updateCountDisplay() {
        binding.showCount.setText(String.valueOf(count));
    }

    // [تعديل 5]: التحكم الديناميكي بالألوان حسب الشروط (الصفر رمادي، الأكبر من صفر نشط، والفردي/الزوجي لزر Count)
    private void updateButtonColors() {
        if (count == 0) {
            binding.buttonZero.setBackgroundColor(
                    ContextCompat.getColor(this, android.R.color.darker_gray));
            return;
        }

        // عندما يصبح العد أكبر من صفر، يصبح زر Zero نشطاً (لون مغاير للرمادي كـ البرتقالي مثلاً)
        binding.buttonZero.setBackgroundColor(
                ContextCompat.getColor(this, android.R.color.holo_orange_dark));

        // تغيير لون زر Count ديناميكياً: أخضر للأعداد الفردية، وأزرق للأعداد الزوجية
        int countColor = (count % 2 == 1)
                ? android.R.color.holo_green_dark
                : android.R.color.holo_blue_dark;
        binding.buttonCount.setBackgroundColor(
                ContextCompat.getColor(this, countColor));
    }
}