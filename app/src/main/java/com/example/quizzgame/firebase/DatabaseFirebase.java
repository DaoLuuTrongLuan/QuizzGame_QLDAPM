package com.example.quizzgame.firebase;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DatabaseFirebase {
    FirebaseFirestore db;
    public DatabaseFirebase() {
        this.db = FirebaseFirestore.getInstance();
    }
    public void saveAccount(String username, String password, String email) {
        Map<String, Object> save = new HashMap<>();
        save.put("username", username);
        save.put("password", password);
        save.put("email", email);
        save.put("name", "");
        save.put("role", "user");
        save.put("typeAccount", "0");
        db.collection("users").add(save);
    }
}

