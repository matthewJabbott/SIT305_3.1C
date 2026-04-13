package com.example.sit305_31c;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private TextView questionTextView, progressText;
    private ProgressBar progressBar;
    private Button[] optionButtons = new Button[4];
    private Button submitNextButton;

    private List<Question> questionList;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private int selectedOptionIndex = -1;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        userName = getIntent().getStringExtra("USER_NAME");

        // Initialize UI
        progressBar = findViewById(R.id.quizProgressBar);
        progressText = findViewById(R.id.progressText);
        questionTextView = findViewById(R.id.questionTextView);
        submitNextButton = findViewById(R.id.submitNextButton);

        optionButtons[0] = findViewById(R.id.btn_opt1);
        optionButtons[1] = findViewById(R.id.btn_opt2);
        optionButtons[2] = findViewById(R.id.btn_opt3);
        optionButtons[3] = findViewById(R.id.btn_opt4);

        setupQuestions();
        loadQuestion();

        // Option Selection Logic
        for (int i = 0; i < 4; i++) {
            final int index = i;
            optionButtons[i].setOnClickListener(v -> {
                selectedOptionIndex = index;
                resetButtonColors();
                optionButtons[index].setBackgroundColor(Color.LTGRAY); // Highlight selection
            });
        }

        // Submit / Next Logic
        submitNextButton.setOnClickListener(v -> {
            if (submitNextButton.getText().equals("SUBMIT")) {
                if (selectedOptionIndex != -1) {
                    checkAnswer();
                }
            } else {
                currentQuestionIndex++;
                if (currentQuestionIndex < questionList.size()) {
                    loadQuestion();
                } else {
                    // Quiz Finished
                    Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                    intent.putExtra("USER_NAME", userName);
                    intent.putExtra("SCORE", score);
                    intent.putExtra("TOTAL", questionList.size());
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void setupQuestions() {
        questionList = new ArrayList<>();
        questionList.add(new Question("Which component handles Android UI layouts?", new String[]{"Activity", "XML", "Gradle", "Manifest"}, 1));
        questionList.add(new Question("What is the primary language for modern Android?", new String[]{"Python", "C++", "Kotlin", "Swift"}, 2));
        questionList.add(new Question("Which tool is used to build Android apps?", new String[]{"VS Code", "Android Studio", "Xcode", "PyCharm"}, 1));
    }

    private void loadQuestion() {
        resetButtonColors();
        selectedOptionIndex = -1;
        submitNextButton.setText("SUBMIT");
        enableButtons(true);

        Question q = questionList.get(currentQuestionIndex);
        questionTextView.setText(q.getQuestionText());
        for (int i = 0; i < 4; i++) {
            optionButtons[i].setText(q.getOptions()[i]);
        }

        // Update Progress
        int progress = (int) (((double) currentQuestionIndex / questionList.size()) * 100);
        progressBar.setProgress(progress);
        progressText.setText("Question " + (currentQuestionIndex + 1) + "/" + questionList.size());
    }

    private void checkAnswer() {
        Question q = questionList.get(currentQuestionIndex);
        enableButtons(false);

        if (selectedOptionIndex == q.getCorrectAnswerIndex()) {
            optionButtons[selectedOptionIndex].setBackgroundColor(Color.GREEN);
            score++;
        } else {
            optionButtons[selectedOptionIndex].setBackgroundColor(Color.RED);
            optionButtons[q.getCorrectAnswerIndex()].setBackgroundColor(Color.GREEN);
        }
        submitNextButton.setText("NEXT");
    }

    private void resetButtonColors() {
        for (Button b : optionButtons) {
            b.setBackgroundColor(Color.parseColor("#6200EE")); // Default Purple
        }
    }

    private void enableButtons(boolean enabled) {
        for (Button b : optionButtons) b.setEnabled(enabled);
    }
}