package com.example.sudarshan.stringzsfpolling;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.example.sudarshan.stringzsfpolling.models.Choice;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChoiceActivity extends AppCompatActivity {

    @BindView(R.id.participate_card)
    CardView participateCard;
    @BindView(R.id.vote_card)
    CardView voteCard;
    DatabaseReference databaseReference;
    @BindView(R.id.participate_card_text)
    TextView participateCardText;
    @BindView(R.id.vote_card_text)
    TextView voteCardText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
        ButterKnife.bind(this);
        databaseReference = FirebaseDatabase.getInstance().getReference("Choice");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Choice choice = dataSnapshot.getValue(Choice.class);
                if (choice != null) {
                    if (!choice.isRegister()) {
                        participateCard.setEnabled(false);
                        participateCardText.setText("Registration closed");
                    } else {
                        participateCard.setEnabled(true);
                        participateCardText.setText("Register for the event");
                    }
                    if (!choice.isVote()) {
                        voteCard.setEnabled(false);
                        voteCardText.setText("Voting disabled");
                    } else {
                        voteCard.setEnabled(true);
                        voteCardText.setText("Cast your vote");

                    }
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
