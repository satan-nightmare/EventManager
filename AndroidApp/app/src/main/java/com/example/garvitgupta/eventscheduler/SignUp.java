package com.example.garvitgupta.eventscheduler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import application.Packet;

public class SignUp extends AppCompatActivity {
    private static final String TAG = "SignupActivity";


    EditText _nameText;
    EditText _emailText;
    EditText _passwordText;
    EditText _usernameText;
    EditText _branchText;
    EditText _mobileText;
    Button _signupButton;
    TextView _loginLink;
    RadioGroup _rg;
    RadioButton _gender;
    Packet p;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        _usernameText=(EditText)findViewById(R.id.input_username);
        _nameText=(EditText)findViewById(R.id.input_name);
        _passwordText=(EditText)findViewById(R.id.input_password);
        _emailText=(EditText)findViewById(R.id.input_email);
        _branchText=(EditText)findViewById(R.id.input_branch);
        _mobileText=(EditText)findViewById(R.id.input_mobile);
        _rg=(RadioGroup)findViewById(R.id.btn_group);
        _signupButton=(Button)findViewById(R.id.btn_signup);
        _loginLink=(TextView)findViewById(R.id.link_login);
        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

//        if (!validate()) {
//            onSignupFailed();
//            return;
//        }

        //_signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignUp.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String username=_usernameText.getText().toString();
        String name = _nameText.getText().toString();
        String password = _passwordText.getText().toString();
        String email = _emailText.getText().toString();
        String branch= _branchText.getText().toString();
        String mobile= _mobileText.getText().toString();
        String gender;
        int selected=_rg.getCheckedRadioButtonId();
        if (selected==R.id.btn_male)
            gender="Male";
        else if(selected==R.id.btn_female)
            gender="Female";
        else if (selected==R.id.btn_other)
            gender="Other";
        else
            gender=null;
        p=new Packet();
        p.operation="signup";
        p.s1=username;
        p.s2=name;
        p.s3=password;
        p.s4=gender;
        p.s5=email;
        p.s6=branch;
        p.s7=mobile;
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    IOstream.oos.writeObject(p);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    p=(Packet)IOstream.ois.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (p.response){
                    Intent i=new Intent(SignUp.this,Login.class);
                    startActivity(i);
                }
                else
                    Log.d("hehe","Unable to signup");
            }
        });
        t.start();



        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Signup failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
