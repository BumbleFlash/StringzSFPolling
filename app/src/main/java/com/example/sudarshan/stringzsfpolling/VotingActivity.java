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
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.sudarshan.stringzsfpolling.adapters.ShortFilmAdapter;
import com.example.sudarshan.stringzsfpolling.models.Movies;
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
    TextView noDataView;
    String[] shortFilms = new String[10];
    int[] votes = new int[20];
    ShortFilmAdapter.OnListItemClickListener itemClickListener;
    DatabaseReference databaseReference, databaseReference1;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        databaseReference= FirebaseDatabase.getInstance().getReference("Users");
        recyclerView = findViewById(R.id.recycler_view);
        noDataView = findViewById(R.id.no_data_view);
        auth=FirebaseAuth.getInstance();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemClickListener = this;
        databaseReference.child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Users users = dataSnapshot.getValue(Users.class);
                if (users != null) {
                    Log.d("Voting", "onDataChange: " + users.isHasVoted() + " " + users.getEmail());
                    if (users.isHasVoted()) {
                        recyclerView.setVisibility(View.GONE);
                        noDataView.setVisibility(View.VISIBLE);
                    } else {
                        recyclerView.setVisibility(View.VISIBLE);
                        noDataView.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        databaseReference1 = FirebaseDatabase.getInstance().getReference("movies");
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Movies movies = dataSnapshot.getValue(Movies.class);
                if (movies != null) {
                    shortFilms[0] = movies.getMovie1();
                    shortFilms[1] = movies.getMovie2();
                    shortFilms[2] = movies.getMovie3();
                    votes[0] = movies.getVotemovie1();
                    votes[1] = movies.getVotemovie2();
                    votes[2] = movies.getVotemovie3();
                    adapter = new ShortFilmAdapter(getApplicationContext(), shortFilms, itemClickListener);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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
                            databaseReference.child(auth.getCurrentUser().getUid()).child("isHasVoted").setValue(true);
                            databaseReference.child(auth.getCurrentUser().getUid()).child("votedTo").setValue(shortFilms[position]);
                            databaseReference1.child("votemovie" + (position + 1)).setValue(votes[position] + 1);
                            layout.setBackgroundColor(Color.parseColor("#009688"));
                        }

                    }
                });
        builder.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        VotingActivity.this);
                builder.setTitle("Short Film Poll!");
                builder.setMessage("Do you wish to exit the app?");
                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                finish();

                            }
                        });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
                return false;
        }
        return super.onOptionsItemSelected(item);

    }
}
