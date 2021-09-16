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

public class member_home extends AppCompatActivity {
    private String username;
    Button exit, meal, rent, profile, shout;
    TextView today, monthly;
    private int mealCount=0, monthlyMeal=0, todayMeal=0;
    double mealFee=0, mealDue=0;
    private boolean checkMonth = false;
    private String thisMonth="";
    DatabaseReference fb , fb2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_home);

        exit = (Button) findViewById(R.id.exit);
        meal = (Button) findViewById(R.id.meal);
        rent = (Button) findViewById(R.id.rent);
        profile = (Button) findViewById(R.id.profile);
        //shout = (Button) findViewById(R.id.shout);
        today = (TextView) findViewById(R.id.today);
        monthly = (TextView) findViewById(R.id.monthly);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        updateMeal();

        showMeal();
    }

    public void exit(View view) {
        System.exit(0);
    }

    public void viewMeal(View view) {
        Intent intent = new Intent(this, meal.class);
        intent.putExtra("username", username);
        intent.putExtra("managing_status", "NO");
        startActivity(intent);
        finish();
    }

    public void viewRent(View view) {
        Intent intent = new Intent(this, rent.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    public void viewProfile(View view) {
        Intent intent = new Intent(this, profile.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    public void viewShout(View view) {
        Intent intent = new Intent(this, shoutbox.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    private void updateMeal() {

        fb = FirebaseDatabase.getInstance().getReference().child("Meal");
        fb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> values = dataSnapshot.getChildren().iterator();
                while(values.hasNext()) {

                    DataSnapshot value = values.next();
                    String mon=value.child("month").getValue().toString();
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
                        checkMonth = true;
                        thisMonth = m;
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        if(checkMonth)
        {
            fb = FirebaseDatabase.getInstance().getReference().child("Meal/");
            fb.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    Iterator<DataSnapshot> values = dataSnapshot.getChildren().iterator();
                    while(values.hasNext())
                    {
                        DataSnapshot value = values.next();

                        String u = (String.valueOf(value.child("username").getValue()));
                        String c= (String.valueOf(value.child("meal_count").getValue()));
                        String f= (String.valueOf(value.child("meal_fee").getValue()));
                        String d= (String.valueOf(value.child("due").getValue()));
                        mealCount = Integer.parseInt(c);
                        mealFee = Double.parseDouble(f);
                        mealDue = Double.parseDouble(d);
                        mealDue += (mealCount * mealFee);

                        updateDue(u , mealDue);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }

    private void updateDue(String u, double mealDue) {
        fb2 = FirebaseDatabase.getInstance().getReference().child("Meal/"+u);
        fb2.child("month").setValue(thisMonth);
        fb2.child("due").setValue(mealDue);
        fb2.child("meal_count").setValue(0);
        checkMonth = false;
    }

    public void showMeal()
    {
        fb = FirebaseDatabase.getInstance().getReference().child("Meal/");
        try
        {
            fb.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    Iterator<DataSnapshot> values = dataSnapshot.getChildren().iterator();
                    while(values.hasNext())
                    {
                        DataSnapshot value = values.next();

                        String m= (String.valueOf(value.child("meal_count").getValue()));
                        String t= (String.valueOf(value.child("todays_meal").getValue()));
                        monthlyMeal+=Integer.parseInt(m);
                        todayMeal+=Integer.parseInt(t);
                    }
                    today.setText(String.valueOf(todayMeal));
                    monthly.setText(String.valueOf(monthlyMeal));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        catch(Exception e)
        {
            today.setText("");
            monthly.setText("");
        }
        todayMeal=0;
        monthlyMeal=0;
    }
}
