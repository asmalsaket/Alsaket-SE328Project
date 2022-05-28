package com.app.asma;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudentAdapter3 extends RecyclerView.Adapter<StudentAdapter3ViewHolder>{

    DBMain dbMain;
    private static final String TABLE = "student";
    Context context;
    private List<StudentModel> studentModelList;
    boolean Task;
    Task5Activity task5Activity;

    public StudentAdapter3(Context context, List<StudentModel> studentModelList, boolean task,Task5Activity task5Activity) {
        this.context = context;
        this.studentModelList = studentModelList;
        Task = task;
        this.task5Activity = task5Activity;

        dbMain = new DBMain(context);
    }

    @Override
    public StudentAdapter3ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_layout,parent,false);
        return new StudentAdapter3ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter3ViewHolder holder, int position) {

        final StudentModel studentModel = studentModelList.get(position);

        holder.txtID.setText(String.valueOf(studentModel.getID()));
        holder.txtName.setText(studentModel.getName());
        holder.txtSname.setText(studentModel.getSurname());
        holder.txtFname.setText(studentModel.getFathers_name());
        holder.txtNID.setText(studentModel.getNational_ID());
        holder.txtDOB.setText(studentModel.getDate_of_birth());
        holder.txtGender.setText(studentModel.getGender());


        holder.stdLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, studentModel.getName()+" "+studentModel.getSurname(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.stdLinearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                if (Task == true){

                    AlertDialog alertDialog = new AlertDialog.Builder(context).create();

                    alertDialog.setTitle("Message");

                    alertDialog.setMessage("Do you want to update or delete?");

                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Delete", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int id) {

                            SQLiteDatabase db = dbMain.getWritableDatabase();

                            db.delete(TABLE, "ID=?", new String[]{String.valueOf(studentModel.getID())});
                            db.close();
                            int removeIndex = position;
                            studentModelList.remove(removeIndex);
                           notifyDataSetChanged();
                            Toast.makeText(context, "Successfully Deleted", Toast.LENGTH_SHORT).show();

                        }
                    });


                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Update", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int id) {
                            showUpdateDialog(studentModel.getID(), studentModel.getName(), studentModel.getSurname(), studentModel.getFathers_name(), studentModel.getNational_ID(), studentModel.getDate_of_birth(), studentModel.getGender());


                        }
                    });

                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Cancel", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int id) {

                            //...

                        }
                    });


                    alertDialog.show();

                }

                return false;
            }

        });
    }

    @Override
    public int getItemCount() {
        return studentModelList.size();
    }


    private void showUpdateDialog(Integer UserID,String taskNameText, String SurnameText, String Fathers_nameText, String National_idText, String date_of_birthText, String GenderText) {
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText taskName = new EditText(context);
        taskName.setHint("Name");
        taskName.setText(taskNameText);
        layout.addView(taskName);

        final EditText taskSurname = new EditText(context);
        taskSurname.setHint("Surname");
        taskSurname.setText(SurnameText);
        layout.addView(taskSurname);

        final EditText taskFathers_name = new EditText(context);
        taskFathers_name.setHint("Father's Name");
        taskFathers_name.setText(Fathers_nameText);
        layout.addView(taskFathers_name);

        final EditText taskNational_ID = new EditText(context);
        taskNational_ID.setHint("National ID");
        taskNational_ID.setText(National_idText);
        layout.addView(taskNational_ID);

        final EditText taskdate_of_birth = new EditText(context);
        taskdate_of_birth.setHint("Date of Birth");
        taskdate_of_birth.setText(date_of_birthText);
        layout.addView(taskdate_of_birth);

        final EditText taskGender = new EditText(context);
        taskGender.setHint("Gender");
        taskGender.setText(GenderText);
        layout.addView(taskGender);

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("Update Record")
                .setMessage("Please enter the information to update student record")
                .setView(layout)
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        update(UserID,taskName.getText().toString(),taskSurname.getText().toString(),taskFathers_name.getText().toString(),taskNational_ID.getText().toString(),taskdate_of_birth.getText().toString(),taskGender.getText().toString());
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

    private void update(Integer UserID,String taskNameText, String SurnameText, String Fathers_nameText, String National_idText, String date_of_birthText, String GenderText){

        SQLiteDatabase db = dbMain.getWritableDatabase();
        ContentValues values = new ContentValues();

        ContentValues contentValues = new ContentValues();

        contentValues.put("Name", taskNameText);
        contentValues.put("Surname", SurnameText);
        contentValues.put("Fathers_name", Fathers_nameText);
        contentValues.put("National_ID", National_idText);
        contentValues.put("date_of_birth", date_of_birthText);
        contentValues.put("Gender", GenderText);

        db.update(TABLE, contentValues, "ID=?", new String[]{String.valueOf(UserID)});
        db.close();

        task5Activity.displayData();
        notifyDataSetChanged();
        Toast.makeText(context, "Successfully Updated", Toast.LENGTH_SHORT).show();

    }


}

class StudentAdapter3ViewHolder extends RecyclerView.ViewHolder{

    TextView txtID,txtName,txtSname,txtFname,txtNID,txtDOB,txtGender;

    LinearLayout stdLinearLayout;
    public StudentAdapter3ViewHolder(@NonNull View itemView) {
        super(itemView);
        txtID=itemView.findViewById(R.id.txtID);
        txtName=itemView.findViewById(R.id.txtName);
        txtSname=itemView.findViewById(R.id.txtSname);
        txtFname=itemView.findViewById(R.id.txtFname);
        txtNID=itemView.findViewById(R.id.txtNID);
        txtDOB=itemView.findViewById(R.id.txtDOB);
        txtGender=itemView.findViewById(R.id.txtGender);
        stdLinearLayout=itemView.findViewById(R.id.stdLinearLayout);
    }
}