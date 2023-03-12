package com.soviet_wave.youtube_downloader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {
    private EditText edit_url;
    private Button start_bt;
    private TextView debug;
    private FloatingActionButton settings;
    private ProgressBar pBar;

    public static final String APP_PREFERENCES = "settings";
    SharedPreferences pref;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(MainActivity.this,"Powered by         Soviet_Wave", Toast.LENGTH_SHORT).show();
        edit_url = findViewById(R.id.editText);
        start_bt = findViewById(R.id.button);
        debug = findViewById(R.id.textV);
        settings = findViewById(R.id.Settings_button);
        pref = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        pBar = findViewById(R.id.progressBar);
        pBar.setVisibility(View.INVISIBLE);
        debug.setText("");
        if (!Python.isStarted()){
            Python.start(new AndroidPlatform(this));
        }

        Python py  = Python.getInstance();




        PyObject pyobj = py.getModule("YouTube");


        start_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                debug.setText("");
                if (edit_url.getText().toString().equals(""))
                {
                    Toast.makeText(MainActivity.this,getString(R.string.toast_url), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    try {
                        String url = edit_url.getText().toString();
                        Toast.makeText(MainActivity.this, getString(R.string.toast_download), Toast.LENGTH_SHORT).show();
                        PyObject obj = pyobj.callAttr("main", url);
                        Toast.makeText(MainActivity.this, getString(R.string.toast_done), Toast.LENGTH_SHORT).show();
                        boolean debug_mode = pref.getBoolean("debug_mode",false);
                        if (debug_mode==true){
                            debug.setText("(debug massage) Ну, наверное скачалось");
                        }
                    } catch (Exception main){
                        Toast.makeText(MainActivity.this,getString(R.string.toast_url_err), Toast.LENGTH_SHORT).show();
                        boolean debug_mode = pref.getBoolean("debug_mode",false);
                        if (debug_mode==true){
                            debug.setText("(debug massage) Сработал обработчик ошибок");
                        }
                    }

                }


            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Settings.class);
                startActivity(intent);

            }
        });

    }
}