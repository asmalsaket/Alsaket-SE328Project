package com.app.asma;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class StudentAdapter2 extends RecyclerView.Adapter<StudentAdapter2ViewHolder>{

    Context context;
    private List<StudentModel> studentModelList;

    public StudentAdapter2(Context context, List<StudentModel> studentModelList) {
        this.context = context;
        this.studentModelList = studentModelList;
    }

    @Override
    public StudentAdapter2ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_layout2,parent,false);
        return new StudentAdapter2ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter2ViewHolder holder, int position) {

        final StudentModel studentModel = studentModelList.get(position);

        holder.txtID.setText(String.valueOf(studentModel.getID()));
        holder.txtName.setText(studentModel.getName());
        holder.txtSname.setText(studentModel.getSurname());
        holder.txtFname.setText(studentModel.getFathers_name());
        holder.txtNID.setText(studentModel.getNational_ID());
        holder.txtDOB.setText(studentModel.getDate_of_birth());
        holder.txtGender.setText(studentModel.getGender());

holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        alertDialog.setTitle("Message");

        alertDialog.setMessage("Do you want to delete?");

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Delete", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference mRef = database.getReference().child("Student").child(String.valueOf(studentModel.getID()));
                mRef.removeValue();
                int removeIndex = position;
                studentModelList.remove(removeIndex);
                notifyDataSetChanged();
                Toast.makeText(context, "Successfully Deleted", Toast.LENGTH_SHORT).show();
            }});

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {

            }});

        alertDialog.show();

    }
});


holder.buttonUpdate.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        showUpdateDialog(studentModel.getID(), studentModel.getName(), studentModel.getSurname(), studentModel.getFathers_name(), studentModel.getNational_ID(), studentModel.getDate_of_birth(), studentModel.getGender());

    }
});


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
        FirebaseDatabase database =  FirebaseDatabase.getInstance();
        DatabaseReference mRef =  database.getReference().child("Student").child(String.valueOf(UserID));

        mRef.child("Name").setValue(taskNameText);
        mRef.child("Surname").setValue(SurnameText);
        mRef.child("Fathers_name").setValue(Fathers_nameText);
        mRef.child("National_ID").setValue(National_idText);
        mRef.child("date_of_birth").setValue(date_of_birthText);
        mRef.child("Gender").setValue(GenderText);

        Toast.makeText(context, "Successfully Updated", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return studentModelList.size();
    }
}

class StudentAdapter2ViewHolder extends RecyclerView.ViewHolder{

    TextView txtID,txtName,txtSname,txtFname,txtNID,txtDOB,txtGender;

    Button buttonDelete,buttonUpdate;
    public StudentAdapter2ViewHolder(@NonNull View itemView) {
        super(itemView);
        txtID=itemView.findViewById(R.id.txtID);
        txtName=itemView.findViewById(R.id.txtName);
        txtSname=itemView.findViewById(R.id.txtSname);
        txtFname=itemView.findViewById(R.id.txtFname);
        txtNID=itemView.findViewById(R.id.txtNID);
        txtDOB=itemView.findViewById(R.id.txtDOB);
        txtGender=itemView.findViewById(R.id.txtGender);

        buttonDelete=itemView.findViewById(R.id.btnDelete);
        buttonUpdate=itemView.findViewById(R.id.btnUpdate);
    }
}
