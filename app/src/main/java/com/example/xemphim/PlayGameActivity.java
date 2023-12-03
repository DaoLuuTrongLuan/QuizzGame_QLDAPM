package com.example.xemphim;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingDeque;

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
        Button button_next = findViewById(R.id.button_next_question);
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PlayGameActivity.this,AddQuestionActivity.class));
            }
        });

    }
    public boolean checkDupplicate(String ans,ArrayList<String> arrayList){
       return arrayList.contains(ans);
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
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                   for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                       String ques =(String) snapshot.child("question").getValue();
                       listQuestion.add(ques);
                       Log.d("resultLog", "ques = " + ques);
                   }
                handleList(listQuestion);

                } else {
                    Log.d("resultLog", "Không có node con.");
                }
                Log.d("resultLog", "onDataChange: "+listQuestion.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("ChildNode", "Lỗi khi đọc dữ liệu từ Firebase.", databaseError.toException());
            }
        });

    }
    public void addQuestion(long num,ArrayList<String> arrayList){
        createChildQuestion(arrayList,num,"Three of these animals hibernate.Which one does not?","Sloth","Mouse","Sloth","Frog","Snake");
        createChildQuestion(arrayList,num,"All of these animals are omnivorous except one.","Snail","Fox","Mouse","Opossum","Snail");
        createChildQuestion(arrayList,num,"Three of these Latin names are names of bears. Which is the odd one?","Felis silvestris catus","Melursus ursinus","Helarctos malayanus","Ursus minimus","Felis silvestris catus");
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
    private  void createChildQuestion(ArrayList<String> arrayList,long number, String question, String answer, String o1,String o2,String o3,String o4){
        if(checkDupplicate(question,arrayList)){
            String node = "data_question/question"+(number+1);
            Log.d("resultLog","Data node = " + node  );
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference(node);
            myRef.child("question").setValue(question);
            myRef.child("answer").setValue(answer);

            myRef.child("option").child("A").setValue(o1);
            myRef.child("option").child("B").setValue(o2);
            myRef.child("option").child("C").setValue(o3);
            myRef.child("option").child("D").setValue(o4);
            listQuestion.add(question);
        }
        countChildNodes();

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
        addQuestion(count,listQuestion);

    }
    private void handleList(ArrayList<String> arr){
        for(String s: arr){
            listQuestion.add(s);
        }
    }

}
