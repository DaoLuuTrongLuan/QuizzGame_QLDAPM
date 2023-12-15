package com.example.quizzgame.firebase;

import com.google.firebase.firestore.DocumentReference;
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
        save.put("name", "no name");
        save.put("role", "user");
        save.put("typeAccount", "0");
        db.collection("users").add(save);
    }

    public void deleteUser(String document) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(document);
        docRef.delete();
    }

    public void updateNameUser(String name, String id) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(id);
        Map<String, Object> newData = new HashMap<>();
        newData.put("name", name);
        docRef.update(newData);
    }

    public void updatePasswordUser(String pass, String id) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(id);
        Map<String, Object> newData = new HashMap<>();
        newData.put("password", pass);
        docRef.update(newData);
    }
}
