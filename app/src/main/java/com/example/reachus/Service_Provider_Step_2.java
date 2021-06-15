package com.example.reachus;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class Service_Provider_Step_2 extends AppCompatActivity {

    LinearLayout repairingServicesLayout,cleaningServicesLayout,maidServicesLayout,carServicesLayout;
    Spinner mainJob;
    String repairing,cleaning,maid,car,userId;
    String firstJob;
    Spinner repairingServices;
    Spinner cleaningServices;
    Spinner maidServices;
    Spinner carServices;
    Button continueStep4;
    EditText description,price;
    Boolean valid;
    int counts;

    FirebaseFirestore fStore;
    FirebaseAuth mAuth;

    private static final String TAG = "Storing data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service__provider__step_2);

        fStore=FirebaseFirestore.getInstance();
        mAuth=FirebaseAuth.getInstance();

        mainJob=findViewById(R.id.mainJob);

        carServicesLayout=findViewById(R.id.carServicesLayout);
        repairingServicesLayout=findViewById(R.id.repairingServicesLayout);
        maidServicesLayout=findViewById(R.id.maidServicesLayout);
        cleaningServicesLayout=findViewById(R.id.cleaningServicesLayout);

        carServices=(Spinner)findViewById(R.id.carServices);
        repairingServices=(Spinner)findViewById(R.id.repairingServices);
        maidServices=(Spinner)findViewById(R.id.maidServices);
        cleaningServices=(Spinner)findViewById(R.id.cleaningServices);

        description=findViewById(R.id.jobDescription);
        price=findViewById(R.id.priceRequirement);

        continueStep4=findViewById(R.id.continueStep4);

        mainJob.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                firstJob=parent.getSelectedItem().toString();
                if(firstJob.equals("Car Service")){
                    carServicesLayout.setVisibility(View.VISIBLE);
                    repairingServicesLayout.setVisibility(View.GONE);
                    maidServicesLayout.setVisibility(View.GONE);
                    cleaningServicesLayout.setVisibility(View.GONE);
                }
                else if(firstJob.equals("Repairing Service")){
                    carServicesLayout.setVisibility(View.GONE);
                    repairingServicesLayout.setVisibility(View.VISIBLE);
                    maidServicesLayout.setVisibility(View.GONE);
                    cleaningServicesLayout.setVisibility(View.GONE);
                }
                else if(firstJob.equals("Maid Service")){
                    carServicesLayout.setVisibility(View.GONE);
                    repairingServicesLayout.setVisibility(View.GONE);
                    maidServicesLayout.setVisibility(View.VISIBLE);
                    cleaningServicesLayout.setVisibility(View.GONE);
                }
                else if(firstJob.equals("Cleaning Service")){
                    carServicesLayout.setVisibility(View.GONE);
                    repairingServicesLayout.setVisibility(View.GONE);
                    maidServicesLayout.setVisibility(View.GONE);
                    cleaningServicesLayout.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(Service_Provider_Step_2.this,"Nothing is selected", Toast.LENGTH_LONG);
            }
        });

        continueStep4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()==true) {
                    storeInfo();
                }
                else
                {
                    Log.d(TAG, "Validation Error");
                }
            }
        });
    }

    private boolean validate() {
        valid = true;
        String descriptionText=description.getText().toString();
        if(TextUtils.isEmpty(descriptionText))
        {
            description.setError("Field Empty");
            return  valid =  false;
        }

        String priceText=price.getText().toString();
        if(priceText.isEmpty())
        {
            price.setError("Field Empty");
            return  valid =  false;
        }


        return valid;
    }

    public void storeInfo(){
        repairing=repairingServices.getSelectedItem().toString();
        car=carServices.getSelectedItem().toString();
        cleaning=cleaningServices.getSelectedItem().toString();
        maid=maidServices.getSelectedItem().toString();

        String descriptionText=description.getText().toString();
        String priceText=price.getText().toString();

        userId=mAuth.getCurrentUser().getUid();
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        DocumentReference ServiceInformation = db.collection("provider").document("userId"+userId);
        DocumentReference ServiceStorage = db.collection("Services").document("userId"+userId);


        Map<String,Object> ServiceInfo = new HashMap<>();
        ServiceInfo.put("mainJob", firstJob);
        if(!(repairing.equals("")))
            ServiceInfo.put("secondaryJob",repairing);
        else if(!(car.equals("")))
            ServiceInfo.put("secondaryJob",car);
        else if(!(maid.equals("")))
            ServiceInfo.put("secondaryJob",maid);
        else if(!(cleaning.equals("")))
            ServiceInfo.put("secondaryJob",cleaning);
        ServiceInfo.put("Description", descriptionText);
        ServiceInfo.put("Price", priceText);


        if(firstJob.equals("Car Service")){
            Map<String,Object> carServiceInfo = new HashMap<>();
            carServiceInfo.put("userID",userId);
            carServiceInfo.put("mainJob", firstJob);
            carServiceInfo.put("secondaryJob",car.replaceAll("\\s", ""));
            carServiceInfo.put("Description", descriptionText);
            carServiceInfo.put("Price", priceText);
            ServiceStorage.set(carServiceInfo,SetOptions.merge());
        }
        else if(firstJob.equals("Repairing Service")){
            Map<String,Object> repairingServiceInfo = new HashMap<>();
            repairingServiceInfo.put("userID",userId);
            repairingServiceInfo.put("mainJob", firstJob);
            repairingServiceInfo.put("secondaryJob",repairing.replaceAll("\\s",""));
            repairingServiceInfo.put("Description", descriptionText);
            repairingServiceInfo.put("Price", priceText);
            ServiceStorage.set(repairingServiceInfo,SetOptions.merge());
        }
        else if(firstJob.equals("Maid Service")){
            Map<String,Object> maidServiceInfo = new HashMap<>();
            maidServiceInfo.put("userID",userId);
            maidServiceInfo.put("mainJob", firstJob);
            maidServiceInfo.put("secondaryJob",maid.replaceAll("\\s", ""));
            maidServiceInfo.put("Description", descriptionText);
            maidServiceInfo.put("Price", priceText);
            ServiceStorage.set(maidServiceInfo,SetOptions.merge());
        }
        else if(firstJob.equals("Cleaning Service")){
            Map<String,Object> cleaningServiceInfo = new HashMap<>();
            cleaningServiceInfo.put("userID",userId);
            cleaningServiceInfo.put("mainJob", firstJob);
            cleaningServiceInfo.put("secondaryJob",cleaning.replaceAll("\\s",""));
            cleaningServiceInfo.put("Description", descriptionText);
            cleaningServiceInfo.put("Price", priceText);
            ServiceStorage.set(cleaningServiceInfo,SetOptions.merge());
        }

        ServiceInformation.collection("ServiceCollection").document("Service").set(ServiceInfo, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "DocumentSnapshot successfully written!");
                startActivity(new Intent(getApplicationContext(), Service_provider_step_3.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error adding document", e);
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}