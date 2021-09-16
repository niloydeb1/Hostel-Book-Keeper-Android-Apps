package com.example.hooperv10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import Model.Hostel;

public class create_hostel extends AppCompatActivity {

    EditText hostel_id, password;
    Button create;
    DatabaseReference fb;
    Hostel hostel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_hostel);

        hostel_id = (EditText) findViewById(R.id.hostel_id);
        password = (EditText) findViewById(R.id.password);
        create = (Button) findViewById(R.id.create);

        fb = FirebaseDatabase.getInstance().getReference().child("Hostel/"+String.valueOf(hostel_id.getText()));
    }

    public void checkHostel(View view) {
        if(String.valueOf(hostel_id.getText()).isEmpty())
        {
            hostel_id.setError("Invalid Hostel ID!");
            hostel_id.requestFocus();
        }
        else if(String.valueOf(password.getText()).isEmpty())
        {
            password.setError("Invalid Password!");
            password.requestFocus();
        }
        else
        {
            newHostel();
        }
    }

    public void newHostel()
    {
        hostel = new Hostel(String.valueOf(hostel_id.getText()), String.valueOf(password.getText()));

        fb.child(String.valueOf(hostel_id.getText())).setValue(hostel);
        Toast.makeText(this,"Hostel Added Successfully!", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, create_user.class);
        intent.putExtra("managing_status", "YES");
        intent.putExtra("hostel_id", String.valueOf(hostel_id.getText()));
        startActivity(intent);
    }
}
