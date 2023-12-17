package com.example.quizzgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ListTestActivity extends AppCompatActivity {

    private LinearLayout containerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_test);

        Button createTest = findViewById(R.id.btn_createTest);
        createTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference rf = firebaseDatabase.getReference("data_question");
                rf.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        long num = snapshot.getChildrenCount();
                        ArrayList<String> list = randomList10Question(num);
                        createTest(list,num);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        containerLayout = findViewById(R.id.container_layout);

        //ArrayList<String> chứa tên các bài test
        ArrayList<String> testNames = getTestNamesFromListTest();

        // Tạo button cho mỗi bài test và thêm vào LinearLayout
        for (String testName : testNames) {
            addButton(testName);
        }
    }
    public void createTest(ArrayList<String> list ,long num){
        String node = "list_test/test"+(num+1);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference rf = firebaseDatabase.getReference(node);
        for(int i=1;i<11;i++){
            String temp = "cau"+i;
            rf.child(temp).setValue(list.get(i-1));
        }
    }
    private void addButton(String testName) {
        Button button = new Button(this);
        button.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        button.setText(testName);
        button.setId(View.generateViewId()); // Đặt ID cho mỗi button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Xử lý sự kiện khi button được nhấn
                onTestButtonClick(testName);
            }
        });

        containerLayout.addView(button); // Thêm button vào LinearLayout
    }

    private void onTestButtonClick(String testName) {
        // Tạo Intent để chuyển từ ListTestActivity sang PlayGameActivity
        Intent intent = new Intent(ListTestActivity.this, PlayGameActivity.class);

        // Đưa dữ liệu vào Intent
        intent.putExtra("TEST_NAME", testName);
        startActivity(intent);
        Toast.makeText(this, "Start Test: " + testName, Toast.LENGTH_SHORT).show();
    }

    private ArrayList<String> getTestNamesFromListTest() {
        ArrayList<String> testNames = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("list_test");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count = (int) snapshot.getChildrenCount();
                for(int i=1;i<=count;i++){
                    String temp = "test"+i;
                    testNames.add(temp);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return testNames;
    }
    public ArrayList<String> randomList10Question(long totalQues){
        Random rd = new Random();
        Set<String> list = new HashSet<>();
        while(list.size()<10){
            int rdNum = rd.nextInt((int) totalQues)+1;
            list.add("question"+rdNum);
        }
        return new ArrayList<>(list);
    }
}
