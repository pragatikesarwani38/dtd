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

public class FavouriteList extends AppCompatActivity {
    ProgressBar progressBar;
    private RecyclerView recyclerView;
    DatabaseReference postref;
    private List<newsdata> examplelist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_list);
        progressBar = findViewById(R.id.favprogressbar);
        recyclerView = findViewById(R.id.favlist);

        postref = FirebaseDatabase.getInstance().getReference().child("News Record").child(getIntent().getStringExtra("category"));

        recyclerView.setHasFixedSize(true);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        DataMethod();

    }
    private void DataMethod() {
        postref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot newsnapshot : dataSnapshot.getChildren()) {
                    newsdata post = newsnapshot.getValue(newsdata.class);
                    examplelist.add(0, post);

                }
                DeleteAdapter adapter = new DeleteAdapter(FavouriteList.this, examplelist);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(FavouriteList.this, "Data Fetching Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
