package com.example.hooperv10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class verify_hostel extends AppCompatActivity {

    EditText hostel_id, password;
    Button verify;
    DatabaseReference fb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_hostel);

        hostel_id = (EditText) findViewById(R.id.vhostel);
        password = (EditText) findViewById(R.id.vpassword);
        verify = (Button) findViewById(R.id.verify);

    }

    public void newVerifiedUser(View view) {
        if(String.valueOf(hostel_id.getText()).isEmpty())
        {
            hostel_id.setError("Invalid Hostel ID!");
            hostel_id.requestFocus();
            hostel_id.setText("");
        }
        else if(String.valueOf(password.getText()).isEmpty())
        {
            password.setError("Invalid Password!");
            password.requestFocus();
            password.setText("");
        }
        else
        {
            fb = FirebaseDatabase.getInstance().getReference().child("Hostel/"+String.valueOf(hostel_id.getText()));
            fb.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    String pass = dataSnapshot.child("hostel_password").getValue().toString();
                    if(pass.equals(String.valueOf(password.getText())))
                    {
                        newUser();
                    }
                    else
                    {
                        setError();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    public void newUser()
    {
        Intent intent = new Intent(this, create_user.class);
        intent.putExtra("managing_status", "NO");
        intent.putExtra("hostel_id", String.valueOf(hostel_id.getText()));
        startActivity(intent);
        Toast.makeText(this,"Verification Successful!", Toast.LENGTH_SHORT).show();
    }

    public void setError()
    {
        hostel_id.setError("Invalid Hostel ID!");
        hostel_id.requestFocus();
        hostel_id.setText("");
        password.setError("Invalid Password!");
        password.requestFocus();
        password.setText("");
        Toast.makeText(this,"Verification Failed!", Toast.LENGTH_SHORT).show();
    }
}
