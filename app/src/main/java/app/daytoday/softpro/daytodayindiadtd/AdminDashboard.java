package app.daytoday.softpro.daytodayindiadtd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class AdminDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

    }
    public void addPost(View view)
    {
        Intent i=new Intent(AdminDashboard.this,AddPost.class);
        startActivity(i);
    }

    public void updelData(View view)
    {
    Intent i=new Intent(this,DeleteDashboard.class);
    startActivity(i);
    }




    public void logout(View view)
    {
        FirebaseAuth mauth=FirebaseAuth.getInstance();
        mauth.signOut();
        Intent i=new Intent(AdminDashboard.this,AdminLogin.class);
        startActivity(i);
        finish();
    }


}
