package com.soviet_wave.youtube_downloader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity {
    private EditText edit_url;
    private Button start_bt;
    private TextView debug;
    private FloatingActionButton settingsM3;
    private FloatingActionButton settings;
    private ProgressBar pBar;
    private ProgressBar pBarM;
    private ProgressBar pBar2;
    private Button loveb;

    private ViewGroup.LayoutParams adViewLayoutParams;
    private TextView tg;




    public static final String APP_PREFERENCES = "settings";
    SharedPreferences pref;
    SharedPreferences pref_theme;

    String[] itemView = { "Video", "Audio", "Playlist"};

    public String item = "Video";

    public static final String MATERIAL_THEME = "theme";
    public int total_progress;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int android_version = Integer.parseInt(Build.VERSION.RELEASE);
        pref_theme = getSharedPreferences(MATERIAL_THEME, Context.MODE_PRIVATE);
        boolean material_mode = pref_theme.getBoolean("material_mode",false);
        SharedPreferences.Editor editor = pref_theme.edit();
        if (android_version==12) {
            if (material_mode == false) {
                setTheme(R.style.Theme_YouTube_downloader);


            } else {
                setTheme(R.style.Theme_MaterialYou);

            }
        }else {
            setTheme(R.style.Theme_YouTube_downloader);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pref = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        boolean debug_mode = pref.getBoolean("debug_mode",false);
        if (debug_mode==true){
            Toast.makeText(MainActivity.this,"Powered by         Soviet_Wave", Toast.LENGTH_SHORT).show();
        }
        edit_url = findViewById(R.id.editText);
        start_bt = findViewById(R.id.button);
        settingsM3 = findViewById(R.id.Settings_buttonM3);
        settings = findViewById(R.id.settings_button);
        debug = findViewById(R.id.textV);
        loveb = findViewById(R.id.button2);
        tg = findViewById(R.id.telegram_url);
        pBar = findViewById(R.id.progressBar);
        pBarM = findViewById(R.id.progressBarM);
        pBar2 = findViewById(R.id.progressBar2);
        pBar.setVisibility(View.INVISIBLE);
        pBar2.setVisibility(View.INVISIBLE);
        pBarM.setVisibility(View.INVISIBLE);
        debug.setText("");
        if (material_mode == false) {
            settingsM3.setVisibility(View.INVISIBLE);
            settings.setVisibility(View.VISIBLE);

        }else {
            settings.setVisibility(View.INVISIBLE);
        }
        if (!Python.isStarted()){
            Python.start(new AndroidPlatform(this));
        }


        Spinner spinner = findViewById(R.id.spinner);
        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, itemView);
        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        spinner.setAdapter(adapter);

        Python py  = Python.getInstance();




        PyObject pyobj = py.getModule("YouTube");
        PyObject pyobj_playlist = py.getModule("playlist_download");
        PyObject pyobj_audio = py.getModule("audio_download");






        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // Получаем выбранный объект
                item = (String)parent.getItemAtPosition(position);
                boolean debug_mode = pref.getBoolean("debug_mode",false);
                if (debug_mode==true){
                    Toast.makeText(MainActivity.this,item, Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        spinner.setOnItemSelectedListener(itemSelectedListener);



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
                        if (material_mode == false) {
                            pBar.setVisibility(View.VISIBLE);
                        }else{
                            pBarM.setVisibility(View.VISIBLE);
                        }
                        //Runnable runnable = new Runnable() {
//
                        //    @Override
                        //    public void run() {
                        //        if (item=="Video") {
                        //            Log.d("Thread", "Video");
                        //            PyObject obj = pyobj.callAttr("main", url, item);
                        //            //while (true){
                        //            //    PyObject obj_progress = pyobj.callAttr("progress");
                        //            //    String total_progress = obj_progress.toString();
                        //            //    Log.d("total_progress", getString(Integer.parseInt(total_progress)));
                        //            //    pBar2.setProgress(total_progress);
                        //            //}
//
//
                        //        }else if (item=="Playlist") {
                        //            Log.d("Thread", "Playlist");
                        //            PyObject obj = pyobj_playlist.callAttr("main", url, item);
                        //        }else {
                        //            Log.d("Thread", "Audio");
                        //            PyObject obj = pyobj_audio.callAttr("main", url, item);
                        //        }
                        //        if (material_mode == false) {
                        //            pBar.setVisibility(View.INVISIBLE);
                        //        }else{
                        //            pBarM.setVisibility(View.INVISIBLE);
                        //        }
//
                        //    }
                        //};
                        ////pBar.setVisibility(View.INVISIBLE);
//
                        //// Определяем объект Thread - новый поток
                        //Thread thread = new Thread(runnable);
//
                        //// Запускаем поток
                        //thread.start();


                        //Toast.makeText(MainActivity.this, getString(R.string.toast_done), Toast.LENGTH_SHORT).show();
                        //boolean debug_mode = pref.getBoolean("debug_mode",false);
                        //if (debug_mode==true){
                        //    debug.setText("(debug massage) Ну, наверное скачалось");
                        //}
                        Thread.UncaughtExceptionHandler h = new Thread.UncaughtExceptionHandler() {
                            @Override
                            public void uncaughtException(Thread th, Throwable ex) {
                                System.out.println("Uncaught exception: " + ex);
                            }
                        };
                        Thread t = new Thread() {
                            @Override
                            public void run() {
                                System.out.println("Sleeping ...");
                                try {
                                    if (item=="Video") {
                                        Log.d("Thread", "Video");
                                        PyObject obj = pyobj.callAttr("main", url, item);
                                        //while (true){
                                        //    PyObject obj_progress = pyobj.callAttr("progress");
                                        //    String total_progress = obj_progress.toString();
                                        //    Log.d("total_progress", getString(Integer.parseInt(total_progress)));
                                        //    pBar2.setProgress(total_progress);
                                        //}
                                    }else if (item=="Playlist") {
                                        Log.d("Thread", "Playlist");
                                        PyObject obj = pyobj_playlist.callAttr("main", url, item);
                                    }else {
                                        Log.d("Thread", "Audio");
                                        PyObject obj = pyobj_audio.callAttr("main", url, item);
                                    }
                                    if (material_mode == false) {
                                        pBar.setVisibility(View.INVISIBLE);
                                    }else{
                                        pBarM.setVisibility(View.INVISIBLE);
                                    }
                                } catch (Exception main) {
                                    Log.d("Thread_ERROR", "Капец пришёл потоку");
                                    if (material_mode == false) {
                                        pBar.setVisibility(View.INVISIBLE);
                                    }else{
                                        pBarM.setVisibility(View.INVISIBLE);
                                    }
                                    debug.setText(getString(R.string.toast_url_err));
                                    try {
                                        Thread.sleep(5000);
                                    } catch (InterruptedException e) {
                                        throw new RuntimeException(e);
                                    }
                                    debug.setText(" ");
                                    //debug.setVisibility(View.INVISIBLE);
                                }
                                throw new RuntimeException();
                            }
                        };
                        t.setUncaughtExceptionHandler(h);
                        t.start();


                    } catch (Exception main){
                        Toast.makeText(MainActivity.this,getString(R.string.toast_url_err), Toast.LENGTH_SHORT).show();
                        boolean debug_mode = pref.getBoolean("debug_mode",false);
                        if (debug_mode==true){
                            debug.setText("(debug massage) Сработал обработчик ошибок");
                        }
                        Log.d("Thread_ERROR", "Капец пришёл потоку");
                    }

                }


            }
        });

        loveb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new
                        Intent(Intent.ACTION_VIEW, Uri.parse("https://boosty.to/mr_artem/donate"));
                startActivity(browserIntent);
            }
        });
        tg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.telegram_linkV)));
                startActivity(browserIntent);
            }

        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Settings.class);
                startActivity(intent);



            }
        });
        settingsM3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Settings.class);
                startActivity(intent);



            }
        });



    }

}
