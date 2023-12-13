package com.example.quizzgame.firebase;

import com.google.firebase.firestore.FirebaseFirestore;

public class DatabaseFirebase {
    FirebaseFirestore db;

    public DatabaseFirebase() {
        this.db = FirebaseFirestore.getInstance();
    }
}
