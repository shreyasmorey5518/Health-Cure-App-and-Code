package com.uer.healthcure;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URLEncoder;

public class Login extends Activity {
    Button main_login;
    EditText main_user_name, main_passward;
    TextView user_register,newregistration;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);


        main_user_name=(EditText)findViewById(R.id.main_user_name);
        main_passward=(EditText)findViewById(R.id.main_password);
        main_login=(Button)findViewById(R.id.main_login);


        main_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                boolean status=true;
                String uname=main_user_name.getText().toString();
                String pass= main_passward.getText().toString();

                if(uname.length()<=0){
                    main_user_name.setError("Enter Valid Username");
                    main_passward.requestFocus();
                    status=false;
                }
                if(pass.length()<=0){
                    main_passward.setError("Enter Valid Username");
                    main_passward.requestFocus();
                    status=false;
                }

                if(status){

                    if(uname.contentEquals("admin")&&pass.contentEquals("admin")) {

                        try {
                            //Toast.makeText(Login.this, "valid", Toast.LENGTH_SHORT).show();
                            Intent i1 = new Intent(Login.this, MainActivity.class);
                            startActivity(i1);
                        }catch(Exception e){
                            //Toast.makeText(Login.this,""+e,Toast.LENGTH_LONG).show();
                        }
                    }else{
                        //Toast.makeText(Login.this,"Invalid Username or Password",Toast.LENGTH_LONG).show();
                    }

                }




            }
        });

    }
}
