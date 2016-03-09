package com.example.cravisundaram.picupload;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

//    Button btpic, btnup;
    Button button;
    String id;
    CountDownTimer c;
    TextView t;
    String ba1;
    public static String URL = "http://192.168.173.1/upload.php";
    String mCurrentPhotoPath;
    EditText e;
    ImageView mImageView;
    private static final int CAMERA_REQUEST = 1888;
    Bitmap photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v7.app.ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle(" SMART BANK");
        actionbar.setDisplayShowHomeEnabled(true);
        actionbar.setLogo(R.mipmap.ic_launcher);
        actionbar.setDisplayUseLogoEnabled(true);

//        btpic = (Button) findViewById(R.id.cpic);
        button = (Button) findViewById(R.id.button);
        //t=(TextView)findViewById(R.id.tc);
        e=(EditText)findViewById(R.id.idnum);
        mImageView = (ImageView) findViewById(R.id.Imageprev);
/*
        btpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                            }
        });
*/
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(button.getText().toString().equals("Click image"))
                {

                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
                else
                {
                    upload();
                }
            }
        });
/*
        btnup = (Button) findViewById(R.id.up);
        btnup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload();
            }
        });
        */
    }

    private void upload() {

        //Bitmap bm = BitmapFactory.decodeFile(mCurrentPhotoPath);
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        //photo= Bitmap.createScaledBitmap(photo,28,28,true);//added line
        photo.compress(Bitmap.CompressFormat.JPEG, 100, bao);
        byte[] ba = bao.toByteArray();
        ba1 = Base64.encodeToString(ba, Base64.DEFAULT);

        // Upload image to server
        id=e.getText().toString();//id number
        if(id.length()==0) e.setError("Enter an ID");
            else new uploadToServer().execute();

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            photo = (Bitmap) data.getExtras().get("data");
            mImageView.setImageBitmap(photo);
            button.setText("Upload image");
            e.setVisibility(View.VISIBLE);
        }
    }

    public class uploadToServer extends AsyncTask<Void, Void, String> {

        private ProgressDialog pd = new ProgressDialog(MainActivity.this);

        protected void onPreExecute() {
            super.onPreExecute();
            pd.setMessage("Wait image uploading!");
            pd.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            String message;
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("base64", ba1));
            nameValuePairs.add(new BasicNameValuePair("ImageName", "1" + ".jpg"));
            nameValuePairs.add(new BasicNameValuePair("id",id));
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(URL);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                String st = EntityUtils.toString(response.getEntity());
                Log.v("log_tag", "In the try Loop" + st);
                message="Success";

            } catch (Exception e) {
                Log.v("log_tag", "Error in http connection " + e.toString());
                message = "Please check your internet connection and try again";
           //     return (e.toString());
            }
            return message;

        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pd.hide();
            pd.dismiss();
            button.setText("Click Image");
            Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
            Intent i=new Intent(MainActivity.this, receive.class);
            i.putExtra("id",id);
            i.putExtra("image",photo);
            startActivity(i);
        }
    }



}





