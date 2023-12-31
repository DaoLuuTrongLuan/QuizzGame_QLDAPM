package com.example.quizzgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Button btn_addques = findViewById(R.id.btn_option_addquestion);
        btn_addques.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity2.this, AddQuestionActivity.class));
            }
        });
        Button btn_delquestion = findViewById(R.id.btn_option_delquestion);
        btn_delquestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity2.this,DelQuestionActivity.class));
            }
        });

        Button btn_playgame = findViewById(R.id.btn_option_playgame);
        btn_playgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity2.this, PlayGameActivity.class));
            }
        });

        Button btn_deltest = findViewById(R.id.btn_option_deltest);
        btn_deltest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity2.this, DelTestActivity.class));
            }
        });
    }
}