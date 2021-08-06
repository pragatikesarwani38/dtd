package app.daytoday.softpro.daytodayindiadtd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DeleteDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_dashboard);
    }
    public void sports(View view)
    {
        Intent i=new Intent(DeleteDashboard.this,Deletelist.class);
        i.putExtra("category","Sports");
        startActivity(i);


    }
    public void entertainment(View view)
    {
        Intent i =new Intent(DeleteDashboard.this,Deletelist.class);
        i.putExtra("category","Entertainment");
        startActivity(i);

    }
    public void politics(View view)
    {
        Intent i =new Intent(DeleteDashboard.this,Deletelist.class);
        i.putExtra("category","Politics");
        startActivity(i);

    }
    public void technical(View view)
    {
        Intent i =new Intent(DeleteDashboard.this,Deletelist.class);
        i.putExtra("category","Technical");
        startActivity(i);

    }
    public void crime(View view)
    {
        Intent i =new Intent(DeleteDashboard.this,Deletelist.class);
        i.putExtra("category","Crime");
        startActivity(i);

    }
    public void business(View view)
    {
        Intent i =new Intent(DeleteDashboard.this,Deletelist.class);
        i.putExtra("category","Business");
        startActivity(i);

    }

}
