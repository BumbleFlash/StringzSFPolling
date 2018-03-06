package com.example.sudarshan.stringzsfpolling;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

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
    public void onListItemClick(int position) {
        Toast.makeText(getApplicationContext(), "Item clicked at"+position, Toast.LENGTH_SHORT).show();
    }
}
