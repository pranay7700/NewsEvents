package com.vaagdevi.newsevents;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText loginemail;
    EditText loginpassword;
    Button loginbtn;
    Button clearbtn;
    Button signupbtn;

    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        loginbtn = (Button) findViewById(R.id.BTNlogin);
        clearbtn = (Button) findViewById(R.id.BTNclear);
        signupbtn = (Button) findViewById(R.id.BTNsignup);

         loginemail= (EditText) findViewById(R.id.ETloginemail);
         loginpassword= (EditText) findViewById(R.id.ETloginpassword);



        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Toast.makeText(MainActivity.this, "User logged in ", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, dashboard.class));

                } else {
                    Toast.makeText(MainActivity.this, "Login to continue", Toast.LENGTH_SHORT).show();
                }

            }
        };





        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = loginemail.getText().toString();
                String pass = loginpassword.getText().toString();

                //VALIDATION STARTS HERE

                if (email.isEmpty()) {
                    loginemail.setError("Provide your Email first!");
                    loginemail.requestFocus();
                } else if (pass.isEmpty()) {
                    loginpassword.setError("Enter Password!");
                    loginpassword.requestFocus();
                } else if (email.isEmpty() && pass.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Fields Empty!", Toast.LENGTH_SHORT).show();
                } else if (!(email.isEmpty() && pass.isEmpty())) {

                    firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Not successfull", Toast.LENGTH_SHORT).show();
                            } else {
                                startActivity(new Intent(MainActivity.this, dashboard.class));
                            }
                        }
                    });
                } else {
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }


        });

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this,registration.class));
            }
        });



        clearbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginemail.setText("");
                loginpassword.setText("");

            }
        });

    }

    @Override
    protected void onStart(){
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
    }


}
