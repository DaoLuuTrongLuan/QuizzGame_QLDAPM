package com.example.quizzgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DelQuestionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_del_question);
        EditText editText  = findViewById(R.id.name_questdel);
        Button btn = findViewById(R.id.btn_delete);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeSingleNode(editText.getText().toString());
            }
        });

    }
    public void removeSingleNode(String nameNode) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("data_question/"+nameNode);

        // Sử dụng removeValue() để xóa node
        databaseReference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d("DeleteNode", "Xóa node  thành công.");
                    showNotionDone();
                } else {
                    showNotionFailure("Không thành công!");
                    Log.e("DeleteNode", "Lỗi khi xóa node question10", task.getException());
                }
            }
        });
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