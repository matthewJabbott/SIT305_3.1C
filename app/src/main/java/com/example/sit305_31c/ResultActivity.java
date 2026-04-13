package com.example.sit305_31c;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        String name = getIntent().getStringExtra("USER_NAME");
        int score = getIntent().getIntExtra("SCORE", 0);
        int total = getIntent().getIntExtra("TOTAL", 3);

        TextView resultText = findViewById(R.id.resultText);
        resultText.setText("Congratulations " + name + "!\nYour Score: " + score + "/" + total);

        Button newQuizBtn = findViewById(R.id.newQuizButton);
        Button finishBtn = findViewById(R.id.finishButton);

        // Subtask 3: Return to main, retain name
        newQuizBtn.setOnClickListener(v -> {
            Intent intent = new Intent(ResultActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        // Subtask 3: Close app
        finishBtn.setOnClickListener(v -> finishAffinity());
    }
}