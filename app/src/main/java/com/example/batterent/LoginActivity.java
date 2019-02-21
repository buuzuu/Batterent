package com.example.batterent;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.batterent.Model.User;
import com.example.batterent.Util.Common;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    EditText edtNewUser, edtNewFirstName, edtNewLastName, edtNewPassword, edtNewEmail, edtNewPhone, edtNewOTP; //for sign up
    private ProgressBar progressBar;
    Button getOTP;
    AlertDialog dialog;
    private String verificationId;
    FirebaseAuth mAuth;
    public FirebaseUser firebaseUser;
    FirebaseDatabase database;
    DatabaseReference users;
    @BindView(R.id.edtUser)
    EditText edtUser;
    @BindView(R.id.edtPassword)
    EditText edtPassword;
    boolean check = false;
        private static final String TAG = "LoginActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");


        findViewById(R.id.log_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtUser.getText().length()>0&&edtPassword.getText().length()>0) {
                    firebaseUser = mAuth.getCurrentUser();
                    if (firebaseUser ==null){
                        signIn(edtUser.getText().toString(), edtPassword.getText().toString());
                        firebaseUser = mAuth.getCurrentUser();
                    }


                }
                else
                    Toast.makeText(LoginActivity.this, "Enter Data Properly", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.log_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignUpDialog();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
       firebaseUser = mAuth.getCurrentUser();

        // checking if user is null
        if (firebaseUser != null) {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }

    }

    private void signIn(final String user, final String password) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Logging in...");
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);

        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(user).exists()) {

                    if (!user.isEmpty()) {

                        User login = dataSnapshot.child(user).getValue(User.class);

                        if (login.getPassword().equals(password)) {

                            Common.currentUser = login;

                            dialog.dismiss();
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));

                            finish();
                        } else {
                            dialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                        }


                    } else {
                        dialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Please enter your user name", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    dialog.dismiss();
                    Toast.makeText(LoginActivity.this, "User does not exist ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(LoginActivity.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });





    }

    private void showSignUpDialog() {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginActivity.this);
        //     alertDialog.setTitle("Sign Up");
        //     alertDialog.setMessage("Please fill full information");
        checkforRegisteredNumber();
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

        getOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.setCancelable(false);
                String number = "+91" + edtNewPhone.getText().toString().trim();
                Log.d(TAG, "onClick: " + check);
                for (int i = 0; i < Common.registederPhone.size(); i++) {

                    Log.d(TAG, "onDataChange: Test two..." + Common.registederPhone.get(i) + "---" + Common.registederUsername.get(i));

                    if (edtNewPhone.getText().toString().equals(Common.registederPhone.get(i)) || edtNewUser.getText().toString().equals(Common.registederUsername.get(i))) {
                        check = true;
                    }

                }
                Log.d(TAG, "onClick: " + check);

                if (check == true) {
                    Snackbar.make(sign_up_layout, "Phone Number Or Username Already Registered !", Snackbar.LENGTH_LONG).show();
                    Toast.makeText(LoginActivity.this, "Phone Number Or Username Already Registered !", Toast.LENGTH_SHORT).show();
                    check = false;
                    Common.registederPhone.clear();
                    Common.registederUsername.clear();
                    dialog.dismiss();
                } else if (check == false) {
                    Common.registederPhone.clear();
                    Common.registederUsername.clear();
                    edtNewOTP.setVisibility(View.VISIBLE);
                    alertDialog.setCancelable(false);
                    sendVerificationCode(number);
                    progressBar.setVisibility(View.VISIBLE);

                }
            }
        });

    }

    private void sendVerificationCode(String number) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );

    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                edtNewOTP.setText(code);
                getOTP.setClickable(false);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {


        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {



            final User user = new User(edtNewUser.getText().toString(), edtNewPassword.getText().toString(), edtNewEmail.getText().toString()

                    , edtNewFirstName.getText().toString(), edtNewLastName.getText().toString(), edtNewPhone.getText().toString());



            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    getOTP.setBackgroundColor(Color.parseColor("#4264fb"));
                    getOTP.setTextColor(Color.WHITE);
                    getOTP.setText("Registered !!!");
                    progressBar.setVisibility(View.INVISIBLE);
                    firebaseUser = mAuth.getCurrentUser();


                    users.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(user.getUserName()).exists()) {
                                Toast.makeText(LoginActivity.this, "Username Already exists !", Toast.LENGTH_SHORT).show();
                            } else {
                                users.child(user.getUserName()).setValue(user);

                                Toast.makeText(LoginActivity.this, "Registration Successful !", Toast.LENGTH_LONG).show();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        dialog.dismiss();
                                    }
                                }, 1500);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(LoginActivity.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    Toast.makeText(LoginActivity.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    private void checkforRegisteredNumber() {

        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {


                    Log.d(TAG, "onDataChange: " + dataSnapshot.getChildrenCount());
                    Iterator<DataSnapshot> iterable = dataSnapshot.getChildren().iterator();

                    while (iterable.hasNext()) {


                        DataSnapshot tempItem = iterable.next();
                        Common.registederPhone.add(tempItem.child("phoneNumber").getValue().toString());
                        Common.registederUsername.add(tempItem.child("userName").getValue().toString());
                    }

                } else {
                    check = false;
                }


                for (int i = 0; i < Common.registederPhone.size(); i++) {
                    Log.d(TAG, "onDataChange: Test one...." + Common.registederPhone.get(i) + "-----" + Common.registederUsername.get(i));
                }
                Log.d(TAG, "onDataChange: Test one..." + check);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


}
