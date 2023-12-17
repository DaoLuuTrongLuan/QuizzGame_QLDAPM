package com.example.quizzgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.example.quizzgame.model.Question;
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
    Set<String> listQuestion;
    ArrayList<Question> list = new ArrayList<>();
    boolean isPressedSubmit;
    private CountDownTimer countDownTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getListQuestion();
        setContentView(R.layout.activity_play_game);

        Button button_submit = findViewById(R.id.button_submit_ans);
        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (countDownTimer != null) {
                    countDownTimer.cancel(); // Hủy Countdown Timer
                }
                int totalScore = checkScore();
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference("list_test");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int num = (int)snapshot.getChildrenCount()+1;
                        String nameNode = "test"+num;
                        databaseReference.child(nameNode).setValue(totalScore);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                showNotionDone(totalScore);
                isPressedSubmit=true;
                startActivity(new Intent(PlayGameActivity.this,MainActivity2.class));
            }
        });
        countDownTime(button_submit,10);
    }
    public void countDownTime(Button button,int minutes){
        // Khai báo và lấy tham chiếu của TextView cho thời gian đếm ngược
        TextView countdownTextView = findViewById(R.id.countdown_timer);

        // Thời gian đếm ngược là 10 phút (600000 milliseconds)
        long countdownMillis = minutes * 60 * 1000;
        // Khởi tạo CountDownTimer với thời gian đếm ngược và bước là 1 giây
        countDownTimer =new CountDownTimer(countdownMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Cập nhật nội dung TextView mỗi giây
                long secondsRemaining = millisUntilFinished / 1000;
                long minutes = secondsRemaining / 60;
                long seconds = secondsRemaining % 60;

                // Định dạng thời gian và cập nhật vào TextView
                String formattedTime = String.format("%02d:%02d", minutes, seconds);
                Log.d("count down time","time : "+formattedTime );
                countdownTextView.setText(formattedTime);

            }

            @Override
            public void onFinish() {
                // Xử lý khi đếm ngược kết thúc (nếu cần)
                Log.d("count down time", "onFinish: da ket thuc");
                countdownTextView.setText("00:00");
                button.callOnClick();
            }
        }.start();

    }
    public int checkScore(){
        int result = 0;
        for(int i =1; i<11;i++){
            int index = i-1;    // vi tri cua cau hoi trong list
            String radioGroupId = "radioGroup"+i;
            String answer = list.get(index).getAns();   // dap an cua cau hoi tuong ung
            // Lấy id tương ứng với tên id
            int groupId = getResources().getIdentifier(radioGroupId, "id", getPackageName());
            // kiem tra id co ton tai hay khong ?
            if(groupId!=0){
                RadioGroup radioGroup = findViewById(groupId);
                //lay id cua radio button duoc chon
                int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                // Kiểm tra xem có RadioButton được chọn hay không
                if (selectedRadioButtonId != -1) {
                    // Lấy tham chiếu đến RadioButton được chọn
                    RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
                    // Lấy giá trị của RadioButton được chọn
                    String selectedValue = selectedRadioButton.getText().toString();
                    if(selectedValue.equals(answer)){
                        result++;
                    }
                } else {
                    // Không có RadioButton được chọn, không cộng điểm
                }
            }
        }
        return result;

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
    // tien hanh set view tu du lieu duoc lay ve vao giao dien cau hoi
    public void setQuestionView(ArrayList<Question> listquestion){
        for(int i=1;i<11;i++){
            String textViewId = "question"+i;
            String chooseId_a = "choose"+i+'a';
            String chooseId_b = "choose"+i+'b';
            String chooseId_c = "choose"+i+'c';
            String chooseId_d = "choose"+i+'d';
            // Lấy id tương ứng với tên id
            int resId = getResources().getIdentifier(textViewId, "id", getPackageName());
            int choose_a = getResources().getIdentifier(chooseId_a,"id",getPackageName());
            int choose_b = getResources().getIdentifier(chooseId_b,"id",getPackageName());
            int choose_c = getResources().getIdentifier(chooseId_c,"id",getPackageName());
            int choose_d = getResources().getIdentifier(chooseId_d,"id",getPackageName());
            // Kiểm tra xem id có tồn tại hay không
            if (resId != 0) {
                TextView questionTextView = findViewById(resId);
                questionTextView.setText(listquestion.get(i-1).getQuestion());
            }
            if(choose_a!=0){
                RadioButton chooseA = findViewById(choose_a);
                chooseA.setText(listquestion.get(i-1).getO1());
            }
            if(choose_b!=0){
                RadioButton chooseB = findViewById(choose_b);
                chooseB.setText(listquestion.get(i-1).getO2());
            }
            if(choose_c!=0){
                RadioButton chooseC = findViewById(choose_c);
                chooseC.setText(listquestion.get(i-1).getO3());
                Log.d("setthanhcong","setc thanh cong");
            }
            if(choose_d!=0){
                RadioButton chooseD = findViewById(choose_d);
                chooseD.setText(listquestion.get(i-1).getO4());
                Log.d("setthanhcong", "set d thanh cong:");
            }
            if(choose_d==0){
                Log.d("id bi loi", "khong tim thay id cua D");
            }
        }
    }
    public boolean checkDupplicate(String ans, List<String> list){
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
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                   long number = dataSnapshot.getChildrenCount();
                    listQuestion=randomList10Question(number);  // tap hop 10  question ngau nhien
//                    ArrayList<Question> list10Question = new ArrayList<>(); //danh sach 10 question lay theo noi dung cau hoi tao ngau nhien
                    //ghi du lieu vao list10Question
                    for(String question : listQuestion){
                        Question temp = new Question();
                        String ques = dataSnapshot.child(question).child("question").getValue().toString();
                        String a = dataSnapshot.child(question).child("option").child("A").getValue().toString();
                        String b = dataSnapshot.child(question).child("option").child("B").getValue().toString();
                        String c = dataSnapshot.child(question).child("option").child("C").getValue().toString();
                        String d = dataSnapshot.child(question).child("option").child("D").getValue().toString();
                        String ans = dataSnapshot.child(question).child("answer").getValue().toString();
                        temp.setQuestion(ques);
                        temp.setO1(a);
                        temp.setO2(b);
                        temp.setO3(c);
                        temp.setO4(d);
                        temp.setAns(ans);
                        list.add(temp);
                    }
                    setQuestionView(list);
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
    public void showNotionDone(int score){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.notion_success_layout, (ViewGroup) findViewById(R.id.notion_done));
        TextView text = layout.findViewById(R.id.text_notion_done);
        text.setText("Nộp bài thành công!!! Tổng điêểm của bạn là : "+score);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

}
