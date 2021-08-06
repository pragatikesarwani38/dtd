package app.daytoday.softpro.daytodayindiadtd;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdminLogin extends AppCompatActivity {
EditText et_email;
EditText et_password;
FirebaseAuth mauth;
Button login;
ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_admin_login_);
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null)
        {
            Intent i=new Intent(AdminLogin.this,AdminDashboard.class);
            startActivity(i);
            finish();
        }
        pd=new ProgressDialog(this);
        et_email=(EditText)findViewById(R.id.et_email);
        et_password=(EditText)findViewById(R.id.et_password);
        login=(Button) findViewById(R.id.btn_login);
        FirebaseApp.initializeApp(AdminLogin.this);
        mauth=FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                String email=et_email.getText().toString().trim();
                String password=et_password.getText().toString().trim();
                if(et_email.getText().toString().isEmpty())
                {
                    et_email.setError("Please enter your email");
                    et_email.requestFocus();
                }
                else if(et_password.getText().toString().isEmpty())
                {
                    et_password.setError("Empty");
                    et_password.requestFocus();
                }
                else
                {
                    pd.setMessage("Loading...");
                    pd.show();
                    pd.setCanceledOnTouchOutside(false);
                    mauth.signInWithEmailAndPassword(email,password).addOnCompleteListener(AdminLogin.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if(task.isSuccessful())
                            {
                                pd.dismiss();
                                Intent i =new Intent(AdminLogin.this,AdminDashboard.class);
                                startActivity(i);
                                finish();
                                //Toast.makeText(AdminLogin.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                pd.dismiss();
                                Toast.makeText(AdminLogin.this, "Login Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

}
