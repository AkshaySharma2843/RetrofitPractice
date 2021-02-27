package com.retex.retrofitpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UploadFile extends AppCompatActivity implements View.OnClickListener {

    ImageView IDProf;
    Button Upload_Btn;
    Button view;
    String col_lead_id;

    private String user_image="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_file);
        col_lead_id = getIntent().getStringExtra("leadid");
        IDProf=(ImageView)findViewById(R.id.id_prof);
        Upload_Btn=(Button)findViewById(R.id.UploadBtn);
        view = findViewById(R.id.btn_view);

        IDProf.setOnClickListener(v -> selectImage());

        Upload_Btn.setOnClickListener(this);
        view.setOnClickListener(this);
    }


    private void selectImage() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(UploadFile.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, (dialog, item) -> {
            if (options[item].equals("Take Photo"))
            {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File f = new File(Environment.getExternalStorageDirectory(), ".jpg");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                startActivityForResult(intent, 1);
            }
            else if (options[item].equals("Choose from Gallery"))
            {
                Intent intent = new   Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2);
            }
            else if (options[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : Objects.requireNonNull(f.listFiles())) {
                    if (temp.getName().equals(".jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(), bitmapOptions);
                    bitmap=getResizedBitmap(bitmap, 200);
                    IDProf.setImageBitmap(bitmap);
                    Log.d("Image",""+bitmap);
                    BitMapToString(bitmap);
                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile;
                    File file = new File(path, System.currentTimeMillis() + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {
                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                assert selectedImage != null;
                Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
                assert c != null;
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                thumbnail=getResizedBitmap(thumbnail, 200);
                Log.w("image from gallery", picturePath+"");
                IDProf.setImageBitmap(thumbnail);
                Log.d("Image_code",""+thumbnail);
                BitMapToString(thumbnail);
            }
        }
    }
    public String BitMapToString(Bitmap userImage1) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        userImage1.compress(Bitmap.CompressFormat.PNG, 50, baos);
        byte[] b = baos.toByteArray();
        user_image = Base64.encodeToString(b, Base64.DEFAULT);
        Log.e("Base_64",""+user_image);
        return user_image;

    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    private void SendDetail(String col_lead_id, String user_image) {

        RetryPolicy mRetryPolicy = new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://arjun.jain.software/arjunguru/add_file.php",
                response -> {
                    if (response.equals("Inserted")) {
                        Log.e("CHECK",""+response);
                        Toast.makeText(UploadFile.this, "Inserted", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(UploadFile.this, "I Don't Know", Toast.LENGTH_SHORT).show();
                    }
                }, error -> {
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("col_lead_id",col_lead_id);
                params.put("user_image",user_image);
                Log.e("ERROR",""+params);
                return params;


            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(UploadFile.this);
        stringRequest.setRetryPolicy(mRetryPolicy);
        requestQueue.add(stringRequest);
    }


    @Override
    public void onClick(View v) {
        if (user_image.equals("") || user_image == null) {
            ContextThemeWrapper ctw = new ContextThemeWrapper( UploadFile.this, R.style.Theme_AppCompat_Dialog_Alert);
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctw);
            alertDialogBuilder.setTitle("Image File Can't Empty ");
            alertDialogBuilder.setMessage("Image File Can't empty please select any one document");
            alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                }
            });
            alertDialogBuilder.show();
        }
        else {
            SendDetail(col_lead_id,user_image);

        }

    }
}