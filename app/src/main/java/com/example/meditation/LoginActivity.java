package com.example.meditation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText username, pwd;
    Button btnlogin;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username= (EditText) findViewById(R.id.username);
        pwd= (EditText) findViewById(R.id.password);
        btnlogin= (Button) findViewById(R.id.login);
        db= new DBHelper(this);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                String user = username.getText().toString();
                String pass=pwd.getText().toString();

                if(user.equals("")|| pass.equals(""))
                    Toast.makeText(LoginActivity.this, "Please complete all fields", Toast.LENGTH_SHORT).show();
                else {
                    Boolean checkuserpass= db.checkUsernamePassword(user,pass);
                    if(checkuserpass==true){
                        Toast.makeText(LoginActivity.this, "Sign in successfully", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
