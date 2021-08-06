package app.daytoday.softpro.daytodayindiadtd;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class DestroyActivity extends AppCompatActivity {

    ImageView imageView;
    EditText et_head,et_desc;
    Button btn_delete,btn_update;
    DatabaseReference dbref;
  public  String downloadurl;
    Bitmap bitmap;
    AlertDialog alertDialog;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destroy);
        imageView=findViewById(R.id.del_img);
        et_head=findViewById(R.id.del_head);
        et_desc=findViewById(R.id.del_desc);
        btn_delete=findViewById(R.id.btn_delete);
        btn_update=findViewById(R.id.btn_update);
        dbref= FirebaseDatabase.getInstance().getReference().child("News Record");
        et_head.setText(getIntent().getStringExtra("head"));
        et_desc.setText(getIntent().getStringExtra("desc"));
        Picasso.get().load(getIntent().getStringExtra("img")).into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showmydialog();
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dbref.child(getIntent().getStringExtra("category")).
                        child(getIntent().getStringExtra("key")).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(DestroyActivity.this, "Content is deleted", Toast.LENGTH_SHORT).show();
                            et_head.setText("");
                            et_desc.setText("");
                            imageView.setImageResource(R.drawable.camera_icon);
                        }
                        else
                        {
                            Toast.makeText(DestroyActivity.this, "Content not deleted", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String head,desc;
                head=et_head.getText().toString().trim();
                desc=et_desc.getText().toString().trim();
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        uploadimg();
                    }
                });

                HashMap hp=new HashMap();
                hp.put("Headline",head);
                hp.put("Description",desc);
                hp.put("Imgurl",downloadurl);

                dbref.child(getIntent().getStringExtra("category")).
                        child(getIntent().getStringExtra("key")).updateChildren(hp).
                        addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(DestroyActivity.this, "Content updated", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(DestroyActivity.this, "Content updation failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode==0 && resultCode==RESULT_OK)
        {
            bitmap= (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
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
            imageView.setImageBitmap(bitmap);
            alertDialog.dismiss();
        }

    }

    private void uploadimg() {
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress( Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finalimg=baos.toByteArray();
        final StorageReference filepath=storageReference.child("News Image").child(finalimg+"jpg");
        final UploadTask uploadTask=filepath.putBytes(finalimg);
        uploadTask.addOnCompleteListener(DestroyActivity.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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

                                    Toast.makeText(DestroyActivity.this, downloadurl, Toast.LENGTH_LONG).show();
                                }
                            });

                        }
                    });
                    //Toast.makeText(AddPost.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    Toast.makeText(DestroyActivity.this, "Failed to Upload", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



}
