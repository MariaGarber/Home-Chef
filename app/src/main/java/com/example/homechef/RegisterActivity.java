/* Assignment: 3
Campus: Ashdod
Authors:
Eliran Naduyev 312089105
Maria Garber
*/

package com.example.homechef;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private CircularProgressButton register;
    private EditText email, pass;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        register = findViewById(R.id.registerBtn);
        email = findViewById(R.id.registerEmailEt);
        pass = findViewById(R.id.registerPasswordEt);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == register) {
            register.startAnimation();
            String user_email = email.getText().toString();
            String user_password = pass.getText().toString();
            try{
                if(user_email.isEmpty() || user_password.isEmpty()){
                    throw new Exception("All fields must be filled");
                }
                if(user_password.length()<6){
                    throw new Exception("Password length should be more than 6 characters");
                }
                if (!(Patterns.EMAIL_ADDRESS.matcher(user_email).matches())){
                    throw new Exception("Invalid Email");
                }
                registerUser(user_email,user_password);
                register.doneLoadingAnimation(Color.parseColor("#5856EC"), BitmapFactory.decodeResource(getResources(),R.drawable.ic_done_white_48dp));

            }
            catch (Exception e){
                Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                register.revertAnimation();

            }
        }
    }

    private void registerUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent mainIntent= new Intent(RegisterActivity.this, MainActivity.class);
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(mainIntent);
                            finish(); // The user can't come back to this page
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(e instanceof FirebaseAuthUserCollisionException){
                    Toast.makeText(RegisterActivity.this, "Email already exists", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
