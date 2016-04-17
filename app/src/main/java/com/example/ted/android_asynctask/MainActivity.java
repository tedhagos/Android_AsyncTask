package com.example.ted.android_asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

  TextView tv;
  Button btnStart;
  ProgressDialog progressDialog;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    tv = (TextView) findViewById(R.id.textView);
    btnStart = (Button) findViewById(R.id.btnStart);

    assert btnStart != null;
    btnStart.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        DownloadTask downloadtask = new DownloadTask(MainActivity.this, btnStart, tv);
        downloadtask.execute();
      }
    });
  }
}

class DownloadTask extends AsyncTask<Void, Integer, String> {

  Context context;
  TextView tv;
  ProgressDialog progressDialog;
  Button btnStart;

  public DownloadTask(Context context,
                      Button btnStart,
                      TextView tv) {
    super();

    this.context = context;
    this.btnStart = btnStart;
    this.tv = tv;
  }

  @Override
  protected void onProgressUpdate(Integer... values) {
    progressDialog.setProgress(values[0]);
  }

  @Override
  protected void onPreExecute() {

    progressDialog = new ProgressDialog(context);
    progressDialog.setMax(10);
    progressDialog.setTitle("Downloading...");
    progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    progressDialog.show();
  }

  @Override
  protected void onPostExecute(String s) {
    tv.setText(s);
  }

  @Override
  protected String doInBackground(Void... params) {

    int counter = 0;
    synchronized (this){
      while(counter++ < 10) {
        try {
          wait(2000);
          publishProgress(counter);
        }
        catch(InterruptedException ioe) {
          ioe.printStackTrace();
        }
      }
    }

    return "Download complete";
  }
}