package com.app.asma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Task1Activity extends AppCompatActivity {

    RecyclerView recyclerView;
    StudentAdapter studentAdapter;
    List<StudentModel> studentModelList;
    StudentModel studentModel;

    Button buttonInsert;


    TextView textViewCity,textViewTemprature;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task1);

        textViewCity = (TextView) findViewById(R.id.txtcity1);
        textViewTemprature = (TextView) findViewById(R.id.txtTemprature1);
        imageView = (ImageView) findViewById(R.id.ivWeather1);



        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Task1Activity.this);

        textViewCity.setText(preferences.getString("City", "0"));
        textViewTemprature.setText(preferences.getString("Temprature", "0"));
        Glide.with(Task1Activity.this).load(preferences.getString("Image", "0")).into(imageView);


        buttonInsert = (Button) findViewById(R.id.btnInsert);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showInsertDialog(Task1Activity.this);

            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.task1RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        studentModelList=new ArrayList<>();
        studentAdapter = new StudentAdapter(this,studentModelList,true);
        recyclerView.setAdapter(studentAdapter);
        fetchList();

    }

    private void fetchList() {
        Query query = FirebaseDatabase.getInstance().getReference("Student");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                    studentModelList.clear();
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {

                        String ID,Name,Surname,Fathers_name,National_ID,date_of_birth,Gender;

                        ID = childSnapshot.getKey();
                        Name = childSnapshot.child("Name").getValue(String.class);
                        Surname = childSnapshot.child("Surname").getValue(String.class);
                        Fathers_name = childSnapshot.child("Fathers_name").getValue(String.class);
                        National_ID = childSnapshot.child("National_ID").getValue(String.class);
                        date_of_birth = childSnapshot.child("date_of_birth").getValue(String.class);
                        Gender = childSnapshot.child("Gender").getValue(String.class);


                        studentModel = new StudentModel(Integer.valueOf(ID),Name,Surname,Fathers_name,National_ID,date_of_birth,Gender);
                        studentModelList.add(studentModel);
                        studentAdapter.notifyDataSetChanged();

                    }

                }




            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void showInsertDialog(Context c) {
        LinearLayout layout = new LinearLayout(c);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText taskUserID = new EditText(c);
        taskUserID.setHint("User ID");
        layout.addView(taskUserID);

        final EditText taskName = new EditText(c);
        taskName.setHint("Name");
        layout.addView(taskName);

        final EditText taskSurname = new EditText(c);
        taskSurname.setHint("Surname");
        layout.addView(taskSurname);

        final EditText taskFathers_name = new EditText(c);
        taskFathers_name.setHint("Father's Name");
        layout.addView(taskFathers_name);

        final EditText taskNational_ID = new EditText(c);
        taskNational_ID.setHint("National ID");
        layout.addView(taskNational_ID);

        final EditText taskdate_of_birth = new EditText(c);
        taskdate_of_birth.setHint("Date of Birth");
        layout.addView(taskdate_of_birth);

        final EditText taskGender = new EditText(c);
        taskGender.setHint("Gender");
        layout.addView(taskGender);

        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Insert Record")
                .setMessage("Please enter the information to create student record")
                .setView(layout)
                .setPositiveButton("Insert", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        insert(taskUserID.getText().toString(),taskName.getText().toString(),taskSurname.getText().toString(),taskFathers_name.getText().toString(),taskNational_ID.getText().toString(),taskdate_of_birth.getText().toString(),taskGender.getText().toString());
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

    private void insert(String UserID,String taskNameText, String SurnameText, String Fathers_nameText, String National_idText, String date_of_birthText, String GenderText){
        FirebaseDatabase database =  FirebaseDatabase.getInstance();

//        Long tsLong = System.currentTimeMillis()/1000;
//        String userId = tsLong.toString();

        DatabaseReference mRef =  database.getReference().child("Student").child(UserID);
        mRef.child("Name").setValue(taskNameText);
        mRef.child("Surname").setValue(SurnameText);
        mRef.child("Fathers_name").setValue(Fathers_nameText);
        mRef.child("National_ID").setValue(National_idText);
        mRef.child("date_of_birth").setValue(date_of_birthText);
        mRef.child("Gender").setValue(GenderText);
        Toast.makeText(Task1Activity.this, "Successfully Added", Toast.LENGTH_SHORT).show();
    }

}