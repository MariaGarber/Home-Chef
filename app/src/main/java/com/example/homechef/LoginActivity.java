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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView forgot_pass, sign_up;
    private CircularProgressButton login;
    private EditText email,pass;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        forgot_pass= findViewById(R.id.loginForgotPassTV);
        sign_up= findViewById(R.id.loginSignUpTv);
        login= findViewById(R.id.loginBtn);
        email= findViewById(R.id.loginEmailEt);
        pass= findViewById(R.id.loginPasswordEt);
        forgot_pass.setOnClickListener(this);
        sign_up.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==sign_up) {
            Intent startIntent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(startIntent);
        }
        else if(v==forgot_pass){
            Intent startIntent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
            startActivity(startIntent);
        }
        else if(v==login) {
            login.startAnimation();
            String user_email = email.getText().toString();
            String user_password = pass.getText().toString();
            try{
                if(user_email.isEmpty() || user_password.isEmpty()){
                    throw new Exception("All fields must be filled");
                }
                if (!(Patterns.EMAIL_ADDRESS.matcher(user_email).matches())){
                    throw new Exception("Invalid Email");
                }
                loginUser(user_email,user_password);
                login.doneLoadingAnimation(Color.parseColor("#5856EC"), BitmapFactory.decodeResource(getResources(),R.drawable.ic_done_white_48dp));

            }
            catch (Exception e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                login.revertAnimation();
            }
        }
    }

    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent mainIntent= new Intent(LoginActivity.this, MainActivity.class);
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(mainIntent);
                            finish(); // The user can't come back to this page
                        }
                        else{
                            if(task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                                Toast.makeText(LoginActivity.this, "Invalid Password", Toast.LENGTH_LONG).show();
                            }
                            else if(task.getException() instanceof FirebaseAuthInvalidUserException){
                                Toast.makeText(LoginActivity.this, "Email not exist", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }
}

