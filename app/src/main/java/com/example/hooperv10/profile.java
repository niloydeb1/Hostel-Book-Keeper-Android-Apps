package com.example.hooperv10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profile extends AppCompatActivity {

    TextView name, contact, email, blood;
    Button exit;
    String user;
    DatabaseReference fb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = (TextView) findViewById(R.id.pname);
        email = (TextView) findViewById(R.id.pemail);
        contact = (TextView) findViewById(R.id.pcontact);
        blood = (TextView) findViewById(R.id.pblood);
        exit = (Button) findViewById(R.id.exit3);

        Intent intent = getIntent();
        user = intent.getStringExtra("username");

        showProfile();
    }

    private void showProfile() {

        fb = FirebaseDatabase.getInstance().getReference().child("Member/"+user);
        fb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                name.setText(dataSnapshot.child("name").getValue().toString());
                email.setText(dataSnapshot.child("email").getValue().toString());
                contact.setText(dataSnapshot.child("contact").getValue().toString());
                blood.setText(dataSnapshot.child("blood").getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void exit(View view) {
        System.exit(0);
    }
}
