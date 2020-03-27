package com.vaagdevi.newsevents;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class registration extends AppCompatActivity {


    private DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        final FirebaseAuth firebaseAuth;
        firebaseAuth = FirebaseAuth.getInstance();

        databaseReference=FirebaseDatabase.getInstance().getReference();


        Button registerbtn=(Button)findViewById(R.id.BTNsignupregister);

        final EditText signupemail=(EditText)findViewById(R.id.ETsignupemail);
        final EditText signuppassword=(EditText)findViewById(R.id.ETsignuppassword);


            registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String useremail=signupemail.getText().toString();
                final String userpassword=signuppassword.getText().toString();

                if (useremail.isEmpty()) {
                    signupemail.setError("Provide Your Email first!");
                    signupemail.requestFocus();

                }
                else if (userpassword.isEmpty())
                {
                    signuppassword.setError("Set your password");
                    signuppassword.requestFocus();
                }
                else if (useremail.isEmpty() && userpassword.isEmpty())
                {
                    Toast.makeText(registration.this, "Fields Empty!", Toast.LENGTH_SHORT).show();
                }
                else if (!(useremail.isEmpty() && userpassword.isEmpty())) {

                    firebaseAuth.createUserWithEmailAndPassword(useremail, userpassword)
                            .addOnCompleteListener(registration.this,
                                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (!task.isSuccessful()) {

                                RegDatabase regDatabase = new RegDatabase(useremail,userpassword);

                                FirebaseDatabase.getInstance().getReference(databaseReference.getKey())
                                        .child(firebaseAuth.getCurrentUser().getUid()).setValue(regDatabase)
                                        .addOnCompleteListener(new OnCompleteListener <Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task <Void> task) {

                                        Toast.makeText(registration.this,"Registered Sucessfully!!!", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(registration.this, MainActivity.class));
                                        finish();
                                    }
                                });

                            }
                            else
                                {
                                Toast.makeText(registration.this.getApplicationContext(),"Signup Unsuccessfull:"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(registration.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



}
