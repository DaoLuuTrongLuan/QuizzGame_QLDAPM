package com.example.quizzgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class PlayGameActivity extends AppCompatActivity {
    private TextView questionView;
    private String question = "";
    String myChosse;
    long countQuest;
    ArrayList<String> listQuestion = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        Button button_submit = findViewById(R.id.button_submit_ans);


    }
    public boolean checkDupplicate(String ans,List<String> list){
       return list.contains(ans);
    }
   
    public void removeSingleNode(String nameNode) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("data_question/"+nameNode);

        // Sử dụng removeValue() để xóa node
        databaseReference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d("DeleteNode", "Xóa node  thành công.");
                } else {
                    Log.e("DeleteNode", "Lỗi khi xóa node question10", task.getException());
                }
            }
        });
    }
    public void getListQuestion(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("data_question");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Set<String> list = randomList10Question(snapshot.getChildrenCount());
                for(String s : list){

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    // tao danh sach 10 cau hoi duoc lay ngau nhien trong database
    public Set<String> randomList10Question(long totalQues){
        Random rd = new Random();
        Set<String> list = new HashSet<>();
        while(list.size()<10){
            int rdNum = rd.nextInt((int) totalQues)+1;
            list.add("question"+rdNum);
        }
        return list;
    }
    // tien hanh ghi du lieu duoc lay ve vao giao dien cau hoi
    public void setQuestionView(ArrayList<Question> listquestion){
        for(int i=1;i<11;i++){
            String textViewId = "question"+i;
            // Lấy id tương ứng với tên id
            int resId = getResources().getIdentifier(textViewId, "id", getPackageName());

            // Kiểm tra xem id có tồn tại hay không
            if (resId != 0) {

                // Nếu tồn tại, thì sử dụng findViewById để lấy TextView và đặt text
                TextView questionTextView = findViewById(resId);
                questionTextView.setText(listquestion.get(i).getQuestion());
            }
        }
    }
    public void removeNode(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("data_question");

        // Xóa các nodes từ ques10 đến ques20
        for (int i = 2; i <= 5; i++) {
            String questionKey = "question" + i;
            DatabaseReference questionRef = databaseReference.child(questionKey);

            // Sử dụng removeValue() để xóa node
            questionRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.d("DeleteNode", "Xóa node " + questionKey + " thành công.");
                    } else {
                        Log.e("DeleteNode", "Lỗi khi xóa node " + questionKey, task.getException());
                    }
                }
            });
        }
    }

    public void updateCountQuest() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("data_question");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long temp = snapshot.getChildrenCount();
                countQuest=temp;
                Log.d("resultCount", "count temp = " + countQuest);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("resultCount", "Lỗi khi đọc dữ liệu từ Firebase.", error.toException());
            }
        });
    }

    private void countChildNodes() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("data_question");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    countQuest = snapshot.getChildrenCount();
                    Log.d("ChildCount", "Number of child nodes: " + countQuest);

                    // Sử dụng giá trị nodeCount ở đây hoặc gọi một hàm khác để sử dụng giá trị này
                    handleNodeCount(countQuest);
                } else {
                    countQuest = 0;
                    Log.d("ChildCount", "No child nodes.");

                    // Xử lý khi không có node con (nodeCount = 0)
                    handleNodeCount(countQuest);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("ChildCount", "Error reading data from Firebase.", error.toException());
            }
        });
    }

    private void handleNodeCount(long count) {
        // Xử lý giá trị nodeCount ở đây
        Log.d("ChildCount", "Handling node count: " + count);
        getListQuestion();

    }
    private void handleList(ArrayList<String> arr){
        for(String s: arr){
            listQuestion.add(s);
        }
    }

}
