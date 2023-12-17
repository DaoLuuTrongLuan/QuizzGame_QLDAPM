package com.example.quizzgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
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
    ArrayList<String> testNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getTestNamesFromListTest();
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
                        createTest(list);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                setContentView(R.layout.activity_list_test);
            }
        });


    }
    //ghi vao list_test
    public void createTest(ArrayList<String> list){

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference rf = firebaseDatabase.getReference("list_test");

        rf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long num = snapshot.getChildrenCount();
                int so = (int) snapshot.getChildrenCount();
                Log.d("check createTest", "onDataChange: "+so);
                for(int i =1;i<11;i++){
                    writeTest(num,i,list.get(i-1));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void writeTest(long number,int socau,String value ){
        String node = "list_test/test"+(number+1);
        String ques = "cau"+socau;
        DatabaseReference rf = FirebaseDatabase.getInstance().getReference(node);
        rf.child(ques).setValue(value);

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
        Toast.makeText(this, "Start Test: " + testName, Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    private void getTestNamesFromListTest() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("list_test");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count = (int) snapshot.getChildrenCount();
                Log.d("testNames", "count :"+count);
                for(int i=1;i<=count;i++){
                    String temp = "test"+i;
                    testNames.add(temp);
                    Log.d("testNames", "onDataChange: "+testNames.size());
                }
                containerLayout = findViewById(R.id.container_layout);

                // Tạo button cho mỗi bài test và thêm vào LinearLayout
                for (String testName : testNames) {
                    addButton(testName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Log.d("testNames", "getTestNamesFromListTest: "+testNames.size());

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
