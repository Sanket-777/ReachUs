package com.example.reachus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Personal_Information extends AppCompatActivity {
        EditText name,email,phone,age;
        Button subm;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    String userId;
    private static final String TAG = "Store Data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal__information);

        name = findViewById(R.id.PI_name);
        email = findViewById(R.id.PI_email);
        phone = findViewById(R.id.PI_phone);
        age = findViewById(R.id.PI_age);
        subm = findViewById(R.id.submit);

        mAuth= FirebaseAuth.getInstance();
        fStore= FirebaseFirestore.getInstance();

        userId=mAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());
                        name.setText(document.getString("fullName"));
                        email.setText(document.getString("Email"));
                        phone.setText(document.getString("Phone"));
                    }
                } else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                }
            }
        });




        subm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Build an AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(Personal_Information.this);

                // Set a title for alert dialog

                // Ask the final question
                builder.setMessage("Are you sure u want to save the changes?");

                // Set the alert dialog yes button click listener
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something when user clicked the Yes button
                        // Set the TextView visibility GONE
                        fStore = FirebaseFirestore.getInstance();
                        userId = mAuth.getCurrentUser().getUid();
                        //Ha code ahee bg databse mse add cha
                        DocumentReference documentReference = fStore.collection("users").document(userId);

                        documentReference.update("fullName",name.getText().toString());
                        documentReference.update("Email",email.getText().toString());
                        documentReference.update("Phone",phone.getText().toString());
                        documentReference.update("Age",age.getText().toString());
                        Toast.makeText(Personal_Information.this, "Values Changed", Toast.LENGTH_SHORT).show();
                    }
                });

                // Set the alert dialog no button click listener
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something when No button clicked
                        Toast.makeText(getApplicationContext(),
                                "No Button Clicked",Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog dialog = builder.create();
                // Display the alert dialog on interface
                dialog.show();
            }
        });
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {



            }
        });
    }



}