package com.example.hooperv10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class manage_rent extends AppCompatActivity {

    EditText rent, salary, utility;
    Button submit;
    Switch aSwitch;
    String member;
    String status;
    DatabaseReference fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_rent);

        Intent intent = getIntent();
        member = intent.getStringExtra("memberName");

        rent = (EditText) findViewById(R.id.setRent);
        salary = (EditText) findViewById(R.id.setSalary);
        utility = (EditText) findViewById(R.id.setUtility);
        submit = (Button) findViewById(R.id.rentsubmit);
        aSwitch = (Switch) findViewById(R.id.switch4);

        setSwitch();

    }

    private void setSwitch() {
        fb = FirebaseDatabase.getInstance().getReference().child("Rent/"+member+"/payment_status");
        fb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                status = String.valueOf(dataSnapshot.getValue());
                if(status.equals("PAID"))
                {
                    aSwitch.setChecked(true);
                }
                else
                {
                    aSwitch.setChecked(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void submitRent(View view) {
        fb = FirebaseDatabase.getInstance().getReference().child("Rent/"+member+"/house_rent");
        fb.setValue(rent.getText().toString());
        fb = FirebaseDatabase.getInstance().getReference().child("Rent/"+member);
        fb.child("maid").setValue(salary.getText().toString());
        fb.child("utility").setValue(utility.getText().toString());

        if(aSwitch.isChecked())
        {
            fb.child("payment_status").setValue("PAID");
        }
        else
        {
            fb.child("payment_status").setValue("DUE");
        }

        Toast.makeText(getApplicationContext(), "Rent updated successfully!", Toast.LENGTH_SHORT).show();
    }
}
