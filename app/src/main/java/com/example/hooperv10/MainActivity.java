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

import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    EditText username, password;
    Button sign_in, join, create;
    DatabaseReference fb;
    String user, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        sign_in = (Button) findViewById(R.id.sign_in);
        join = (Button) findViewById(R.id.join);
        create = (Button) findViewById(R.id.create);


    }

    public void goToHome()
    {
        fb = FirebaseDatabase.getInstance().getReference().child("Member/");
        fb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                boolean valid=false;
                Iterator<DataSnapshot> values = dataSnapshot.getChildren().iterator();
                while(values.hasNext())
                {
                    DataSnapshot value = values.next();
                    if(String.valueOf(value.child("username").getValue()).equals(user))
                    {
                        valid=true;
                        break;
                    }
                }

                if(valid)
                {
                    String go = dataSnapshot.child(user).child("password").getValue().toString();
                    if(go.equals(pass))
                    {
                        go = dataSnapshot.child(user).child("managing_status").getValue().toString();
                        if(go.equals("YES"))
                        {
                            manager();
                        }
                        else
                        {
                            member();
                        }
                    }
                    else
                    {
                        error();
                    }
                }
                else
                {
                    error();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                error();
            }
        });

    }

    public void manager()
    {
        Intent intent = new Intent(this, manager_home.class);
        intent.putExtra("username", user);
        startActivity(intent);
        finish();
    }

    public void member()
    {
        Intent intent = new Intent(this, member_home.class);
        intent.putExtra("username", user);
        startActivity(intent);
        finish();
    }

    public void checkUser(View view) {
        if(String.valueOf(username.getText()).isEmpty())
        {
            username.setError("Invalid Username!");
            username.requestFocus();
            username.setText("");
        }
        else if(String.valueOf(password.getText()).isEmpty())
        {
            password.setError("Invalid Password!");
            password.requestFocus();
            password.setText("");
        }
        else
        {
            user=String.valueOf(username.getText());
            pass=String.valueOf(password.getText());
            goToHome();
        }

    }

    public void newUser(View view) {
        Intent intent = new Intent(this, verify_hostel.class);
        startActivity(intent);

    }

    public void newHostel(View view) {
        Intent intent = new Intent(this, create_hostel.class);
        startActivity(intent);
    }

    public void error()
    {
        username.setError("Invalid Username!");
        username.requestFocus();
        username.setText("");
        password.setError("Invalid Password!");
        password.requestFocus();
        password.setText("");
    }
}
