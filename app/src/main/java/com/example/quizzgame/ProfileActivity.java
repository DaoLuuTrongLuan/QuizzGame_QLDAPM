package com.example.quizzgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileActivity extends AppCompatActivity {
    TextView tv_name, tv_email, tv_update_profile, tv_test, tv_rank, tv_team_rank, tv_exit, tv_admin;
    View back, admin;
    String idUser;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("data");
        idUser = bundle.getString("idUser");
        tv_admin = findViewById(R.id.tv_admin);
        admin = findViewById(R.id.admin);
        checkRole();

        tv_name = findViewById(R.id.tv_name);
        tv_email = findViewById(R.id.tv_email);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tv_exit = findViewById(R.id.tv_logout);
        tv_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = openOrCreateDatabase("statelogin", MODE_PRIVATE, null);
                db.delete("login", null, null);
                db.close();
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        tv_update_profile = findViewById(R.id.tv_update);
        tv_update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, UpdateInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("idUser", idUser);
                intent.putExtra("data", bundle);
                startActivity(intent);
            }
        });
        tv_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAdmin();
            }
        });
        displayUser(idUser);
    }

    public void dialogAdmin() {
        dialog = new Dialog(ProfileActivity.this);
        dialog.setContentView(R.layout.admin_dialog);
        TextView tv_quiz = dialog.findViewById(R.id.tv_quiz);
        TextView tv_acc = dialog.findViewById(R.id.tv_acc);

        tv_quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(ProfileActivity.this, ManageRss.class);
//                startActivity(intent);
                dialog.dismiss();
            }
        });

        tv_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, ListUser.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void displayUser(String idUser) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(idUser);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    tv_name.setText(documentSnapshot.getString("name") + "");
                    tv_email.setText(hideEmail(documentSnapshot.getString("email") + ""));
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }

    public String hideEmail(String email) {
        int atSignIndex = email.indexOf('@');
        int numOfChars = atSignIndex;
        String[] parts = email.split("@");
        String masked = "";
        String domain = parts[1];
        int len = email.length();
        int hideLen = Math.min(len - numOfChars, numOfChars);
        for (int i = 0; i < len - domain.length(); i++) {
            if (i < hideLen || i >= len - numOfChars) {
                masked += "*";
            } else {
                masked += email.charAt(i);
            }
        }
        masked += domain;

        return masked;
    }

    public void checkRole() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(idUser);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    if (documentSnapshot.getString("role").equals("admin")) {
                        tv_admin.setVisibility(View.VISIBLE);
                        admin.setVisibility(View.VISIBLE);
                    } else {
                        tv_admin.setVisibility(View.GONE);
                        admin.setVisibility(View.GONE);
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }
}