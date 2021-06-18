package com.example.reachus;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class complete_profile extends AppCompatActivity {

    private TextView beInsider,logout,youraddresses,insiderOrders,personalInform,contactus,loginandsecurit;
    String becomeinsider,userId,isInsider;
    private static final String TAG = "Storing data";

    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    boolean isServiceProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_profile);

        Intent intent=getIntent();
        isServiceProvider=intent.getBooleanExtra("isServiceProvider",false);

        mAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        beInsider=findViewById(R.id.be_insider);
        youraddresses=findViewById(R.id.yourAddresses);
        insiderOrders=findViewById(R.id.insiderOrders);
        personalInform = findViewById(R.id.personalinfo);
        userId=mAuth.getCurrentUser().getUid();
        insiderOrders=findViewById(R.id.insiderOrders);
        contactus = findViewById(R.id.contactus);

        loginandsecurit = findViewById(R.id.loginandsecurity);
        loginandsecurit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(complete_profile.this,loginandesecurity.class));
                finish();
            }
        });







        Log.d("Data", isServiceProvider+"");
        if(isServiceProvider)
            insiderOrders.setVisibility(View.VISIBLE);

        beInsider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isServiceProvider)
                    startActivity(new Intent(getApplicationContext(),Service_Provider_Info.class));
                else
                    Toast.makeText(getApplicationContext(), "You are already a Service Provide", Toast.LENGTH_LONG).show();
            }
        });

        contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sub="ReachUs FeedBack";
                String to="teamreachus247@gmail.com";
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
                email.putExtra(Intent.EXTRA_SUBJECT,sub );
                email.putExtra(Intent.EXTRA_TEXT, "Enter your Message here");
               //need this to prompts email client only
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Choose an Email client :"));
            }
        });
        insiderOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), serviceBookings.class));
            }
        });

        youraddresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), inputUserAddress.class));
                Toast.makeText(getApplicationContext(),"your addresses here", Toast.LENGTH_LONG);
            }
        });
        //Logging out

        personalInform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Personal_Information.class));
                finish();
            }
        });
    }
}
