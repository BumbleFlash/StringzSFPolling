package com.example.sudarshan.stringzsfpolling;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
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

    String mRepresentative;
    @BindView(R.id.short_film_name)
    EditText shortFilmTheme;
    @BindView(R.id.phone_number_et)
    EditText phoneNumberEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link);
        ButterKnife.bind(this);


        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        auth = FirebaseAuth.getInstance();


        Spinner spinner = findViewById(R.id.representative_spinner);
        spinner.setSelection(0);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.representative_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mRepresentative = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sendLinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String text = sendLinkButton.getText().toString();
                ProgressBarSettings.showProgressBarOnButton(sendLinkButton, progressBar, getApplicationContext(), android.R.color.white, true, text);
                String link = linkEditText.getText().toString();
                if (auth.getCurrentUser() != null) {
                    if (validate()) {
                        databaseReference.child(auth.getCurrentUser().getUid()).child("phone").setValue(phoneNumberEt.getText().toString());
                        databaseReference.child(auth.getCurrentUser().getUid()).child("theme").setValue(shortFilmTheme.getText().toString());
                        databaseReference.child(auth.getCurrentUser().getUid()).child("representative").setValue(mRepresentative);
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
            }
        });
    }

    public boolean validate() {

        boolean valid;
        String shortFilm = shortFilmTheme.getText().toString();
        String link = linkEditText.getText().toString();
        String phone = phoneNumberEt.getText().toString();
        if (TextUtils.isEmpty(shortFilm)) {
            shortFilmTheme.setError("Please enter your theme of your film");
            valid = false;
        } else if (TextUtils.isEmpty(link)) {
            shortFilmTheme.setError("Please paste your link");
            valid = false;
        } else if (TextUtils.isEmpty(phone) || phone.length() < 6 && phone.length() > 13) {
            phoneNumberEt.setError("Please enter valid phone number");
            valid = false;
        } else
            valid = true;
        return valid;
    }
}
