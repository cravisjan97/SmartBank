package com.example.cravisundaram.picupload;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by C RAVI SUNDARAM on 31-01-2016.
 */
public class receive extends ActionBarActivity {
    HttpClient httpClient;
    HttpPost httpPost;
    ImageView imageView;
    Button b;
    Bitmap photo;
    person[] array = {new person("1", "Vijay", "95666531310", "ACC12", "2600000", "Bank of India"),
            new person("2", "Sangeeth", "8452145877", "ACC23", "5444122", "SBI"),
            new person("3", "Vignesh", "8544478547", "ACC34", "654", "IOB"),
            new person("4", "Ravi", "9854645547", "ACC45", "5874", "ICICI"),
            new person("5", "Judge", "984567854", "ACC21", "152450", "HDFC"),//change here
            new person("6","Sibi","84578965740","ACC31","54788","SBI")
    };
    person employee;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recieve);

        android.support.v7.app.ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle(" SMART BANK");
        actionbar.setDisplayShowHomeEnabled(true);
        actionbar.setLogo(R.mipmap.ic_launcher);
        actionbar.setDisplayUseLogoEnabled(true);

        imageView = (ImageView) findViewById(R.id.imageEmployee);

        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id");

        httpClient = new DefaultHttpClient();
        httpPost = new HttpPost("http://192.168.173.1/sample.php");
        new extract().execute();


    }

    public class extract extends AsyncTask<Void, Void, String> {
        private ProgressDialog pd = new ProgressDialog(receive.this);

        protected void onPreExecute() {
            super.onPreExecute();
            pd.setMessage("Processing...");
            pd.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                String response = httpClient.execute(httpPost, responseHandler);
                return response;
            } catch (Exception e) {
                return e.toString();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            pd.hide();
            pd.dismiss();
            //t.setText(s);


            if (s.equals("yes")) {

                for (int i = 0; i < array.length; i++) {
                    if (array[i].id.equals(id))
                        employee = array[i];
                }
            }

                if (employee != null) {


                    //SET PEOPLE DATA HERE
                    TextView text = (TextView) findViewById(R.id.user_id_number);
                    text.setText(employee.id);
                    text = (TextView) findViewById(R.id.user_account_number);
                    text.setText(employee.accountNumber);
                    text = (TextView) findViewById(R.id.user_mobile_number);
                    text.setText(employee.mobileNumber);
                    text = (TextView) findViewById(R.id.user_name);
                    text.setText(employee.name);
                    text = (TextView) findViewById(R.id.user_bank_name);
                    text.setText(employee.bankName);
                    text = (TextView) findViewById(R.id.user_balance_amount);
                    text.setText(employee.balanceAmount);

                    if (id .equals("1")) {
                     imageView.setImageResource(getResources().getIdentifier("vijay", "drawable", getPackageName()));
                    }
                    if (id .equals("2"))
                        imageView.setImageResource(getResources().getIdentifier("sangeeth", "drawable", getPackageName()));
                    if (id .equals("3"))
                        imageView.setImageResource(getResources().getIdentifier("vignesh", "drawable", getPackageName()));
                    if (id .equals("4")) {
                        imageView.setImageResource(getResources().getIdentifier("ravi", "drawable", getPackageName()));
                    }
                    if (id .equals("5")) {

                        imageView.setImageResource(getResources().getIdentifier("judge", "drawable", getPackageName()));//change here
                    }
                    if (id .equals("6")) {
                        imageView.setImageResource(getResources().getIdentifier("sibi", "drawable", getPackageName()));
                    }
                } else if (s.equals("no")) {
                    TextView t = (TextView) findViewById(R.id.unauthorized_text);
                    LinearLayout layout = (LinearLayout) findViewById(R.id.authorized_layout);
                    layout.setVisibility(View.INVISIBLE);
                    t.setVisibility(View.VISIBLE);
                }
            }


        }
    }

