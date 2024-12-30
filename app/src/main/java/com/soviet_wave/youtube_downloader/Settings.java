package com.soviet_wave.youtube_downloader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import  com.soviet_wave.youtube_downloader.BuildConfig;

import com.google.android.material.materialswitch.MaterialSwitch;

public class Settings extends AppCompatActivity {
    private MaterialSwitch debug_sweech;
    private MaterialSwitch lib_switch;
    private MaterialSwitch theme_switch;
    private MaterialSwitch rutube_switch;
    private TextView debug_text;
    private ImageButton button_github;
    private SharedPreferences pref;
    private final String save_key = "save_key";
    public static final String APP_PREFERENCES = "settings";

    private TextView ver_code;
    private  TextView version;

    public int deviceAndroidVersion;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        boolean theme_mode = pref.getBoolean("Dynamic_Colors",false);
        SharedPreferences.Editor theme_editor = pref.edit();
        if (theme_mode == true){
            setTheme(R.style.Theme_MaterialYou);
        }else {
            setTheme(R.style.Theme_YouTube_downloader);
        }
        setContentView(R.layout.activity_settings);
        debug_sweech = findViewById(R.id.switch_debug);
        lib_switch = findViewById(R.id.switch_lib);
        TextView debug_text = findViewById(R.id.textView2);
        button_github = findViewById(R.id.github_Button);
        theme_switch = findViewById(R.id.switch_theme);
        rutube_switch = findViewById(R.id.switch_rutube);


        //получаем номер версии и отображаем его
        version = findViewById(R.id.version);
        String version_name = getResources().getString(R.string.version_name) + " " + BuildConfig.VERSION_NAME;
        version.setText(version_name);

        //получаем код версии и отображаем его
        ver_code = findViewById(R.id.version_code);
        String versionCode = getResources().getString(R.string.version_code) + " " + BuildConfig.VERSION_CODE;
        ver_code.setText(versionCode);


        //Получаем значения из памяти чтобы отобразить верное значение переключателя вкл/выкл
        boolean debug_mode = pref.getBoolean("debug_mode",false);
        SharedPreferences.Editor editor = pref.edit();

        boolean fix_mode = pref.getBoolean("fix_mode",false);
        SharedPreferences.Editor fix_editor = pref.edit();

        boolean rutube_mode = pref.getBoolean("rutube_mode",false);
        SharedPreferences.Editor rutube_editor = pref.edit();

        int android_version = pref.getInt("Android_version", 0);
        SharedPreferences.Editor android_editor = pref.edit();

        //Получаем версию Android
        deviceAndroidVersion = Integer.parseInt(Build.VERSION.RELEASE);
        android_editor.putInt("Android_version", deviceAndroidVersion);
        android_editor.apply();

        if (deviceAndroidVersion >= 12){
            theme_switch.setVisibility(View.VISIBLE);
        }else {
            theme_switch.setVisibility(View.INVISIBLE);
        }

        if(debug_mode == true){
            debug_sweech.setChecked(true);
        }else {
            debug_sweech.setChecked(false);
        }

        if(fix_mode == true){
            lib_switch.setChecked(true);
        }else {
            lib_switch.setChecked(false);
        }

        if(theme_mode == true){
            theme_switch.setChecked(true);
        }else {
            theme_switch.setChecked(false);
        }
        if(rutube_mode == true){
            rutube_switch.setChecked(true);
        }else {
            rutube_switch.setChecked(false);
        }
        debug_text.setText("");

        //Переключатель debug режима
        debug_sweech.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                // при изменении состояния switch новое значение
                //будет сразу записано в соответствующее "поле" в Ваших SharedPreferences

                debug_sweech.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    if (debug_mode == false) {
                        editor.putBoolean("debug_mode", true);
                        editor.apply();

                    } else {
                        editor.putBoolean("debug_mode", false);
                        editor.apply();
                    }

            }
        });

        //Выбор библиотеки pytube или pytubefix
        lib_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                lib_switch.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                boolean rutube_mode = pref.getBoolean("rutube_mode",false);
                if (fix_mode == false) {
                    fix_editor.putBoolean("fix_mode", true);
                    fix_editor.apply();


                } else {
                    fix_editor.putBoolean("fix_mode", false);
                    fix_editor.apply();
                }
                if(rutube_mode == true){
                    rutube_switch.setChecked(false);
                    rutube_editor.putBoolean("rutube_mode", false);
                    rutube_editor.apply();
                }

            }

        });

        theme_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                theme_switch.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                if (theme_mode == false) {
                    theme_editor.putBoolean("Dynamic_Colors", true);
                    theme_editor.apply();

                } else {
                    theme_editor.putBoolean("Dynamic_Colors", false);
                    theme_editor.apply();
                }
            }
        });

        rutube_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                rutube_switch.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                boolean fix_mode = pref.getBoolean("fix_mode",false);
                if (rutube_mode == false) {
                    rutube_editor.putBoolean("rutube_mode", true);
                    rutube_editor.apply();


                } else {
                    rutube_editor.putBoolean("rutube_mode", false);
                    rutube_editor.apply();
                    lib_switch.setChecked(false);
                }
                if(fix_mode == true){
                    lib_switch.setChecked(false);
                    fix_editor.putBoolean("fix_mode", false);
                    fix_editor.apply();
                }
            }
        });


        //Кнопка для перехода на github
        button_github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button_github.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/MrArtemIT/YouTube_downloader/tree/YouTube_downloader"));
                startActivity(browserIntent);
            }
        });
    }
}