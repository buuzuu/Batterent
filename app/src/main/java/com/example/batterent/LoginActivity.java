package com.example.batterent;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import butterknife.BindView;

public class LoginActivity extends AppCompatActivity {

    EditText edtNewUser, edtNewFirstName, edtNewLastName, edtNewPassword, edtNewEmail, edtNewPhone, edtNewOTP; //for sign up
    private ProgressBar progressBar;
    Button getOTP;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        findViewById(R.id.log_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                finish();
            }
        });

        findViewById(R.id.log_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignUpDialog();
            }
        });
    }

    private void showSignUpDialog() {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginActivity.this);
        //     alertDialog.setTitle("Sign Up");
        //     alertDialog.setMessage("Please fill full information");
        LayoutInflater inflater = this.getLayoutInflater();
        final View sign_up_layout = inflater.inflate(R.layout.signup_layout, null);
        edtNewUser = sign_up_layout.findViewById(R.id.edtNewUserName);
        edtNewPassword = sign_up_layout.findViewById(R.id.edtNewPassword);
        edtNewEmail = sign_up_layout.findViewById(R.id.edtNewEmail);
        edtNewFirstName = sign_up_layout.findViewById(R.id.edtNewFirstName);
        edtNewLastName = sign_up_layout.findViewById(R.id.edtNewLastName);
        edtNewPhone = sign_up_layout.findViewById(R.id.editNewPhone);
        edtNewOTP = sign_up_layout.findViewById(R.id.edtNewOTP);
        progressBar = sign_up_layout.findViewById(R.id.progressBar2);
        getOTP = sign_up_layout.findViewById(R.id.getOTP);
        edtNewOTP.setVisibility(View.INVISIBLE);
        alertDialog.setView(sign_up_layout);
        alertDialog.setCancelable(true);
        dialog = alertDialog.create();
        dialog.show();
    }
}
