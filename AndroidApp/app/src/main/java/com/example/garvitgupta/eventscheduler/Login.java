package com.example.garvitgupta.eventscheduler;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import application.Packet;


public class Login extends AppCompatActivity implements Serializable{
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    EditText _usernameText;
    EditText _passwordText;
    Button _loginButton;
    TextView _signupLink;
    Packet p;
    int check;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //ButterKnife.inject(this);
        _usernameText=(EditText)findViewById(R.id.input_username);
        _passwordText=(EditText)findViewById(R.id.input_password);
        _loginButton=(Button)findViewById(R.id.btn_login);
        _signupLink=(TextView)findViewById(R.id.link_signup);
        //Log.d("hehe",IOstream.temp);
        check=0;

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

//        if (!validate()) {
//            onLoginFailed();
//            return;
//        }

//        _loginButton.setEnabled(false);

//        final ProgressDialog progressDialog = new ProgressDialog(Login.this,
//                R.style.AppTheme_AppBarOverlay);
//        progressDialog.setIndeterminate(true);
//        progressDialog.setMessage("Authenticating...");
//        progressDialog.show();

        String username = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();

        p=new Packet();
        p.operation="login";
        p.s1=username;
        p.s2=password;
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
                    check=1;
                    Intent i = new Intent(Login.this,Create_event.class);
                    startActivity(i);
                }
                else {
                    check=2;
                    //onLoginFailed();
                }
            }
        });
        t.start();
        while(true){
            if (check!=0)
                break;
        }
        if(check==2)
            onLoginFailed();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String username = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();

        if (username.isEmpty()) {
            _usernameText.setError("enter a valid username");
            valid = false;
        } else {
            _usernameText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }
//            if(valid == true)
//            {
//                Intent i = new Intent(this,GetNotifications.class);
//                    startActivity(i);
//            }
        return valid;
    }
}
