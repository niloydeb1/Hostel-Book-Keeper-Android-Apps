package com.example.hooperv10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.Month;

import Model.Meal;
import Model.Member;
import Model.Rent;

public class create_user extends AppCompatActivity {

    EditText name, blood, contact, email, username, password;
    Button create;
    DatabaseReference fb, fb2;
    Member member;
    Meal meal;
    Rent rent;
    String n, u, p, b, c, e, manage, hostel;
    long memberCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        password = (EditText) findViewById(R.id.cpassword);
        username = (EditText) findViewById(R.id.cusername);
        name = (EditText) findViewById(R.id.cname);
        blood = (EditText) findViewById(R.id.cblood);
        contact = (EditText) findViewById(R.id.ccontact);
        email = (EditText) findViewById(R.id.cemail);

        create = (Button) findViewById(R.id.ccreate);

        Intent intent = getIntent();
        manage = intent.getStringExtra("managing_status");
        hostel = intent.getStringExtra("hostel_id");

    }


    public void checkUser(View view) {

        p=String.valueOf(password.getText());
        u=String.valueOf(username.getText());
        n=String.valueOf(name.getText());
        b=String.valueOf(blood.getText());
        c=String.valueOf(contact.getText());
        e=String.valueOf(email.getText());

        if(String.valueOf(name.getText()).isEmpty())
        {
            name.setError("Invalid Name!");
            name.requestFocus();
        }
        else if(String.valueOf(email.getText()).isEmpty())
        {
            email.setError("Invalid Email Address!");
            email.requestFocus();
        }
        else if(String.valueOf(contact.getText()).isEmpty())
        {
            contact.setError("Invalid Contact No!");
            contact.requestFocus();
        }
        else if(String.valueOf(blood.getText()).isEmpty())
        {
            blood.setError("Invalid Blood Group!");
            blood.requestFocus();
        }
        else if(String.valueOf(username.getText()).isEmpty())
        {
            username.setError("Invalid Username!");
            username.requestFocus();
        }
        else if(String.valueOf(password.getText()).isEmpty())
        {
            password.setError("Invalid Password!");
            password.requestFocus();
        }
        else
        {
            newUser();
        }
    }
    public void newUser()
    {
        fb = FirebaseDatabase.getInstance().getReference().child("Member/"+u);
        fb2 = FirebaseDatabase.getInstance().getReference().child("Hostel/"+hostel+"/Member");

        member = new Member(u,p,n,manage,hostel,e,c,b);
        fb.setValue(member);
        fb2.push().setValue(u);

        fb = FirebaseDatabase.getInstance().getReference().child("Meal/"+u);

        LocalDate date;
        Month month;
        String d="", m="";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            date = LocalDate.now();
            month = date.getMonth();
            d=date.toString();
            m=month.toString();
            meal = new Meal(u,0,0,0,d,0,m);
        }

        fb.setValue(meal);

        fb = FirebaseDatabase.getInstance().getReference().child("Rent/"+u);
        rent = new Rent(u,0,0,0,"PAID",m);

        fb.setValue(rent);

        Toast.makeText(this,"User Added Successfully!", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
