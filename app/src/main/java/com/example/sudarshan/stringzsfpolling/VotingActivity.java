package com.example.sudarshan.stringzsfpolling;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.sudarshan.stringzsfpolling.adapters.ShortFilmAdapter;
import com.example.sudarshan.stringzsfpolling.models.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class VotingActivity extends AppCompatActivity implements ShortFilmAdapter.OnListItemClickListener {

    RecyclerView recyclerView;
    ShortFilmAdapter adapter;
    String[] shortFilms = {"yolo", "yodo","asd", "asdas","sada"};
    DatabaseReference databaseReference;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting);
        databaseReference= FirebaseDatabase.getInstance().getReference("Users");
        recyclerView = findViewById(R.id.recycler_view);
        auth=FirebaseAuth.getInstance();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseReference.child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Users users=dataSnapshot.getValue(Users.class);
                if(users!=null){
                    if(users.HasVoted())
                        recyclerView.setVisibility(View.INVISIBLE);
                    else
                        recyclerView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        adapter = new ShortFilmAdapter(this, shortFilms, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onListItemClick(final int position, final ConstraintLayout layout) {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                VotingActivity.this);
        builder.setTitle("Short Film Poll!");
        builder.setMessage("Do you want to cast your vote?");
        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        if(auth.getCurrentUser()!=null) {
                            databaseReference.child(auth.getCurrentUser().getUid()).child("hasVoted").setValue(true);
                            databaseReference.child(auth.getCurrentUser().getUid()).child("votedTo").setValue(shortFilms[position]);
                            layout.setBackgroundColor(Color.parseColor("#009688"));
                        }

                    }
                });
        builder.show();
    }
}
