package com.example.hooperv10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Model.Hostel;
import Model.Member;

public class manage_meal extends AppCompatActivity {

    Button submit;
    EditText fee;
    ListView list;
    ArrayAdapter<String> itemsAdapter;
    private static List<String> items;
    private static String user, hostel="", member;
    private static int meal_count=0;
    private static double meal_fee=0;
    private static double due=0;
    DatabaseReference fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_meal);

        Intent intent = getIntent();
        user = intent.getStringExtra("username");

        submit = (Button) findViewById(R.id.mealsubmit);
        fee = (EditText) findViewById(R.id.meal_fee);

        list = (ListView) findViewById(R.id.list);
        items = new ArrayList<String>();

        getMembers();

        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        list.setAdapter(itemsAdapter);

        getLongClickListner();

        getClickListner();

    }

    private void getLongClickListner() {
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                String name = (String) parent.getItemAtPosition(position);
                setDue(name);
                return true;
            }
        });
    }

    private void getClickListner() {
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String name = (String) parent.getItemAtPosition(position);
                manageRent(name);
            }
        });
    }
    public void manageRent(String name)
    {
        Intent intent = new Intent(this, manage_rent.class);
        intent.putExtra("memberName", name);
        startActivity(intent);
    }

    private void getMembers() {

        fb = FirebaseDatabase.getInstance().getReference().child("Member/"+user+"/hostel_id");
        fb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                hostel = dataSnapshot.getValue().toString();
                setMembers();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void setMembers() {

        fb = FirebaseDatabase.getInstance().getReference().child("Hostel/"+hostel+"/Member");
        fb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Iterator<DataSnapshot> values = dataSnapshot.getChildren().iterator();
                while(values.hasNext()) {

                    DataSnapshot value = values.next();

                    member=value.getValue().toString();

                    items.add(member);
                    itemsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void setMeal(View view) {
        int count=0;
        while(count!=items.size())
        {
            fb = FirebaseDatabase.getInstance().getReference().child("Meal/"+items.get(count));
            fb.child("meal_fee").setValue(fee.getText().toString());
            count++;
        }
        fee.setText("");
        Toast.makeText(getApplicationContext(), "Meal Fee Updated Successfully!", Toast.LENGTH_SHORT).show();
    }

    private void setDue(String name) {
        fb = FirebaseDatabase.getInstance().getReference().child("Meal/"+name);
        fb.child("due").setValue(0);
        Toast.makeText(getApplicationContext(), "Due of "+name+" is cleared.", Toast.LENGTH_SHORT).show();
    }
}
