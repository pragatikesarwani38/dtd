package app.daytoday.softpro.daytodayindiadtd;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AddPost extends AppCompatActivity {
EditText et_head,et_des;
ImageView opencamera;
Button btn;
Spinner cat_spinner;
String [] categories={"Select a Category","Sports","Entertainment","Politics","Business","Crime","Technical"};
ArrayAdapter adapter;
AlertDialog alertDialog;
Bitmap bitmap;
String category;
String downloadurl;

StorageReference storageReference;
ProgressDialog pd;
DatabaseReference dbref,newsref;
String headline,description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        et_des=(EditText)findViewById(R.id.et_des);
        et_head=(EditText)findViewById(R.id.et_head);
        opencamera =(ImageView)findViewById(R.id.opencamera);
        btn=findViewById(R.id.btn);
        cat_spinner=(Spinner) findViewById(R.id.cat_spinner);
        adapter=new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,categories);
        cat_spinner.setAdapter(adapter);
        checkmyvalue();
        pd=new ProgressDialog(this);
        storageReference= FirebaseStorage.getInstance().getReference(); //gives the reference of parent url.
        dbref= FirebaseDatabase.getInstance().getReference();
        ActivityCompat.requestPermissions(AddPost.this,new String[]{Manifest.permission.CAMERA},0);
        
        opencamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showmydialog();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                headline=et_head.getText().toString().trim();
                description=et_des.getText().toString().trim();
                if(headline.isEmpty())
                {
                    et_head.setError("Enter the headline");
                    et_head.requestFocus();
                }
                else if(description.isEmpty())
                {
                    et_des.setError("Enter the description");
                    et_des.requestFocus();
                }

                else
                {
                    pd.setMessage("Uploading");
                    pd.show();
                    uploadImage();

                }







            }

        });
    }

    private void checkmyvalue()
    {
        cat_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item=parent.getItemAtPosition(position);
               /* if(item.toString().equals("Sports"))
                {
                    newsref=dbref.child("News Record").child("Sports");
                    category="Sports";
                }
                else if(item.toString().equals("Entertainment"))
                {
                    newsref=dbref.child("News Record").child("Entertainment");
                    category="Entertainment";
                }
                else if(item.toString().equals("Politics"))
                {
                    newsref=dbref.child("News Record").child("Politics");
                    category="Politics";
                }
                else if(item.toString().equals("Business"))
                {
                    newsref=dbref.child("News Record").child("Business");
                    category="Business";
                }
                else if(item.toString().equals("Crime"))
                {
                    newsref=dbref.child("News Record").child("Crime");
                    category="Crime";
                }
                else if(item.toString().equals("Technical"))
                {
                    newsref=dbref.child("News Record").child("Technical");
                    category="Technical";
                }*/
               for(int i=0;i<categories.length;i++)
               {
                   if(item.toString().equals(categories[i]))
                   {
                       newsref=dbref.child("News Record").child(categories[i]);
                       category=categories[i];
                   }
               }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void uploadImage()
    {
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress( Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finalimg=baos.toByteArray();
        final StorageReference filepath=storageReference.child("News Image").child(finalimg+"jpg");
        final UploadTask uploadTask=filepath.putBytes(finalimg);
        uploadTask.addOnCompleteListener(AddPost.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful())
                {

                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                        {
                            filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadurl=String.valueOf(uri);
                                    insertdata();
                                    //Toast.makeText(AddPost.this, downloadurl, Toast.LENGTH_LONG).show();
                                }
                            });

                        }
                    });
                    //Toast.makeText(AddPost.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    Toast.makeText(AddPost.this, "Failed to Upload", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void insertdata()
    {
        final String uniquekey=newsref.push().getKey();
        Calendar calfordate=Calendar.getInstance();
        SimpleDateFormat currentdate=new SimpleDateFormat("dd-MM-yyyy");
        String date=currentdate.format(calfordate.getTime());
        Calendar calfortime=Calendar.getInstance();
        SimpleDateFormat currenttime=new SimpleDateFormat("HH:mm");
        String time=currenttime.format(calfortime.getTime());
       HashMap hp=new HashMap();
        //LinkedHashMap<String,String> newMap=new LinkedHashMap<String, String>(hp);
        hp.put("Headline",headline);
        hp.put("Description",description);
        hp.put("Imgurl",downloadurl);
        hp.put("Time",time);
        hp.put("Date",date);
        hp.put("Category",category);
        hp.put("Key",uniquekey);

        newsref.child(uniquekey).updateChildren(hp).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task)
            {
                if(task.isSuccessful())
                {
                    Toast.makeText(AddPost.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
                else
                {
                    pd.dismiss();
                    Toast.makeText(AddPost.this, "Data insertion failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showmydialog()
    {
        AlertDialog.Builder dialog =new AlertDialog.Builder(this);
        LayoutInflater inflater=this.getLayoutInflater();
        View dialogview=inflater.inflate(R.layout.custom_camera,null);
        dialog.setView(dialogview);
        alertDialog=dialog.create();
        alertDialog.show();

        LinearLayout camera_btn,gallery_btn;
        camera_btn=alertDialog.findViewById(R.id.camera_btn);
        gallery_btn=alertDialog.findViewById(R.id.gallery_btn);
        camera_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takeimage=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(takeimage,0);
            }
        });
        gallery_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickgallery=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickgallery,1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
       if(requestCode==0 && resultCode==RESULT_OK)
       {
        bitmap= (Bitmap) data.getExtras().get("data");
        opencamera.setImageBitmap(bitmap);
        alertDialog.dismiss();
       }
       else if(requestCode==1&&resultCode==RESULT_OK)

       {
           Uri uri=data.getData();
           try {
               bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
           } catch (IOException e) {
               e.printStackTrace();
           }
           opencamera.setImageBitmap(bitmap);
           alertDialog.dismiss();
       }

    }
}
