package com.triplegamaappsinc.downloadimage1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Button buttonDownload;
    EditText editTextUrl;
    ProgressBar progressBar;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        buttonDownload = findViewById(R.id.button);
        editTextUrl = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);

        File downloadsFolder = new File(this.getExternalFilesDir(null)+File.separator+"Downloads");
        if(!downloadsFolder.exists())
        {
            downloadsFolder.mkdirs();
        }



        buttonDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTextUrl.getText().toString().endsWith(".jpg")){
                    new DownloadImage(MainActivity.this, editTextUrl.getText().toString(), MainActivity.this).execute(0);


                } else
                {
                    Toast.makeText(MainActivity.this, "Enter .jpg image url!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}