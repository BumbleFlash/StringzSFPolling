package com.example.sudarshan.stringzsfpolling;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.sudarshan.stringzsfpolling.utils.ProgressBarSettings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LinkActivity extends AppCompatActivity {

    @BindView(R.id.link_edit_text)
    EditText linkEditText;
    @BindView(R.id.send_link_button)
    AppCompatButton sendLinkButton;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    DatabaseReference databaseReference;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link);
        ButterKnife.bind(this);
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        auth = FirebaseAuth.getInstance();
        sendLinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String text = sendLinkButton.getText().toString();
                ProgressBarSettings.showProgressBarOnButton(sendLinkButton, progressBar, getApplicationContext(), android.R.color.white, true, text);
                String link = linkEditText.getText().toString();
                if (auth.getCurrentUser() != null) {
                    databaseReference.child(auth.getCurrentUser().getUid()).child("role").setValue("participant");
                    databaseReference.child(auth.getCurrentUser().getUid()).child("link").setValue(link, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            Toast.makeText(getApplicationContext(), "Thank you for registering", Toast.LENGTH_SHORT).show();
                            ProgressBarSettings.showProgressBarOnButton(sendLinkButton, progressBar, getApplicationContext(), android.R.color.white, false, text);
                            startActivity(new Intent(getApplicationContext(), ChoiceActivity.class));
                        }
                    });
                }
            }
        });
    }
}
