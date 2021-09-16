package com.example.hooperv10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.Month;
import java.util.Iterator;

import Model.Meal;

public class rent extends AppCompatActivity {
    TextView house, maid, utility, total, status, month;
    Button exit;
    String user;
    DatabaseReference fb, fb2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent);

        house = (TextView) findViewById(R.id.house);
        maid = (TextView) findViewById(R.id.maid);
        utility = (TextView) findViewById(R.id.utility);
        total = (TextView) findViewById(R.id.total);
        status = (TextView) findViewById(R.id.status);
        month = (TextView) findViewById(R.id.rentmonth);
        exit = (Button) findViewById(R.id.exit3);

        Intent intent = getIntent();
        user = intent.getStringExtra("username");

        showRent();
    }

    private void showRent() {

        fb = FirebaseDatabase.getInstance().getReference().child("Rent/"+user);
        fb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                house.setText(dataSnapshot.child("house_rent").getValue().toString());
                maid.setText(dataSnapshot.child("maid").getValue().toString());
                utility.setText(dataSnapshot.child("utility").getValue().toString());
                status.setText(dataSnapshot.child("payment_status").getValue().toString());

                String mon=dataSnapshot.child("month").getValue().toString();
                LocalDate date;
                Month mo;
                String d="", m="";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    date = LocalDate.now();
                    mo = date.getMonth();
                    d=date.toString();
                    m=mo.toString();
                }
                if(!mon.equals(m))
                {
                    setMonth(m);
                    Log.e("Error", "Here "+mon+" "+m);
                    fb2 = FirebaseDatabase.getInstance().getReference().child("Rent/"+user+"/month");
                    fb2.setValue(m);

                }
                month.setText(m);

                int t=Integer.parseInt(dataSnapshot.child("house_rent").getValue().toString())
                        + Integer.parseInt(dataSnapshot.child("maid").getValue().toString())
                        + Integer.parseInt(dataSnapshot.child("utility").getValue().toString());
                total.setText(String.valueOf(t));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setMonth(String m) {

    }

    public void exit(View view) {
        System.exit(0);
    }
}
