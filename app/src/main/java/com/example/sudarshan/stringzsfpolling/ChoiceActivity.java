package com.example.sudarshan.stringzsfpolling;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChoiceActivity extends AppCompatActivity {

    @BindView(R.id.participate_card)
    CardView participateCard;
    @BindView(R.id.vote_card)
    CardView voteCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.participate_card)
    public void onParticipateCardClicked(View view) {
        Intent intent = new Intent(ChoiceActivity.this, LinkActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.vote_card)
    public void onVoteCardClicked(View view) {
        Intent intent = new Intent(ChoiceActivity.this, VotingActivity.class);
        startActivity(intent);
    }
}
