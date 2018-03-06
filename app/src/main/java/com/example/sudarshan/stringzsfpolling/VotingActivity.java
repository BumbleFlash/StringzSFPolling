package com.example.sudarshan.stringzsfpolling;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.sudarshan.stringzsfpolling.adapters.ShortFilmAdapter;

public class VotingActivity extends AppCompatActivity implements ShortFilmAdapter.OnListItemClickListener {

    RecyclerView recyclerView;
    ShortFilmAdapter adapter;
    String[] shortFilms = {"yolo", "yodo","asd", "asdas","sada"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ShortFilmAdapter(this, shortFilms, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onListItemClick(int position, final ConstraintLayout layout) {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                VotingActivity.this);
        builder.setTitle("Short Film Poll!");
        builder.setMessage("Do you want to cast your vote?");
        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        layout.setBackgroundColor(Color.parseColor("#009688"));

                    }
                });
        builder.show();
    }
}
