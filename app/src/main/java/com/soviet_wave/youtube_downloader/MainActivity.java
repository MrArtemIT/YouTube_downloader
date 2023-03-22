package com.soviet_wave.youtube_downloader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
    private FloatingActionButton settings;
    private ProgressBar pBar;
    private Button loveb;



    public static final String APP_PREFERENCES = "settings";
    SharedPreferences pref;

    String[] itemView = { "Video", "Only_audio(experimental)" };

    public String item = "Video";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String android_v = (Build.VERSION.RELEASE);
        Toast.makeText(MainActivity.this,"Powered by         Soviet_Wave", Toast.LENGTH_SHORT).show();
        edit_url = findViewById(R.id.editText);
        start_bt = findViewById(R.id.button);
        debug = findViewById(R.id.textV);
        loveb = findViewById(R.id.button2);
        settings = findViewById(R.id.Settings_button);
        pref = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        pBar = findViewById(R.id.progressBar);
        pBar.setVisibility(View.INVISIBLE);
        debug.setText("");
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
                        PyObject obj = pyobj.callAttr("main", url, item);
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

        loveb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new
                        Intent(Intent.ACTION_VIEW, Uri.parse("https://new.donatepay.ru/@884550"));
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

    }
}
