package com.example.meditation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class activity_register extends AppCompatActivity {

    EditText username, pwd, repwd;
    Button signup , signin;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username=(EditText) findViewById(R.id.username);
        pwd=(EditText) findViewById(R.id.password2);
        repwd=(EditText) findViewById(R.id.repassword);
        Button signup=findViewById(R.id.registerbtn);
        signin=(Button) findViewById(R.id.login);
        db= new DBHelper(this);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                String user= username.getText().toString();
                String pass= pwd.getText().toString();
                String repass= repwd.getText().toString();

                if(user.equals("")||pass.equals("") || repass.equals(""))
                    Toast.makeText(activity_register.this, "Please complete all fields", Toast.LENGTH_SHORT).show();
                else{
                    if(pass.equals(repass)){
                        Boolean checkuser= db.checkUsername(user);
                        if(checkuser==false)
                        {
                            Boolean insert=db.insertData(user, pass);
                            if(insert==true){
                                Toast.makeText(activity_register.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(i);
                            }
                            else{
                                Toast.makeText(activity_register.this, "Registered failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(activity_register.this, "User already exists.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(activity_register.this, "Passwords not matching.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        });
    }
}