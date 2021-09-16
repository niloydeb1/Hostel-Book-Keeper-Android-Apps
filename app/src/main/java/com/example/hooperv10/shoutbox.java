package com.example.hooperv10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class shoutbox extends AppCompatActivity {
    EditText message;
    Button send;
    DatabaseReference fb;
    RecyclerView recyclerView;
//    ArrayList<Shout> list;
//    private FirebaseRecyclerOptions<Shout> options;
//    private FirebaseRecyclerAdapter<Shout, FirebaseViewHolder> adapter;

    private static String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoutbox);

        Intent intent = getIntent();
        user = intent.getStringExtra("username");

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        message = (EditText) findViewById(R.id.message);
        send = (Button) findViewById(R.id.sendMessage);

        //list = new ArrayList<Shout>();
    }

    public void sendMessage(View view) {

    }
}
