package com.example.clgapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UploadTimeTable extends AppCompatActivity {

    Spinner stream;
    RadioGroup radioGroup;
    RadioButton rdOne, rdTwo, rdThree;
    Number year;

    Button btnChoose, btnUpload;
    ImageView imageView;

    Bitmap bitmap;
    final int REQ = 1;
    ProgressDialog pd;
    DatabaseReference userDatabaseRef;
    StorageReference reference;
    String downloadUrl, streamValue;
    EditText roll_no,yearValue;
    Uri imageUri;

    /*  private DatabaseReference root= FirebaseDatabase.getInstance().getReference("Image");
      private StorageReference reference= FirebaseStorage.getInstance().getReference();
      private Uri imageUri;*/
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_time_table);

        stream = findViewById(R.id.streamspinner2);
        //yearValue=findViewById(R.id.editTextYear);
    /*    radioGroup = findViewById(R.id.radioGroup1);
        rdOne = findViewById(R.id.rdOne);
        rdTwo = findViewById(R.id.rdTwo);
        rdThree = findViewById(R.id.rdThree);*/

     /*   radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rdone)
                    year = 1;
                else if (checkedId == R.id.rdtwo)
                    year = 2;
                else if (checkedId == R.id.rdthree)
                    year = 3;
            }
        });*/

        Spinner spinner1 = (Spinner) findViewById(R.id.streamspinner2);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(UploadTimeTable.this, R.array.stream, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);

        pd = new ProgressDialog(this);
        btnUpload = findViewById(R.id.btnUpload);
        imageView = findViewById(R.id.imageView2);
        btnChoose = findViewById(R.id.btnChoose);
        yearValue=findViewById(R.id.editTextYear1);
        streamValue = stream.getSelectedItem().toString();




        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Time Table").child(streamValue).child(yearValue.getText().toString()).push();
                reference = FirebaseStorage.getInstance().getReference();
                if (imageUri != null) {
                    final StorageReference filePath = FirebaseStorage.getInstance().getReference().child(imageUri.getPath());
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), imageUri);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);
                    byte[] data = byteArrayOutputStream.toByteArray();
                    UploadTask uploadTask = filePath.putBytes(data);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UploadTimeTable.this, "Image upload failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                    uploadTask.addOnSuccessListener(taskSnapshot -> {
                        if (taskSnapshot.getMetadata() != null && taskSnapshot.getMetadata().getReference() != null) {
                            Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                            result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String imageUrl = uri.toString();
                                    Map newImageMap = new HashMap();
                                    //  newImageMap.put("Roll_no",rollno.getText().toString());
                                    // newImageMap.put("Stream",qstn.getText().toString());
                                    newImageMap.put("TimeTableUrl", imageUrl);

                                    userDatabaseRef.updateChildren(newImageMap).addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()) {
                                            Toast.makeText(UploadTimeTable.this, "Image added to database successfully", Toast.LENGTH_SHORT).show();

                                        } else {
                                            Toast.makeText(UploadTimeTable.this, task1.getException().toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    finish();
                                }
                            });
                        }
                    });
                }
            }
        });

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 2);
            }
        });

        /*add=findViewById(R.id.btnAdd);
        btnUpload=findViewById(R.id.btnUpload);
        imageView=findViewById(R.id.imageView2);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // startActivity(new Intent(UploadTimeTable.this,));
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent timetable=new Intent();
                timetable.setAction(Intent.ACTION_GET_CONTENT);
                timetable.setType("timetable/3BCA/*");
                startActivityForResult(timetable,2);


            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageUri!=null)
                {
                    uploadToFirebase(imageUri);
                }
                else {
                    Toast.makeText(UploadTimeTable.this,"Please Select Image",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==2&&resultCode==RESULT_OK&&data!=null)
        {
            imageUri=data.getData();
            imageView.setImageURI(imageUri);

        }
    }

    private void uploadToFirebase(Uri uri)
    {
        StorageReference fileRef=reference.child(System.currentTimeMillis()+"."+getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Model model=new Model(uri.toString());
                        String modelId=root.push().getKey();
                        root.child(modelId).setValue(model);

                        Toast.makeText(UploadTimeTable.this,"Upload Success",Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }
    private  String getFileExtension(Uri mUri)
    {
        ContentResolver cr=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));

    }*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);

        }
    }
}