package com.example.quizzgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class DelQuestionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_del_question);
        EditText editText  = findViewById(R.id.name_questdel);
        Button btn = findViewById(R.id.btn_delete);
    }
}