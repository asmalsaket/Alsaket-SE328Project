package com.app.asma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Task5Activity extends AppCompatActivity {


    RecyclerView recyclerView;
    StudentAdapter3 studentAdapter;
    List<StudentModel> studentModelList;
    StudentModel studentModel;

    DBMain dbMain;
    private static final String TABLE = "student";
    SQLiteDatabase sqLiteDatabase;
    Button buttonFetch, btnInsert;
    int[] id;

    TextView textViewCity,textViewTemprature;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task5);

        textViewCity = (TextView) findViewById(R.id.txtcitym);
        textViewTemprature = (TextView) findViewById(R.id.txtTempraturem);
        imageView = (ImageView) findViewById(R.id.ivWeatherm);


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Task5Activity.this);
        textViewCity.setText(preferences.getString("City", "0"));
        textViewTemprature.setText(preferences.getString("Temprature", "0"));
        Glide.with(Task5Activity.this).load(preferences.getString("Image", "0")).into(imageView);


        dbMain = new DBMain(this);

        recyclerView = (RecyclerView) findViewById(R.id.task5RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        buttonFetch = (Button) findViewById(R.id.btnFetch);
        buttonFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchData();
            }
        });

        btnInsert = (Button) findViewById(R.id.btnInsertSQLite);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInsertDialog(Task5Activity.this);
            }
        });

        displayData();


    }

    private void fetchData() {
        Query query = FirebaseDatabase.getInstance().getReference("Student");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Long recid = null;
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {

                    String ID,Name,Surname,Fathers_name,National_ID,date_of_birth,Gender;

                    ID = childSnapshot.getKey();
                    Name = childSnapshot.child("Name").getValue(String.class);
                    Surname = childSnapshot.child("Surname").getValue(String.class);
                    Fathers_name = childSnapshot.child("Fathers_name").getValue(String.class);
                    National_ID = childSnapshot.child("National_ID").getValue(String.class);
                    date_of_birth = childSnapshot.child("date_of_birth").getValue(String.class);
                    Gender = childSnapshot.child("Gender").getValue(String.class);

                    sqLiteDatabase = dbMain.getWritableDatabase();

                    ContentValues contentValues = new ContentValues();
                    contentValues.put("ID", Integer.valueOf(ID));
                    contentValues.put("Name", Name);
                    contentValues.put("Surname", Surname);
                    contentValues.put("Fathers_name", Fathers_name);
                    contentValues.put("National_ID", National_ID);
                    contentValues.put("date_of_birth", date_of_birth);
                    contentValues.put("Gender", Gender);

                    recid = sqLiteDatabase.insert(TABLE, null, contentValues);

                }
                if (recid != null) {
                    Toast.makeText(Task5Activity.this, "successfully retrieved from firebase and loaded in SQlite", Toast.LENGTH_SHORT).show();
                    displayData();
                } else {
                    Toast.makeText(Task5Activity.this, "something wrong try again", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void displayData() {

        sqLiteDatabase = dbMain.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE, null);

        ArrayList<StudentModel> studentModelList = new ArrayList<>();

        // moving our cursor to first position.
        if (cursor.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                studentModelList.add(new StudentModel(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6)));


                studentAdapter = new StudentAdapter3(this,studentModelList,true,Task5Activity.this);
                recyclerView.setAdapter(studentAdapter);
                studentAdapter.notifyDataSetChanged();
            } while (cursor.moveToNext());
            // moving our cursor to next.
        }
        cursor.close();

    }


    private void showInsertDialog(Context c) {
        LinearLayout layout = new LinearLayout(c);
        layout.setOrientation(LinearLayout.VERTICAL);

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
                        insert(taskName.getText().toString(),taskSurname.getText().toString(),taskFathers_name.getText().toString(),taskNational_ID.getText().toString(),taskdate_of_birth.getText().toString(),taskGender.getText().toString());
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

    private void insert(String taskNameText, String SurnameText, String Fathers_nameText, String National_idText, String date_of_birthText, String GenderText){

        Long tsLong = System.currentTimeMillis()/1000;
        String ID = tsLong.toString();

        sqLiteDatabase = dbMain.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", Integer.valueOf(ID));
        contentValues.put("Name", taskNameText);
        contentValues.put("Surname", SurnameText);
        contentValues.put("Fathers_name", Fathers_nameText);
        contentValues.put("National_ID", National_idText);
        contentValues.put("date_of_birth", date_of_birthText);
        contentValues.put("Gender", GenderText);

        Long recid = sqLiteDatabase.insert(TABLE, null, contentValues);

        if (recid != null) {
            Toast.makeText(Task5Activity.this, "successfully Added", Toast.LENGTH_SHORT).show();
            displayData();
        } else {
            Toast.makeText(Task5Activity.this, "something wrong try again", Toast.LENGTH_SHORT).show();
        }
    }
}


