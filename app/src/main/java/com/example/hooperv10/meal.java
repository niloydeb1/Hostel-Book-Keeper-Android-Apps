package com.example.hooperv10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.Month;
import java.util.Iterator;

public class meal extends AppCompatActivity {
    private String user;
    private String managing_status;
    Button submit;
    TextView count, fee, due;
    Switch breakfast, lunch, dinner;
    DatabaseReference fb;
    private int countMeal=0;
    private boolean date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);

        submit = (Button) findViewById(R.id.msubmit);
        count = (TextView) findViewById(R.id.mCount);
        fee = (TextView) findViewById(R.id.mFee);
        due = (TextView) findViewById(R.id.mDue);
        breakfast = (Switch) findViewById(R.id.switch1);
        lunch = (Switch) findViewById(R.id.switch2);
        dinner = (Switch) findViewById(R.id.switch3);

        Intent intent = getIntent();
        user = intent.getStringExtra("username");
        managing_status = intent.getStringExtra("managing_status");

        setSwitch();

        showMeal();
    }

    private void setSwitch() {

        fb = FirebaseDatabase.getInstance().getReference().child("Meal/"+user);
        fb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String temp = String.valueOf(dataSnapshot.child("todays_meal").getValue());
                int mC= Integer.parseInt(temp);

                if(mC==3)
                {
                    breakfast.setChecked(true);
                    lunch.setChecked(true);
                    dinner.setChecked(true);
                }
                else if(mC==2)
                {
                    lunch.setChecked(true);
                    dinner.setChecked(true);
                }
                else if(mC==1)
                {
                    dinner.setChecked(true);
                }
                countMeal = mC;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showMeal() {
        fb = FirebaseDatabase.getInstance().getReference().child("Meal/"+user);
        fb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String da = dataSnapshot.child("current_date").getValue().toString();
                String temp = dataSnapshot.child("meal_count").getValue().toString();
                int mC= Integer.parseInt(temp);
                LocalDate date;
                Month mo;
                String d="";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    date = LocalDate.now();
                    d=date.toString();
                }
                if(!da.equals(d))
                {
                    fb = FirebaseDatabase.getInstance().getReference().child("Meal/"+user);
                    fb.child("current_date").setValue(d);
                    fb.child("todays_meal").setValue(countMeal);
                    int t = mC+countMeal;
                    fb.child("meal_count").setValue(t);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        fb = FirebaseDatabase.getInstance().getReference().child("Meal/"+user);
        fb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                count.setText(dataSnapshot.child("meal_count").getValue().toString());
                fee.setText(dataSnapshot.child("meal_fee").getValue().toString());
                due.setText(dataSnapshot.child("due").getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void submit(View view) {

        countMeal=0;

        if(breakfast.isChecked())
        {
            countMeal++;
        }
        if(lunch.isChecked())
        {
            countMeal++;
        }
        if(dinner.isChecked())
        {
            countMeal++;
        }

        fb = FirebaseDatabase.getInstance().getReference().child("Meal/"+user);
        fb.child("todays_meal").setValue(countMeal);

        Intent intent;
        if(managing_status.equals("YES"))
        {
            intent = new Intent(this, manager_home.class);
        }
        else
        {
            intent = new Intent(this, member_home.class);
        }
        intent.putExtra("username", user);
        startActivity(intent);
        finish();
    }
}
