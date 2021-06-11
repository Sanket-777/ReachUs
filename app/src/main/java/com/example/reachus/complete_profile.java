package com.example.reachus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class complete_profile extends AppCompatActivity {

    private TextView beInsider,logout,youraddresses,insiderOrders,personalInform;
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
        logout=findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Login_page.class));
                finish();
            }
        });

        personalInform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Personal_Information.class));
                finish();
            }
        });
    }
}
