package app.daytoday.softpro.daytodayindiadtd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SportsActivity extends AppCompatActivity /*implements View.OnClickListener*/
{

    ProgressBar progressBar;
    private RecyclerView recyclerView;
    DatabaseReference postref;
    private List<newsdata> examplelist=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports);
        progressBar=findViewById(R.id.progressbar);
        recyclerView=findViewById(R.id.newslist);

        postref= FirebaseDatabase.getInstance().getReference().child("News Record").child(getIntent().getStringExtra("category"));

        recyclerView.setHasFixedSize(true);
        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(1, LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        DataMethod();
       /* CardView cd_sports=findViewById(R.id.cd_sports);
        CardView cd_entertainment=findViewById(R.id.cd_entertainment);
        CardView cd_business=findViewById(R.id.cd_business);
        CardView cd_politics=findViewById(R.id.cd_politics);
        CardView cd_crime=findViewById(R.id.cd_crime);
        CardView cd_technical=findViewById(R.id.cd_technical);
        cd_sports.setOnClickListener(this);
        cd_entertainment.setOnClickListener(this);
        cd_business.setOnClickListener(this);
        cd_politics.setOnClickListener(this);
        cd_crime.setOnClickListener(this);
        cd_technical.setOnClickListener(this);*/
    }






    private void DataMethod()
    {
        postref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for(DataSnapshot newsnapshot:dataSnapshot.getChildren())
                {
                    newsdata post=newsnapshot.getValue(newsdata.class);
                    examplelist.add(0,post);

                }
                NewsAdapter adapter=new NewsAdapter(SportsActivity.this,examplelist);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                Toast.makeText(SportsActivity.this, "Data Fetching Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

   /* @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.cd_sports:
                postref= FirebaseDatabase.getInstance().getReference().child("News Record").child("Sports");
                break;
            case R.id.cd_entertainment:
                Toast.makeText(this, "Entertainment", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cd_business:
                Toast.makeText(this, "Business", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cd_crime:
                Toast.makeText(this, "Crime", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cd_politics:
                Toast.makeText(this, "Politics", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cd_technical:
                Toast.makeText(this, "Technical", Toast.LENGTH_SHORT).show();
                break;
        }

    }*/
}
