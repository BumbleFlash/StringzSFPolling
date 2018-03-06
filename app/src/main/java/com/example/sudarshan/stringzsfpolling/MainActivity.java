package com.example.sudarshan.stringzsfpolling;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.input_name)
    EditText inputName;
    @BindView(R.id.input_email)
    EditText inputEmail;
    @BindView(R.id.input_password)
    EditText inputPassword;
    @BindView(R.id.input_college_name)
    EditText inputCollegeName;
    @BindView(R.id.input_course_name)
    EditText inputCourseName;
    @BindView(R.id.btn_signup)
    AppCompatButton btnSignup;
    @BindView(R.id.link_login)
    TextView linkLogin;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    FirebaseAuth auth;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        auth=FirebaseAuth.getInstance();
        if(auth.getCurrentUser()!=null)
            startActivity(new Intent(getApplicationContext(),VotingActivity.class));
        databaseReference= FirebaseDatabase.getInstance().getReference("Users");
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });


    }
    public void signup() {
        Log.d(TAG, "Signup");
        final String text= btnSignup.getText().toString();
       // ProgressBarSettings.showProgressBarOnButton(btnSignup,progressBar,getApplicationContext(),android.R.color.white,true,text);

        if (!validate()) {
            onSignupFailed();
            return;
        }

        btnSignup.setEnabled(false);



        final String name = inputName.getText().toString();
        final String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();
        final String collegeName = inputCollegeName.getText().toString();
        final String courseName = inputCourseName.getText().toString();

        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Log.d(TAG,task.getException().getMessage());
                    btnSignup.setEnabled(true);
                    //ProgressBarSettings.showProgressBarOnButton(btnSignup,progressBar,getApplicationContext(),android.R.color.white,false,text);

                }
                else{
                    Log.d(TAG, "onComplete: success "+ auth.getCurrentUser().getUid());
                    databaseReference.child(auth.getCurrentUser().getUid()).child("Name").setValue(name);
                    databaseReference.child(auth.getCurrentUser().getUid()).child("Email").setValue(email);
                    databaseReference.child(auth.getCurrentUser().getUid()).child("College").setValue(collegeName);
                    databaseReference.child(auth.getCurrentUser().getUid()).child("Course").setValue(courseName);
                    databaseReference.child(auth.getCurrentUser().getUid()).child("hasVoted").setValue(false);
                    //ProgressBarSettings.showProgressBarOnButton(btnSignup,progressBar,getApplicationContext(),android.R.color.white,false,text);
                    Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),VotingActivity.class));
                }
            }
        });


    }
    public boolean validate() {
        boolean valid = true;

        String name = inputName.getText().toString();
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            inputName.setError("at least 3 characters");
            valid = false;
        } else {
            inputName.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            inputEmail.setError("enter a valid email address");
            valid = false;
        } else {
            inputEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            inputPassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            inputPassword.setError(null);
        }

        return valid;
    }
    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Registration failed", Toast.LENGTH_LONG).show();

        btnSignup.setEnabled(true);
    }

}
