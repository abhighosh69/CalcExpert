package com.ghoshabhi.calcexpert;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.preference.PreferenceManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements calculations {

    private EditText typeFirstNum, typeSecondNum;
    private Button addButton, subButton, mulButton, divButton;
    private TextView displayResult;
    private FloatingActionButton settingsButton;
    private SoundManager soundManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        typeFirstNum = findViewById(R.id.typeFirstNum);
        typeSecondNum = findViewById(R.id.typeSecondNum);
        addButton = findViewById(R.id.addButton);
        subButton = findViewById(R.id.subButton);
        mulButton = findViewById(R.id.mulButton);
        divButton = findViewById(R.id.divButton);
        displayResult = findViewById(R.id.displayResult);
        settingsButton = findViewById(R.id.settingsButton);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean soundEnabled = sharedPreferences.getBoolean("sound_preference", true);

        soundManager = new SoundManager(this, soundEnabled);


        addButton.setOnClickListener(v-> {
            performOperation("add");
            soundManager.playClickSound();
        });
        subButton.setOnClickListener(v -> {
            performOperation("sub");
            soundManager.playClickSound();
        });
        mulButton.setOnClickListener(v -> {
            performOperation("mul");
            soundManager.playClickSound();
        });
        divButton.setOnClickListener(v -> {
            performOperation("div");
            soundManager.playClickSound();
        });

        settingsButton.setOnClickListener(v -> {
            soundManager.playClickSound();
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        SoundManager.muteSystemSound(this);
    }

    // Getter method for SoundManager
    public SoundManager getSoundManager() {
        return soundManager;
    }

    protected void performOperation(String operation) {
        try {
            int firstNum = Integer.parseInt(typeFirstNum.getText().toString());
            int secondNum = Integer.parseInt(typeSecondNum.getText().toString());
            double result = 0;
            switch (operation) {
                case "add":
                    result = add(firstNum, secondNum);
                    break;
                case "sub":
                    result = sub(firstNum, secondNum);
                    break;
                case "mul":
                    result = mul(firstNum, secondNum);
                    break;
                case "div":
                    result = div(firstNum, secondNum);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid operation");
            }
            String formattedResult;
            if(result % 1 == 0){
                formattedResult = String.format("%d", (int)result);
            }else{
                formattedResult = String.format("%.2f", result);
            }
            displayResult.setText(formattedResult);
        }catch (IllegalArgumentException e) {
            displayResult.setText(e.getMessage());
        }
    }

    @Override
    public int add(int num1, int num2) {
        return num1 + num2;
    }

    @Override
    public int sub(int num1, int num2) {
        return num1 - num2;
    }

    @Override
    public int mul(int num1, int num2) {
        return num1 * num2;
    }

    @Override
    public double div(int num1, int num2) {
        if (num2 != 0) {
            return(double) num1 / num2;
        } else {
            throw new IllegalArgumentException("Cannot divide by zero");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundManager.release();
    }
}