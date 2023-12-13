package com.example.quizzgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.quizzgame.firebase.DatabaseFirebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {
    private EditText Username;
    private EditText Password;
    private Button LoginButton;
    private Button RegisterButton;
    private TextView Display;
    private DatabaseFirebase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Username = findViewById(R.id.edit_text_username);
        Password = findViewById(R.id.edit_text_password);
        LoginButton = findViewById(R.id.button_login);
        RegisterButton = findViewById(R.id.button_register);
        Display = findViewById(R.id.display);
        database = new DatabaseFirebase();

        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = Username.getText().toString();
                String password = Password.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    Display.setText("Nhập đầy đủ tên đăng nhập hoặc mật khẩu!");
                } else {
                    AccountExist(username, password);
                }
            }
        });
    }

    private void AccountExist(String username, String password) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            boolean result = false;
                            String id = "";
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.getData().get("username").toString().equals(username) && document.getData().get("password").toString().equals(password)) {
                                    result = true;
                                    id = document.getId();
                                    break;
                                }
                            }
                            if (result) {
                                writeInfoLogin(id, username, password);
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("idUser", id);
                                intent.putExtra("data", bundle);
                                startActivity(intent);
                                finish();
                            }
                            if (!result) {
                                Display.setText("Sai tên đăng nhập hoặc mật khẩu!");
                            }
                        }
                    }
                });
    }

    private void writeInfoLogin(String id, String username, String password) {
        SQLiteDatabase db = openOrCreateDatabase("statelogin", MODE_PRIVATE, null);
        String sql = "CREATE TABLE login (idUser TEXT,username TEXT,password TEXT)";
        db.execSQL(sql);
        ContentValues values = new ContentValues();
        values.put("idUser", id);
        values.put("username", username);
        values.put("password", password);
        db.insert("login", null, values);
        db.close();
    }
}