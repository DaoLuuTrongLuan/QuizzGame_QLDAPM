package com.example.quizzgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddQuestionActivity extends AppCompatActivity {
    EditText input_question,input_choose1,input_choose2,input_choose3,input_choose4,input_ans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        input_question = findViewById(R.id.input_ques);
        input_choose1 = findViewById(R.id.input_choose1);
        input_choose2 = findViewById(R.id.input_choose2);
        input_choose3 = findViewById(R.id.input_choose3);
        input_choose4 = findViewById(R.id.input_choose4);
        input_ans = findViewById(R.id.input_ans);


        input_question.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                input_choose1.requestFocus();
                return true;
            }
            return false;
        });
        input_choose1.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                input_choose2.requestFocus();
                return true;
            }
            return false;
        });
        input_choose2.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                input_choose3.requestFocus();
                return true;
            }
            return false;
        });
        input_choose3.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                input_choose4.requestFocus();
                return true;
            }
            return false;
        });
        input_choose4.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                input_ans.requestFocus();
                return true;
            }
            return false;
        });

        input_ans.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                // Thực hiện hành động sau khi nhập xong tất cả các trường
                // Ví dụ: ẩn bàn phím, xử lý dữ liệu, v.v.
                return true;
            }
            return false;
        });



        Button button_submit = findViewById(R.id.submit_button);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("data_question");
        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String question = input_question.getText().toString();
                String choice1 = input_choose1.getText().toString();
                String choice2 = input_choose2.getText().toString();
                String choice3 = input_choose3.getText().toString();
                String choice4 = input_choose4.getText().toString();
                String answer = input_ans.getText().toString();
                Log.d("readtext",question);
                if (!question.isEmpty() && !choice1.isEmpty() && !choice2.isEmpty() && !choice3.isEmpty() && !choice4.isEmpty() && !answer.isEmpty()) {

                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            long number_question = snapshot.getChildrenCount();
                            if (number_question == 0) {
                                createChildQuestion(number_question, question, answer, choice1, choice2, choice3, choice4);
                                clearDataInput();
                                showNotionDone();

                            } else {
                                ArrayList<String> arrayList = new ArrayList<>();
                                //  Lấy danh sách các câu hỏi đã có
                                for (DataSnapshot snapshotQues : snapshot.getChildren()) {
                                    String ques = (String) snapshotQues.child("question").getValue();
                                    arrayList.add(ques);
                                    Log.d("listQues", ques);
                                    if(arrayList.contains(question)){
                                        clearDataInput();
                                        showNotionFailure("Question is already exists!!!");
                                    }
                                    else{
                                        createChildQuestion(number_question, question, answer, choice1, choice2, choice3, choice4);
                                        clearDataInput();
                                        showNotionDone();
                                    }

                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else{
                    showNotionFailure("Please fill in all data");
                }
            }
        });


    }
    private  void createChildQuestion(long number, String question, String answer, String o1, String o2, String o3, String o4){
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
    }
    public boolean checkDupplicate(String ans,ArrayList<String> arrayList){
        return arrayList.contains(ans);
    }
    public void clearDataInput(){
        input_question.setText("");
        input_choose1.setText("");
        input_choose2.setText("");
        input_choose3.setText("");
        input_choose4.setText("");
        input_ans.setText("");
    }
    public void showNotionDone(){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.notion_success_layout, (ViewGroup) findViewById(R.id.notion_done));
        TextView text = layout.findViewById(R.id.text_notion_done);
        text.setText("Successful!!!");

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
    public void showNotionFailure(String s){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.notion_failure_layout, (ViewGroup) findViewById(R.id.notion_failure));
        TextView text = layout.findViewById(R.id.text_notion_failure);
        text.setText(s);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}