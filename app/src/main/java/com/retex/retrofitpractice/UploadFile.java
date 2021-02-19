package com.retex.retrofitpractice;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
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
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import static java.text.DateFormat.DEFAULT;

public class UploadFile extends AppCompatActivity implements View.OnClickListener {
    public static final String KEY_User_Document1 = "doc1";
    ImageView IdProf;
    Button upload;
    Button viewImage;
    private String Document_img1 = "";
    String IMAGE_BASE_64;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_file);
        initView();
    }

    private void initView() {
        IdProf = findViewById(R.id.id_prof);
        upload = findViewById(R.id.UploadBtn);
        viewImage = findViewById(R.id.btn_view);
        upload.setOnClickListener(this);
        viewImage.setOnClickListener(this);
        IdProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

    }

    private void selectImage() {

        final CharSequence[] option = {"Take Photo", "Choose From Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(UploadFile.this);
        builder.setTitle("Add Photo!");
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (option[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
                    //intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                } else if (option[item].equals("Choose From Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);

                } else if (option[item].equals("Cancel")) {
                    dialog.dismiss();
                }

            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /*Bitmap photo = (Bitmap) data.getExtras().get("data");
        IdProf.setImageBitmap(photo);*/

        if (resultCode == RESULT_OK) {


           if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory(), toString());
               Bitmap photo = (Bitmap) data.getExtras().get("data");
               IdProf.setImageBitmap(photo);
               ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
               photo.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
               byte[] byteArray = byteArrayOutputStream .toByteArray();
               String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

               Log.e("regfh",""+encoded);

                IMAGE_BASE_64 = encoded.getBytes().toString();

                /*for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;

                    }
                }*/
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOption = new BitmapFactory.Options();
                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(), bitmapOption);
                    bitmap = getResizedBitMap(bitmap, 400);
                    IdProf.setImageBitmap(bitmap);
                    BitMapToString(bitmap);
                    String path = Environment.getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
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
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                thumbnail = getResizedBitMap(thumbnail, 400);
                IdProf.setImageBitmap(thumbnail);
               ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
               thumbnail.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
               byte[] byteArray = byteArrayOutputStream .toByteArray();
               String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

               Log.e("choose qq",""+encoded);

               IMAGE_BASE_64 = encoded.getBytes().toString();

            }


        }
    }

    private String BitMapToString(Bitmap userImage1) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        userImage1.compress(Bitmap.CompressFormat.PNG,100   ,baos);
        byte[] b = baos.toByteArray();
        Document_img1 = Base64.encodeToString(b,Base64.DEFAULT);
        return Document_img1;
    }

    private Bitmap getResizedBitMap(Bitmap image, int maxSize) {
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

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.UploadBtn:
                if(IMAGE_BASE_64.equals("") || IMAGE_BASE_64.equals(null)){
                    ContextThemeWrapper ctw = new ContextThemeWrapper(UploadFile.this, R.style.Theme_AppCompat_Dialog_Alert);
                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctw);
                    alertDialogBuilder.setTitle("Image File Can't Empty");
                    alertDialogBuilder.setMessage("Image File Can't Empty Please Add Image");
                    alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }

                    });
                    alertDialogBuilder.show();
                    return;
                }
                else{
                    uploadImage();
                }

        }

    }

    private void uploadImage() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://arjun.jain.software/arjunguru/add_file.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("Data Inserted")) {
                            Toast.makeText(UploadFile.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(UploadFile.this, response, Toast.LENGTH_SHORT).show();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UploadFile.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }

        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("cid",cid);
                params.put("imageuser",IMAGE_BASE_64);

                return params;
            }
        };


    }
}