package com.soviet_wave.youtube_downloader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.os.Vibrator;
import android.util.Log;
import android.view.HapticFeedbackConstants;
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
    private ProgressBar pBar_test;
    private TextView tg;
    private Button love;

    public static final String APP_PREFERENCES = "settings";
    //public String item_test = getString(R.string.video);
    //String[] itemView = { item_test, "Only_audio", "Playlist" };

    public String item_static = "Video";
    SharedPreferences pref;


    // Идентификатор уведомления
    private static final int NOTIFY_ID = 101;

    // Идентификатор канала
    private static String CHANNEL_ID = "Cat channel";
    //Длина вибрации (пауза, вибрация, пауза и тд)
    public long[] pattern = {0, 100, 100, 100, 100, 100};

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toast.makeText(MainActivity.this,"Powered by         Soviet_Wave", Toast.LENGTH_SHORT).show();
        edit_url = findViewById(R.id.editText);
        start_bt = findViewById(R.id.button);
        debug = findViewById(R.id.textV);
        settings = findViewById(R.id.Settings_button);
        love = findViewById((R.id.button2));
        tg = findViewById(R.id.telegram_url);
        pref = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        pBar = findViewById(R.id.progressBar);
        pBar_test = findViewById(R.id.progressBar_test);
        pBar.setVisibility(View.INVISIBLE);
        pBar_test.setVisibility(View.INVISIBLE);
        debug.setText("");
        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }

        //Список
        //String item_video = getString(R.string.video);
        //String item_only_audio = getString(R.string.only_audio);
        //String item_playlist = getString(R.string.playlist);
        //String[] itemView = { item_video, item_only_audio, item_playlist };

        //Это список объектов в выпадающем списке
        String[] itemView = {"Video", "Playlist(BETA)", "Audio"};

        Spinner spinner = findViewById(R.id.spinner);
        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, itemView);
        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        spinner.setAdapter(adapter);
        Python py = Python.getInstance();


        PyObject pyobj = py.getModule("YouTube");
        PyObject pyobj_playlist = py.getModule("playlist_download");
        PyObject pyobj_audio = py.getModule("audio_download");
        //Уведомление
        // Идентификатор уведомления


        //Выпадающий список
        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // Получаем выбранный объект
                item_static = (String) parent.getItemAtPosition(position);
                boolean debug_mode = pref.getBoolean("debug_mode", false);
                if (debug_mode == true) {
                    Toast.makeText(MainActivity.this, item_static, Toast.LENGTH_SHORT).show();
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
                start_bt.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                if (edit_url.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, getString(R.string.toast_url), Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        String url = edit_url.getText().toString();
                        Toast.makeText(MainActivity.this, getString(R.string.toast_download), Toast.LENGTH_SHORT).show();
                        pBar.setVisibility(View.VISIBLE);
                        Thread.UncaughtExceptionHandler h = new Thread.UncaughtExceptionHandler() {
                            @Override
                            public void uncaughtException(Thread th, Throwable ex) {
                                System.out.println("Uncaught exception: " + ex);
                            }
                        };
                        Thread t = new Thread() {
                            @Override
                            public void run() {
                                try {
                                    Log.d("item_static", item_static);
                                    if (item_static == "Video") {
                                        Log.d("Thread", "Video");
                                        PyObject obj = pyobj.callAttr("main", url, item_static);

                                    } else if (item_static == "Playlist(BETA)") {
                                        Log.d("Thread", "Playlist");
                                        PyObject obj = pyobj_playlist.callAttr("main", url, item_static);
                                    } else if (item_static == "Audio") {
                                        Log.d("Thread", "Audio");
                                        PyObject obj = pyobj_audio.callAttr("main", url, item_static);
                                        //}else if (item_static=="Видео"){
                                        //    Log.d("Thread", "Видео");
                                        //    PyObject obj = pyobj.callAttr("main", url, item_static);
                                        //}else if (item_static=="Плейлист(БЕТА)"){
                                        //    Log.d("Thread", "Плейлист");
                                        //    PyObject obj = pyobj_playlist.callAttr("main", url, item_static);
                                    } else {
                                        Log.d("Thread", "Аудио");
                                        PyObject obj = pyobj_audio.callAttr("main", url, item_static);
                                    }
                                    //Запуск вибрации
                                    Vibrator vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                                    vibrator.vibrate(pattern, -1);

                                    pBar.setVisibility(View.INVISIBLE);

                                } catch (Exception main) {
                                    Log.d("Thread_ERROR", "Капец пришёл потоку");
                                    pBar.setVisibility(View.INVISIBLE);
                                    Vibrator vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                                    vibrator.vibrate(1000);
                                    debug.setText(getString(R.string.toast_url_err));
                                    try {
                                        Thread.sleep(5000);
                                    } catch (InterruptedException err) {
                                        throw new RuntimeException(err);
                                    }
                                    debug.setText(" ");
                                    //debug.setVisibility(View.INVISIBLE);
                                }
                                throw new RuntimeException();
                            }
                        };
                        t.setUncaughtExceptionHandler(h);
                        t.start();


                    } catch (Exception main) {
                        //Toast.makeText(MainActivity.this, getString(R.string.toast_url_err), Toast.LENGTH_SHORT).show();
                        boolean debug_mode = pref.getBoolean("debug_mode", false);
                        if (debug_mode == true) {
                            debug.setText("(debug massage) Сработал обработчик ошибок");
                        }
                        Log.d("Thread_ERROR", "Капец пришёл потоку");
                    }

                }


            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settings.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                Intent intent = new Intent(MainActivity.this, Settings.class);
                startActivity(intent);

            }
        });

        tg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                tg.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.telegram_linkV)));
                startActivity(browserIntent);
            }

        });

        love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                love.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://boosty.to/mr_artem/donate"));
                startActivity(browserIntent);
            }

        });
    }
}