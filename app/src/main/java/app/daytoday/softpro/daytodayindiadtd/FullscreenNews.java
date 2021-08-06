package app.daytoday.softpro.daytodayindiadtd;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class FullscreenNews extends AppCompatActivity {
TextView f_tv_head,f_tv_date,f_tv_time,f_tv_desc;
ImageView f_img;
ImageView gmail,whatsapp,share;
ImageButton imgbtn;
DatabaseReference dbref;
//DatabaseReference dbref,newsref;
ImageView facebook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_news);
       // final ImageButton imageButton=findViewById(R.id.favbtn);
        gmail=findViewById(R.id.btn_gmail);
        facebook=findViewById(R.id.btn_facebook);

        whatsapp=findViewById(R.id.btn_whatsapp);
        share=findViewById(R.id.btn_share);
        f_tv_head=findViewById(R.id.f_tv_head);
        f_img=findViewById(R.id.f_img);
        f_tv_date=findViewById(R.id.f_tv_date);
        f_tv_time=findViewById(R.id.f_tv_time);
        f_tv_desc=findViewById(R.id.f_tv_desc);
        dbref= FirebaseDatabase.getInstance().getReference().child("News Record");
        f_tv_head.setText(getIntent().getStringExtra("head"));
        f_tv_desc.setText(getIntent().getStringExtra("desc"));
        f_tv_time.setText(getIntent().getStringExtra("time"));
        f_tv_date.setText(getIntent().getStringExtra("date"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            f_tv_desc.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
        }
        Picasso.get().load(getIntent().getStringExtra("img")).into(f_img);




        gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT,getIntent().getStringExtra("head"));
                i.putExtra(Intent.EXTRA_TEXT,getIntent().getStringExtra("desc"));
                i.setPackage("com.google.android.gm");
                startActivity(i);

            }
        });
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT,getIntent().getStringExtra("head"));
                i.putExtra(Intent.EXTRA_TEXT,getIntent().getStringExtra("desc"));
                i.setPackage("com.facebook.katana");
                startActivity(i);

            }
        });
        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent obj=new Intent(Intent.ACTION_SEND);
               obj.setType("text/plain");
               obj.putExtra(Intent.EXTRA_TEXT,"*"+getIntent().getStringExtra("head")+"* " +
                       ""+getIntent().getStringExtra("desc"));
               obj.setPackage("com.whatsapp");
               startActivity(obj);
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT,getIntent().getStringExtra("head")+getIntent().getStringExtra("desc"));
                startActivity(i);

            }
        });
    }


}
